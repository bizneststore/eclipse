package gr.sr.configsEngine.configs.impl;

import gr.sr.configsEngine.AbstractConfigs;

public class BackupManagerConfigs extends AbstractConfigs
{
	public static boolean ENABLE_DATABASE_BACKUP_MANAGER;
	public static boolean DATABASE_BACKUP_SCHEDULER;
	public static int DATABASE_BACKUP_START_DELAY;
	public static int DATABASE_BACKUP_DELAY_BETWEEN_BACK_UPS;
	public static boolean DATABASE_BACKUP_MAKE_BACKUP_ON_STARTUP;
	public static boolean DATABASE_BACKUP_MAKE_BACKUP_ON_SHUTDOWN;
	public static String DATABASE_BACKUP_DATABASE_NAME;
	public static String DATABASE_BACKUP_SAVE_PATH;
	public static boolean DATABASE_BACKUP_COMPRESSION;
	public static String DATABASE_BACKUP_MYSQLDUMP_PATH;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/BackupManager.ini");
		
		ENABLE_DATABASE_BACKUP_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableDatabaseBackupManager", "False"));
		DATABASE_BACKUP_SCHEDULER = Boolean.parseBoolean(getString(_settings, _override, "DatabaseBackupScheduler", "False"));
		DATABASE_BACKUP_START_DELAY = Integer.parseInt(getString(_settings, _override, "DatabaseBackupStartDelay", "5"));
		DATABASE_BACKUP_DELAY_BETWEEN_BACK_UPS = Integer.parseInt(getString(_settings, _override, "DelayBetweenBackups", "120"));
		DATABASE_BACKUP_MAKE_BACKUP_ON_STARTUP = Boolean.parseBoolean(getString(_settings, _override, "DatabaseBackupMakeBackupOnStartup", "False"));
		DATABASE_BACKUP_MAKE_BACKUP_ON_SHUTDOWN = Boolean.parseBoolean(getString(_settings, _override, "DatabaseBackupMakeBackupOnShutdown", "False"));
		DATABASE_BACKUP_DATABASE_NAME = getString(_settings, _override, "DatabaseBackupDatabaseName", "l2jsunrisegs");
		DATABASE_BACKUP_SAVE_PATH = getString(_settings, _override, "DatabaseBackupSavePath", "./backup/database/");
		DATABASE_BACKUP_COMPRESSION = Boolean.parseBoolean(getString(_settings, _override, "DatabaseBackupCompression", "False"));
		DATABASE_BACKUP_MYSQLDUMP_PATH = getString(_settings, _override, "DatabaseBackupMysqldumpPath", "C:/Program Files/MySQL/MySQL Server 5.5/bin");
	}
	
	public static BackupManagerConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final BackupManagerConfigs _instance = new BackupManagerConfigs();
	}
}