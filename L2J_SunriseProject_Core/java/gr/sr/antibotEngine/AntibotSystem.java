package gr.sr.antibotEngine;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.instancemanager.PunishmentManager;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.actor.instance.L2GrandBossInstance;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.actor.instance.L2RaidBossInstance;
import l2r.gameserver.model.effects.AbnormalEffect;
import l2r.gameserver.model.punishment.PunishmentAffect;
import l2r.gameserver.model.punishment.PunishmentTask;
import l2r.gameserver.model.punishment.PunishmentType;
import l2r.util.Rnd;

import gr.sr.antibotEngine.dynamicHtmls.GenerateHtmls;
import gr.sr.antibotEngine.runnable.EnchantChance;
import gr.sr.antibotEngine.runnable.JailTimer;
import gr.sr.configsEngine.configs.impl.AntibotConfigs;

/**
 * @author vGodFather
 */
public class AntibotSystem
{
	private AntibotSystem()
	{
		// Dummy default
	}
	
	// ================================================================================= //
	// ============================== Farm Antibot System ============================== //
	// ================================================================================= //
	public static void sendFarmBotSignal(L2Character killer)
	{
		if (AntibotConfigs.ENABLE_ANTIBOT_SYSTEMS && AntibotConfigs.ENABLE_ANTIBOT_FARM_SYSTEM && (killer != null))
		{
			L2PcInstance player = killer.getActingPlayer();
			if ((player != null))
			{
				if (AntibotConfigs.ENABLE_ANTIBOT_FOR_GMS)
				{
					AntibotSystem.antibotFarmSystem(player);
				}
				else if (!AntibotConfigs.ENABLE_ANTIBOT_FOR_GMS && !player.isGM())
				{
					AntibotSystem.antibotFarmSystem(player);
				}
			}
		}
	}
	
	private static void antibotFarmSystem(L2PcInstance player)
	{
		if ((player.getTarget() == null) || !player.getTarget().isMonster())
		{
			return;
		}
		
		if (((player.getTarget() instanceof L2RaidBossInstance) || (player.getTarget() instanceof L2GrandBossInstance)) && !AntibotConfigs.ENABLE_ANTIBOT_FARM_SYSTEM_ON_RAIDS)
		{
			return;
		}
		
		switch (AntibotConfigs.ANTIBOT_FARM_TYPE)
		{
			case 0: // count mobs
				if (AntibotConfigs.ENABLE_ANTIBOT_SPECIFIC_MOBS)
				{
					if (AntibotConfigs.ANTIBOT_FARM_MOBS_IDS.contains(player.getTarget().getId()))
					{
						increaseMobCounter(player);
					}
				}
				else
				{
					increaseMobCounter(player);
				}
				break;
			case 1: // create a chance by each kill
				if (AntibotConfigs.ENABLE_ANTIBOT_SPECIFIC_MOBS)
				{
					if (AntibotConfigs.ANTIBOT_FARM_MOBS_IDS.contains(player.getTarget().getId()))
					{
						randomMobChance(player);
					}
				}
				else
				{
					randomMobChance(player);
				}
				break;
		}
	}
	
	private static void increaseMobCounter(L2PcInstance player)
	{
		// +1 to mobcounter
		player.setKills(player.getKills() + 1);
		
		// Checks the mobcounter
		// getkills() == when the popup window show
		if ((player.getKills() == AntibotConfigs.ANTIBOT_MOB_COUNTER) || (player.getKills() == 0))
		{
			antibotFarmProcedure(player, false);
		}
	}
	
	private static void randomMobChance(L2PcInstance player)
	{
		// Checks the mob chance
		if (Rnd.get(1000) <= (AntibotConfigs.ANTIBOT_FARM_CHANCE * 10))
		{
			antibotFarmProcedure(player, false);
		}
	}
	
