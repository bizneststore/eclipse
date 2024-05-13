package gr.sr.achievementEngine.conditions;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;

public class Stats extends Condition
{
	public Stats(Object value, ConditionType type)
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
		
		switch (getType())
		{
			case MAX_HP:
				if (player.getMaxHp() >= val)
				{
					return true;
				}
				break;
			case MAX_CP:
				if (player.getMaxCp() >= val)
				{
					return true;
				}
				break;
			case MAX_MP:
				if (player.getMaxMp() >= val)
				{
					return true;
				}
				break;
			case MIN_PK_COUNT:
				if (player.getPkKills() >= val)
				{
					return true;
				}
				break;
			case MIN_PVP_COUNT:
				if (player.getPvpKills() >= val)
				{
					return true;
				}
				break;
			case MIN_KARMA:
				if (player.getKarma() >= val)
				{
					return true;
				}
				break;
		}
		return false;
	}
}