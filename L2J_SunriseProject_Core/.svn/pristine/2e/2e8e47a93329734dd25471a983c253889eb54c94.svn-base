package gr.sr.configsEngine.configs.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import l2r.Config;
import l2r.gameserver.data.xml.impl.ItemData;
import l2r.gameserver.model.items.L2Item;
import l2r.gameserver.network.serverpackets.NpcHtmlMessage;
import l2r.util.StringUtil;

import gr.sr.configsEngine.AbstractConfigs;

public class CustomServerConfigs extends AbstractConfigs
{
	// Alternative Payment System Settings
	public static boolean ALTERNATE_PAYMODE_SHOPS;
	public static boolean ALTERNATE_PAYMODE_MAILS;
	public static boolean ALTERNATE_PAYMODE_CLANHALLS;
	public static int ALTERNATE_PAYMENT_ID;
	// Shop Distance Settings
	public static int SHOP_MIN_RANGE_FROM_NPC;
	public static int SHOP_MIN_RANGE_FROM_PLAYER;
	// Custom Announcements Settings
	public static boolean EXTRA_MESSAGES;
	public static boolean ANNOUNCE_HEROS_ON_LOGIN;
	// Custom General Settings
	public static boolean ALLOW_ONLINE_COMMAND;
	public static boolean ALLOW_REPAIR_COMMAND;
	public static boolean ALLOW_EXP_GAIN_COMMAND;
	public static boolean ALLOW_TELEPORTS_COMMAND;
	public static boolean GIVE_HELLBOUND_MAP;
	public static int TOP_LISTS_RELOAD_TIME;
	public static boolean AUTO_ACTIVATE_SHOTS;
	public static int AUTO_ACTIVATE_SHOTS_MIN;
	public static boolean PVP_SPREE_SYSTEM;
	// Custom Clan Settings
	public static boolean ALT_ALLOW_CLAN_LEADER_NAME;
	public static String CLAN_LEADER_NAME_COLOR;
	public static String CLAN_LEADER_TITLE_COLOR;
	public static int CLAN_LEVEL_ACTIVATION;
	public static boolean ANNOUNCE_CASTLE_LORDS;
	// Allow Augment PvP-Hero Items Settings
	public static boolean ALT_ALLOW_REFINE_PVP_ITEM;
	public static boolean ALT_ALLOW_REFINE_HERO_ITEM;
	public static int MAX_REWARD_COUNT_FOR_STACK_ITEM1;
	public static int MAX_REWARD_COUNT_FOR_STACK_ITEM2;
	public static int DELAY_FOR_NEXT_REWARD;
	public static boolean VOTE_REWARD_HOPZONE_ENABLE;
	public static boolean VOTE_REWARD_TOPZONE_ENABLE;
	// Share items in party not only adena
	public static boolean EVENLY_DISTRIBUTED_ITEMS;
	public static boolean EVENLY_DISTRIBUTED_ITEMS_SEND_LIST;
	public static NpcHtmlMessage EVENLY_DISTRIBUTED_ITEMS_CACHED_HTML = null;
	public static boolean EVENLY_DISTRIBUTED_ITEMS_FORCED;
	public static boolean EVENLY_DISTRIBUTED_ITEMS_FOR_SPOIL_ENABLED;
	public static List<Integer> EVENLY_DISTRIBUTED_ITEMS_LIST;
	// Skill Enchant Settings
	public static boolean ENABLE_SKILL_ENCHANT;
	public static boolean ENABLE_SKILL_MAX_ENCHANT_LIMIT;
	public static int SKILL_MAX_ENCHANT_LIMIT_LEVEL;
	// Bonus exp Manager Settings
	public static boolean ENABLE_RUNE_BONUS;
	// Character control panel
	public static boolean ENABLE_CHARACTER_CONTROL_PANEL;
	// Statring title Settings
	public static boolean ENABLE_STARTING_TITLE;
	public static String STARTING_TITLE;
	// Raid/Grand Boss Announce
	public static boolean ANNOUNCE_DEATH_REVIVE_OF_RAIDS;
	// Dual box Settings
	public static int DUAL_BOX_IN_GAME;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/Custom.ini");
		
