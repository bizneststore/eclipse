package gr.sr.player;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import l2r.gameserver.data.xml.impl.TransformData;
import l2r.gameserver.enums.Race;
import l2r.gameserver.enums.Team;
import l2r.gameserver.enums.ZoneIdType;
import l2r.gameserver.model.L2Party;
import l2r.gameserver.model.Location;
import l2r.gameserver.model.actor.L2Summon;
import l2r.gameserver.model.actor.appearance.PcAppearance;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.actor.transform.Transform;
import l2r.gameserver.model.base.ClassId;
import l2r.gameserver.model.effects.AbnormalEffect;
import l2r.gameserver.model.itemcontainer.Inventory;
import l2r.gameserver.network.L2GameClient;
import l2r.gameserver.network.serverpackets.L2GameServerPacket;
import l2r.log.filter.Log;

/**
 * @author vGodFather
 */
public class PlayerData
{
	public Future<?> _exitZoneTask = null;
	public Future<?> _fireZoneDeflagTask = null;
	public ScheduledFuture<?> _antiFeedRemoveTask = null;
	
	private PlayerData _previousVictim;
	
	protected final L2PcInstance _owner;
	
	public PlayerData(L2PcInstance owner)
	{
		_owner = owner;
		_previousVictim = this;
	}
	
	public L2PcInstance getPlayer()
	{
		return _owner;
	}
	
	public int getObjectId()
	{
		return _owner.getObjectId();
	}
	
	public int getLevel()
	{
		return _owner.getLevel();
	}
	
	public String getAccountName()
	{
		return _owner.getAccountName();
	}
	
	public void setVisibleTitle(String title)
	{
		_owner.getAppearance().setVisibleTitle(title);
	}
	
	public void restoreTitle()
	{
		_owner.getAppearance().setVisibleTitle(_owner.getTitle());
	}
	
	public Team getTeam()
	{
		return _owner.getTeam();
	}
	
	public void setTeam(Team team)
	{
		_owner.setTeam(team);
	}
	
	public boolean isOnline()
	{
		return _owner.isOnline();
	}
	
	public boolean isDead()
	{
		return _owner.isDead();
	}
	
	public ClassId getClassId()
	{
		return _owner.getClassId();
	}
	
	public void setPvpFlag(int flag)
	{
		_owner.setPvpFlag(flag);
	}
	
	public void startFlag()
	{
		_owner.startFlag();
	}
	
	public void sendMessage(String message)
	{
		_owner.sendMessage(message);
	}
	
	public void sendMessageS(String text, int timeonscreenins)
	{
		_owner.sendMessageS(text, timeonscreenins);
	}
	
	public void teleToLocation(Location location)
	{
		Log.info("", new IllegalArgumentException());
		_owner.teleToLocation(location);
	}
	
	public boolean hasSummon()
	{
		return _owner.hasSummon();
	}
	
	public L2Summon getSummon()
	{
		return _owner.getSummon();
	}
	
	public void removeSummons()
	{
		if (_owner.hasSummon())
		{
			getSummon().unSummon(_owner);
		}
	}
	
	public void setInsideZone(ZoneIdType zone, boolean add)
	{
		_owner.setInsideZone(zone, add);
	}
	
	public Inventory getInventory()
	{
		return _owner.getInventory();
	}
	
	public void stopPvpRegTask()
	{
		_owner.stopPvpRegTask();
	}
	
	public void setVar(String value, String key)
	{
		_owner.setVar(value, key);
	}
	
	public boolean isHero()
	{
		return _owner.isHero();
	}
	
	public void setHero(boolean hero)
	{
		_owner.setHero(hero);
	}
	
	public String getName()
	{
		return _owner.getName();
	}
	
	public void broadcastInfo()
	{
		_owner.broadcastInfo();
	}
	
	public void removeReviving()
	{
		_owner.removeReviving();
	}
	
	public void doRevive()
	{
		_owner.doRevive();
	}
	
