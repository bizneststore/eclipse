package gr.sr.configsEngine.configs.impl;

import java.util.HashMap;
import java.util.Map;

import gr.sr.configsEngine.AbstractConfigs;

public class PvpRewardSystemConfigs extends AbstractConfigs
{
	public static boolean ENABLE_PVP_REWARD_SYSTEM;
	public static boolean ENABLE_PVP_REWARD_SYSTEM_IP_RESTRICTION;
	public static Map<Integer, Long> PVP_REWARDS;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/PvpSystem.ini");
		
		ENABLE_PVP_REWARD_SYSTEM = Boolean.parseBoolean(getString(_settings, _override, "EnablePvpRewardSystem", "false"));
		ENABLE_PVP_REWARD_SYSTEM_IP_RESTRICTION = Boolean.parseBoolean(getString(_settings, _override, "EnablePvpRewardSystemIpRestriction", "false"));
		
		String[] rewardSplit = getString(_settings, _override, "PvpRewards", "").split(";");
		PVP_REWARDS = new HashMap<>(rewardSplit.length);
		if (!rewardSplit[0].isEmpty())
		{
			for (String item : rewardSplit)
			{
				String[] itemSplit = item.split(",");
				if (itemSplit.length != 2)
				{
					_log.warn(getClass() + ": invalid config property -> PvpRewards \"", item, "\"");
				}
				else
				{
					try
					{
						PVP_REWARDS.put(Integer.parseInt(itemSplit[0]), Long.parseLong(itemSplit[1]));
					}
					catch (NumberFormatException nfe)
					{
						if (!item.isEmpty())
						{
							_log.warn(getClass() + ": invalid config property -> PvpRewards \"", item, "\"");
						}
					}
				}
			}
		}
	}
	
	public static PvpRewardSystemConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PvpRewardSystemConfigs _instance = new PvpRewardSystemConfigs();
	}
}