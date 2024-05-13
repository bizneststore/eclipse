package l2r.gameserver.model.conditions;

import java.util.ArrayList;

import l2r.gameserver.model.items.instance.L2ItemInstance;
import l2r.gameserver.model.stats.Env;

/**
 * @author Serveros
 */
public class ConditionAgathionItemId extends Condition
{
	private final ArrayList<Integer> _itemId;
	
	public ConditionAgathionItemId(final ArrayList<Integer> itemId)
	{
		this._itemId = itemId;
	}
	
	@Override
	public boolean testImpl(final Env env)
	{
		if (env.getPlayer() != null)
		{
			final L2ItemInstance item = env.getPlayer().getInventory().getPaperdollItem(15);
			if (item != null)
			{
				return _itemId.contains(item.getId());
			}
		}
		return false;
	}
}
