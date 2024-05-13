package gr.sr.securityEngine;

import l2r.Config;
import l2r.gameserver.instancemanager.PunishmentManager;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.punishment.PunishmentAffect;
import l2r.gameserver.model.punishment.PunishmentTask;
import l2r.gameserver.model.punishment.PunishmentType;
import l2r.gameserver.util.Broadcast;
import l2r.gameserver.util.Util;

import gr.sr.configsEngine.configs.impl.SecuritySystemConfigs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vGodFather
 */
public class SecurityActions
{
	private SecurityActions()
	{
		// Dummy default
	}
	
	private static Logger _log = LoggerFactory.getLogger(SecurityActions.class);
	
	public static void startSecurity(L2PcInstance player, SecurityType type)
	{
		if (!SecuritySystemConfigs.ENABLE_SECURITY_SYSTEM)
		{
			return;
		}
		
		String reason = "";
		String punishLevel = "";
		switch (type)
		{
			case ACHIEVEMENT_SYSTEM:
				reason = SecurityType.ACHIEVEMENT_SYSTEM.getText();
				punishLevel = "High";
				break;
			case AIO_ITEM:
				reason = SecurityType.AIO_ITEM.getText();
				punishLevel = "High";
				break;
			case COMMUNITY_SYSTEM:
				reason = SecurityType.COMMUNITY_SYSTEM.getText();
				punishLevel = "High";
				break;
			case AIO_ITEM_BUFFER:
				reason = SecurityType.AIO_ITEM_BUFFER.getText();
				punishLevel = "High";
				break;
			case AIO_NPC:
				reason = SecurityType.AIO_NPC.getText();
				punishLevel = "High";
				break;
			case NPC_BUFFER:
				reason = SecurityType.NPC_BUFFER.getText();
				punishLevel = "High";
				break;
			case CUSTON_GATEKEEPER:
				reason = SecurityType.CUSTON_GATEKEEPER.getText();
				punishLevel = "High";
				break;
			case DONATE_MANAGER:
				reason = SecurityType.DONATE_MANAGER.getText();
				punishLevel = "High";
				break;
			case VOTE_SYSTEM:
				reason = SecurityType.VOTE_SYSTEM.getText();
				punishLevel = "High";
				break;
			case ENCHANT_EXPLOIT:
				reason = SecurityType.ENCHANT_EXPLOIT.getText();
				punishLevel = "High";
				break;
			case ANTIBOT_SYSTEM:
				reason = SecurityType.ANTIBOT_SYSTEM.getText();
				punishLevel = "Low";
			default:
				break;
		}
		callSecurity(player, reason, punishLevel);
	}
	
	private static void callSecurity(L2PcInstance player, String reason, String punishLevel)
	{
		// If Audit is only a Kick, with this the player goes in Jail
		switch (punishLevel)
		{
			case "Low":
				sendMessages(player, true);
				break;
			case "Mid":
				sendMessages(player, true);
				PunishmentManager.getInstance().startPunishment(new PunishmentTask(player.getObjectId(), PunishmentAffect.CHARACTER, PunishmentType.JAIL, System.currentTimeMillis() + (SecuritySystemConfigs.TIME_IN_JAIL_MID * 1000), "", SecurityActions.class.getSimpleName()));
				break;
			case "High":
				sendMessages(player, true);
				sendMessages(player, false);
				PunishmentManager.getInstance().startPunishment(new PunishmentTask(player.getObjectId(), PunishmentAffect.CHARACTER, PunishmentType.JAIL, System.currentTimeMillis() + (SecuritySystemConfigs.TIME_IN_JAIL_HIGH * 1000), "", SecurityActions.class.getSimpleName()));
				break;
			default:
				break;
		}
		
		// Generate logs to console and audit
		Util.handleIllegalPlayerAction(player, "Player: " + player.getName() + " might use third party program to exploit " + reason + ".", Config.DEFAULT_PUNISH);
		_log.warn("#### ATTENCTION ####");
		_log.warn(player.getName() + " might use third party program to exploit " + reason + ".");
	}
	
	private static void sendMessages(L2PcInstance player, boolean self)
	{
		if (self)
		{
			if (SecuritySystemConfigs.ENABLE_MESSAGE_TO_PLAYER)
			{
				PunishmentManager.getInstance().startPunishment(new PunishmentTask(player.getObjectId(), PunishmentAffect.CHARACTER, PunishmentType.JAIL, System.currentTimeMillis() + (SecuritySystemConfigs.TIME_IN_JAIL_LOW * 1000), "", SecurityActions.class.getSimpleName()));
			}
		}
		else
		{
			if (SecuritySystemConfigs.ENABLE_GLOBAL_ANNOUNCE)
			{
				Broadcast.toAllOnlinePlayers("Security System: " + player.getName() + " " + SecuritySystemConfigs.ANNOUNCE_TO_SEND, true);
			}
		}
	}
}