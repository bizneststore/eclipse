package gr.sr.configsEngine.configs.impl;

import java.util.ArrayList;
import java.util.List;

import gr.sr.configsEngine.AbstractConfigs;

public class CustomNpcsConfigs extends AbstractConfigs
{
	// Custom Gatekeeper
	public static boolean ENABLE_CUSTOM_GATEKEEPER;
	public static boolean ALLOW_TELEPORT_DURING_SIEGE;
	public static boolean ALLOW_TELEPORT_WITH_KARMA;
	public static boolean ALLOW_TELEPORT_WHILE_COMBAT;
	public static boolean ENABLE_PLAYERS_COUNT;
	public static String ZONE_TYPE_FOR_PLAYERS_COUNT;
	// Noble Manager
	public static boolean ENABLE_NOBLE_MANAGER;
	public static int NOBLE_NPC_ID;
	public static int NOBLE_ITEM_ID;
	public static int NOBLE_ITEM_AMOUNT;
	public static int NOBLE_REQUIRED_LEVEL;
	// Casino Manager
	public static boolean ENABLE_CASINO_MANAGER;
	public static int CASINO_NPC_ID;
	public static int CASINO_ITEM_ID;
	public static int CASINO_BET1;
	public static int CASINO_BET2;
	public static int CASINO_BET3;
	public static int CASINO_REQUIRED_LEVEL;
	public static int CASINO_SUCCESS_CHANCE;
	// Points Manager
	public static boolean ENABLE_POINTS_MANAGER;
	public static int POINTS_NPC_ID;
	public static int POINTS_ITEM_ID_FOR_REP;
	public static int POINTS_ITEM_AMOUNT_FOR_REP;
	public static int POINTS_AMOUNT_FOR_REP;
	public static int POINTS_ITEM_ID_FOR_FAME;
	public static int POINTS_ITEM_AMOUNT_FOR_FAME;
	public static int POINTS_AMOUNT_FOR_FAME;
	// Delevel Manager
	public static boolean ENABLE_DELEVEL_MANAGER;
	public static int DELEVEL_NPC_ID;
	public static int DELEVEL_REQUIRED_LEVEL;
	public static boolean DELEVEL_DYNAMIC_PRICE;
	public static int DELEVEL_ITEM_ID;
	public static int DELEVEL_ITEM_AMOUNT;
	// Report Manager
	public static boolean ENABLE_REPORT_MANAGER;
	public static int REPORT_MANAGER_NPC_ID;
	public static int REPORT_REQUIRED_LEVEL;
	public static String REPORT_PATH;
	// Achievement Manager
	public static boolean ENABLE_ACHIEVEMENT_MANAGER;
	public static int ACHIEVEMENT_NPC_ID;
	public static int ACHIEVEMENT_REQUIRED_LEVEL;
	// Beta Manager
	public static boolean ENABLE_BETA_MANAGER;
	public static int BETA_NPC_ID;
	// Premium Manager
	public static boolean ENABLE_PREMIUM_MANAGER;
	public static int PREMIUM_NPC_ID;
	public static int PREMIUM_ITEM_ID;
	public static int PREMIUM_ITEM_AMOUNT_1;
	public static int PREMIUM_ITEM_AMOUNT_2;
	public static int PREMIUM_ITEM_AMOUNT_3;
	public static int PREMIUM_REQUIRED_LEVEL;
	// Castle Manager
	public static boolean ENABLE_CASTLE_MANAGER;
	public static int CASTLE_NPC_ID;
	public static int CASTLE_REQUIRED_LEVEL;
	// GrandBoss Manager
	public static boolean ENABLE_GRANDBOSS_MANAGER;
	public static int GRANDBOSS_NPC_ID;
	public static List<Integer> GRANDBOSS_LIST;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/Npcs.ini");
		
