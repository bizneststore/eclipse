package gr.sr.achievementEngine.conditions;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;

public class Status extends Condition
{
	public Status(Object value, ConditionType type)
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
		
		switch (getType())
		{
			case MUST_BE_HERO:
				if (player.isHero())
				{
					return true;
				}
				break;
			case MUST_BE_NOBLE:
				if (player.isNoble())
				{
					return true;
				}
				break;
			case CLAN_LEADER:
				if (player.isClanLeader())
				{
					return true;
				}
				break;
			case ACADEMY_MEMBER:
				if (player.isAcademyMember())
				{
					return true;
				}
				break;
			case MUST_BE_MARRIED:
				if (player.isMarried())
				{
					return true;
				}
				break;
			case MUST_BE_MAGE:
				if (player.isMageClass())
				{
					return true;
				}
				break;
			case MUST_BE_SUMMONER:
				if (player.getClassId().isSummoner())
				{
					return true;
				}
				break;
			case COMMON_CRAFT:
				if (player.hasCommonCraft())
				{
					return true;
				}
				break;
			case DWARVEN_CRAFT:
				if (player.hasCommonCraft())
				{
					return true;
				}
				break;
		}
		return false;
	}
}