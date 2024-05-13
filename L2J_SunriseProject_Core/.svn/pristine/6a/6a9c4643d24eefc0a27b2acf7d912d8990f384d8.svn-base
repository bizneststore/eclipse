package gr.sr.configsEngine.configs.impl;

import java.util.ArrayList;
import java.util.List;

import gr.sr.configsEngine.AbstractConfigs;

public class CommunityServicesConfigs extends AbstractConfigs
{
	public static boolean COMMUNITY_SERVICES_ALLOW;
	public static String BYPASS_COMMAND;
	
	public static boolean COMMUNITY_SERVICES_TP_ALLOW;
	public static boolean ALLOW_TELEPORT_DURING_SIEGE;
	public static int TELEPORT_DELAY;
	
	public static boolean COMMUNITY_SERVICES_SHOP_ALLOW;
	public static boolean COMMUNITY_SERVICES_SHOP_NONPEACE;
	
	public static boolean COMMUNITY_SERVICES_WASH_PK_ALLOW;
	public static boolean COMMUNITY_SERVICES_WASH_PK_NONPEACE;
	public static int COMMUNITY_SERVICES_WASH_PK_PRICE;
	public static int COMMUNITY_SERVICES_WASH_PK_ID;
	
	// Name Change Services Settings
	public static boolean COMMUNITY_SERVICES_NAME_CHANGE_ALLOW;
	public static boolean COMMUNITY_SERVICES_NAME_CHANGE_NONPEACE;
	public static int COMMUNITY_SERVICES_NAME_CHANGE_PRICE;
	public static int COMMUNITY_SERVICES_NAME_CHANGE_ID;
	
	// Clan Name Change Services Settings
	public static boolean COMMUNITY_SERVICES_CLAN_NAME_CHANGE_ALLOW;
	public static boolean COMMUNITY_SERVICES_CLAN_NAME_CHANGE_NONPEACE;
	public static int COMMUNITY_SERVICES_CLAN_NAME_CHANGE_PRICE;
	public static int COMMUNITY_SERVICES_CLAN_NAME_CHANGE_ID;
	
	// Attribute Manager Services Settings
	public static boolean COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_ALLOW;
	public static boolean COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_NONPEACE;
	public static int COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_PRICE_ARMOR;
	public static int COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_PRICE_WEAPON;
	public static int COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_ID;
	public static int COMMUNITY_SERVICES_ATTRIBUTE_LVL_FOR_ARMOR;
	public static int COMMUNITY_SERVICES_ATTRIBUTE_LVL_FOR_WEAPON;
	
	// Protection For Shop Settings
	public static List<Integer> MULTISELL_LIST;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/CommunityServices.ini");
		
		// Global Services Settings
		COMMUNITY_SERVICES_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowCommunityServices", "false"));
		BYPASS_COMMAND = getString(_settings, _override, "BypassCommand", "_bbsloc");
		// Teleport Services Settings
		COMMUNITY_SERVICES_TP_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowTeleport", "false"));
		ALLOW_TELEPORT_DURING_SIEGE = Boolean.parseBoolean(getString(_settings, _override, "TeleportDuringSiege", "False"));
		TELEPORT_DELAY = getInt(_settings, _override, "TeleportDelay", 30);
		// Teleport Services Settings
		COMMUNITY_SERVICES_SHOP_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowGMShop", "false"));
		COMMUNITY_SERVICES_SHOP_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowGMShopInNonPeace", "false"));
		// Pk Remover Services Settings
		COMMUNITY_SERVICES_WASH_PK_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowDecreasePK", "false"));
		COMMUNITY_SERVICES_WASH_PK_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowDecreasePKNonPeace", "false"));
		COMMUNITY_SERVICES_WASH_PK_PRICE = Integer.parseInt(getString(_settings, _override, "DecreasePKPrice", "57"));
		COMMUNITY_SERVICES_WASH_PK_ID = Integer.parseInt(getString(_settings, _override, "DecreasePKCoin", "57"));
		// Name Change Services Settings
		COMMUNITY_SERVICES_NAME_CHANGE_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowChangeName", "false"));
		COMMUNITY_SERVICES_NAME_CHANGE_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowChangeNameNonPeace", "false"));
		COMMUNITY_SERVICES_NAME_CHANGE_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeNamePrice", "57"));
		COMMUNITY_SERVICES_NAME_CHANGE_ID = Integer.parseInt(getString(_settings, _override, "ChangeNameCoin", "57"));
		// Clan Name Change Services Settings
		COMMUNITY_SERVICES_CLAN_NAME_CHANGE_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowChangeClanName", "false"));
		COMMUNITY_SERVICES_CLAN_NAME_CHANGE_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowChangeClanNameNonPeace", "false"));
		COMMUNITY_SERVICES_CLAN_NAME_CHANGE_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeClanNamePrice", "57"));
		COMMUNITY_SERVICES_CLAN_NAME_CHANGE_ID = Integer.parseInt(getString(_settings, _override, "ChangeClanNameCoin", "57"));
		// Attribute Manager Services Settings
		COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowAttribute", "false"));
		COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowAttributeNonPeace", "false"));
		COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_PRICE_ARMOR = Integer.parseInt(getString(_settings, _override, "AttributePriceArmor", "57"));
		COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_PRICE_WEAPON = Integer.parseInt(getString(_settings, _override, "AttributePriceWeapon", "57"));
		COMMUNITY_SERVICES_ATTRIBUTE_MANAGER_ID = Integer.parseInt(getString(_settings, _override, "AttributeCoin", "57"));
		COMMUNITY_SERVICES_ATTRIBUTE_LVL_FOR_ARMOR = Integer.parseInt(getString(_settings, _override, "AttributeLevelForArmor", "7"));
		COMMUNITY_SERVICES_ATTRIBUTE_LVL_FOR_WEAPON = Integer.parseInt(getString(_settings, _override, "AttributeLevelForWeapon", "7"));
		
		String[] multisells = getString(_settings, _override, "MultisellList", "1204;1035;1048").trim().split(";");
		MULTISELL_LIST = new ArrayList<>(multisells.length);
		for (String multisell : multisells)
		{
			try
			{
				MULTISELL_LIST.add(Integer.parseInt(multisell));
			}
			catch (NumberFormatException nfe)
			{
				_log.warn(CommunityServicesConfigs.class.getSimpleName() + ": Wrong Multisell Id passed: " + multisell);
				_log.warn(nfe.getMessage());
			}
		}
	}
	
	public static CommunityServicesConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final CommunityServicesConfigs _instance = new CommunityServicesConfigs();
	}
}