package gr.sr.configsEngine.configs.impl;

import java.util.ArrayList;
import java.util.List;

import gr.sr.configsEngine.AbstractConfigs;

public class AntibotConfigs extends AbstractConfigs
{
	public static boolean ENABLE_ANTIBOT_SYSTEMS;
	public static boolean ENABLE_DOUBLE_PROTECTION;
	public static boolean ENABLE_ANTIBOT_FOR_GMS;
	
	// Antibot Farm
	public static boolean ENABLE_ANTIBOT_FARM_SYSTEM;
	public static boolean ENABLE_ANTIBOT_FARM_SYSTEM_ON_RAIDS;
	public static int JAIL_TIMER;
	public static int TIME_TO_SPEND_IN_JAIL;
	public static int ANTIBOT_FARM_TYPE;
	public static float ANTIBOT_FARM_CHANCE;
	public static int ANTIBOT_MOB_COUNTER;
	public static boolean ENABLE_ANTIBOT_SPECIFIC_MOBS;
	public static List<Integer> ANTIBOT_FARM_MOBS_IDS;
	
	// Antibot Enchant
	public static boolean ENABLE_ANTIBOT_ENCHANT_SYSTEM;
	public static int ENCHANT_CHANCE_TIMER;
	public static int ENCHANT_CHANCE_PERCENT_TO_START;
	public static int ENCHANT_CHANCE_PERCENT_TO_LOW;
	public static int ANTIBOT_ENCHANT_TYPE;
	public static int ANTIBOT_ENCHANT_COUNTER;
	public static int ANTIBOT_ENCHANT_CHANCE;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/AntiBot.ini");
		
		// general settings
		ENABLE_ANTIBOT_SYSTEMS = Boolean.parseBoolean(getString(_settings, _override, "EnableAntibotSystems", "False"));
		ENABLE_DOUBLE_PROTECTION = Boolean.parseBoolean(getString(_settings, _override, "EnableDoubleProtection", "True"));
		ENABLE_ANTIBOT_FOR_GMS = Boolean.parseBoolean(getString(_settings, _override, "EnableAntibotForGms", "False"));
		
		// antibot farm settings
		ENABLE_ANTIBOT_FARM_SYSTEM = Boolean.parseBoolean(getString(_settings, _override, "EnableAntibotFarmSystem", "False"));
		ENABLE_ANTIBOT_FARM_SYSTEM_ON_RAIDS = Boolean.parseBoolean(getString(_settings, _override, "EnableAntibotFarmSystemOnRaids", "True"));
		JAIL_TIMER = Integer.parseInt(getString(_settings, _override, "JailTimer", "180"));
		TIME_TO_SPEND_IN_JAIL = Integer.parseInt(getString(_settings, _override, "TimeToSpendInJail", "180"));
		ENABLE_ANTIBOT_SPECIFIC_MOBS = Boolean.parseBoolean(getString(_settings, _override, "EnableAntibotSpecificMobs", "False"));
		ANTIBOT_FARM_TYPE = Integer.parseInt(getString(_settings, _override, "AntibotFarmType", "0"));
		ANTIBOT_FARM_CHANCE = Float.parseFloat(getString(_settings, _override, "AntibotFarmChance", "50"));
		ANTIBOT_MOB_COUNTER = Integer.parseInt(getString(_settings, _override, "AntibotMobCounter", "100"));
		String[] mobIds = getString(_settings, _override, "FarmMobsIds", "").split(";");
		ANTIBOT_FARM_MOBS_IDS = new ArrayList<>(mobIds.length);
		for (String mobId : mobIds)
		{
			try
			{
				ANTIBOT_FARM_MOBS_IDS.add(Integer.parseInt(mobId.trim()));
			}
			catch (NumberFormatException e)
			{
				_log.info("Antibot System: Error parsing mob id. Skipping " + mobId + ".");
			}
		}
		
		// antibot enchant settings
		ENABLE_ANTIBOT_ENCHANT_SYSTEM = Boolean.parseBoolean(getString(_settings, _override, "EnableAntibotEnchantSystem", "False"));
		ENCHANT_CHANCE_TIMER = Integer.parseInt(getString(_settings, _override, "EnchantChanceTimer", "180"));
		ENCHANT_CHANCE_PERCENT_TO_START = Integer.parseInt(getString(_settings, _override, "EnchantChancePercentToStart", "80"));
		ENCHANT_CHANCE_PERCENT_TO_LOW = Integer.parseInt(getString(_settings, _override, "EnchantChancePercentToLow", "10"));
		ANTIBOT_ENCHANT_TYPE = Integer.parseInt(getString(_settings, _override, "AntibotEnchantType", "0"));
		ANTIBOT_ENCHANT_COUNTER = Integer.parseInt(getString(_settings, _override, "AntibotEnchantCounter", "100"));
		ANTIBOT_ENCHANT_CHANCE = Integer.parseInt(getString(_settings, _override, "AntibotEnchantChance", "100"));
	}
	
	public static AntibotConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final AntibotConfigs _instance = new AntibotConfigs();
	}
}