		// Alternative Payment System Settings
		ALTERNATE_PAYMODE_SHOPS = Boolean.parseBoolean(getString(_settings, _override, "AlternatePaymodeShops", "False"));
		ALTERNATE_PAYMODE_MAILS = Boolean.parseBoolean(getString(_settings, _override, "AlternatePaymodeMails", "False"));
		ALTERNATE_PAYMODE_CLANHALLS = Boolean.parseBoolean(getString(_settings, _override, "AlternatePaymodeClanHalls", "False"));
		ALTERNATE_PAYMENT_ID = Integer.parseInt(getString(_settings, _override, "AlternatePayment", "57"));
		
		// Shop Distance
		SHOP_MIN_RANGE_FROM_PLAYER = Integer.parseInt(getString(_settings, _override, "ShopMinRangeFromPlayer", "0"));
		SHOP_MIN_RANGE_FROM_NPC = Integer.parseInt(getString(_settings, _override, "ShopMinRangeFromNpc", "0"));
		
		// Custom Announcements
		EXTRA_MESSAGES = Boolean.parseBoolean(getString(_settings, _override, "ExtraMessages", "false"));
		ANNOUNCE_HEROS_ON_LOGIN = Boolean.parseBoolean(getString(_settings, _override, "AnnounceHerosOnLogin", "false"));
		
		// Custom General Settings
		ALLOW_ONLINE_COMMAND = Boolean.parseBoolean(getString(_settings, _override, "AllowOnlineCommand", "False"));
		ALLOW_REPAIR_COMMAND = Boolean.parseBoolean(getString(_settings, _override, "AllowRepairCommand", "False"));
		ALLOW_EXP_GAIN_COMMAND = Boolean.parseBoolean(getString(_settings, _override, "AllowExpGainCommand", "False"));
		ALLOW_TELEPORTS_COMMAND = Boolean.parseBoolean(getString(_settings, _override, "AllowTeleportsCommand", "False"));
		GIVE_HELLBOUND_MAP = Boolean.parseBoolean(getString(_settings, _override, "GiveHellboundMap", "False"));
		TOP_LISTS_RELOAD_TIME = Integer.parseInt(getString(_settings, _override, "TopListsReloadTime", "20"));
		AUTO_ACTIVATE_SHOTS = Boolean.parseBoolean(getString(_settings, _override, "AutoActivateShotsEnabled", "False"));
		AUTO_ACTIVATE_SHOTS_MIN = Integer.parseInt(getString(_settings, _override, "AutoActivateShotsMin", "200"));
		PVP_SPREE_SYSTEM = Boolean.parseBoolean(getString(_settings, _override, "PvpSpreeSystem", "False"));
		
		// Custom Clan Settings
		ALT_ALLOW_CLAN_LEADER_NAME = Boolean.parseBoolean(getString(_settings, _override, "AltAllowClanLeaderName", "false"));
		CLAN_LEADER_NAME_COLOR = getString(_settings, _override, "ClanLeaderNameColor", "FFFF00");
		CLAN_LEADER_TITLE_COLOR = getString(_settings, _override, "ClanLeaderTitleColor", "FFFF00");
		CLAN_LEVEL_ACTIVATION = Integer.parseInt(getString(_settings, _override, "ClanLevelActivation", "0"));
		ANNOUNCE_CASTLE_LORDS = Boolean.parseBoolean(getString(_settings, _override, "AnnounceCastleLords", "false"));
		
		// Allow augment on pvp-hero items or not
		ALT_ALLOW_REFINE_PVP_ITEM = Boolean.parseBoolean(getString(_settings, _override, "AltAllowRefinePVPItem", "False"));
		ALT_ALLOW_REFINE_HERO_ITEM = Boolean.parseBoolean(getString(_settings, _override, "AltAllowRefineHEROItem", "False"));
		
		// Skill Enchant Settings
		ENABLE_SKILL_ENCHANT = Boolean.parseBoolean(getString(_settings, _override, "EnableSkillEnchant", "True"));
		ENABLE_SKILL_MAX_ENCHANT_LIMIT = Boolean.parseBoolean(getString(_settings, _override, "EnableSkillMaxEnchantLimit", "False"));
		SKILL_MAX_ENCHANT_LIMIT_LEVEL = Integer.parseInt(getString(_settings, _override, "SkillMaxEnchantLimitLevel", "30"));
		
