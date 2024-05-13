package gr.sr.achievementEngine.base;

import l2r.gameserver.model.actor.instance.L2PcInstance;

public abstract class Condition
{
	private final Object _value;
	private final ConditionType _type;
	
	public Condition(Object value, ConditionType type)
	{
		_value = value;
		_type = type;
	}
	
	public abstract boolean meetConditionRequirements(L2PcInstance player);
	
	public Object getValue()
	{
		return _value;
	}
	
	public ConditionType getType()
	{
		return _type;
	}
}