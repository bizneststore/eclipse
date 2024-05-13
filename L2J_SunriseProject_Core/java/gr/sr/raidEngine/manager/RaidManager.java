package gr.sr.raidEngine.manager;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

import l2r.Config;
import l2r.gameserver.GameTimeController;
import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.enums.CtrlIntention;
import l2r.gameserver.model.L2World;
import l2r.gameserver.model.Location;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.actor.instance.L2RaidBossInstance;
import l2r.gameserver.network.SystemMessageId;
import l2r.gameserver.network.clientpackets.Say2;
import l2r.gameserver.network.serverpackets.CreatureSay;
import l2r.gameserver.network.serverpackets.MagicSkillUse;
import l2r.gameserver.network.serverpackets.SetupGauge;
import l2r.gameserver.network.serverpackets.SystemMessage;
import l2r.gameserver.util.Broadcast;
import l2r.gameserver.util.Util;
import l2r.util.Rnd;

import gr.sr.raidEngine.RaidConfigs;
import gr.sr.raidEngine.RaidDrop;
import gr.sr.raidEngine.RaidGroup;
import gr.sr.raidEngine.RaidLocation;
import gr.sr.raidEngine.tasks.RaidSpawnTask;
import gr.sr.raidEngine.tasks.TeleportBack;
import gr.sr.raidEngine.xml.dataHolder.RaidConfigsHolder;
import gr.sr.raidEngine.xml.dataParser.RaidAndDropsParser;
import gr.sr.raidEngine.xml.dataParser.RaidConfigsParser;
import gr.sr.raidEngine.xml.dataParser.RaidLocationsParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manager to handle all the automatic raids
 * @author vGodFather
 */
public class RaidManager
{
	private static Logger _log = LoggerFactory.getLogger(RaidManager.class);
	
	// Configs
	public RaidConfigs configs;
	
	// Drops configuration
	private static boolean USE_DEEP_BLUE_ON_RAIDS = false;
	private static int MAX_PARTY_MEMBER_LVL_DIFF = 5;
	private static int MAX_LEVLE_DIFF_FOR_RAID_DROP = 8;
	
	// Misc
	public L2RaidBossInstance _raid = null;
	public RaidLocation _currentLocation = null;
	public Future<?> _notifyThread = null;
	public Future<?> _despawnThread = null;
	public Future<?> _spawnThread = null;
	public int _lastRaidId = 0;
	public RaidGroup _raidGroup = null;
	private final int MAX_DRIFT_RANGE = 2000;
	
	public RaidManager()
	{
		// Initializing
		RaidConfigsParser.getInstance().load();
		RaidAndDropsParser.getInstance().load();
		RaidLocationsParser.getInstance().load();
		
		// Load random configs for next spawn ;)
		configs = RaidConfigsHolder.getInstance().getConfigs().get(Rnd.get(RaidConfigsHolder.getInstance().getConfigs().size()));
		
		// Schedule next raid spawn
		if (configs.isEnabled())
		{
			setNextRaidSpawn();
		}
		else
		{
			_log.info(RaidManager.class.getSimpleName() + ": Disabled.");
		}
	}
	
	public void reload()
	{
		clearTasksAndVars();
		
		RaidConfigsParser.getInstance().reload();
		RaidAndDropsParser.getInstance().reload();
		RaidLocationsParser.getInstance().reload();
		
		// Load random configs for next spawn ;)
		configs = RaidConfigsHolder.getInstance().getConfigs().get(Rnd.get(RaidConfigsHolder.getInstance().getConfigs().size()));
		
		// Schedule next raid spawn
		if (configs.isEnabled())
		{
			setNextRaidSpawn();
		}
		else
		{
			_log.info(RaidManager.class.getSimpleName() + ": Disabled.");
		}
	}
	