		// Bonus exp Manager Settings
		ENABLE_RUNE_BONUS = Boolean.parseBoolean(getString(_settings, _override, "EnableBonusManager", "False"));
		
		// Character control panel Settings
		ENABLE_CHARACTER_CONTROL_PANEL = Boolean.parseBoolean(getString(_settings, _override, "EnableCharacterControlPanel", "False"));
		
		// Starting title Settings
		ENABLE_STARTING_TITLE = Boolean.parseBoolean(getString(_settings, _override, "EnableStartingTitle", "False"));
		STARTING_TITLE = getString(_settings, _override, "StartingTitle", "L2sunrise");
		
		// Loot Mod by Zoey76
		EVENLY_DISTRIBUTED_ITEMS = Boolean.parseBoolean(getString(_settings, _override, "EnableEvenlyDistribution", "False"));
		EVENLY_DISTRIBUTED_ITEMS_SEND_LIST = Boolean.parseBoolean(getString(_settings, _override, "SendEvenlyDistributionList", "True"));
		EVENLY_DISTRIBUTED_ITEMS_FORCED = Boolean.parseBoolean(getString(_settings, _override, "ForceEvenlyDistribution", "False"));
		EVENLY_DISTRIBUTED_ITEMS_FOR_SPOIL_ENABLED = Boolean.parseBoolean(getString(_settings, _override, "EnableEvenlyDistributionForSpoil", "False"));
		if (EVENLY_DISTRIBUTED_ITEMS)
		{
			String[] itemIds = getString(_settings, _override, "ItemsToEvenlyDistribute", "").split(";");
			EVENLY_DISTRIBUTED_ITEMS_LIST = new ArrayList<>(itemIds.length);
			for (String itemId : itemIds)
			{
				try
				{
					EVENLY_DISTRIBUTED_ITEMS_LIST.add(Integer.parseInt(itemId.trim()));
				}
				catch (NumberFormatException e)
				{
					_log.info("Loot Mod: Error parsing item id. Skiping " + itemId + ".");
				}
			}
			
			if (EVENLY_DISTRIBUTED_ITEMS_SEND_LIST)
			{
				final StringBuilder sb = StringUtil.startAppend(1000, "<table>");
				final ItemData itemTable = ItemData.getInstance();
				L2Item template;
				for (int itemId : EVENLY_DISTRIBUTED_ITEMS_LIST)
				{
					template = itemTable.getTemplate(itemId);
					StringUtil.append(sb, "<tr><td>", "<img src=\"" + template.getIcon() + "\" height=\"32\" width=\"32\" /></td><td>", template.getName(), "</td></tr>");
				}
				StringUtil.append(sb, "</table>");
				final File htmlFile = new File(Config.DATAPACK_ROOT, "data/html/mods/EvenlyDistributeItems.htm");
				String html = null;
				try (FileInputStream fis = new FileInputStream(htmlFile);
					BufferedInputStream bis = new BufferedInputStream(fis);)
				{
					final int bytes = bis.available();
					final byte[] raw = new byte[bytes];
					
					bis.read(raw);
					html = new String(raw, "UTF-8");
					html = html.replaceAll("\r\n", "\n");
				}
				catch (Exception e)
				{
					_log.warn("EXTRA: Evenly distributed error: " + e.getMessage());
				}
				if (html != null)
				{
					EVENLY_DISTRIBUTED_ITEMS_CACHED_HTML = new NpcHtmlMessage();
					EVENLY_DISTRIBUTED_ITEMS_CACHED_HTML.setHtml(html);
					EVENLY_DISTRIBUTED_ITEMS_CACHED_HTML.replace("%list%", sb.toString());
				}
			}
		}
		
		// Raid/Grand Boss announce settings
		ANNOUNCE_DEATH_REVIVE_OF_RAIDS = Boolean.parseBoolean(getString(_settings, _override, "AnnounceDeathAndReviveOfRaids", "False"));
		
		// Dual box settings
		DUAL_BOX_IN_GAME = Integer.parseInt(getString(_settings, _override, "DualBoxesInGame", "0"));
	}
	
	public static CustomServerConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final CustomServerConfigs _instance = new CustomServerConfigs();
	}
}