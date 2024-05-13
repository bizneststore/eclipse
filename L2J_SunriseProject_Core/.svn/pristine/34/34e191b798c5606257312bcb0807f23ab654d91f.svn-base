package gr.sr.configsEngine.configs.impl;

import gr.sr.configsEngine.AbstractConfigs;

public class PcBangConfigs extends AbstractConfigs
{
	public static boolean PC_BANG_ENABLED;
	public static int MAX_PC_BANG_POINTS;
	public static boolean ENABLE_DOUBLE_PC_BANG_POINTS;
	public static int DOUBLE_PC_BANG_POINTS_CHANCE;
	public static double PC_BANG_POINT_RATE;
	public static boolean RANDOM_PC_BANG_POINT;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/PcCafe.ini");
		
		PC_BANG_ENABLED = Boolean.parseBoolean(getString(_settings, _override, "Enabled", "false"));
		MAX_PC_BANG_POINTS = Integer.parseInt(getString(_settings, _override, "MaxPcBangPoints", "200000"));
		if (MAX_PC_BANG_POINTS < 0)
		{
			MAX_PC_BANG_POINTS = 0;
		}
		ENABLE_DOUBLE_PC_BANG_POINTS = Boolean.parseBoolean(getString(_settings, _override, "DoublingAcquisitionPoints", "false"));
		DOUBLE_PC_BANG_POINTS_CHANCE = Integer.parseInt(getString(_settings, _override, "DoublingAcquisitionPointsChance", "1"));
		if ((DOUBLE_PC_BANG_POINTS_CHANCE < 0) || (DOUBLE_PC_BANG_POINTS_CHANCE > 100))
		{
			DOUBLE_PC_BANG_POINTS_CHANCE = 1;
		}
		PC_BANG_POINT_RATE = Double.parseDouble(getString(_settings, _override, "AcquisitionPointsRate", "1.0"));
		if (PC_BANG_POINT_RATE < 0)
		{
			PC_BANG_POINT_RATE = 1;
		}
		RANDOM_PC_BANG_POINT = Boolean.parseBoolean(getString(_settings, _override, "AcquisitionPointsRandom", "false"));
	}
	
	public static PcBangConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PcBangConfigs _instance = new PcBangConfigs();
	}
}