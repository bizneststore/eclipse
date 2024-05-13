package gr.sr.main;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.handler.VoicedCommandHandler;
import l2r.gameserver.instancemanager.BonusExpManager;

import gr.sr.achievementEngine.AchievementsManager;
import gr.sr.advancedBuffer.AdvancedBufferLoader;
import gr.sr.backupManager.DatabaseBackupManager;
import gr.sr.backupManager.runnable.BackUpManagerTask;
import gr.sr.configsEngine.configs.impl.AioItemsConfigs;
import gr.sr.configsEngine.configs.impl.AntibotConfigs;
import gr.sr.configsEngine.configs.impl.BackupManagerConfigs;
import gr.sr.configsEngine.configs.impl.CustomServerConfigs;
import gr.sr.configsEngine.configs.impl.LeaderboardsConfigs;
import gr.sr.configsEngine.configs.impl.PcBangConfigs;
import gr.sr.configsEngine.configs.impl.PremiumServiceConfigs;
import gr.sr.configsEngine.configs.impl.SecuritySystemConfigs;
import gr.sr.imageGeneratorEngine.GlobalImagesCache;
import gr.sr.imageGeneratorEngine.ImagesCache;
import gr.sr.leaderboards.ArenaLeaderboard;
import gr.sr.leaderboards.CraftLeaderboard;
import gr.sr.leaderboards.FishermanLeaderboard;
import gr.sr.leaderboards.TvTLeaderboard;
import gr.sr.mods.autorestart.Restart;
import gr.sr.premiumEngine.runnable.PremiumChecker;
import gr.sr.raidEngine.manager.RaidManager;
import gr.sr.voteEngine.RewardVote;
import gr.sr.voteEngine.VoteDataConfigs;
import gr.sr.voteEngine.xml.VoteEngineParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SunriseServerMods
{
	private final static Logger _log = LoggerFactory.getLogger(SunriseServerMods.class);
	
	public SunriseServerMods()
	{
		// Dummy default
	}
	
	public void checkSunriseMods()
	{
		AdvancedBufferLoader.getInstance();
		
		VoteEngineParser.getInstance().load();
		if (VoteDataConfigs.ENABLE_VOTE_SYSTEM)
		{
			VoicedCommandHandler.getInstance().registerHandler(new RewardVote());
			_log.info("Vote System: Enabled.");
		}
		
		if (BackupManagerConfigs.ENABLE_DATABASE_BACKUP_MANAGER)
		{
			if (BackupManagerConfigs.DATABASE_BACKUP_SCHEDULER)
			{
				ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new BackUpManagerTask(), BackupManagerConfigs.DATABASE_BACKUP_START_DELAY * 1000 * 60, BackupManagerConfigs.DATABASE_BACKUP_DELAY_BETWEEN_BACK_UPS * 1000 * 60);
			}
			else if (BackupManagerConfigs.DATABASE_BACKUP_MAKE_BACKUP_ON_STARTUP)
			{
				DatabaseBackupManager.makeBackup();
			}
			_log.info("Backup Manager: Enabled.");
		}
		
		if (PremiumServiceConfigs.USE_PREMIUM_SERVICE)
		{
			_log.info("Premium System: Enabled.");
			ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new PremiumChecker(), 60 * 60 * 1000, 60 * 60 * 1000);
		}
		
		if (AioItemsConfigs.GIVEANDCHECK_ATSTARTUP && AioItemsConfigs.ENABLE_AIO_NPCS)
		{
			_log.info("Aio Item On Login: Enabled.");
		}
		
		if (PcBangConfigs.PC_BANG_ENABLED)
		{
			_log.info("Pc Bang Points: Enabled.");
		}
		
		if (SecuritySystemConfigs.ENABLE_SECURITY_SYSTEM)
		{
			_log.info("Security System: Enabled.");
		}
		
		if (CustomServerConfigs.ALTERNATE_PAYMODE_CLANHALLS || CustomServerConfigs.ALTERNATE_PAYMODE_MAILS || CustomServerConfigs.ALTERNATE_PAYMODE_SHOPS)
		{
			_log.info("Alternative Payments: Enabled.");
		}
		
		if (CustomServerConfigs.EXTRA_MESSAGES)
		{
			_log.info("Extra Messages: Enabled.");
		}
		
		if (CustomServerConfigs.ANNOUNCE_HEROS_ON_LOGIN)
		{
			_log.info("Announce Heros: Enabled.");
		}
		
		if (CustomServerConfigs.ANNOUNCE_DEATH_REVIVE_OF_RAIDS)
		{
			_log.info("Announce Raid's: Enabled.");
		}
		
		if (CustomServerConfigs.ALLOW_ONLINE_COMMAND)
		{
			_log.info("Online Command: Enabled.");
		}
		
		if (CustomServerConfigs.ALLOW_REPAIR_COMMAND)
		{
			_log.info("Repair Command: Enabled.");
		}
		
		if (CustomServerConfigs.ALLOW_EXP_GAIN_COMMAND)
		{
			_log.info("Experience Command: Enabled.");
		}
		
		if (CustomServerConfigs.ALLOW_TELEPORTS_COMMAND)
		{
			_log.info("Teleport Command System: Enabled.");
		}
		
		if (CustomServerConfigs.GIVE_HELLBOUND_MAP)
		{
			_log.info("Give HellBound Map: Enabled.");
		}
		
		if (CustomServerConfigs.TOP_LISTS_RELOAD_TIME > 0)
		{
			_log.info("Top Lists Updater: Enabled.");
			ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new TopListsUpdater(), 30 * 1000, CustomServerConfigs.TOP_LISTS_RELOAD_TIME * 60 * 1000);
		}
		
		if (CustomServerConfigs.AUTO_ACTIVATE_SHOTS)
		{
			_log.info("Auto Soul Shots: Enabled.");
		}
		
		if (CustomServerConfigs.PVP_SPREE_SYSTEM)
		{
			_log.info("Pvp Spree System: Enabled.");
		}
		
		if (CustomServerConfigs.ALT_ALLOW_CLAN_LEADER_NAME)
		{
			_log.info("Clan Leader Color Name: Enabled.");
		}
		
		if (CustomServerConfigs.ANNOUNCE_CASTLE_LORDS)
		{
			_log.info("Announce Castle Lords: Enabled.");
		}
		
		if (CustomServerConfigs.ALT_ALLOW_REFINE_PVP_ITEM)
		{
			_log.info("Allow Augment For PvP Items:: Enabled.");
		}
		
		if (CustomServerConfigs.ALT_ALLOW_REFINE_HERO_ITEM)
		{
			_log.info("Allow Augment For Hero Items:: Enabled.");
		}
		
		if (CustomServerConfigs.EVENLY_DISTRIBUTED_ITEMS)
		{
			_log.info("Distribute Party Items: Enabled.");
		}
		
		if (CustomServerConfigs.ENABLE_SKILL_MAX_ENCHANT_LIMIT)
		{
			_log.info("Skills Max Enchant Limit: Enabled.");
		}
		
		if (CustomServerConfigs.ENABLE_CHARACTER_CONTROL_PANEL)
		{
			_log.info("Character Control Panel: Enabled.");
		}
		
		if (CustomServerConfigs.ENABLE_STARTING_TITLE)
		{
			_log.info("Starting Title: Enabled.");
		}
		
		Restart.getInstance();
		
		AchievementsManager.getInstance().loadAchievements();
		BonusExpManager.getInstance();
		TopListsLoader.getInstance();
		ImagesCache.getInstance();
		GlobalImagesCache.getInstance();
		checkLeaderboardsMod();
		checkAntibotMod();
		RaidManager.getInstance();
	}
	
	public void checkAntibotMod()
	{
		if (AntibotConfigs.ENABLE_ANTIBOT_SYSTEMS)
		{
			_log.info("Antibot Engine: Enabled.");
		}
		else
		{
			_log.info("Antibot Engine: Disabled.");
		}
		
		if (AntibotConfigs.ENABLE_ANTIBOT_FARM_SYSTEM)
		{
			_log.info("Antibot Farm Engine: Enabled.");
		}
		else
		{
			_log.info("Antibot Farm Engine: Disabled.");
		}
		
		if (AntibotConfigs.ENABLE_ANTIBOT_ENCHANT_SYSTEM)
		{
			_log.info("Antibot Enchant Engine: Enabled.");
		}
		else
		{
			_log.info("Antibot Enchant Engine: Disabled.");
		}
	}
	
	public void checkLeaderboardsMod()
	{
		if (LeaderboardsConfigs.ENABLE_LEADERBOARD)
		{
			_log.info("LeaderBoards: Enabled.");
			
			if (LeaderboardsConfigs.RANK_ARENA_ENABLED)
			{
				ArenaLeaderboard.getInstance();
			}
			else
			{
				_log.info("ArenaLeaderboard: Disabled.");
			}
			
			if (LeaderboardsConfigs.RANK_FISHERMAN_ENABLED)
			{
				FishermanLeaderboard.getInstance();
			}
			else
			{
				_log.info("FishermanLeaderboard: Disabled.");
			}
			
			if (LeaderboardsConfigs.RANK_CRAFT_ENABLED)
			{
				CraftLeaderboard.getInstance();
			}
			else
			{
				_log.info("CraftLeaderboard: Disabled.");
			}
			
			if (LeaderboardsConfigs.RANK_TVT_ENABLED)
			{
				TvTLeaderboard.getInstance();
			}
			else
			{
				_log.info("TvTLeaderboard: Disabled.");
			}
		}
		else
		{
			_log.info("LeaderBoards: Disabled.");
		}
	}
	
	public static SunriseServerMods getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final SunriseServerMods _instance = new SunriseServerMods();
	}
}