		// Custom Gatekeeper
		ENABLE_CUSTOM_GATEKEEPER = Boolean.parseBoolean(getString(_settings, _override, "EnableCustomGatekeeper", "False"));
		ALLOW_TELEPORT_DURING_SIEGE = Boolean.parseBoolean(getString(_settings, _override, "TeleportDuringSiege", "False"));
		ALLOW_TELEPORT_WITH_KARMA = Boolean.parseBoolean(getString(_settings, _override, "TeleportWithKarma", "False"));
		ALLOW_TELEPORT_WHILE_COMBAT = Boolean.parseBoolean(getString(_settings, _override, "TeleportWhileCombat", "False"));
		ENABLE_PLAYERS_COUNT = Boolean.parseBoolean(getString(_settings, _override, "EnablePlayersCount", "False"));
		ZONE_TYPE_FOR_PLAYERS_COUNT = getString(_settings, _override, "ZoneTypeForPlayersCount", "FlagZone");
		// Noble Manager
		ENABLE_NOBLE_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableNobleManager", "False"));
		NOBLE_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForNoblesseManager", "575"));
		NOBLE_ITEM_ID = Integer.parseInt(getString(_settings, _override, "ItemIDForNoble", "3470"));
		NOBLE_ITEM_AMOUNT = Integer.parseInt(getString(_settings, _override, "ItemAmountForNoble", "100"));
		NOBLE_REQUIRED_LEVEL = Integer.parseInt(getString(_settings, _override, "LevelForNoble", "76"));
		// Casino Manager
		ENABLE_CASINO_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableCasinoManager", "False"));
		CASINO_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForCasinoManager", "574"));
		CASINO_ITEM_ID = Integer.parseInt(getString(_settings, _override, "ItemIDForBet", "3480"));
		CASINO_BET1 = Integer.parseInt(getString(_settings, _override, "ItemAmountForBet1", "5"));
		CASINO_BET2 = Integer.parseInt(getString(_settings, _override, "ItemAmountForBet2", "10"));
		CASINO_BET3 = Integer.parseInt(getString(_settings, _override, "ItemAmountForBet3", "15"));
		CASINO_REQUIRED_LEVEL = Integer.parseInt(getString(_settings, _override, "LevelForCasino", "76"));
		CASINO_SUCCESS_CHANCE = Integer.parseInt(getString(_settings, _override, "SuccessChance", "40"));
		// Points Manager
		ENABLE_POINTS_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnablePointsManager", "False"));
		POINTS_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForPointsManager", "554"));
		POINTS_ITEM_ID_FOR_REP = Integer.parseInt(getString(_settings, _override, "ItemIdForRep", "3470"));
		POINTS_ITEM_AMOUNT_FOR_REP = Integer.parseInt(getString(_settings, _override, "ItemAmountForRep", "10"));
		POINTS_AMOUNT_FOR_REP = Integer.parseInt(getString(_settings, _override, "AmountOfRep", "10000"));
		POINTS_ITEM_ID_FOR_FAME = Integer.parseInt(getString(_settings, _override, "ItemIdForFame", "3470"));
		POINTS_ITEM_AMOUNT_FOR_FAME = Integer.parseInt(getString(_settings, _override, "ItemAmountForFame", "10"));
		POINTS_AMOUNT_FOR_FAME = Integer.parseInt(getString(_settings, _override, "AmountOfFame", "10000"));
		// Delevel Manager
		ENABLE_DELEVEL_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableDelevelManager", "False"));
		DELEVEL_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForDelevelManager", "560"));
		DELEVEL_REQUIRED_LEVEL = Integer.parseInt(getString(_settings, _override, "LevelForDelevel", "80"));
		DELEVEL_DYNAMIC_PRICE = Boolean.parseBoolean(getString(_settings, _override, "DynamicPrices", "False"));
		DELEVEL_ITEM_ID = Integer.parseInt(getString(_settings, _override, "ItemIDForDelevel", "3470"));
		DELEVEL_ITEM_AMOUNT = Integer.parseInt(getString(_settings, _override, "ItemAmountForDelevel", "1"));
		// Report Manager
		ENABLE_REPORT_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableReportManager", "False"));
		REPORT_MANAGER_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForReportManager", "553"));
		REPORT_REQUIRED_LEVEL = Integer.parseInt(getString(_settings, _override, "LevelForReport", "80"));
		REPORT_PATH = getString(_settings, _override, "ReportPath", "data/sunrise/BugReports/");
		// Achievement Manager
		ENABLE_ACHIEVEMENT_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableAchievementManager", "False"));
		ACHIEVEMENT_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForAchievementManager", "539"));
		ACHIEVEMENT_REQUIRED_LEVEL = Integer.parseInt(getString(_settings, _override, "LevelForAchievement", "80"));
		// Beta Manager
		ENABLE_BETA_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableBetaManager", "False"));
		BETA_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForBetaManager", "559"));
		// Premium Manager
		ENABLE_PREMIUM_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnablePremiumManager", "False"));
		PREMIUM_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForPremiumManager", "542"));
		PREMIUM_ITEM_ID = Integer.parseInt(getString(_settings, _override, "ItemIDForPremium", "3470"));
		PREMIUM_ITEM_AMOUNT_1 = Integer.parseInt(getString(_settings, _override, "ItemAmountForPremium1", "10"));
		PREMIUM_ITEM_AMOUNT_2 = Integer.parseInt(getString(_settings, _override, "ItemAmountForPremium2", "20"));
		PREMIUM_ITEM_AMOUNT_3 = Integer.parseInt(getString(_settings, _override, "ItemAmountForPremium3", "30"));
		PREMIUM_REQUIRED_LEVEL = Integer.parseInt(getString(_settings, _override, "LevelForPremium", "80"));
		// Castle Manager
		ENABLE_CASTLE_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableCastleManager", "False"));
		CASTLE_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForCastleManager", "541"));
		CASTLE_REQUIRED_LEVEL = Integer.parseInt(getString(_settings, _override, "LevelForCastleManager", "80"));
		
		// Grand boss Manager
		ENABLE_GRANDBOSS_MANAGER = Boolean.parseBoolean(getString(_settings, _override, "EnableGrandBossManager", "False"));
		GRANDBOSS_NPC_ID = Integer.parseInt(getString(_settings, _override, "NpcIdForGrandBossManager", "543"));
		
		String[] grandbossList = getString(_settings, _override, "GrandBossesList", "1204;1035;1048").trim().split(";");
		GRANDBOSS_LIST = new ArrayList<>(grandbossList.length);
		for (String grandbosId : grandbossList)
		{
			try
			{
				GRANDBOSS_LIST.add(Integer.parseInt(grandbosId));
			}
			catch (NumberFormatException nfe)
			{
				_log.warn(getClass() + ": Wrong Grand boss Id passed: " + grandbosId);
				_log.warn(nfe.getMessage());
			}
		}
	}
	
	public static CustomNpcsConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final CustomNpcsConfigs _instance = new CustomNpcsConfigs();
	}
}