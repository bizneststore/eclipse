package gr.sr.configsEngine.configs.impl;

import gr.sr.configsEngine.AbstractConfigs;

public class ChaoticZoneConfigs extends AbstractConfigs
{
	// Chaotic Zone Settings
	public static boolean ENABLE_CHAOTIC_ZONE;
	public static boolean ENABLE_CHAOTIC_ZONE_AUTO_REVIVE;
	public static boolean ENABLE_CHAOTIC_ZONE_SKILL;
	public static int CHAOTIC_ZONE_SKILL_ID;
	public static int CHAOTIC_ZONE_REVIVE_DELAY;
	public static int CHAOTIC_ZONE_AUTO_RES_LOCS_COUNT;
	public static int[] xCoords;
	public static int[] yCoords;
	public static int[] zCoords;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/zones/ChaoticZone.ini");
		
		// Chaotic Zone Settings
		ENABLE_CHAOTIC_ZONE = Boolean.parseBoolean(getString(_settings, _override, "EnableChaoticZone", "False"));
		ENABLE_CHAOTIC_ZONE_AUTO_REVIVE = Boolean.parseBoolean(getString(_settings, _override, "EnableChaoticZoneAutoRes", "False"));
		ENABLE_CHAOTIC_ZONE_SKILL = Boolean.parseBoolean(getString(_settings, _override, "EnableChaoticZoneExtraSkill", "False"));
		CHAOTIC_ZONE_SKILL_ID = Integer.parseInt(getString(_settings, _override, "ChaoticZoneExtraSkillId", "26074"));
		CHAOTIC_ZONE_REVIVE_DELAY = Integer.parseInt(getString(_settings, _override, "ChaoticZoneReviveDelay", "5"));
		CHAOTIC_ZONE_AUTO_RES_LOCS_COUNT = Integer.parseInt(getString(_settings, _override, "ChaoticZoneAutoResLocsCount", "5"));
		
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
	}
	
	public static ChaoticZoneConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final ChaoticZoneConfigs _instance = new ChaoticZoneConfigs();
	}
}