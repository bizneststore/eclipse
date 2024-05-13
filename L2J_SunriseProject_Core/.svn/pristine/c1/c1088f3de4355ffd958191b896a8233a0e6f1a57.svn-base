package gr.sr.antibotEngine.runnable;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.antibotEngine.dynamicHtmls.GenerateHtmls;
import gr.sr.configsEngine.configs.impl.AntibotConfigs;

public class EnchantChance implements Runnable
{
	L2PcInstance activeChar;
	
	public EnchantChance(L2PcInstance player)
	{
		activeChar = player;
	}
	
	@Override
	public void run()
	{
		if (activeChar.isEnchantBot())
		{
			GenerateHtmls.captchaHtml(activeChar, "ENCHANT");
			activeChar._enchantChanceTimer = ThreadPoolManager.getInstance().scheduleGeneral(new EnchantChance(activeChar), AntibotConfigs.ENCHANT_CHANCE_TIMER * 1000);
			if ((activeChar.getEnchantChance() > 10))
			{
				activeChar.setEnchantChance(activeChar.getEnchantChance() - AntibotConfigs.ENCHANT_CHANCE_PERCENT_TO_LOW); // tmp solution till we have configs
			}
		}
	}
}