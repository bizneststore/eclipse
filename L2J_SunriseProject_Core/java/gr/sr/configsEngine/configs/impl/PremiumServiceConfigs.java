package gr.sr.configsEngine.configs.impl;

import java.util.HashMap;
import java.util.Map;

import l2r.gameserver.model.itemcontainer.Inventory;

import gr.sr.configsEngine.AbstractConfigs;

public class PremiumServiceConfigs extends AbstractConfigs
{
	public static boolean USE_PREMIUM_SERVICE;
	public static float PREMIUM_RATE_XP;
	public static float PREMIUM_RATE_SP;
	public static Map<Integer, Float> PR_RATE_DROP_ITEMS_ID;
	public static float PREMIUM_RATE_DROP_ITEMS;
	public static boolean PR_ENABLE_MODIFY_SKILL_DURATION;
	public static Map<Integer, Integer> PR_SKILL_DURATION_LIST;
	public static boolean ALLOW_PREMIUM_CHAT;
	public static String PREMIUM_CHAT_PREFIX;
	public static String PREMIUM_NAME_PREFIX;
	public static int PREMIUM_MAX_SCHEME;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/PremiumService.ini");
		
		USE_PREMIUM_SERVICE = Boolean.parseBoolean(getString(_settings, _override, "UsePremiumServices", "False"));
		PREMIUM_RATE_XP = Float.parseFloat(getString(_settings, _override, "PremiumRateXp", "2"));
		PREMIUM_RATE_SP = Float.parseFloat(getString(_settings, _override, "PremiumRateSp", "2"));
		PREMIUM_RATE_DROP_ITEMS = Float.parseFloat(getString(_settings, _override, "PremiumRateDropItems", "2"));
		ALLOW_PREMIUM_CHAT = Boolean.parseBoolean(getString(_settings, _override, "AllowPremiumChat", "False"));
		PREMIUM_CHAT_PREFIX = getString(_settings, _override, "PremiumChatPrefix", "-");
		PREMIUM_NAME_PREFIX = getString(_settings, _override, "PremiumNamePrefix", "[PR]");
		// For Premium Service
		String[] premiumSplit = getString(_settings, _override, "PrRateDropItemsById", "").split(";");
		PR_RATE_DROP_ITEMS_ID = new HashMap<>(premiumSplit.length);
		if (!premiumSplit[0].isEmpty())
		{
			for (String item : premiumSplit)
			{
				String[] itemSplit = item.split(",");
				if (itemSplit.length != 2)
				{
					_log.warn(getClass() + ": invalid config property -> PrRateDropItemsById \"", item, "\"");
				}
				else
				{
					try
					{
						PR_RATE_DROP_ITEMS_ID.put(Integer.parseInt(itemSplit[0]), Float.parseFloat(itemSplit[1]));
					}
					catch (NumberFormatException nfe)
					{
						if (!item.isEmpty())
						{
							_log.warn(getClass() + ": invalid config property -> PrRateDropItemsById \"", item, "\"");
						}
					}
				}
			}
		}
		if (!PR_RATE_DROP_ITEMS_ID.containsKey(Inventory.ADENA_ID))
		{
			PR_RATE_DROP_ITEMS_ID.put(Inventory.ADENA_ID, PREMIUM_RATE_DROP_ITEMS); // for Adena rate if not defined
		}
		
		PR_ENABLE_MODIFY_SKILL_DURATION = Boolean.parseBoolean(getString(_settings, _override, "EnablePrModifySkillDuration", "false"));
		
		// Create Map only if enabled
		if (PR_ENABLE_MODIFY_SKILL_DURATION)
		{
			String[] BuffTimeSplit = getString(_settings, _override, "PrSkillDurationList", "").split(";");
			PR_SKILL_DURATION_LIST = new HashMap<>(BuffTimeSplit.length);
			for (String skill : BuffTimeSplit)
			{
				String[] skillSplit = skill.split(",");
				if (skillSplit.length != 2)
				{
					_log.warn(getClass() + ": invalid config property -> PrSkillDurationList \"", skill, "\"");
				}
				else
				{
					try
					{
						PR_SKILL_DURATION_LIST.put(Integer.parseInt(skillSplit[0]), Integer.parseInt(skillSplit[1]));
					}
					catch (NumberFormatException nfe)
					{
						if (!skill.isEmpty())
						{
							_log.warn(getClass() + ": invalid config property ->  PrSkillDurationList \"" + skillSplit[0], skillSplit[1]);
						}
					}
				}
			}
		}
		PREMIUM_MAX_SCHEME = Integer.parseInt(getString(_settings, _override, "PremiumBufferScheme", "7"));
	}
	
	public static PremiumServiceConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PremiumServiceConfigs _instance = new PremiumServiceConfigs();
	}
}