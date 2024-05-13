package gr.sr.configsEngine.configs.impl;

import java.util.ArrayList;
import java.util.List;

import gr.sr.configsEngine.AbstractConfigs;

public class SecuritySystemConfigs extends AbstractConfigs
{
	public static boolean ENABLE_SECURITY_SYSTEM;
	public static boolean ENABLE_ADMIN_SECURITY_SYSTEM;
	public static int SAFE_ADMIN_PUNISH;
	public static int MAX_ENCHANT_LEVEL;
	public static boolean ENABLE_GLOBAL_ANNOUNCE;
	public static boolean ENABLE_MESSAGE_TO_PLAYER;
	public static int TIME_IN_JAIL_LOW;
	public static int TIME_IN_JAIL_MID;
	public static int TIME_IN_JAIL_HIGH;
	public static String MESSAGE_TO_SEND;
	public static String ANNOUNCE_TO_SEND;
	public static List<Integer> ADMIN_OBJECT_ID_LIST;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/SecuritySystem.ini");
		
		ENABLE_SECURITY_SYSTEM = Boolean.parseBoolean(getString(_settings, _override, "EnableSecuritySystem", "true"));
		ENABLE_ADMIN_SECURITY_SYSTEM = Boolean.parseBoolean(getString(_settings, _override, "EnableAdminSecuritySystem", "false"));
		SAFE_ADMIN_PUNISH = Integer.parseInt(getString(_settings, _override, "SafeAdminPunish", "3"));
		MAX_ENCHANT_LEVEL = Integer.parseInt(getString(_settings, _override, "MaxEnchantLevel", "0"));
		ENABLE_GLOBAL_ANNOUNCE = Boolean.parseBoolean(getString(_settings, _override, "EnableGlobalAnnounce", "false"));
		ENABLE_MESSAGE_TO_PLAYER = Boolean.parseBoolean(getString(_settings, _override, "EnableMessageToPlayer", "false"));
		TIME_IN_JAIL_LOW = Integer.parseInt(getString(_settings, _override, "TimeInJailLow", "1"));
		TIME_IN_JAIL_MID = Integer.parseInt(getString(_settings, _override, "TimeInJailMid", "60"));
		TIME_IN_JAIL_HIGH = Integer.parseInt(getString(_settings, _override, "TimeInJailHigh", "120"));
		MESSAGE_TO_SEND = getString(_settings, _override, "MessageToSend", "[GM]Server: You must be fool!");
		ANNOUNCE_TO_SEND = getString(_settings, _override, "AnnounceToSend", "tried to corrupt server bypasses, punish jail!");
		
		String[] objectIds = getString(_settings, _override, "AdminsObjectIds", "1204;1035;1048").trim().split(";");
		ADMIN_OBJECT_ID_LIST = new ArrayList<>(objectIds.length);
		for (String objectId : objectIds)
		{
			try
			{
				ADMIN_OBJECT_ID_LIST.add(Integer.parseInt(objectId));
			}
			catch (NumberFormatException nfe)
			{
				_log.warn("Security System: Wrong Object Id passed: " + objectId);
				_log.warn(nfe.getMessage());
			}
		}
	}
	
	public static SecuritySystemConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final SecuritySystemConfigs _instance = new SecuritySystemConfigs();
	}
}