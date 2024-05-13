package gr.sr.main;

import l2r.gameserver.instancemanager.CastleManager;
import l2r.gameserver.model.L2Clan;
import l2r.gameserver.model.L2World;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.entity.Castle;
import l2r.gameserver.network.SystemMessageId;
import l2r.gameserver.network.serverpackets.ExAutoSoulShot;
import l2r.gameserver.network.serverpackets.ExBrPremiumState;
import l2r.gameserver.network.serverpackets.ExPCCafePointInfo;
import l2r.gameserver.network.serverpackets.SystemMessage;
import l2r.gameserver.util.Broadcast;

import gr.sr.configsEngine.configs.impl.AioItemsConfigs;
import gr.sr.configsEngine.configs.impl.CustomServerConfigs;
import gr.sr.configsEngine.configs.impl.PcBangConfigs;
import gr.sr.configsEngine.configs.impl.SmartCommunityConfigs;
import gr.sr.pvpColorEngine.ColorSystemHandler;

public class EnterWorldCustomHandler
{
	public EnterWorldCustomHandler()
	{
		// Dummy default
	}
	
	/**
	 * Method to send extra message
	 * @param activeChar
	 */
	public void extraNotifies(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		if (CustomServerConfigs.ANNOUNCE_CASTLE_LORDS)
		{
			EnterWorldCustomHandler.getInstance().notifyCastleOwner(activeChar);
		}
		
		if (CustomServerConfigs.ANNOUNCE_HEROS_ON_LOGIN)
		{
			EnterWorldCustomHandler.getInstance().notifyHeros(activeChar);
		}
	}
	
	/**
	 * Method to check clan leader system
	 * @param activeChar
	 */
	public void clanLeaderSystem(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		// Clan Leader settings
		if (CustomServerConfigs.ALT_ALLOW_CLAN_LEADER_NAME)
		{
			if (!activeChar.isGM() && (activeChar.getClan() != null) && (activeChar.getClan().getLevel() >= CustomServerConfigs.CLAN_LEVEL_ACTIVATION) && activeChar.isClanLeader())
			{
				activeChar.getAppearance().setNameColor(Integer.decode("0x" + CustomServerConfigs.CLAN_LEADER_NAME_COLOR));
				activeChar.getAppearance().setTitleColor(Integer.decode("0x" + CustomServerConfigs.CLAN_LEADER_TITLE_COLOR));
			}
		}
	}
	
	/**
	 * Method to send extra messages on enter world
	 * @param activeChar
	 */
	public void extraMessages(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		int _allPlayers = L2World.getInstance().getAllPlayersCount();
		if (SmartCommunityConfigs.EXTRA_PLAYERS_COUNT > 0)
		{
			_allPlayers += SmartCommunityConfigs.EXTRA_PLAYERS_COUNT;
		}
		
		if (activeChar.isGM())
		{
			activeChar.sendMessage("Welcome Mister: " + activeChar.getName());
			activeChar.sendMessage("There are: " + _allPlayers + " players online");
		}
		else
		{
			// Enteworld announces
			if (CustomServerConfigs.EXTRA_MESSAGES)
			{
				activeChar.sendMessage("Welcome Mister: " + activeChar.getName());
				activeChar.sendMessage("PvP Kills: " + activeChar.getPvpKills());
				activeChar.sendMessage("PK Kills: " + activeChar.getPkKills());
				activeChar.sendMessage("There are: " + _allPlayers + " players online");
			}
		}
	}
	
	/**
	 * Method to give extra items on enter world
	 * @param activeChar
	 */
	public void extraItemsCheck(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		int hellboundMapId = 9994;
		
		if (CustomServerConfigs.GIVE_HELLBOUND_MAP)
		{
			if (activeChar.getInventory().getItemByItemId(hellboundMapId) == null)
			{
				activeChar.addItem("Hellbound Map", hellboundMapId, 1, activeChar, true);
			}
		}
		else
		{
			if ((activeChar.getInventory().getItemByItemId(hellboundMapId) != null) && (activeChar.getInventory().getItemByItemId(hellboundMapId).getCount() > 0))
			{
				activeChar.destroyItemByItemId("Hellbound Map", hellboundMapId, activeChar.getInventory().getItemByItemId(hellboundMapId).getCount(), activeChar, true);
			}
		}
		
		if (AioItemsConfigs.DESTROY_ON_DISABLE && !AioItemsConfigs.ENABLE_AIO_NPCS)
		{
			if ((activeChar.getInventory().getItemByItemId(AioItemsConfigs.AIO_ITEM_ID) != null) && (activeChar.getInventory().getItemByItemId(AioItemsConfigs.AIO_ITEM_ID).getCount() > 0))
			{
				activeChar.destroyItemByItemId("AIO Item", AioItemsConfigs.AIO_ITEM_ID, activeChar.getInventory().getItemByItemId(AioItemsConfigs.AIO_ITEM_ID).getCount(), activeChar, true);
			}
		}
		
		if (AioItemsConfigs.GIVEANDCHECK_ATSTARTUP && AioItemsConfigs.ENABLE_AIO_NPCS)
		{
			if (activeChar.getInventory().getItemByItemId(AioItemsConfigs.AIO_ITEM_ID) == null)
			{
				activeChar.addItem("AIO Item", AioItemsConfigs.AIO_ITEM_ID, 1, activeChar, true);
			}
		}
	}
	
	/**
	 * Method to check bots
	 * @param activeChar
	 */
	public void checkIfBot(L2PcInstance activeChar)
	{
		if (!PlayerValues.isPlayer())
		{
			activeChar.getClient().closeNow();
		}
	}
	
