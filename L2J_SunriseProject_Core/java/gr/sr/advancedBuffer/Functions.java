/*
 * Copyright (C) L2J Sunrise
 * This file is part of L2J Sunrise.
 */
package gr.sr.advancedBuffer;

import l2r.gameserver.model.actor.L2Playable;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.serverpackets.ShowBoard;

/**
 * @author vGodFather
 */
public class Functions
{
	public static void separateAndSend(String text, L2PcInstance player)
	{
		if (text == null)
		{
			return;
		}
		text = text.replace("\t", "");
		if (text.length() < 8180)
		{
			player.sendPacket(new ShowBoard(text, "101"));
			player.sendPacket(new ShowBoard(null, "102"));
			player.sendPacket(new ShowBoard(null, "103"));
		}
		else if (text.length() < (8180 * 2))
		{
			player.sendPacket(new ShowBoard(text.substring(0, 8180), "101"));
			player.sendPacket(new ShowBoard(text.substring(8180, text.length()), "102"));
			player.sendPacket(new ShowBoard(null, "103"));
		}
		else if (text.length() < (8180 * 3))
		{
			player.sendPacket(new ShowBoard(text.substring(0, 8180), "101"));
			player.sendPacket(new ShowBoard(text.substring(8180, 16360), "102"));
			player.sendPacket(new ShowBoard(text.substring(16360, text.length()), "103"));
		}
	}
	
	public static long getItemCount(L2Playable paramL2Playable, int paramInt)
	{
		if (paramL2Playable == null)
		{
			return 0L;
		}
		L2PcInstance l2PcInstance = paramL2Playable.getActingPlayer();
		return l2PcInstance.getInventory().getInventoryItemCount(paramInt, 0);
	}
	
	public static void removeItem(L2PcInstance player, int consumableId, int price, String string)
	{
		player.destroyItemByItemId("SchemeBuffer", consumableId, price, player, true);
	}
}
