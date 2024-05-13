package l2r.gameserver.taskmanager;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.actor.instance.L2PcInstance;

public class PvpFlagTaskManager
{
	protected final Set<L2PcInstance> _container = ConcurrentHashMap.newKeySet();
	
	protected PvpFlagTaskManager()
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
				// Log.info("PvpFlagTaskManager Running: " + _container.size());
				
				final long time = System.currentTimeMillis();
				for (L2PcInstance player : _container)
				{
					if (time > player.getPvpFlagLasts())
					{
						player.stopPvPFlag();
					}
					else if (time > (player.getPvpFlagLasts() - 20000))
					{
						player.updatePvPFlag(2);
					}
					else
					{
						player.updatePvPFlag(1);
					}
				}
				
				// Log.info("PvpFlagTaskManager gentime: " + (System.currentTimeMillis() - time));
			}
			catch (Exception e)
			{
				// e.printStackTrace();
			}
		}
	}
	
	public void add(L2PcInstance player)
	{
		_container.add(player);
	}
	
	public void remove(L2PcInstance player)
	{
		_container.remove(player);
	}
	
	public static PvpFlagTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final PvpFlagTaskManager INSTANCE = new PvpFlagTaskManager();
	}
}
