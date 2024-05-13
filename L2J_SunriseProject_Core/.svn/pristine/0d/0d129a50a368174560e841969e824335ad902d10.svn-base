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
package l2r.gameserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import l2r.Config;
import l2r.gameserver.enums.ItemLocation;
import l2r.gameserver.instancemanager.ItemsOnGroundManager;
import l2r.gameserver.model.items.instance.L2ItemInstance;
import l2r.gameserver.model.object.ObjectsStorage;

public final class ItemsAutoDestroy
{
	private final Map<Integer, Integer> _items = new ConcurrentHashMap<>();
	
	protected ItemsAutoDestroy()
	{
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(this::removeItems, 5000, 5000);
	}
	
	public static ItemsAutoDestroy getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public synchronized void addItem(L2ItemInstance item)
	{
		item.setDropTime(System.currentTimeMillis());
		_items.put(item.getObjectId(), item.getObjectId());
	}
	
	public synchronized void removeItems()
	{
		final long curtime = System.currentTimeMillis();
		for (int itemobjid : _items.values())
		{
			final L2ItemInstance item = ObjectsStorage.getAsItem(itemobjid);
			if (item == null)
			{
				// Log.error("ItemAutoDestroy: none existing item: " + itemobjid);
				_items.remove(itemobjid);
				continue;
			}
			
			if ((item.getDropTime() == 0) || (item.getItemLocation() != ItemLocation.VOID))
			{
				_items.remove(item.getObjectId());
			}
			else
			{
				if (item.getItem().getAutoDestroyTime() > 0)
				{
					if ((curtime - item.getDropTime()) > item.getItem().getAutoDestroyTime())
					{
						item.deleteMe();
						_items.remove(item.getObjectId());
						if (Config.SAVE_DROPPED_ITEM)
						{
							ItemsOnGroundManager.getInstance().removeObject(item);
						}
					}
				}
				else if (item.getItem().hasExImmediateEffect())
				{
					if ((curtime - item.getDropTime()) > Config.HERB_AUTO_DESTROY_TIME)
					{
						item.deleteMe();
						_items.remove(item.getObjectId());
						if (Config.SAVE_DROPPED_ITEM)
						{
							ItemsOnGroundManager.getInstance().removeObject(item);
						}
					}
				}
				else
				{
					final long sleep = ((Config.AUTODESTROY_ITEM_AFTER == 0) ? 3600000 : Config.AUTODESTROY_ITEM_AFTER * 1000);
					
					if ((curtime - item.getDropTime()) > sleep)
					{
						item.deleteMe();
						_items.remove(item.getObjectId());
						if (Config.SAVE_DROPPED_ITEM)
						{
							ItemsOnGroundManager.getInstance().removeObject(item);
						}
					}
				}
			}
		}
		
		// Log.info("[ItemsAutoDestroy] : " + _items.size() + " items remaining.");
	}
	
	private static class SingletonHolder
	{
		protected static final ItemsAutoDestroy _instance = new ItemsAutoDestroy();
	}
}