package gr.sr.javaBuffer;

import java.util.List;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.data.xml.impl.SkillData;
import l2r.gameserver.model.actor.L2Summon;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.skills.L2Skill;

import gr.sr.configsEngine.configs.impl.BufferConfigs;
import gr.sr.javaBuffer.runnable.BuffSaver;
import gr.sr.javaBuffer.xml.dataHolder.BuffsHolder;
import gr.sr.securityEngine.SecurityActions;
import gr.sr.securityEngine.SecurityType;

public class JavaBufferBypass
{
	public static void callBuffCommand(L2PcInstance player, String buffId, String returnPage, int objectId)
	{
		if (!PlayerMethods.checkDelay(player))
		{
			return;
		}
		
		if (!PlayerMethods.checkPriceConsume(player, 1))
		{
			return;
		}
		
		int _buffId = Integer.parseInt(buffId);
		BuffsInstance buff = BuffsHolder.getInstance().getBuff(_buffId);
		if (buff == null)
		{
			switch (objectId)
			{
				case 0:
					SecurityActions.startSecurity(player, SecurityType.AIO_ITEM_BUFFER);
					break;
				case 1:
					SecurityActions.startSecurity(player, SecurityType.COMMUNITY_SYSTEM);
					break;
				default:
					SecurityActions.startSecurity(player, SecurityType.NPC_BUFFER);
					break;
			}
			return;
		}
		
		L2Skill skill = (player.getInventory().getItemByItemId(BufferConfigs.DONATE_BUFF_ITEM_ID) != null) || player.isPremium() ? SkillData.getInstance().getInfo(buff.getId(), buff.getCustomLevel()) : SkillData.getInstance().getInfo(buff.getId(), buff.getLevel());
		if (skill == null)
		{
			switch (objectId)
			{
				case 0:
					SecurityActions.startSecurity(player, SecurityType.AIO_ITEM_BUFFER);
					break;
				case 1:
					SecurityActions.startSecurity(player, SecurityType.COMMUNITY_SYSTEM);
					break;
				default:
					SecurityActions.startSecurity(player, SecurityType.NPC_BUFFER);
					break;
			}
			return;
		}
		
		skill.getEffects(player, player);
		returnPage(player, returnPage, objectId);
		PlayerMethods.addDelay(player);
	}
	
	public static void callPetBuffCommand(L2PcInstance player, String buffId)
	{
		List<Integer> buffIds = PlayerMethods.getProfileBuffs(buffId, player);
		int buffsCount = buffIds.size();
		
		if (!PlayerMethods.checkDelay(player))
		{
			return;
		}
		
		L2Summon summon = player.getSummon();
		if (summon == null)
		{
			player.sendMessage("Summon your pet first.");
			return;
		}
		
		if (!PlayerMethods.checkPriceConsume(player, buffsCount))
		{
			return;
		}
		
		for (int id : buffIds)
		{
			BuffsInstance buff = BuffsHolder.getInstance().getBuff(id);
			
			if (!player.isInsideRadius(summon, 300, false, false) || (buff == null))
			{
				continue;
			}
			
			L2Skill skill = (player.getInventory().getItemByItemId(BufferConfigs.DONATE_BUFF_ITEM_ID) != null) || player.isPremium() ? SkillData.getInstance().getInfo(buff.getId(), buff.getCustomLevel()) : SkillData.getInstance().getInfo(buff.getId(), buff.getLevel());
			if (skill == null)
			{
				continue;
			}
			
			skill.getEffects(player, summon);
		}
		
		if (BufferConfigs.HEAL_PLAYER_AFTER_ACTIONS)
		{
			player.getSummon().setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
			player.getSummon().setCurrentCp(player.getMaxCp());
		}
		PlayerMethods.addDelay(player);
	}
	
