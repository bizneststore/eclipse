package gr.sr.raidEngine.tasks;

import l2r.gameserver.model.actor.instance.L2RaidBossInstance;

import gr.sr.raidEngine.manager.RaidManager;

/**
 * Task to despawn the raid when the max time expire
 * @author vGodFather
 */
public class RaidDespawnTask implements Runnable
{
	private final L2RaidBossInstance _raid;
	
	public RaidDespawnTask(L2RaidBossInstance raid)
	{
		_raid = raid;
	}
	
	@Override
	public void run()
	{
		if (_raid != null)
		{
			// Announce that the raid time has ended
			RaidManager.getInstance().announceToAllOnline(_raid.getName() + " wasn't killed in time and escaped. Wait for next Event Raid!");
			
			// Delete the raid and reset variables
			_raid.deleteMe();
		}
		
		// Clear variables and tasks
		RaidManager.getInstance().clearTasksAndVars();
		
		// Schedule the next raid spawn
		RaidManager.getInstance().setNextRaidSpawn();
	}
}