package gr.sr.fakeNpcs.xml;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gr.sr.data.xml.AbstractHolder;
import gr.sr.fakeNpcs.FakePc;

/**
 * @author vGodFather
 */
public class FakeNpcsHolder extends AbstractHolder
{
	private static final FakeNpcsHolder _instance = new FakeNpcsHolder();
	public final Map<Integer, FakePc> _data = new ConcurrentHashMap<>();
	
	public static FakeNpcsHolder getInstance()
	{
		return _instance;
	}
	
	public FakePc get(int id)
	{
		return _data.get(id);
	}
	
	public void addData(FakePc pc)
	{
		_data.put(pc.npcId, pc);
	}
	
	public Map<Integer, FakePc> getData()
	{
		return _data;
	}
	
	@Override
	public int size()
	{
		return _data.size();
	}
	
	@Override
	public void clear()
	{
		_data.clear();
	}
}