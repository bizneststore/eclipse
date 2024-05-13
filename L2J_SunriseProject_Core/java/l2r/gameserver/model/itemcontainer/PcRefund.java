/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package l2r.gameserver.model.itemcontainer;

import l2r.gameserver.data.xml.impl.ItemData;
import l2r.gameserver.enums.ItemLocation;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.items.instance.L2ItemInstance;
import l2r.gameserver.model.object.ObjectsStorage;
import l2r.log.filter.Log;

/**
 * @author DS
 */
public class PcRefund extends ItemContainer
{
	private final L2PcInstance _owner;
	
	public PcRefund(L2PcInstance owner)
	{
		_owner = owner;
	}
	
	@Override
	public String getName()
	{
		return "Refund";
	}
	
	@Override
	public L2PcInstance getOwner()
	{
		return _owner;
	}
	
	@Override
	public ItemLocation getBaseLocation()
	{
		return ItemLocation.REFUND;
	}
	
	@Override
	protected void addItem(L2ItemInstance item)
	{
		super.addItem(item);
		try
		{
			if (getSize() > 12)
			{
				L2ItemInstance removedItemoobj = _items.remove(0);
				if (removedItemoobj != null)
				{
					final L2ItemInstance removedItem = ObjectsStorage.getAsItem(removedItemoobj.getObjectId());
					if (removedItem == null)
					{
						Log.error("PcRefund: none existing item: " + removedItemoobj);
						return;
					}
					
					ItemData.getInstance().destroyItem("ClearRefund", removedItem, getOwner(), null);
					removedItem.updateDatabase(true);
				}
			}
		}
		catch (Exception e)
		{
			_log.error("addItem()", e);
		}
	}
	
	@Override
	public void refreshWeight()
	{
	}
	
	@Override
	public void deleteMe()
	{
		try
		{
			for (L2ItemInstance item : getItems())
			{
				ItemData.getInstance().destroyItem("ClearRefund", item, getOwner(), null);
				item.updateDatabase(true);
			}
		}
		catch (Exception e)
		{
			_log.error("deleteMe()", e);
		}
		_items.clear();
	}
	
	@Override
	public void restore()
	{
	}
}