	public static void callPartyBuffCommand(L2PcInstance player, String buffId)
	{
		List<Integer> buffIds = PlayerMethods.getProfileBuffs(buffId, player);
		int buffsCount = buffIds.size();
		
		if (!PlayerMethods.checkDelay(player))
		{
			return;
		}
		
		if (player.getParty() == null)
		{
			player.sendMessage("Your are not in a party.");
			return;
		}
		
		if (!PlayerMethods.checkPriceConsume(player, buffsCount))
		{
			return;
		}
		
		for (L2PcInstance member : player.getParty().getMembers())
		{
			if (!player.isInsideRadius(member, 300, false, false))
			{
				continue;
			}
			
			for (int id : buffIds)
			{
				BuffsInstance buff = BuffsHolder.getInstance().getBuff(id);
				
				if (buff == null)
				{
					continue;
				}
				
				L2Skill skill = (player.getInventory().getItemByItemId(BufferConfigs.DONATE_BUFF_ITEM_ID) != null) || player.isPremium() ? SkillData.getInstance().getInfo(buff.getId(), buff.getCustomLevel()) : SkillData.getInstance().getInfo(buff.getId(), buff.getLevel());
				if (skill == null)
				{
					continue;
				}
				
				skill.getEffects(member, member);
			}
			
			if (BufferConfigs.HEAL_PLAYER_AFTER_ACTIONS)
			{
				member.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
				member.setCurrentCp(player.getMaxCp());
			}
		}
		PlayerMethods.addDelay(player);
	}
	
	public static void callSelfBuffCommand(L2PcInstance player, String buffId)
	{
		List<Integer> buffIds = PlayerMethods.getProfileBuffs(buffId, player);
		if (buffIds == null)
		{
			return;
		}
		
		int buffsCount = buffIds.size();
		
		if (!PlayerMethods.checkPriceConsume(player, buffsCount))
		{
			return;
		}
		
		for (int id : buffIds)
		{
			BuffsInstance buff = BuffsHolder.getInstance().getBuff(id);
			
			if (buff == null)
			{
				continue;
			}
			
			L2Skill skill = (player.getInventory().getItemByItemId(BufferConfigs.DONATE_BUFF_ITEM_ID) != null) || player.isPremium() ? SkillData.getInstance().getInfo(buff.getId(), buff.getCustomLevel()) : SkillData.getInstance().getInfo(buff.getId(), buff.getLevel());
			if (skill == null)
			{
				continue;
			}
			
			skill.getEffects(player, player);
		}
		
		if (BufferConfigs.HEAL_PLAYER_AFTER_ACTIONS)
		{
			player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
			player.setCurrentCp(player.getMaxCp());
		}
		PlayerMethods.addDelay(player);
	}
	
	// Scheme methods
	public static void callSaveProfile(L2PcInstance player, String profile, int objectId)
	{
		if (!PlayerMethods.createProfile(profile, player))
		{
			return;
		}
		
		switch (objectId)
		{
			case 0:
				BufferPacketSender.sendPacket(player, "scheme.htm", BufferPacketCategories.FILE, 0);
				break;
			case 1:
				BufferPacketSender.sendPacket(player, "scheme.htm", BufferPacketCategories.COMMUNITY, 1);
				break;
			default:
				BufferPacketSender.sendPacket(player, "scheme.htm", BufferPacketCategories.FILE, objectId);
				break;
		}
	}
	
