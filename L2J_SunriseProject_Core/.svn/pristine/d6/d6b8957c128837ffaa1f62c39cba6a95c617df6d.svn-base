package gr.sr.configsEngine.configs.impl;

import gr.sr.configsEngine.AbstractConfigs;

public class SmartCommunityConfigs extends AbstractConfigs
{
	public static int TOP_CURRENCY_ID;
	public static int TOP_PLAYER_RESULTS;
	public static int RAID_LIST_ROW_HEIGHT;
	public static int RAID_LIST_RESULTS;
	public static int EXTRA_PLAYERS_COUNT;
	public static boolean RAID_LIST_SORT_ASC;
	public static boolean ALLOW_REAL_ONLINE_STATS;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/SmartCB.ini");
		
		TOP_CURRENCY_ID = Integer.parseInt(getString(_settings, _override, "TopCurrencyId", "57"));
		TOP_PLAYER_RESULTS = Integer.parseInt(getString(_settings, _override, "TopPlayerResults", "20"));
		RAID_LIST_ROW_HEIGHT = Integer.parseInt(getString(_settings, _override, "RaidListRowHeight", "18"));
		RAID_LIST_RESULTS = Integer.parseInt(getString(_settings, _override, "RaidListResults", "20"));
		RAID_LIST_SORT_ASC = Boolean.parseBoolean(getString(_settings, _override, "RaidListSortAsc", "True"));
		EXTRA_PLAYERS_COUNT = Integer.parseInt(getString(_settings, _override, "ExtraPlayersCount", "20"));
		
		ALLOW_REAL_ONLINE_STATS = Boolean.parseBoolean(getString(_settings, _override, "AllowRealOnlineStats", "True"));
	}
	
	public static SmartCommunityConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final SmartCommunityConfigs _instance = new SmartCommunityConfigs();
	}
}