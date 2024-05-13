package gr.sr.raidEngine.tasks;

import java.util.ArrayList;
import java.util.List;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.data.sql.NpcTable;
import l2r.gameserver.model.actor.instance.L2RaidBossInstance;
import l2r.util.Rnd;

import gr.sr.raidEngine.RaidGroup;
import gr.sr.raidEngine.manager.RaidManager;
import gr.sr.raidEngine.xml.dataHolder.RaidAndDropsHolder;
import gr.sr.raidEngine.xml.dataHolder.RaidLocationsHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task to control the raid spawn
 * @author vGodFather
 */
public class RaidSpawnTask implements Runnable
{
	protected static final Logger _log = LoggerFactory.getLogger(RaidSpawnTask.class);
	
	private L2RaidBossInstance _raid;
	
	public RaidSpawnTask(L2RaidBossInstance raid)
	{
		_raid = raid;
	}
	
	@Override
	public void run()
	{
		// Choose a random location to spawn the raid
		RaidManager.getInstance()._currentLocation = RaidLocationsHolder.getInstance().getLocations().get(Rnd.get(RaidLocationsHolder.getInstance().getLocations().size()));
		
		// Spawn the raid in the chosen location
		List<RaidGroup> raidGroups = RaidAndDropsHolder.getInstance().getRaidGroups();
		int raidGroupsSize = RaidAndDropsHolder.getInstance().getRaidGroups().size();
		// AutoRaid autoRaid = autoRaids.get(Rnd.get(autoRaidsSize));
		RaidGroup raidGroup = null;
		
		while (raidGroup == null)
		{
			double randomChance = Rnd.get() * 100;
			for (int i = 0; i < raidGroupsSize; i++)
			{
				raidGroup = raidGroups.get(i);
				if (raidGroup.getGroupChance() >= randomChance)
				{
					break;
				}
			}
		}
		
		int newRaidId = 0;
		List<Integer> raidIds = new ArrayList<>(raidGroup.getRaids().keySet());
		if (raidIds.size() <= 0)
		{
			RaidManager.getInstance().clearTasksAndVars();
			RaidManager.getInstance().setNextRaidSpawn();
			return;
		}
		while ((newRaidId == 0) || (newRaidId == RaidManager.getInstance()._lastRaidId))
		{
			newRaidId = raidIds.get(Rnd.get(raidIds.size()));
			if (raidIds.size() == 1)
			{
				break;
			}
		}
		
		_raid = new L2RaidBossInstance(NpcTable.getInstance().getTemplate(newRaidId));
		_raid.setHeading(0);
		_raid.setIsEventRaid(true);
		_raid.setTitle("Event Boss");
		_raid.spawnMe(RaidManager.getInstance()._currentLocation.getLocation().getX(), RaidManager.getInstance()._currentLocation.getLocation().getY(), RaidManager.getInstance()._currentLocation.getLocation().getZ());
		// _raid.addSkill(SkillData.getInstance().getInfo(4390, 1));
		
		switch (raidGroup.getTypeName())
		{
			case "Weak":
				break;
			case "Strong":
				// SkillData.getInstance().getInfo(40262, 1).getEffects(_raid, _raid);
				break;
			case "Super Strong":
				// SkillData.getInstance().getInfo(40262, 1).getEffects(_raid, _raid);
				// SkillData.getInstance().getInfo(1232, 330).getEffects(_raid, _raid);
				// SkillData.getInstance().getInfo(1500, 1).getEffects(_raid, _raid);
				break;
			default:
				break;
		}
		_raid.setCurrentHpMp(_raid.getMaxHp(), _raid.getMaxMp());
		RaidManager.getInstance()._raid = _raid;
		RaidManager.getInstance()._raidGroup = raidGroup;
		RaidManager.getInstance()._lastRaidId = newRaidId;
		
		long time = RaidManager.getInstance().configs.getDuration();
		double numSecs = (time / 1000) % 60;
		double countDown = ((time / 1000) - numSecs) / 60;
		int numMins = (int) Math.floor(countDown % 60);
		countDown = (countDown - numMins) / 60;
		int numHours = (int) Math.floor(countDown % 24);
		
		// Announce that the raid has spawned
		RaidManager.getInstance().announceToAllOnline(raidGroup.getTypeName() + " Raid Boss: " + _raid.getName() + " has spawned in " + RaidManager.getInstance()._currentLocation.getName() + " and will be there for the next " + numHours + " hours " + numMins + " mins! Open your map and use .findraid in order to find raid location.");
		
		// Log spawn
		_log.info(getClass().getSimpleName() + " : Special Raid(" + _raid.getName() + ") spawned in " + RaidManager.getInstance()._currentLocation.getName());
		
		// RaidManager.getInstance().sendRadarInfo(true);
		
		// Create a thread to repeat each 2 minutes to announce to all players that the raid is in x location
		if (RaidManager.getInstance().configs.getNotifyDelay() > 0)
		{
			RaidManager.getInstance()._notifyThread = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new RaidNotifyTask(_raid), RaidManager.getInstance().configs.getNotifyDelay(), RaidManager.getInstance().configs.getNotifyDelay());
		}
		
		// Create a thread to schedule the despawn of the raid if it was not killed by that time
		RaidManager.getInstance()._despawnThread = ThreadPoolManager.getInstance().scheduleGeneral(new RaidDespawnTask(_raid), RaidManager.getInstance().configs.getDuration());
	}
}