	public static void callAvailableCommand(L2PcInstance player, String category, String profile, int objectId)
	{
		switch (category)
		{
			case "showAvaliableDwarf":
				switch (objectId)
				{
					case 0:
						gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.DWARF, "addDwarf");
						break;
					case 1:
						gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.DWARF, "addDwarf");
						break;
					default:
						gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.DWARF, "addDwarf", objectId);
						break;
				}
				break;
			case "showAvaliableMisc":
				switch (objectId)
				{
					case 0:
						gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.MISC, "addMisc");
						break;
					case 1:
						gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.MISC, "addMisc");
						break;
					default:
						gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.MISC, "addMisc", objectId);
						break;
				}
				break;
			case "showAvaliableElder":
				switch (objectId)
				{
					case 0:
						gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.ELDER, "addElder");
						break;
					case 1:
						gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.ELDER, "addElder");
						break;
					default:
						gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.ELDER, "addElder", objectId);
						break;
				}
				break;
			case "showAvaliableChant":
				switch (objectId)
				{
					case 0:
						gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.CHANT, "addChant");
						break;
					case 1:
						gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.CHANT, "addChant");
						break;
					default:
						gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.CHANT, "addChant", objectId);
						break;
				}
				break;
			case "showAvaliableOver":
				switch (objectId)
				{
					case 0:
						gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.OVERLORD, "addOver");
						break;
					case 1:
						gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.OVERLORD, "addOver");
						break;
					default:
						gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.OVERLORD, "addOver", objectId);
						break;
				}
				break;
			case "showAvaliableProp":
				switch (objectId)
				{
					case 0:
						gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.PROPHET, "addProp");
						break;
					case 1:
						gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.PROPHET, "addProp");
						break;
					default:
						gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.PROPHET, "addProp", objectId);
						break;
				}
				break;
			case "showAvaliableDance":
				switch (objectId)
				{
					case 0:
						gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.DANCE, "addDance");
						break;
					case 1:
						gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.DANCE, "addDance");
						break;
					default:
						gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.DANCE, "addDance", objectId);
						break;
				}
				break;
			case "showAvaliableSong":
				switch (objectId)
				{
					case 0:
						gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.SONG, "addSong");
						break;
					case 1:
						gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.SONG, "addSong");
						break;
					default:
						gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, BufferMenuCategories.SONG, "addSong", objectId);
						break;
				}
				break;
		}
	}
	
	/**
	 * Method to separate buff categories
	 * @param category
	 * @param player
	 * @param profile
	 * @param objectId
	 */
	public static void callBuffToAdd(BufferMenuCategories category, L2PcInstance player, String profile, int objectId)
	{
		String bypass = "addProp";
		
		switch (category)
		{
			case DANCE:
				bypass = "addDance";
				break;
			case SONG:
				bypass = "addSong";
				break;
			case MISC:
				bypass = "addMisc";
				break;
			case ELDER:
				bypass = "addElder";
				break;
			case OVERLORD:
				bypass = "addOver";
				break;
			case PROPHET:
				bypass = "addProp";
				break;
			case DWARF:
				bypass = "addDwarf";
				break;
			case CHANT:
				bypass = "addChant";
				break;
			case NONE:
				bypass = "removeBuffs";
				break;
		}
		
		switch (objectId)
		{
			case 0:
				gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, category, bypass);
				break;
			case 1:
				gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, category, bypass);
				break;
			default:
				gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToAdd(player, profile, category, bypass, objectId);
				break;
		}
	}
	
	public static void callAddCommand(L2PcInstance player, String category, String profile, String buffId, int objectId)
	{
		BufferMenuCategories _category = BufferMenuCategories.PROPHET;
		switch (category)
		{
			case "addChant":
				_category = BufferMenuCategories.CHANT;
				break;
			case "addOver":
				_category = BufferMenuCategories.OVERLORD;
				break;
			case "addElder":
				_category = BufferMenuCategories.ELDER;
				break;
			case "addDwarf":
				_category = BufferMenuCategories.DWARF;
				break;
			case "addMisc":
				_category = BufferMenuCategories.MISC;
				break;
			case "addProp":
				_category = BufferMenuCategories.PROPHET;
				break;
			case "addDance":
				_category = BufferMenuCategories.DANCE;
				break;
			case "addSong":
				_category = BufferMenuCategories.SONG;
				break;
		}
		
		if ((_category == BufferMenuCategories.DANCE) || (_category == BufferMenuCategories.SONG))
		{
			if (!PlayerMethods.checkDanceAmount(player, profile, _category, objectId))
			{
				return;
			}
		}
		else
		{
			if (!PlayerMethods.checkBuffsAmount(player, profile, _category, objectId))
			{
				return;
			}
		}
		
		ThreadPoolManager.getInstance().executeGeneral(new BuffSaver(player, _category, profile, Integer.parseInt(buffId), objectId));
	}
	
	public static void returnPage(L2PcInstance player, String command, int objectId)
	{
		switch (objectId)
		{
			case 0:
				switch (command)
				{
					case "buffdance":
						BufferPacketSender.sendPacket(player, "dances.htm", BufferPacketCategories.FILE, 0);
						break;
					case "buffsong":
						BufferPacketSender.sendPacket(player, "songs.htm", BufferPacketCategories.FILE, 0);
						break;
					case "buffprop":
						BufferPacketSender.sendPacket(player, "prophet.htm", BufferPacketCategories.FILE, 0);
						break;
					case "buffover":
						BufferPacketSender.sendPacket(player, "overlord.htm", BufferPacketCategories.FILE, 0);
						break;
					case "buffdwarf":
						BufferPacketSender.sendPacket(player, "warsmith.htm", BufferPacketCategories.FILE, 0);
						break;
					case "buffwar":
						BufferPacketSender.sendPacket(player, "warcryer.htm", BufferPacketCategories.FILE, 0);
						break;
					case "buffmisc":
						BufferPacketSender.sendPacket(player, "misc.htm", BufferPacketCategories.FILE, 0);
						break;
					case "buffelder":
						BufferPacketSender.sendPacket(player, "elder.htm", BufferPacketCategories.FILE, 0);
						break;
				}
				break;
			case 1:
				switch (command)
				{
					case "buffdance":
						BufferPacketSender.sendPacket(player, "dances.htm", BufferPacketCategories.COMMUNITY, 1);
						break;
					case "buffsong":
						BufferPacketSender.sendPacket(player, "songs.htm", BufferPacketCategories.COMMUNITY, 1);
						break;
					case "buffprop":
						BufferPacketSender.sendPacket(player, "prophet.htm", BufferPacketCategories.COMMUNITY, 1);
						break;
					case "buffover":
						BufferPacketSender.sendPacket(player, "overlord.htm", BufferPacketCategories.COMMUNITY, 1);
						break;
					case "buffdwarf":
						BufferPacketSender.sendPacket(player, "warsmith.htm", BufferPacketCategories.COMMUNITY, 1);
						break;
					case "buffwar":
						BufferPacketSender.sendPacket(player, "warcryer.htm", BufferPacketCategories.COMMUNITY, 1);
						break;
					case "buffmisc":
						BufferPacketSender.sendPacket(player, "misc.htm", BufferPacketCategories.COMMUNITY, 1);
						break;
					case "buffelder":
						BufferPacketSender.sendPacket(player, "elder.htm", BufferPacketCategories.COMMUNITY, 1);
						break;
				}
				break;
			default:
				switch (command)
				{
					case "buffdance":
						BufferPacketSender.sendPacket(player, "dances.htm", BufferPacketCategories.FILE, objectId);
						break;
					case "buffsong":
						BufferPacketSender.sendPacket(player, "songs.htm", BufferPacketCategories.FILE, objectId);
						break;
					case "buffprop":
						BufferPacketSender.sendPacket(player, "prophet.htm", BufferPacketCategories.FILE, objectId);
						break;
					case "buffover":
						BufferPacketSender.sendPacket(player, "overlord.htm", BufferPacketCategories.FILE, objectId);
						break;
					case "buffdwarf":
						BufferPacketSender.sendPacket(player, "warsmith.htm", BufferPacketCategories.FILE, objectId);
						break;
					case "buffwar":
						BufferPacketSender.sendPacket(player, "warcryer.htm", BufferPacketCategories.FILE, objectId);
						break;
					case "buffmisc":
						BufferPacketSender.sendPacket(player, "misc.htm", BufferPacketCategories.FILE, objectId);
						break;
					case "buffelder":
						BufferPacketSender.sendPacket(player, "elder.htm", BufferPacketCategories.FILE, objectId);
						break;
				}
				break;
		}
	}
}