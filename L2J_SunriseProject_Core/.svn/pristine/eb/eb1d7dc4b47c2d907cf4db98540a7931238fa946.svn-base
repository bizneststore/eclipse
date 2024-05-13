package gr.sr.zones;

import java.util.Map.Entry;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.configsEngine.configs.impl.FlagZoneConfigs;
import gr.sr.utils.Tools;

public class FlagZoneHandler
{
	private static int maxSameTargetContinuousKills = FlagZoneConfigs.MAX_SAME_TARGET_CONTINUOUS_KILLS;
	
	public static void validateRewardConditions(L2PcInstance killer, L2PcInstance target)
	{
		if (FlagZoneConfigs.ENABLE_PC_IP_PROTECTION && Tools.isDualBox(killer, target))
		{
			return;
		}
		
		if (target.getObjectId() == killer.getPreviousVictimId())
		{
			if (killer.getSameTargetCounter() >= maxSameTargetContinuousKills)
			{
				return;
			}
			
			addReward(killer);
			killer.increaseSameTargetCounter();
		}
		else
		{
			addReward(killer);
			killer.setSameTargetCounter(0);
			killer.setPreviousVictimId(target.getObjectId());
		}
	}
	
	private static void addReward(L2PcInstance killer)
	{
		for (Entry<Integer, Long> rewards : FlagZoneConfigs.FLAG_ZONE_REWARDS.entrySet())
		{
			if (rewards != null)
			{
				killer.addItem("FlagZone", rewards.getKey(), rewards.getValue(), killer, true);
			}
		}
	}
}