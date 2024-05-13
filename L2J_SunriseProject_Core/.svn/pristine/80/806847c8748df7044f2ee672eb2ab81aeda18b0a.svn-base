package gr.sr.configsEngine;

import gr.sr.configsEngine.configs.impl.AioItemsConfigs;
import gr.sr.configsEngine.configs.impl.AntibotConfigs;
import gr.sr.configsEngine.configs.impl.AutoRestartConfigs;
import gr.sr.configsEngine.configs.impl.BackupManagerConfigs;
import gr.sr.configsEngine.configs.impl.BufferConfigs;
import gr.sr.configsEngine.configs.impl.ChaoticZoneConfigs;
import gr.sr.configsEngine.configs.impl.ColorSystemConfigs;
import gr.sr.configsEngine.configs.impl.CommunityDonateConfigs;
import gr.sr.configsEngine.configs.impl.CommunityServicesConfigs;
import gr.sr.configsEngine.configs.impl.CustomNpcsConfigs;
import gr.sr.configsEngine.configs.impl.CustomServerConfigs;
import gr.sr.configsEngine.configs.impl.DonateManagerConfigs;
import gr.sr.configsEngine.configs.impl.FlagZoneConfigs;
import gr.sr.configsEngine.configs.impl.FormulasConfigs;
import gr.sr.configsEngine.configs.impl.LeaderboardsConfigs;
import gr.sr.configsEngine.configs.impl.PcBangConfigs;
import gr.sr.configsEngine.configs.impl.PremiumServiceConfigs;
import gr.sr.configsEngine.configs.impl.PvpRewardSystemConfigs;
import gr.sr.configsEngine.configs.impl.SecuritySystemConfigs;
import gr.sr.configsEngine.configs.impl.SmartCommunityConfigs;

public class ConfigsController
{
	public ConfigsController()
	{
		// Dummy default
	}
	
	public void reloadSunriseConfigs()
	{
		AioItemsConfigs.getInstance().loadConfigs();
		AntibotConfigs.getInstance().loadConfigs();
		AutoRestartConfigs.getInstance().loadConfigs();
		BackupManagerConfigs.getInstance().loadConfigs();
		BufferConfigs.getInstance().loadConfigs();
		ChaoticZoneConfigs.getInstance().loadConfigs();
		ColorSystemConfigs.getInstance().loadConfigs();
		CommunityDonateConfigs.getInstance().loadConfigs();
		CommunityServicesConfigs.getInstance().loadConfigs();
		CustomNpcsConfigs.getInstance().loadConfigs();
		CustomServerConfigs.getInstance().loadConfigs();
		DonateManagerConfigs.getInstance().loadConfigs();
		FlagZoneConfigs.getInstance().loadConfigs();
		FormulasConfigs.getInstance().loadConfigs();
		LeaderboardsConfigs.getInstance().loadConfigs();
		PcBangConfigs.getInstance().loadConfigs();
		PremiumServiceConfigs.getInstance().loadConfigs();
		PvpRewardSystemConfigs.getInstance().loadConfigs();
		SecuritySystemConfigs.getInstance().loadConfigs();
		SmartCommunityConfigs.getInstance().loadConfigs();
	}
	
	public static ConfigsController getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final ConfigsController _instance = new ConfigsController();
	}
}