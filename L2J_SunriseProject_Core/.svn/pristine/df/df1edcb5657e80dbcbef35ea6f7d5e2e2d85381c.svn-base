package gr.sr.achievementEngine.conditions;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;

public class Subclass extends Condition
{
	public Subclass(Object value, ConditionType type)
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
		
		int val = Integer.parseInt(getValue().toString());
		
		if (player.getTotalSubClasses() >= val)
		{
			return true;
		}
		return false;
	}
}