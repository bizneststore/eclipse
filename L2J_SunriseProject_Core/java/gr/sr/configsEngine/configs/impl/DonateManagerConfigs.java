package gr.sr.configsEngine.configs.impl;

import java.util.ArrayList;
import java.util.List;

import gr.sr.configsEngine.AbstractConfigs;

public class DonateManagerConfigs extends AbstractConfigs
{
	// About Donate Functions
	public static int CHANGE_GENDER_DONATE_COIN;
	public static int CHANGE_GENDER_DONATE_PRICE;
	public static int CHANGE_NAME_COIN;
	public static int CHANGE_NAME_PRICE;
	public static int CHANGE_CNAME_COIN;
	public static int CHANGE_CNAME_PRICE;
	public static int REPUTATION_POINTS_TO_ADD;
	public static int[] CLAN_MAIN_SKILLS;
	public static int[] CLAN_SQUAD_SKILLS;
	public static int GET_FULL_CLAN_COIN;
	public static int GET_FULL_CLAN_PRICE;
	public static int AIO_EXCHANGE_ID;
	public static int AIO_EXCHANGE_PRICE;
	public static List<Integer> MULTISELL_LIST;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/Donate.ini");
		
		// Change Gender - Name Settings
		CHANGE_GENDER_DONATE_COIN = Integer.parseInt(getString(_settings, _override, "ChangeGenderDonateCoin", "40000"));
		CHANGE_GENDER_DONATE_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeGenderDonatePrice", "10"));
		CHANGE_NAME_COIN = Integer.parseInt(getString(_settings, _override, "ChangeNameCoin", "40000"));
		CHANGE_NAME_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeNamePrice", "25000"));
		
		// Clan Settings
		GET_FULL_CLAN_COIN = Integer.parseInt(getString(_settings, _override, "GetFullClanCoin", "40000"));
		GET_FULL_CLAN_PRICE = Integer.parseInt(getString(_settings, _override, "GetFullClanPrice", "25"));
		CHANGE_CNAME_COIN = Integer.parseInt(getString(_settings, _override, "ChangeClanNameCoin", "40000"));
		CHANGE_CNAME_PRICE = Integer.parseInt(getString(_settings, _override, "ChangeClanNamePrice", "8"));
		REPUTATION_POINTS_TO_ADD = Integer.parseInt(getString(_settings, _override, "ReputationPointsToAdd", "500000"));
		String[] mainSkills = getString(_settings, _override, "ClanMainSkills", "1204;1035;1048").trim().split(";");
		CLAN_MAIN_SKILLS = new int[mainSkills.length];
		try
		{
			int i = 0;
			for (String mainSkill : mainSkills)
			{
				CLAN_MAIN_SKILLS[i++] = Integer.parseInt(mainSkill);
			}
		}
		catch (NumberFormatException nfe)
		{
			_log.warn(nfe.getMessage(), nfe);
		}
		
		String[] squadSkills = getString(_settings, _override, "ClanSquadSkills", "1204;1035;1048").trim().split(";");
		CLAN_SQUAD_SKILLS = new int[squadSkills.length];
		try
		{
			int i = 0;
			for (String squadSkill : squadSkills)
			{
				CLAN_SQUAD_SKILLS[i++] = Integer.parseInt(squadSkill);
			}
		}
		catch (NumberFormatException nfe)
		{
			_log.warn(nfe.getMessage(), nfe);
		}
		
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
				_log.warn(DonateManagerConfigs.class.getSimpleName() + ": Wrong Multisell Id passed: " + multisell);
				_log.warn(nfe.getMessage());
			}
		}
	}
	
	public static DonateManagerConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final DonateManagerConfigs _instance = new DonateManagerConfigs();
	}
}