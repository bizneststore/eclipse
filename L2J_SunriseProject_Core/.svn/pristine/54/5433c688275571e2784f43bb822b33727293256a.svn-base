package l2r.gameserver.taskmanager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.L2Object;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.object.ObjectsStorage;
import l2r.gameserver.model.stats.Formulas;

import gr.sr.logging.Log;

public class RegenTaskManager
{
	protected Map<Integer, Long> container = new ConcurrentHashMap<>();
	
	protected RegenTaskManager()
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
				if (!container.isEmpty())
				{
					// Log.info(printStatus());
				}
				
				final long time = System.currentTimeMillis();
				for (Entry<Integer, Long> entry : container.entrySet())
				{
					final int objectId = entry.getKey();
					final long decayTime = entry.getValue();
					
					if (time > decayTime)
					{
						final L2Character actor = ObjectsStorage.getAsCharacter(objectId);
						
						if ((actor == null) || actor.isDead())
						{
							container.remove(objectId);
							continue;
						}
						
						actor.getStatus().doRegeneration();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void add(L2Character actor)
	{
		final int period = Formulas.getRegeneratePeriod(actor);
		
		container.put(actor.getObjectId(), System.currentTimeMillis() + period);
	}
	
	public void cancel(L2Object actor)
	{
		removeObject(actor);
	}
	
	private void removeObject(L2Object actor)
	{
		container.remove(actor.getObjectId());
	}
	
	public boolean isScheduled(L2Object actor)
	{
		return container.containsKey(actor.getObjectId());
	}
	
	public long getRemainingTime(L2Character character)
	{
		try
		{
			long endTime = container.get(character.getObjectId());
			final long decayTime = endTime;
			final long remaningtime = decayTime - System.currentTimeMillis();
			
			return remaningtime;
		}
		catch (Exception e)
		{
			Log.warning("Error in RegenTaskManager: " + e.getMessage(), e);
		}
		
		return Long.MAX_VALUE;
	}
	
	public Map<Integer, Long> getTasks()
	{
		return container;
	}
	
	public String printStatus()
	{
		if (container.isEmpty())
		{
			return "";
		}
		
		StringBuffer sb = new StringBuffer("============= RegenTaskManager queue Manager Report ============\r");
		sb.append("Tasks count: ").append(container.size()).append("\r");
		// sb.append("Tasks dump:\r");
		
		// long current = System.currentTimeMillis();
		// for (QueueTask container : _tasks.values())
		// {
		// if (container == null)
		// {
		// continue;
		// }
		//
		// sb.append("Class/Name: ").append(container.getClass().getSimpleName()).append('/').append(container.getActor());
		// sb.append(" task timer: ").append(container._endTime - current).append("ms\r");
		// }
		
		return sb.toString();
	}
	
	public static RegenTaskManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RegenTaskManager INSTANCE = new RegenTaskManager();
	}
}