	private static void antibotFarmProcedure(L2PcInstance player, boolean end)
	{
		if (end)
		{
			player.stopAbnormalEffect(AbnormalEffect.REAL_TARGET);
			player.setIsInvul(false);
			player.setIsParalyzed(false);
			
			if (player.hasSummon())
			{
				player.getSummon().setIsParalyzed(false);
				player.getSummon().setIsInvul(false);
			}
			
			player.setFarmBot(false);
			player.setTries(3);
			player.setKills(0);
			
			if (player._jailTimer != null)
			{
				player._jailTimer.cancel(true);
			}
		}
		else
		{
			GenerateHtmls.captchaHtml(player, "FARM");
			player.startAbnormalEffect(AbnormalEffect.REAL_TARGET);
			player.setIsParalyzed(true);
			player.setIsInvul(true);
			if (player.hasSummon())
			{
				player.getSummon().setIsParalyzed(true);
				player.getSummon().setIsInvul(true);
			}
			player._jailTimer = ThreadPoolManager.getInstance().scheduleGeneral(new JailTimer(player), AntibotConfigs.JAIL_TIMER * 1000);
			player.setFarmBot(true);
		}
	}
	
	public static void jailPlayer(L2PcInstance player, String reason)
	{
		PunishmentManager.getInstance().startPunishment(new PunishmentTask(player.getObjectId(), PunishmentAffect.CHARACTER, PunishmentType.JAIL, System.currentTimeMillis() + (AntibotConfigs.TIME_TO_SPEND_IN_JAIL * 1000), "", AntibotSystem.class.getSimpleName()));
		antibotFarmProcedure(player, true);
		
		switch (reason)
		{
			case "time":
				player.sendMessage(+AntibotConfigs.JAIL_TIMER + " second(s) passed, punish jail.");
				break;
			case "tries":
				player.sendMessage("You have wasted your tries, punish jail.");
				break;
			default:
				break;
		}
	}
	
	public static void refreshImage(L2PcInstance activeChar, boolean decreaseTries, boolean farmBot)
	{
		if (farmBot)
		{
			if (decreaseTries)
			{
				activeChar.setTries(activeChar.getTries() - 1);
				activeChar.sendMessage("Wrong captcha code or bot answer, try again!");
			}
			
			if (activeChar.getTries() > 0)
			{
				GenerateHtmls.captchaHtml(activeChar, "FARM");
			}
			else
			{
				// here will run method to jail player
				AntibotSystem.jailPlayer(activeChar, "tries");
			}
		}
		else
		{
			GenerateHtmls.captchaHtml(activeChar, "ENCHANT");
		}
	}
	
	public static void checkFarmCaptchaCode(L2PcInstance activeChar, String boxCode, String botAnswer)
	{
		String playerCode = activeChar.getFarmBotCode();
		String playerBotAnswer = activeChar.getBotAnswer();
		
		if (boxCode.equals(playerCode)) // Right:)
		{
			if (AntibotConfigs.ENABLE_DOUBLE_PROTECTION)
			{
				if (botAnswer.equals(playerBotAnswer))
				{
					activeChar.sendMessage("Captcha code accepted.");
					antibotFarmProcedure(activeChar, true);
					return;
				}
			}
			
			activeChar.sendMessage("Captcha code accepted.");
			antibotFarmProcedure(activeChar, true);
			return;
		}
		
		if (AntibotConfigs.ENABLE_DOUBLE_PROTECTION)
		{
			if (!botAnswer.equals(playerBotAnswer))
			{
				refreshImage(activeChar, true, true);
				return;
			}
		}
		
		if (!boxCode.equals(playerCode)) // Wrong
		{
			refreshImage(activeChar, true, true);
			return;
		}
	}
	
