package gr.sr.javaBuffer.xml.dataHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gr.sr.data.xml.AbstractHolder;
import gr.sr.javaBuffer.BuffsInstance;

/**
 * @author vGodFather
 */
public class BuffsHolder extends AbstractHolder
{
	private static final BuffsHolder _instance = new BuffsHolder();
	public final Map<Integer, BuffsInstance> _buffs = new ConcurrentHashMap<>();
	
	public static BuffsHolder getInstance()
	{
		return _instance;
	}
	
	public BuffsInstance getBuff(int buffId)
	{
		return _buffs.get(buffId);
	}
	
	public Map<Integer, BuffsInstance> getBuffs()
	{
		return _buffs;
	}
	
	@Override
	public int size()
	{
		return _buffs.size();
	}
	
	@Override
	public void clear()
	{
		_buffs.clear();
	}
}