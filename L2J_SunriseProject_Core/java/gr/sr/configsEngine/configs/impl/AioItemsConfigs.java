package gr.sr.configsEngine.configs.impl;

import java.util.ArrayList;
import java.util.List;

import gr.sr.configsEngine.AbstractConfigs;

public class AioItemsConfigs extends AbstractConfigs
{
	// About Aio Item Npcs
	public static boolean ALLOW_AIO_ITEM_COMMAND;
	public static boolean ENABLE_AIO_NPCS;
	public static int AIO_ITEM_ID;
	public static boolean DESTROY_ON_DISABLE;
	public static boolean GIVEANDCHECK_ATSTARTUP;
	// About Delays
	public static boolean AIO_ENABLE_TP_DELAY;
	public static boolean AIO_ENABLE_DELAY;
	public static double AIO_DELAY;
	public static boolean AIO_DELAY_SENDMESSAGE;
	// About Service Functions
	public static int CHANGE_GENDER_DONATE_COIN;
	public static int CHANGE_GENDER_DONATE_PRICE;
	public static int CHANGE_GENDER_NORMAL_COIN;
	public static int CHANGE_GENDER_NORMAL_PRICE;
	public static int CHANGE_NAME_COIN;
	public static int CHANGE_NAME_PRICE;
	public static int CHANGE_CNAME_COIN;
	public static int CHANGE_CNAME_PRICE;
	public static int AUGMENT_COIN;
	public static int AUGMENT_PRICE;
	public static int ELEMENT_COIN;
	public static int ELEMENT_PRICE;
	public static boolean ELEMENT_ALLOW_MORE_ATT_FOR_WEAPONS;
	public static int ELEMENT_VALUE_ARMOR;
	public static int ELEMENT_VALUE_WEAPON;
	public static int GET_FULL_CLAN_COIN;
	public static int GET_FULL_CLAN_PRICE;
	public static int AIO_EXCHANGE_ID;
	public static int AIO_EXCHANGE_PRICE;
	// About teleports
	public static boolean ALLOW_TELEPORT_DURING_SIEGE;
	public static int TELEPORT_DELAY;
	// Protection properties
	public static List<Integer> MULTISELL_LIST;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/AioItems.ini");
		
		// Aio Npcs Settings
		ALLOW_AIO_ITEM_COMMAND = getBoolean(_settings, _override, "AllowAioItemVoiceCommand", false);
		ENABLE_AIO_NPCS = getBoolean(_settings, _override, "EnableAioNpcs", true);
		AIO_ITEM_ID = Integer.parseInt(getString(_settings, _override, "AioItemId", "41005"));
		DESTROY_ON_DISABLE = getBoolean(_settings, _override, "DestroyOnDisable", false);
		GIVEANDCHECK_ATSTARTUP = getBoolean(_settings, _override, "CheckAndReGiveAioItem", true);
		
		// Change Gender - Name Settings
		CHANGE_GENDER_DONATE_COIN = Integer.parseInt(getString(_settings, _override, "ChangeGenderDonateCoin", "40000"));
		CHANGE_GENDER_DONATE_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeGenderDonatePrice", "10"));
		CHANGE_GENDER_NORMAL_COIN = Integer.parseInt(getString(_settings, _override, "ChangeGenderNormalCoin", "40002"));
		CHANGE_GENDER_NORMAL_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeGenderNormalPrice", "50000"));
		CHANGE_NAME_COIN = Integer.parseInt(getString(_settings, _override, "ChangeNameCoin", "40000"));
		CHANGE_NAME_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeNamePrice", "25000"));
		
		// Augment Settings
		AUGMENT_COIN = Integer.parseInt(getString(_settings, _override, "AugmentCoin", "40000"));
		AUGMENT_PRICE = Integer.parseInt(getString(_settings, _override, "AugmentPrice", "8"));
		
		// Element Setting
		ELEMENT_COIN = Integer.parseInt(getString(_settings, _override, "ElementCoin", "40000"));
		ELEMENT_PRICE = Integer.parseInt(getString(_settings, _override, "ElementPrice", "8"));
		ELEMENT_ALLOW_MORE_ATT_FOR_WEAPONS = getBoolean(_settings, _override, "ElementAllowMoreAttForWeapons", false);
		ELEMENT_VALUE_ARMOR = Integer.parseInt(getString(_settings, _override, "ElementValueArmor", "120"));
		ELEMENT_VALUE_WEAPON = Integer.parseInt(getString(_settings, _override, "ElementValueWeapon", "120"));
		
		// Clan Settings
		GET_FULL_CLAN_COIN = Integer.parseInt(getString(_settings, _override, "GetFullClanCoin", "40000"));
		GET_FULL_CLAN_PRICE = Integer.parseInt(getString(_settings, _override, "GetFullClanPrice", "25"));
		CHANGE_CNAME_COIN = Integer.parseInt(getString(_settings, _override, "ChangeClanNameCoin", "40000"));
		CHANGE_CNAME_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeClanNamePrice", "8"));
		
		// Exchange Settings
		AIO_EXCHANGE_ID = Integer.parseInt(getString(_settings, _override, "AioExchangeId", "3470"));
		AIO_EXCHANGE_PRICE = Integer.parseInt(getString(_settings, _override, "AioExchangePrice", "1000000000"));
		
		// Delays - Punishment Settings
		AIO_ENABLE_DELAY = getBoolean(_settings, _override, "AioEnableDelay", false);
		AIO_ENABLE_TP_DELAY = getBoolean(_settings, _override, "AioEnableTPDelay", false);
		AIO_DELAY = Double.parseDouble(getString(_settings, _override, "AioDelay", "0.75"));
		AIO_DELAY_SENDMESSAGE = getBoolean(_settings, _override, "AioDelaySendMessage", false);
		
		// Teleport Settings
		ALLOW_TELEPORT_DURING_SIEGE = getBoolean(_settings, _override, "TeleportDuringSiege", false);
		TELEPORT_DELAY = getInt(_settings, _override, "TeleportDelay", 30);
		
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
				_log.warn(getClass() + ": Wrong Multisell Id passed: " + multisell);
				_log.warn(nfe.getMessage());
			}
		}
	}
	
	public static AioItemsConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final AioItemsConfigs _instance = new AioItemsConfigs();
	}
}