	// ================================================================================== //
	// ============================= Enchant Antibot System ============================= //
	// ================================================================================== //
	public static void sendEnchantBotSignal(L2PcInstance player)
	{
		if (AntibotConfigs.ENABLE_ANTIBOT_SYSTEMS && AntibotConfigs.ENABLE_ANTIBOT_ENCHANT_SYSTEM)
		{
			if (!player.isEnchantBot())
			{
				if (AntibotConfigs.ENABLE_ANTIBOT_FOR_GMS)
				{
					AntibotSystem.antibotEnchantSystem(player);
				}
				else if (!AntibotConfigs.ENABLE_ANTIBOT_FOR_GMS && !player.isGM())
				{
					AntibotSystem.antibotEnchantSystem(player);
				}
			}
		}
	}
	
	public static void checkOnEnterBot(final L2PcInstance activeChar)
	{
		if (activeChar.isEnchantBot() && AntibotConfigs.ENABLE_ANTIBOT_SYSTEMS && AntibotConfigs.ENABLE_ANTIBOT_ENCHANT_SYSTEM)
		{
			ThreadPoolManager.getInstance().scheduleGeneral(() -> activeChar._enchantChanceTimer = ThreadPoolManager.getInstance().scheduleGeneral(new EnchantChance(activeChar), 1000), 15000);
		}
	}
	
	public static void antibotEnchantSystem(final L2PcInstance activeChar)
	{
		if (AntibotConfigs.ANTIBOT_ENCHANT_TYPE == 0)
		{
			activeChar.setEnchants(activeChar.getEnchants() + 1);
			
			if ((activeChar.getEnchants() == 0) || (activeChar.getEnchants() == AntibotConfigs.ANTIBOT_ENCHANT_COUNTER))
			{
				startAntibotEnchantProcedure(activeChar);
			}
		}
		if (AntibotConfigs.ANTIBOT_ENCHANT_TYPE == 1)
		{
			Rnd.chance(AntibotConfigs.ANTIBOT_ENCHANT_CHANCE);
			{
				startAntibotEnchantProcedure(activeChar);
			}
		}
	}
	
	private static void startAntibotEnchantProcedure(L2PcInstance activeChar)
	{
		activeChar._enchantChanceTimer = ThreadPoolManager.getInstance().scheduleGeneral(new EnchantChance(activeChar), 1000);
		activeChar.setEnchantBot(true);
	}
	
	public static void checkEnchantCaptchaCode(L2PcInstance activeChar, String boxCode, String botAnswer)
	{
		String playerCode = activeChar.getEnchantBotCode();
		String playerBotAnswer = activeChar.getBotAnswer();
		
		if (boxCode.equals(playerCode)) // Right:)
		{
			if (AntibotConfigs.ENABLE_DOUBLE_PROTECTION)
			{
				if (botAnswer.equals(playerBotAnswer))
				{
					activeChar.sendMessage("Captcha code accepted.");
					activeChar.sendMessage("Enchant chance is normal again!");
					activeChar.setEnchants(0);
					activeChar.setEnchantChance(AntibotConfigs.ENCHANT_CHANCE_PERCENT_TO_START);
					activeChar.setEnchantBot(false);
					
					if (activeChar._enchantChanceTimer != null)
					{
						activeChar._enchantChanceTimer.cancel(true);
					}
					
					return;
				}
			}
			
			activeChar.sendMessage("Captcha code accepted.");
			activeChar.sendMessage("Enchant chance is normal again!");
			activeChar.setEnchants(0);
			activeChar.setEnchantChance(AntibotConfigs.ENCHANT_CHANCE_PERCENT_TO_START);
			activeChar.setEnchantBot(false);
			
			if (activeChar._enchantChanceTimer != null)
			{
				activeChar._enchantChanceTimer.cancel(true);
			}
			
			return;
		}
		
		if (AntibotConfigs.ENABLE_DOUBLE_PROTECTION)
		{
			if (!botAnswer.equals(playerBotAnswer))
			{
				activeChar.sendMessage("Wrong bot answer.");
				GenerateHtmls.captchaHtml(activeChar, "ENCHANT");
				return;
			}
		}
		
		if (!boxCode.equals(playerCode)) // Wrong
		{
			GenerateHtmls.captchaHtml(activeChar, "ENCHANT");
			return;
		}
	}
}