package l2r.gameserver.taskmanager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import l2r.Config;
import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.L2Object;
import l2r.gameserver.model.actor.L2Attackable;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.actor.templates.L2NpcTemplate;
import l2r.gameserver.model.object.ObjectsStorage;

import gr.sr.logging.Log;

/**
 * @author vGodFather
 */
public class DecayTaskManager
{
	protected Map<Integer, Long> container = new ConcurrentHashMap<>();
	
	public DecayTaskManager()
	{
		ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new TaskScheduler(), 1000, 5000);
	}
	
	public void add(L2Character actor)
	{
		long delay;
		if (actor.getTemplate() instanceof L2NpcTemplate)
		{
			delay = ((L2NpcTemplate) actor.getTemplate()).getCorpseTime();
		}
		else
		{
			delay = Config.DEFAULT_CORPSE_TIME;
		}
		
		if ((actor instanceof L2Attackable) && (((L2Attackable) actor).isSpoiled() || ((L2Attackable) actor).isSeeded()))
		{
			delay += Config.SPOILED_CORPSE_EXTEND_TIME;
		}
		
		addDecayTask(actor, delay * 1000);
	}
	
	public void addDecayTask(L2Character actor, long interval)
	{
		removeObject(actor);
		addObject(actor, System.currentTimeMillis() + interval);
	}
	
	public void cancel(L2Object actor)
	{
		removeObject(actor);
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
					// for (Set<L2Spawn> spawns : SpawnTable.getInstance().getSpawnTable().values())
					// {
					// for (L2Spawn spawn : spawns)
					// {
					// final L2Character ac = spawn.getLastSpawn();
					// if ((ac != null) && !ac.isDead())
					// {
					// if (ac.isAttackable())
					// {
					// ac.doDie(null);
					// }
					// }
					// }
					// }
					
					// Log.info(ObjectsStorage.getStats().toString());
					// Log.info(printStatus());
				}
				
				final long time = System.currentTimeMillis();
				for (Entry<Integer, Long> entry : container.entrySet())
				{
					final int objectId = entry.getKey();
					final long decayTime = entry.getValue();
					
					if (time > decayTime)
					{
						container.remove(objectId);
						
						final L2Character actor = ObjectsStorage.getAsCharacter(objectId);
						
						if (actor == null)
						{
							// Log.info("DecayTaskManager TaskScheduler: NULL CHARACTER IN WORLD: " + objectId);
							continue;
						}
						
						// Object will be removed from objects temporarily
						// and if there is a respawn task will be added again from onSpawn method
						ObjectsStorage.remove(actor);
						
						actor.onDecay();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public String printStatus()
	{
		if (container.isEmpty())
		{
			return "";
		}
		
		StringBuffer sb = new StringBuffer("============= DecayTask queue Manager Report ============\n\r");
		sb.append("Tasks count: ").append(container.size()).append("\r");
		sb.append("Tasks dump:\r");
		
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
	
	private void addObject(L2Character actor, long time)
	{
		container.put(actor.getObjectId(), time);
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
			// TODO: Find out the reason for exception. Unless caught here, mob decay would stop.
			Log.warning("Error in DecayScheduler: " + e.getMessage(), e);
		}
		
		return Long.MAX_VALUE;
	}
	
	public Map<Integer, Long> getTasks()
	{
		return container;
	}
	
	public static DecayTaskManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final DecayTaskManager _instance = new DecayTaskManager();
	}
}
