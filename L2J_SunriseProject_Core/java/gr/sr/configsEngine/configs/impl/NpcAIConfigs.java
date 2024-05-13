package gr.sr.configsEngine.configs.impl;

import gr.sr.configsEngine.AbstractConfigs;

public class NpcAIConfigs extends AbstractConfigs
{
	public static int MIN_NPC_ANIMATION;
	public static int MAX_NPC_ANIMATION;
	public static int MIN_MONSTER_ANIMATION;
	public static int MAX_MONSTER_ANIMATION;
	
	public static int RANDOM_ANIMATION_CHANCE;
	
	public static int RANDOM_WALK_INTERVAL;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/NpcAI.ini");
		
		MIN_NPC_ANIMATION = getInt(_settings, _override, "MinNPCAnimation", 10);
		MAX_NPC_ANIMATION = getInt(_settings, _override, "MaxNPCAnimation", 20);
		MIN_MONSTER_ANIMATION = getInt(_settings, _override, "MinMonsterAnimation", 5);
		MAX_MONSTER_ANIMATION = getInt(_settings, _override, "MaxMonsterAnimation", 20);
		RANDOM_ANIMATION_CHANCE = getInt(_settings, _override, "RandomAnimationRate", 5);
		RANDOM_WALK_INTERVAL = Math.max(getInt(_settings, _override, "RandomWalkInterval", 45), 1) * 1000;
	}
	
	public static NpcAIConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final NpcAIConfigs _instance = new NpcAIConfigs();
	}
}