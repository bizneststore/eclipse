package gr.sr.achievementEngine.conditions;

import java.util.StringTokenizer;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;

public class ItemsCount extends Condition
{
	public ItemsCount(Object value, ConditionType type)
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
		String s = getValue().toString();
		StringTokenizer st = new StringTokenizer(s, ",");
		int id = 0;
		long ammount = 0;
		
		try
		{
			id = Integer.parseInt(st.nextToken());
			ammount = Integer.parseInt(st.nextToken());
			
			if (player.getInventory().getItemsByItemId(id).size() >= ammount)
			{
				return true;
			}
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		return false;
	}
}