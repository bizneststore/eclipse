package l2r.gameserver.taskmanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.L2Object;
import l2r.gameserver.model.actor.L2Npc;
import l2r.gameserver.model.object.ObjectsStorage;
import l2r.util.Rnd;

public class RandomAnimationTaskManager
{
	protected final Map<Integer, L2Npc> _container = new ConcurrentHashMap<>();
	
	protected RandomAnimationTaskManager()
	{
		ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new TaskScheduler(), 1000, 5000);
	}
	
	public class TaskScheduler implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				// Log.info("RandomAnimationTaskManager Running: " + _container.size() + " : " + ObjectsStorage.getAllNpcs().size() + " diff: " + (ObjectsStorage.getAllNpcs().size() - _container.size()));
				
				// final long time = System.currentTimeMillis();
				for (L2Npc npc : _container.values())
				{
					if (ObjectsStorage.getObject(npc.getObjectId()) == null)
					{
						// Log.info("RandomAnimationTaskManager : NONE EXISTING OBJECT IN WORLD");
						remove(npc);
						continue;
					}
					
					if (npc.isInActiveRegion() && !npc.isDead() && !npc.isDecayed() && !npc.isInCombat() && !npc.isMoving() && !npc.isStunned() && !npc.isSleeping() && !npc.isParalyzed())
					{
						npc.onRandomAnimation(Rnd.get(2, 3));
					}
				}
				
				// Log.info("RandomAnimationTaskManager gentime: " + (System.currentTimeMillis() - time));
			}
			catch (Exception e)
			{
				// e.printStackTrace();
			}
		}
	}
	
	public void add(L2Npc npc)
	{
		if (npc.hasRandomAnimation() && !_container.containsKey(npc.getObjectId()))
		{
			_container.put(npc.getObjectId(), npc);
		}
	}
	
	public void remove(L2Object npc)
	{
		_container.remove(npc.getObjectId());
	}
	
	public static RandomAnimationTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RandomAnimationTaskManager INSTANCE = new RandomAnimationTaskManager();
	}
}