	/**
	 * Method to check premium and pc bang system
	 * @param activeChar
	 */
	public void checkPremiumAndPcBangSystems(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		// Premium State
		if (activeChar.isPremium())
		{
			activeChar.sendPacket(new ExBrPremiumState(activeChar.getObjectId(), 1));
			activeChar.sendMessage("Premium account: Activated.");
		}
		else
		{
			activeChar.sendPacket(new ExBrPremiumState(activeChar.getObjectId(), 0));
		}
		
		if (PcBangConfigs.PC_BANG_ENABLED)
		{
			if (activeChar.getPcBangPoints() > 0)
			{
				activeChar.sendPacket(new ExPCCafePointInfo(activeChar.getPcBangPoints(), 0, false, false, 1));
			}
			else
			{
				activeChar.sendPacket(new ExPCCafePointInfo());
			}
		}
	}
	
	/**
	 * Method to check if auto soulshot on enter world is active
	 * @param activeChar
	 */
	public void checkAutoSoulshot(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		if (CustomServerConfigs.AUTO_ACTIVATE_SHOTS && activeChar.getVarB("onEnterLoadSS"))
		{
			verifyAndLoadShots(activeChar);
		}
	}
	
	/**
	 * Method to show castle lord name on enter world
	 * @param activeChar
	 */
	public void notifyCastleOwner(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		L2Clan clan = activeChar.getClan();
		
		if (clan != null)
		{
			if (clan.getCastleId() > 0)
			{
				Castle castle = CastleManager.getInstance().getCastleById(clan.getCastleId());
				if ((castle != null) && (activeChar.getObjectId() == clan.getLeaderId()))
				{
					Broadcast.toAllOnlinePlayers(activeChar.getName() + " lord of " + castle.getName() + " logged in!");
				}
			}
		}
	}
	
	/**
	 * Method to show hero name on enter world
	 * @param activeChar
	 */
	public void notifyHeros(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		if (!activeChar.isGM() && activeChar.isHero())
		{
			Broadcast.toAllOnlinePlayers("Hero " + activeChar.getName() + " logged in!");
		}
	}
	
	/**
	 * This method will get the correct soulshot/spirishot and activate it for the current weapon if it's over the minimum.
	 * @param activeChar
	 */
	public void verifyAndLoadShots(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		
		int soulId = -1;
		int spiritId = -1;
		int bspiritId = -1;
		
		if (!activeChar.isDead() && (activeChar.getActiveWeaponItem() != null))
		{
			switch (activeChar.getActiveWeaponItem().getCrystalType())
			{
				case NONE:
					soulId = 1835;
					spiritId = 2509;
					bspiritId = 3947;
					break;
				case D:
					soulId = 1463;
					spiritId = 2510;
					bspiritId = 3948;
					break;
				case C:
					soulId = 1464;
					spiritId = 2511;
					bspiritId = 3949;
					break;
				case B:
					soulId = 1465;
					spiritId = 2512;
					bspiritId = 3950;
					break;
				case A:
					soulId = 1466;
					spiritId = 2513;
					bspiritId = 3951;
					break;
				case S:
				case S80:
				case S84:
					soulId = 1467;
					spiritId = 2514;
					bspiritId = 3952;
					break;
				default:
					break;
			}
			
			// Soulshots.
			if ((soulId > -1) && (activeChar.getInventory().getInventoryItemCount(soulId, -1) > CustomServerConfigs.AUTO_ACTIVATE_SHOTS_MIN))
			{
				activeChar.addAutoSoulShot(soulId);
				activeChar.sendPacket(new ExAutoSoulShot(soulId, 1));
				// Message
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.USE_OF_S1_WILL_BE_AUTO);
				sm.addItemName(activeChar.getInventory().getItemByItemId(soulId));
				activeChar.sendPacket(sm);
			}
			
			// Blessed Spirishots first, then Spirishots.
			if ((bspiritId > -1) && (activeChar.getInventory().getInventoryItemCount(bspiritId, -1) > CustomServerConfigs.AUTO_ACTIVATE_SHOTS_MIN))
			{
				activeChar.addAutoSoulShot(bspiritId);
				activeChar.sendPacket(new ExAutoSoulShot(bspiritId, 1));
				// Message
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.USE_OF_S1_WILL_BE_AUTO);
				sm.addItemName(activeChar.getInventory().getItemByItemId(bspiritId));
				activeChar.sendPacket(sm);
			}
			else if ((spiritId > -1) && (activeChar.getInventory().getInventoryItemCount(spiritId, -1) > CustomServerConfigs.AUTO_ACTIVATE_SHOTS_MIN))
			{
				activeChar.addAutoSoulShot(spiritId);
				activeChar.sendPacket(new ExAutoSoulShot(spiritId, 1));
				// Message
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.USE_OF_S1_WILL_BE_AUTO);
				sm.addItemName(activeChar.getInventory().getItemByItemId(spiritId));
				activeChar.sendPacket(sm);
			}
			
			activeChar.rechargeShots(true, true);
		}
	}
	
	/**
	 * Method to check bots
	 * @param activeChar
	 */
	public void initializeColorSystem(L2PcInstance activeChar)
	{
		checkIfBot(activeChar);
		ColorSystemHandler.getInstance().updateColor(activeChar);
	}
	
	public static EnterWorldCustomHandler getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final EnterWorldCustomHandler _instance = new EnterWorldCustomHandler();
	}
}