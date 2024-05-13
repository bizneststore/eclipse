package gr.sr.configsEngine.configs.impl;

import java.util.ArrayList;
import java.util.List;

import gr.sr.configsEngine.AbstractConfigs;

public class CommunityDonateConfigs extends AbstractConfigs
{
	// global services settings
	public static boolean COMMUNITY_DONATE_ALLOW;
	public static String BYPASS_COMMAND;
	
	// character name change services
	public static boolean COMMUNITY_DONATE_NAME_CHANGE_ALLOW;
	public static boolean COMMUNITY_DONATE_NAME_CHANGE_NONPEACE;
	public static int COMMUNITY_DONATE_NAME_CHANGE_PRICE;
	public static int COMMUNITY_DONATE_NAME_CHANGE_ID;
	
	// clan name change services
	public static boolean COMMUNITY_DONATE_CLAN_NAME_CHANGE_ALLOW;
	public static boolean COMMUNITY_DONATE_CLAN_NAME_CHANGE_NONPEACE;
	public static int COMMUNITY_DONATE_CLAN_NAME_CHANGE_PRICE;
	public static int COMMUNITY_DONATE_CLAN_NAME_CHANGE_ID;
	
	// full clan services
	public static boolean COMMUNITY_DONATE_FULL_CLAN_ALLOW;
	public static boolean COMMUNITY_DONATE_FULL_CLAN_NONPEACE;
	public static int COMMUNITY_DONATE_FULL_CLAN_PRICE;
	public static int COMMUNITY_DONATE_FULL_CLAN_ID;
	public static int COMMUNITY_DONATE_FULL_CLAN_REP_AMOUNT;
	
	// recommends services
	public static boolean COMMUNITY_DONATE_REC_ALLOW;
	public static boolean COMMUNITY_DONATE_REC_NONPEACE;
	public static int COMMUNITY_DONATE_REC_PRICE;
	public static int COMMUNITY_DONATE_REC_ID;
	
	// fame services
	public static boolean COMMUNITY_DONATE_FAME_ALLOW;
	public static boolean COMMUNITY_DONATE_FAME_NONPEACE;
	public static int COMMUNITY_DONATE_FAME_PRICE;
	public static int COMMUNITY_DONATE_FAME_ID;
	public static int COMMUNITY_DONATE_FAME_AMOUNT;
	
	// noble services
	public static boolean COMMUNITY_DONATE_NOBLE_ALLOW;
	public static boolean COMMUNITY_DONATE_NOBLE_NONPEACE;
	public static int COMMUNITY_DONATE_NOBLE_PRICE;
	public static int COMMUNITY_DONATE_NOBLE_ID;
	
	// buy premium services
	public static boolean COMMUNITY_DONATE_PREMIUM_ALLOW;
	public static boolean COMMUNITY_DONATE_PREMIUM_NONPEACE;
	public static int COMMUNITY_DONATE_PREMIUM_PRICE_1_MONTH;
	public static int COMMUNITY_DONATE_PREMIUM_PRICE_2_MONTH;
	public static int COMMUNITY_DONATE_PREMIUM_PRICE_3_MONTH;
	public static int COMMUNITY_DONATE_PREMIUM_ID;
	
	// augment services
	public static boolean COMMUNITY_DONATE_AUGMENT_ALLOW;
	public static boolean COMMUNITY_DONATE_AUGMENT_NONPEACE;
	public static int COMMUNITY_DONATE_AUGMENT_PRICE;
	public static int COMMUNITY_DONATE_AUGMENT_ID;
	public static String COMMUNITY_DONATE_AUGMENT_SKILL;
	
	// multisell shop
	public static boolean COMMUNITY_DONATE_SHOP_ALLOW;
	public static boolean COMMUNITY_DONATE_SHOP_NONPEACE;
	public static List<Integer> MULTISELL_LIST;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/CommunityDonate.ini");
		
		// global services settings
		COMMUNITY_DONATE_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowCommunityDonate", "false"));
		BYPASS_COMMAND = getString(_settings, _override, "BypassCommand", "_bbsloc");
		
		// character name change services
		COMMUNITY_DONATE_NAME_CHANGE_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowChangeName", "false"));
		COMMUNITY_DONATE_NAME_CHANGE_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowChangeNameNonPeace", "false"));
		COMMUNITY_DONATE_NAME_CHANGE_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeNamePrice", "57"));
		COMMUNITY_DONATE_NAME_CHANGE_ID = Integer.parseInt(getString(_settings, _override, "ChangeNameCoin", "57"));
		
		// clan name change services
		COMMUNITY_DONATE_CLAN_NAME_CHANGE_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowChangeClanName", "false"));
		COMMUNITY_DONATE_CLAN_NAME_CHANGE_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowChangeClanNameNonPeace", "false"));
		COMMUNITY_DONATE_CLAN_NAME_CHANGE_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeClanNamePrice", "57"));
		COMMUNITY_DONATE_CLAN_NAME_CHANGE_ID = Integer.parseInt(getString(_settings, _override, "ChangeClanNameCoin", "57"));
		
