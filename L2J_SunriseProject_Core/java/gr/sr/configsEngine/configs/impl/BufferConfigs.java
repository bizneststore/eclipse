package gr.sr.configsEngine.configs.impl;

import gr.sr.configsEngine.AbstractConfigs;

public class BufferConfigs extends AbstractConfigs
{
	public static boolean ENABLE_ITEM_BUFFER;
	public static int DONATE_BUFF_ITEM_ID;
	public static int FREE_BUFFS_TILL_LEVEL;
	public static int BUFF_ITEM_ID;
	public static int PRICE_PERBUFF;
	public static boolean HEAL_PLAYER_AFTER_ACTIONS;
	public static int MAX_SCHEME_PROFILES;
	public static int MAX_DANCE_PERPROFILE;
	public static int MAX_BUFFS_PERPROFILE;
	public static int[] MAGE_BUFFS_LIST;
	public static int[] FIGHTER_BUFFS_LIST;
	// About Delays
	public static boolean BUFFER_ENABLE_DELAY;
	public static double BUFFER_DELAY;
	public static boolean BUFFER_DELAY_SENDMESSAGE;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/Buffer.ini");
		
		// Buffer settings
		ENABLE_ITEM_BUFFER = getBoolean(_settings, _override, "EnableItemBuffer", true);
		DONATE_BUFF_ITEM_ID = Integer.parseInt(getString(_settings, _override, "DonateItemId", "40001"));
		FREE_BUFFS_TILL_LEVEL = Integer.parseInt(getString(_settings, _override, "FreeBuffsTillLevel", "0"));
		BUFF_ITEM_ID = Integer.parseInt(getString(_settings, _override, "PriceId", "57"));
		PRICE_PERBUFF = Integer.parseInt(getString(_settings, _override, "PriceCount", "100"));
		HEAL_PLAYER_AFTER_ACTIONS = Boolean.parseBoolean(getString(_settings, _override, "HealPlayerAfterAction", "false"));
		MAX_SCHEME_PROFILES = Integer.parseInt(getString(_settings, _override, "MaxProfilesPerChar", "4"));
		MAX_DANCE_PERPROFILE = Integer.parseInt(getString(_settings, _override, "MaxDancesPerProfile", "16"));
		MAX_BUFFS_PERPROFILE = Integer.parseInt(getString(_settings, _override, "MaxBuffsPerProfile", "24"));
		String[] mageBuffSplit = getString(_settings, _override, "MageBuffsList", "1204;1035;1048").trim().split(";");
		MAGE_BUFFS_LIST = new int[mageBuffSplit.length];
		try
		{
			int i = 0;
			for (String mageBuffId : mageBuffSplit)
			{
				MAGE_BUFFS_LIST[i++] = Integer.parseInt(mageBuffId);
			}
		}
		catch (NumberFormatException nfe)
		{
			_log.warn(nfe.getMessage(), nfe);
		}
		
		String[] fighterBuffSplit = getString(_settings, _override, "FighterBuffsList", "1204;1035;1048").trim().split(";");
		FIGHTER_BUFFS_LIST = new int[fighterBuffSplit.length];
		try
		{
			int i = 0;
			for (String fighterBuffId : fighterBuffSplit)
			{
				FIGHTER_BUFFS_LIST[i++] = Integer.parseInt(fighterBuffId);
			}
		}
		catch (NumberFormatException nfe)
		{
			_log.warn(nfe.getMessage(), nfe);
		}
		
		// Delays - Punishment Settings
		BUFFER_ENABLE_DELAY = Boolean.parseBoolean(getString(_settings, _override, "BufferEnableDelay", "false"));
		BUFFER_DELAY = Double.parseDouble(getString(_settings, _override, "BufferDelay", "0.75"));
		BUFFER_DELAY_SENDMESSAGE = Boolean.parseBoolean(getString(_settings, _override, "BufferDelaySendMessage", "false"));
	}
	
	public static BufferConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final BufferConfigs _instance = new BufferConfigs();
	}
}