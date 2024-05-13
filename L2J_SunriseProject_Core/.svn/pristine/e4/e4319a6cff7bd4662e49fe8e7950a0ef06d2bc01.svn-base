package gr.sr.achievementEngine.conditions;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;

public class Adena extends Condition
{
	public Adena(Object value, ConditionType type)
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
		long val = Integer.parseInt(getValue().toString());
		
		if (player.getInventory().getAdena() >= val)
		{
			return true;
		}
		return false;
	}
}