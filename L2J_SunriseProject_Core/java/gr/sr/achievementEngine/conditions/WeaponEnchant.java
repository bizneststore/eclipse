package gr.sr.achievementEngine.conditions;

import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.itemcontainer.Inventory;
import l2r.gameserver.model.items.instance.L2ItemInstance;

import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;

public class WeaponEnchant extends Condition
{
	public WeaponEnchant(Object value, ConditionType type)
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
		
		L2ItemInstance weapon = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);
		
		if (weapon != null)
		{
			if (weapon.getEnchantLevel() >= val)
			{
				return true;
			}
		}
		return false;
	}
}