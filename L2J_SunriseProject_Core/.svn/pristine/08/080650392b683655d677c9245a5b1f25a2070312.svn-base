package l2r.gameserver.taskmanager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.L2Spawn;
import l2r.gameserver.model.actor.L2Npc;

public class RespawnTaskManager
{
	protected final Map<L2Npc, Long> _container = new ConcurrentHashMap<>();
	
	protected RespawnTaskManager()
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
				// Log.info("RespawnTaskManager Running: " + _container.size());
				
				final long time = System.currentTimeMillis();
				for (Entry<L2Npc, Long> entry : _container.entrySet())
				{
					if (time > entry.getValue().longValue())
					{
						final L2Npc npc = entry.getKey();
						_container.remove(npc);
						final L2Spawn spawn = npc.getSpawn();
						if (spawn != null)
						{
							spawn.respawnNpc(npc);
							spawn._scheduledCount--;
						}
					}
				}
				
				// Log.info("RespawnTaskManager gentime: " + (System.currentTimeMillis() - time));
			}
			catch (Exception e)
			{
				// e.printStackTrace();
			}
		}
	}
	
	public void add(L2Npc npc, long time)
	{
		_container.put(npc, time);
	}
	
	public static RespawnTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RespawnTaskManager INSTANCE = new RespawnTaskManager();
	}
}
