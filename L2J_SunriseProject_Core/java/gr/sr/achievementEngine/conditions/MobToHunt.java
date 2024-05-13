package gr.sr.achievementEngine.conditions;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;

public class MobToHunt extends Condition
{
	public MobToHunt(Object value, ConditionType type)
	{
		super(value, type);
	}
	
	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
		{
			return false;
		}
		
		if (player.isKilledSpecificMob())
		{
			return true;
		}
		return false;
	}
}