	/**
	 * Calculates the time for the next spawn and schedules it
	 */
	public void setNextRaidSpawn()
	{
		final long currentTime = System.currentTimeMillis();
		final Calendar spawnTime = Calendar.getInstance();
		spawnTime.set(Calendar.HOUR_OF_DAY, configs.getHour());
		spawnTime.set(Calendar.MINUTE, configs.getMinutes() + Rnd.get(configs.getRandomMins()));
		spawnTime.set(Calendar.DAY_OF_MONTH, configs.getDay());
		
		if ((configs.getDay() > 0) && !configs.isDaily())
		{
			// if we already pass date of this month set next month
			while (spawnTime.getTimeInMillis() <= currentTime)
			{
				spawnTime.add(Calendar.MONTH, 1);
				spawnTime.set(Calendar.DAY_OF_MONTH, configs.getDay());
			}
		}
		
		if ((configs.getDay() <= 0) && configs.isDaily())
		{
			// if we already pass this day and is daily set next day
			while (spawnTime.getTimeInMillis() <= currentTime)
			{
				spawnTime.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		
		long time = spawnTime.getTimeInMillis() - currentTime;
		double numSecs = (time / 1000) % 60;
		double countDown = ((time / 1000) - numSecs) / 60;
		int numMins = (int) Math.floor(countDown % 60);
		countDown = (countDown - numMins) / 60;
		int numHours = (int) Math.floor(countDown % 24);
		int numDays = (int) Math.floor((countDown - numHours) / 24);
		
		_log.info(getClass().getSimpleName() + ": Next Raid In: " + numDays + " day(s) " + numHours + " hour(s) and " + numMins + " min(s).");
		
		_spawnThread = ThreadPoolManager.getInstance().scheduleGeneral(new RaidSpawnTask(_raid), time);
	}
	
	/**
	 * Called when a monster dies. If its the event raid, then announce the death, stop threads and set a new spawn task
	 * @param raid
	 * @param player
	 */
	public void onRaidDeath(L2RaidBossInstance raid, L2PcInstance player)
	{
		if ((_raidGroup == null) || (raid == null) || (_raid == null) || (raid.getObjectId() != _raid.getObjectId()))
		{
			return;
		}
		
		// Announce that the raid has died
		announceToAllOnline(_raid.getName() + " has fallen in the hands of powerful warriors. Wait for next Event Boss spawn!");
		
		// Disable radar
		sendRadarInfo(false);
		
		// Check for rewards
		checkRewards(raid, player);
		
		// Clear variables and tasks
		clearTasksAndVars();
		
		// Load random configs for next spawn ;)
		configs = RaidConfigsHolder.getInstance().getConfigs().get(Rnd.get(RaidConfigsHolder.getInstance().getConfigs().size()));
		
		// Schedule the next raid spawn
		setNextRaidSpawn();
	}
	
	private void checkRewards(L2RaidBossInstance monster, L2PcInstance player)
	{
		final List<L2PcInstance> rewarded = new LinkedList<>();
		for (RaidDrop di : _raidGroup.getRandomDrops(monster.getId()))
		{
			int dropCount = Rnd.get(di.getMin(), di.getMax());
			if (player.isInParty())
			{
				final List<L2PcInstance> toReward = new LinkedList<>();
				player.getParty().getMembers().stream().filter(member -> Util.checkIfInRange(1400, monster, member, true)).forEach(member -> toReward.add(member));
				
				if (!toReward.isEmpty())
				{
					// Now we can actually distribute the item count for reward
					// (Total item count split by the number of party members that are in range and must be rewarded)
					long count = dropCount / toReward.size();
					rewarded.clear();
					
					for (L2PcInstance member : toReward)
					{
						if (rewarded.contains(member))
						{
							continue;
						}
						rewardPlayer(member, monster, di.getItemId(), count >= 1 ? count : 1, di.getChance());
						rewarded.add(member);
						if (count < 1)
						{
							break;
						}
					}
				}
			}
			else
			{
				rewardPlayer(player, monster, di.getItemId(), dropCount, di.getChance());
			}
		}
	}
	
	private void rewardPlayer(L2PcInstance player, L2RaidBossInstance npc, int itemId, long itemCount, float chance)
	{
		float dropChance = chance;
		int levelDiff = 0;
		boolean giveReward = true;
		
		if (player.getParty() == null)
		{
			if ((npc.getLevel() < player.getLevel()) && ((player.getLevel() - npc.getLevel()) > MAX_LEVLE_DIFF_FOR_RAID_DROP))
			{
				return;
			}
		}
		else
		{
			for (L2PcInstance member : player.getParty().getMembers())
			{
				if ((npc.getLevel() < member.getLevel()) && ((member.getLevel() - npc.getLevel()) > MAX_LEVLE_DIFF_FOR_RAID_DROP))
				{
					return;
				}
			}
		}
		
		if (USE_DEEP_BLUE_ON_RAIDS)
		{
			if (player.isInParty())
			{
				int tempLvlDiff = 0;
				int levelMod = 0;
				for (L2PcInstance member : player.getParty().getMembers())
				{
					tempLvlDiff = calculateLevelModifierForDrop(npc, member);
					levelMod = player.getLevel() - member.getLevel();
					levelMod = levelMod < 0 ? (levelMod * -1) : levelMod;
					giveReward = levelMod < MAX_PARTY_MEMBER_LVL_DIFF;
					
					if (levelDiff < tempLvlDiff)
					{
						levelDiff = tempLvlDiff;
					}
				}
			}
			else
			{
				levelDiff = calculateLevelModifierForDrop(npc, player);
			}
			
			// Check if we should apply our maths so deep blue mobs will not drop that easy
			dropChance = ((dropChance - ((dropChance * levelDiff) / 100)));
		}
		
		if (giveReward && (Rnd.get(100) < dropChance))
		{
			if (Config.AUTO_LOOT_RAIDS)
			{
				player.addItem("drop", itemId, itemCount, player, true);
			}
			else
			{
				npc.dropItem(player, itemId, itemCount);
			}
			
			sendDropMessage(npc, itemId, itemCount);
		}
	}
	
	private int calculateLevelModifierForDrop(L2Character target, L2PcInstance lastAttacker)
	{
		if (USE_DEEP_BLUE_ON_RAIDS)
		{
			int highestLevel = lastAttacker.getLevel();
			
			// Check to prevent very high level player to nearly kill mob and let low level player do the last hit.
			if (!target.getAttackByList().isEmpty())
			{
				for (L2Character atkChar : target.getAttackByList())
				{
					if ((atkChar != null) && (atkChar.getLevel() > highestLevel))
					{
						highestLevel = atkChar.getLevel();
					}
				}
			}
			
			// According to official data (Prima), deep blue mobs are 9 or more levels below players
			if ((highestLevel - 9) >= target.getLevel())
			{
				return ((highestLevel - (target.getLevel() + 8)) * 9);
			}
		}
		return 0;
	}
	
	public void checkRaidAttack(L2PcInstance attacker, L2RaidBossInstance npc)
	{
		if ((_currentLocation != null) && (Util.calculateDistance(npc.getLocation2(), _currentLocation.getLocation(), true, false) > MAX_DRIFT_RANGE))
		{
			teleportRaidBack(npc, _currentLocation.getLocation());
		}
	}
	
	private void teleportRaidBack(L2RaidBossInstance character, Location loc)
	{
		if (!character.isPorting())
		{
			character.setIsPorting(true);
			character.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			character.setTarget(character);
			character.disableAllSkills();
			character.broadcastPacket(new MagicSkillUse(character, 1050, 1, 700, 0));
			character.sendPacket(new SetupGauge(SetupGauge.BLUE, 700));
			character.setSkillCast(ThreadPoolManager.getInstance().scheduleGeneral(new TeleportBack(character, loc), 700));
			character.forceIsCasting(10 + GameTimeController.getInstance().getGameTicks() + (700 / GameTimeController.MILLIS_IN_TICK));
		}
	}
	
	public void clearTasksAndVars()
	{
		_raid = null;
		_currentLocation = null;
		
		// Cancel spawn thread
		if (_spawnThread != null)
		{
			_spawnThread.cancel(true);
			_spawnThread = null;
		}
		
		// Cancel notify thread
		if (_notifyThread != null)
		{
			_notifyThread.cancel(true);
			_notifyThread = null;
		}
		
		// Cancel despawn thread
		if (_despawnThread != null)
		{
			_despawnThread.cancel(true);
			_despawnThread = null;
		}
	}
	
	private void sendDropMessage(L2RaidBossInstance raid, int itemId, long itemCount)
	{
		final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_DIED_DROPPED_S3_S2);
		sm.addCharName(raid);
		sm.addItemName(itemId);
		sm.addLong(itemCount);
		raid.broadcastPacket(sm);
	}
	
	public void sendRadarInfo(boolean enable)
	{
		int locx = _raid.getLocation().getX();
		int locy = _raid.getLocation().getY();
		int locz = _raid.getLocation().getZ();
		
		for (L2PcInstance player : L2World.getInstance().getPlayers())
		{
			if ((player != null) && player.isOnline() && (_raid != null) && (!_raid.getKnownList().getKnownPlayers().values().contains(player)))
			{
				// player.sendPacket(enable ? new RadarControl(0, 1, _currentLocation.getLocation().getX(), _currentLocation.getLocation().getY(), _currentLocation.getLocation().getZ()) : new RadarControl(2, 2, 0, 0, 0));
				if (enable)
				{
					player.getRadar().removeMarker(locx, locy, locz);
					player.getRadar().addMarker(locx, locy, locz);
				}
				else
				{
					player.getRadar().removeMarker(locx, locy, locz);
				}
			}
		}
	}
	
	public void announceToAllOnline(String message)
	{
		Broadcast.toAllOnlinePlayers(new CreatureSay(1, Say2.BATTLEFIELD, "Raid Manager", message));
	}
	
	public static RaidManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final RaidManager _instance = new RaidManager();
	}
}
