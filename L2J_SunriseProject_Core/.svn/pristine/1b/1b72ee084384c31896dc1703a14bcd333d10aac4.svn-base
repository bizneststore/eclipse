package gr.sr.configsEngine.configs.impl;

import java.util.HashMap;
import java.util.Map;

import gr.sr.configsEngine.AbstractConfigs;

public class FlagZoneConfigs extends AbstractConfigs
{
	public static boolean ENABLE_FLAG_ZONE;
	public static boolean ENABLE_ANTIFEED_PROTECTION;
	public static boolean ENABLE_PC_IP_PROTECTION;
	public static boolean SHOW_DIE_ANIMATION;
	public static boolean AUTO_FLAG_ON_ENTER;
	public static int MAX_SAME_TARGET_CONTINUOUS_KILLS;
	public static Map<Integer, Long> FLAG_ZONE_REWARDS;
	public static boolean ENABLE_FLAG_ZONE_AUTO_REVIVE;
	public static int FLAG_ZONE_REVIVE_DELAY;
	public static int FLAG_ZONE_AUTO_RES_LOCS_COUNT;
	public static int[] xCoords;
	public static int[] yCoords;
	public static int[] zCoords;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/zones/FlagZone.ini");
		
		ENABLE_FLAG_ZONE = Boolean.parseBoolean(getString(_settings, _override, "EnableFlagZone", "False"));
		ENABLE_ANTIFEED_PROTECTION = Boolean.parseBoolean(getString(_settings, _override, "EnableAntifeedProtection", "True"));
		ENABLE_PC_IP_PROTECTION = Boolean.parseBoolean(getString(_settings, _override, "EnablePcIpProtection", "True"));
		SHOW_DIE_ANIMATION = Boolean.parseBoolean(getString(_settings, _override, "ShowDieAnimation", "True"));
		AUTO_FLAG_ON_ENTER = Boolean.parseBoolean(getString(_settings, _override, "AutoFlagOnEnter", "True"));
		MAX_SAME_TARGET_CONTINUOUS_KILLS = Integer.parseInt(getString(_settings, _override, "MaxSameTargetContinuousKills", "3"));
		ENABLE_FLAG_ZONE_AUTO_REVIVE = Boolean.parseBoolean(getString(_settings, _override, "EnableFlagZoneAutoRes", "False"));
		FLAG_ZONE_REVIVE_DELAY = Integer.parseInt(getString(_settings, _override, "FlagZoneReviveDelay", "5"));
		FLAG_ZONE_AUTO_RES_LOCS_COUNT = Integer.parseInt(getString(_settings, _override, "FlagZoneAutoResLocsCount", "5"));
		
		String[] splitX = getString(_settings, _override, "AutoResXCoords", "1204;1035;1048").trim().split(";");
		xCoords = new int[splitX.length];
		try
		{
			int i = 0;
			for (String xCoord : splitX)
			{
				xCoords[i++] = Integer.parseInt(xCoord);
			}
		}
		catch (NumberFormatException nfe)
		{
			_log.warn(nfe.getMessage(), nfe);
		}
		
		String[] splitY = getString(_settings, _override, "AutoResYCoords", "1204;1035;1048").trim().split(";");
		yCoords = new int[splitY.length];
		try
		{
			int i = 0;
			for (String yCoord : splitY)
			{
				yCoords[i++] = Integer.parseInt(yCoord);
			}
		}
		catch (NumberFormatException nfe)
		{
			_log.warn(nfe.getMessage(), nfe);
		}
		
		String[] splitZ = getString(_settings, _override, "AutoResZCoords", "1204;1035;1048").trim().split(";");
		zCoords = new int[splitZ.length];
		try
		{
			int i = 0;
			for (String zCoord : splitZ)
			{
				zCoords[i++] = Integer.parseInt(zCoord);
			}
		}
		catch (NumberFormatException nfe)
		{
			_log.warn(nfe.getMessage(), nfe);
		}
		
		String[] rewardSplit = getString(_settings, _override, "FlagZoneRewards", "").split(";");
		FLAG_ZONE_REWARDS = new HashMap<>(rewardSplit.length);
		if (!rewardSplit[0].isEmpty())
		{
			for (String item : rewardSplit)
			{
				String[] itemSplit = item.split(",");
				if (itemSplit.length != 2)
				{
					_log.warn(getClass() + ": invalid config property -> FlagZoneRewards \"", item, "\"");
				}
				else
				{
					try
					{
						FLAG_ZONE_REWARDS.put(Integer.parseInt(itemSplit[0]), Long.parseLong(itemSplit[1]));
					}
					catch (NumberFormatException nfe)
					{
						if (!item.isEmpty())
						{
							_log.warn(getClass() + ": invalid config property -> FlagZoneRewards \"", item, "\"");
						}
					}
				}
			}
		}
	}
	
	public static FlagZoneConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final FlagZoneConfigs _instance = new FlagZoneConfigs();
	}
}