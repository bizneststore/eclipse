package gr.sr.configsEngine.configs.impl;

import gr.sr.configsEngine.AbstractConfigs;

public class LeaderboardsConfigs extends AbstractConfigs
{
	public static boolean ENABLE_LEADERBOARD;
	public static boolean RANK_ARENA_ACCEPT_SAME_IP;
	public static boolean RANK_ARENA_ENABLED;
	public static int RANK_ARENA_INTERVAL;
	public static int RANK_ARENA_REWARD_ID;
	public static int RANK_ARENA_REWARD_COUNT;
	public static boolean RANK_FISHERMAN_ENABLED;
	public static int RANK_FISHERMAN_INTERVAL;
	public static int RANK_FISHERMAN_REWARD_ID;
	public static int RANK_FISHERMAN_REWARD_COUNT;
	public static boolean RANK_CRAFT_ENABLED;
	public static int RANK_CRAFT_INTERVAL;
	public static int RANK_CRAFT_REWARD_ID;
	public static int RANK_CRAFT_REWARD_COUNT;
	public static boolean RANK_TVT_ENABLED;
	public static int RANK_TVT_INTERVAL;
	public static int RANK_TVT_REWARD_ID;
	public static int RANK_TVT_REWARD_COUNT;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/Leaderboard.ini");
		
		ENABLE_LEADERBOARD = Boolean.parseBoolean(getString(_settings, _override, "Enableleaderboard", "true"));
		
		RANK_ARENA_ACCEPT_SAME_IP = Boolean.parseBoolean(getString(_settings, _override, "ArenaAcceptSameIP", "true"));
		
		RANK_ARENA_ENABLED = Boolean.parseBoolean(getString(_settings, _override, "RankArenaEnabled", "false"));
		RANK_ARENA_INTERVAL = Integer.parseInt(getString(_settings, _override, "RankArenaInterval", "120"));
		RANK_ARENA_REWARD_ID = Integer.parseInt(getString(_settings, _override, "RankArenaRewardId", "57"));
		RANK_ARENA_REWARD_COUNT = Integer.parseInt(getString(_settings, _override, "RankArenaRewardCount", "1000"));
		
		RANK_FISHERMAN_ENABLED = Boolean.parseBoolean(getString(_settings, _override, "RankFishermanEnabled", "false"));
		RANK_FISHERMAN_INTERVAL = Integer.parseInt(getString(_settings, _override, "RankFishermanInterval", "120"));
		RANK_FISHERMAN_REWARD_ID = Integer.parseInt(getString(_settings, _override, "RankFishermanRewardId", "57"));
		RANK_FISHERMAN_REWARD_COUNT = Integer.parseInt(getString(_settings, _override, "RankFishermanRewardCount", "1000"));
		
		RANK_CRAFT_ENABLED = Boolean.parseBoolean(getString(_settings, _override, "RankCraftEnabled", "false"));
		RANK_CRAFT_INTERVAL = Integer.parseInt(getString(_settings, _override, "RankCraftInterval", "120"));
		RANK_CRAFT_REWARD_ID = Integer.parseInt(getString(_settings, _override, "RankCraftRewardId", "57"));
		RANK_CRAFT_REWARD_COUNT = Integer.parseInt(getString(_settings, _override, "RankCraftRewardCount", "1000"));
		
		RANK_TVT_ENABLED = Boolean.parseBoolean(getString(_settings, _override, "RankTvTEnabled", "false"));
		RANK_TVT_INTERVAL = Integer.parseInt(getString(_settings, _override, "RankTvTInterval", "120"));
		RANK_TVT_REWARD_ID = Integer.parseInt(getString(_settings, _override, "RankTvTRewardId", "57"));
		RANK_TVT_REWARD_COUNT = Integer.parseInt(getString(_settings, _override, "RankTvTRewardCount", "1000"));
	}
	
	public static LeaderboardsConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final LeaderboardsConfigs _instance = new LeaderboardsConfigs();
	}
}