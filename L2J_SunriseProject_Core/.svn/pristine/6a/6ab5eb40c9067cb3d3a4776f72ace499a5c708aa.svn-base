package gr.sr.javaBuffer.runnable;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.configsEngine.configs.impl.BufferConfigs;

/**
 * The class used to create a delay for players, reducing the overhead due to RequestBypassToServer
 * @author vGodFather
 */
public class BuffDelay implements Runnable
{
	L2PcInstance _player;
	/* A List which contains the player on delay */
	public static Set<L2PcInstance> _delayers = ConcurrentHashMap.newKeySet();
	
	public BuffDelay(L2PcInstance player)
	{
		_player = player;
		_delayers.add(_player);
	}
	
	@Override
	public void run()
	{
		double start = System.currentTimeMillis();
		while (System.currentTimeMillis() < (start + (BufferConfigs.BUFFER_DELAY * 1000)))
		{
			// Do nothing
		}
		
		if (_delayers.contains(_player))
		{
			_delayers.remove(_player);
		}
	}
}