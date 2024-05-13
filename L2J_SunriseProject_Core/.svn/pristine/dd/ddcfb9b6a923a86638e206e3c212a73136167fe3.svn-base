package gr.sr.raidEngine.tasks;

import l2r.gameserver.model.actor.instance.L2RaidBossInstance;

import gr.sr.raidEngine.manager.RaidManager;

/**
 * Task to control the messages that are sent each 2 minutes to announce the raid status
 * @author vGodFather
 */
public class RaidNotifyTask implements Runnable
{
	private final L2RaidBossInstance _raid;
	
	public RaidNotifyTask(L2RaidBossInstance raid)
	{
		_raid = raid;
	}
	
	@Override
	public void run()
	{
		if ((_raid == null) || (RaidManager.getInstance()._currentLocation == null))
		{
			return;
		}
		
		RaidManager.getInstance().announceToAllOnline(_raid.getName() + " is in " + RaidManager.getInstance()._currentLocation.getName() + ", so come and kill it now! Open your map and use .findraid in order to find raid location.");
	}
}