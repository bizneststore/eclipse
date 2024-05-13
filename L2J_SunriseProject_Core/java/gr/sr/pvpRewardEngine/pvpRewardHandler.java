package gr.sr.pvpRewardEngine;

import java.util.Map.Entry;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.configsEngine.configs.impl.PvpRewardSystemConfigs;
import gr.sr.utils.Tools;

public class pvpRewardHandler
{
	/**
	 * Gives reward on every kill
	 * @param player
	 * @param target
	 */
	public static void pvpRewardSystem(L2PcInstance player, L2PcInstance target)
	{
		if (PvpRewardSystemConfigs.ENABLE_PVP_REWARD_SYSTEM_IP_RESTRICTION)
		{
			if (!Tools.isDualBox(player, target))
			{
				addReward(player);
			}
		}
		else
		{
			addReward(player);
		}
	}
	
	private static void addReward(L2PcInstance player)
	{
		for (Entry<Integer, Long> rewards : PvpRewardSystemConfigs.PVP_REWARDS.entrySet())
		{
			if (rewards != null)
			{
				player.addItem("PvpReward", rewards.getKey(), rewards.getValue(), player, true);
			}
		}
	}
}