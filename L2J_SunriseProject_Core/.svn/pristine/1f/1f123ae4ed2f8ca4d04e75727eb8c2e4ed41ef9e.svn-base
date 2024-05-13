package gr.sr.configsEngine.configs.impl;

import gr.sr.configsEngine.AbstractConfigs;

public class AutoRestartConfigs extends AbstractConfigs
{
	public static int AUTO_RESTART_TIME;
	public static String AUTO_RESTART_PATTERN;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/AutoRestart.ini");
		
		AUTO_RESTART_TIME = getInt(_settings, _override, "AutoRestartSeconds", 360);
		AUTO_RESTART_PATTERN = getString(_settings, _override, "AutoRestartPattern", "0 6 * * *");
	}
	
	public static AutoRestartConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final AutoRestartConfigs _instance = new AutoRestartConfigs();
	}
}