package gr.sr.achievementEngine.conditions;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;

public class Clan extends Condition
{
	public Clan(Object value, ConditionType type)
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
		
		if (player.getClan() != null)
		{
			int val = Integer.parseInt(getValue().toString());
			
			switch (getType())
			{
				case MIN_CLAN_LEVEL:
					if (player.getClan().getLevel() >= val)
					{
						return true;
					}
					break;
				case CLAN_MEMBERS:
					if (player.getClan().getMembersCount() >= val)
					{
						return true;
					}
					break;
				case CRP_AMMOUNT:
					if (player.getClan().getReputationScore() >= val)
					{
						return true;
					}
					break;
				case CASTLE_LORD:
					if (player.isCastleLord(val))
					{
						return true;
					}
					break;
			}
		}
		return false;
	}
}