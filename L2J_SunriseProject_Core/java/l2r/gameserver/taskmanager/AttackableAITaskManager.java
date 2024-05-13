package l2r.gameserver.taskmanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.L2Object;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.object.ObjectsStorage;

public class AttackableAITaskManager
{
	protected final Map<Integer, L2Character> _container = new ConcurrentHashMap<>();
	
	protected AttackableAITaskManager()
	{
		ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new TaskScheduler(), 1000, 1000);
	}
	
	public class TaskScheduler implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				// Log.info("AttackableAITaskManager Running: " + _container.size() + " : " + ObjectsStorage.getAllNpcs().size() + " diff: " + (ObjectsStorage.getAllNpcs().size() - _container.size()));
				
				// final long time = System.currentTimeMillis();
				for (L2Character npc : _container.values())
				{
					if (ObjectsStorage.getObject(npc.getObjectId()) == null)
					{
						// Log.info("AttackableAITaskManager : NONE EXISTING OBJECT IN WORLD: " + npc);
						remove(npc);
						continue;
					}
					
					if (npc.isDead())
					{
						// Log.info("Dead npc cannot think: " + npc);
						remove(npc);
						continue;
					}
					npc.getAI().runAI();
				}
				
				// Log.info("AttackableAITaskManager gentime: " + (System.currentTimeMillis() - time));
			}
			catch (Exception e)
			{
				// e.printStackTrace();
			}
		}
	}
	
	public void add(L2Character npc)
	{
		// Do not add dead npcs
		if (npc.isDead())
		{
			return;
		}
		
		if (!_container.containsKey(npc.getObjectId()))
		{
			_container.put(npc.getObjectId(), npc);
		}
	}
	
	public void remove(L2Object npc)
	{
		_container.remove(npc.getObjectId());
	}
	
	public static AttackableAITaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final AttackableAITaskManager INSTANCE = new AttackableAITaskManager();
	}
}