	public void addItem(String string, int itemId, long itemCount, PlayerData reference, boolean sendMessage)
	{
		_owner.addItem(string, itemId, itemCount, reference.getPlayer(), sendMessage);
	}
	
	public void setIsParalyzed(boolean start)
	{
		_owner.setIsParalyzed(start);
	}
	
	public void setIsInvul(boolean start)
	{
		_owner.setIsInvul(start);
	}
	
	public void stopAbnormalEffect(AbnormalEffect effect)
	{
		_owner.stopAbnormalEffect(effect);
	}
	
	public void startAbnormalEffect(AbnormalEffect effect)
	{
		_owner.startAbnormalEffect(effect);
	}
	
	public void teleToLocation(int x, int y, int z)
	{
		_owner.teleToLocation(x, y, z);
	}
	
	public boolean isPlayer()
	{
		return _owner.isPlayer();
	}
	
	public L2Party getParty()
	{
		return _owner.getParty();
	}
	
	public void leaveParty()
	{
		_owner.leaveParty();
	}
	
	public Race getRace()
	{
		return _owner.getRace();
	}
	
	public PcAppearance getAppearance()
	{
		return _owner.getAppearance();
	}
	
	public void startAntifeedProtection(boolean start)
	{
		_owner.startAntifeedProtection(start);
	}
	
	public void untransform()
	{
		_owner.untransform();
	}
	
	public int getMaxHp()
	{
		return _owner.getMaxHp();
	}
	
	public int getMaxCp()
	{
		return _owner.getMaxCp();
	}
	
	public int getMaxMp()
	{
		return _owner.getMaxMp();
	}
	
	public void setCurrentHpMp(int maxHp, int maxMp)
	{
		_owner.setCurrentHpMp(maxHp, maxMp);
	}
	
	public void setCurrentCp(int maxCp)
	{
		_owner.setCurrentCp(maxCp);
	}
	
	public boolean isPremium()
	{
		return _owner.isPremium();
	}
	
	public PlayerData getPreviousVictim()
	{
		return _previousVictim;
	}
	
	public void setPreviousVictim(PlayerData previousVictim)
	{
		_previousVictim = previousVictim;
	}
	
	public int getQuickVarI(String name, int defaultValue)
	{
		return _owner.getQuickVarI(name, defaultValue);
	}
	
	public void setQuickVar(String name, int defaultValue)
	{
		_owner.setQuickVar(name, defaultValue);
	}
	
	public boolean getVarB(String name, boolean defaultValue)
	{
		return _owner.getVarB(name, defaultValue);
	}
	
	public void unsetVar(String name)
	{
		_owner.unsetVar(name);
	}
	
	public L2GameClient getClient()
	{
		return _owner.getClient();
	}
	
	public void broadcastPacket(L2GameServerPacket packet)
	{
		_owner.broadcastPacket(packet);
	}
	
	public void broadcastUserInfo()
	{
		_owner.broadcastUserInfo();
	}
	
	public void sendPacket(L2GameServerPacket packer)
	{
		_owner.sendPacket(packer);
	}
	
	public L2PcInstance getActingPlayer()
	{
		return _owner.getActingPlayer();
	}
	
	public boolean isFriend(PlayerData target)
	{
		return _owner.isFriend(target.getActingPlayer());
	}
	
	public boolean isInsideRadius(PlayerData player, int range, boolean square, boolean zaxis)
	{
		return _owner.isInsideRadius(player.getPlayer(), range, square, zaxis);
	}
	
	public boolean isGM()
	{
		return _owner.isGM();
	}
	
	public int getPvpFlag()
	{
		return _owner.getPvpFlag();
	}
	
	public int getTransformationId()
	{
		return _owner.getTransformationId();
	}
	
	public void transform(int id)
	{
		final Transform transform = TransformData.getInstance().getTransform(id);
		if (transform != null)
		{
			_owner.transform(transform);
		}
	}
	
	public void unTransform(int id)
	{
		_owner.untransform();
	}
}
