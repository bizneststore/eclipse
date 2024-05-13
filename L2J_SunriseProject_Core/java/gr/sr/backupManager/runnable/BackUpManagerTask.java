package gr.sr.backupManager.runnable;

import gr.sr.backupManager.DatabaseBackupManager;

/**
 * Class to back up data from database.
 * @author vGodFather
 */
public class BackUpManagerTask implements Runnable
{
	@Override
	public void run()
	{
		DatabaseBackupManager.makeBackup();
	}
}