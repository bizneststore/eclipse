package gr.sr.voteEngine.xml;

import java.util.ArrayList;
import java.util.List;

import gr.sr.data.xml.AbstractHolder;
import gr.sr.voteEngine.VoteData;

/**
 * @author vGodFather
 */
public class VoteEngineHolder extends AbstractHolder
{
	private static final VoteEngineHolder _instance = new VoteEngineHolder();
	public final List<VoteData> _container = new ArrayList<>();
	
	public static VoteEngineHolder getInstance()
	{
		return _instance;
	}
	
	public List<VoteData> get()
	{
		return _container;
	}
	
	public void add(VoteData voteData)
	{
		_container.add(voteData);
	}
	
	@Override
	public int size()
	{
		return _container.size();
	}
	
	@Override
	public void clear()
	{
		_container.clear();
	}
}