		// full clan services
		COMMUNITY_DONATE_FULL_CLAN_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowFullClan", "false"));
		COMMUNITY_DONATE_FULL_CLAN_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowFullClanNonPeace", "false"));
		COMMUNITY_DONATE_FULL_CLAN_PRICE = Integer.parseInt(getString(_settings, _override, "FullClanPrice", "57"));
		COMMUNITY_DONATE_FULL_CLAN_ID = Integer.parseInt(getString(_settings, _override, "FullClanCoin", "57"));
		COMMUNITY_DONATE_FULL_CLAN_REP_AMOUNT = Integer.parseInt(getString(_settings, _override, "FullClanRepAmount", "57"));
		
		// recommends services
		COMMUNITY_DONATE_REC_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowRecommends", "false"));
		COMMUNITY_DONATE_REC_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowRecommendsNonPeace", "false"));
		COMMUNITY_DONATE_REC_PRICE = Integer.parseInt(getString(_settings, _override, "RecommendsPrice", "57"));
		COMMUNITY_DONATE_REC_ID = Integer.parseInt(getString(_settings, _override, "RecommendsCoin", "57"));
		
		// fame services
		COMMUNITY_DONATE_FAME_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowFame", "false"));
		COMMUNITY_DONATE_FAME_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowFameNonPeace", "false"));
		COMMUNITY_DONATE_FAME_PRICE = Integer.parseInt(getString(_settings, _override, "FamePrice", "57"));
		COMMUNITY_DONATE_FAME_ID = Integer.parseInt(getString(_settings, _override, "FameCoin", "57"));
		COMMUNITY_DONATE_FAME_AMOUNT = Integer.parseInt(getString(_settings, _override, "FameAmount", "57"));
		
		// noble services
		COMMUNITY_DONATE_NOBLE_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowNoble", "false"));
		COMMUNITY_DONATE_NOBLE_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowNobleNonPeace", "false"));
		COMMUNITY_DONATE_NOBLE_PRICE = Integer.parseInt(getString(_settings, _override, "NoblePrice", "57"));
		COMMUNITY_DONATE_NOBLE_ID = Integer.parseInt(getString(_settings, _override, "NobleCoin", "57"));
		
		// buy premium services
		COMMUNITY_DONATE_PREMIUM_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowPremium", "false"));
		COMMUNITY_DONATE_PREMIUM_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowPremiumNonPeace", "false"));
		COMMUNITY_DONATE_PREMIUM_PRICE_1_MONTH = Integer.parseInt(getString(_settings, _override, "PremiumPrice1Month", "57"));
		COMMUNITY_DONATE_PREMIUM_PRICE_2_MONTH = Integer.parseInt(getString(_settings, _override, "PremiumPrice2Month", "57"));
		COMMUNITY_DONATE_PREMIUM_PRICE_3_MONTH = Integer.parseInt(getString(_settings, _override, "PremiumPrice3Month", "57"));
		COMMUNITY_DONATE_PREMIUM_ID = Integer.parseInt(getString(_settings, _override, "PremiumCoin", "57"));
		
		// augment services
		COMMUNITY_DONATE_AUGMENT_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowAugment", "false"));
		COMMUNITY_DONATE_AUGMENT_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowAugmentNonPeace", "false"));
		COMMUNITY_DONATE_AUGMENT_PRICE = Integer.parseInt(getString(_settings, _override, "AugmentPrice", "57"));
		COMMUNITY_DONATE_AUGMENT_ID = Integer.parseInt(getString(_settings, _override, "AugmentCoin", "57"));
		COMMUNITY_DONATE_AUGMENT_SKILL = getString(_settings, _override, "AugmentSkill", "Heal Empower,16279;Prayer,16280;Empower,16281;Magic Barrier,16282;Might,16283;Shield,16284;Duel Might,16285;Agility,16332;Focus,16333;Reflect Damage,16334;Guidance,16335;Wild Magic,16336;Refresh,16287;Spell Refresh,16301;Skill Refresh,16297;Celestial Shield,16293;Heal,16195;Stone,16184;Prominence,16186;Solar Flare,16192;Shadow Flare,16233;Hydro Blast,16236;Tempest,16237;Aura Flare,16205;Medusa,16324;Stun,16323;Paralyze,16321;Fear,16318;Sleep,16271;Hold,16257;Bleed,16252;Cheer,16262;Blessed Body,16263;Blessed Soul,16264");
		
		// multisell shop
		COMMUNITY_DONATE_SHOP_ALLOW = Boolean.parseBoolean(getString(_settings, _override, "AllowShop", "false"));
		COMMUNITY_DONATE_SHOP_NONPEACE = Boolean.parseBoolean(getString(_settings, _override, "AllowShopInNonPeace", "false"));
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
				_log.warn(CommunityDonateConfigs.class.getSimpleName() + ": Wrong Multisell Id passed: " + multisell);
				_log.warn(nfe.getMessage());
			}
		}
	}
	
	public static CommunityDonateConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final CommunityDonateConfigs _instance = new CommunityDonateConfigs();
	}
}