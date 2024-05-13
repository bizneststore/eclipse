package gr.sr.raidEngine.xml.dataHolder;

import java.util.LinkedList;
import java.util.List;

import gr.sr.data.xml.AbstractHolder;
import gr.sr.raidEngine.RaidConfigs;

/**
 * @author vGodFather
 */
public class RaidConfigsHolder extends AbstractHolder
{
	private static final RaidConfigsHolder _instance = new RaidConfigsHolder();
	public final List<RaidConfigs> _configs = new LinkedList<>();
	
	public static RaidConfigsHolder getInstance()
	{
		return _instance;
	}
	
	public List<RaidConfigs> getConfigs()
	{
		return _configs;
	}
	
	@Override
	public int size()
	{
		return _configs.size();
	}
	
	@Override
	public void clear()
	{
		_configs.clear();
	}
}