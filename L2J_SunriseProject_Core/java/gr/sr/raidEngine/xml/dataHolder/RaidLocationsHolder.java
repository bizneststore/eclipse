package gr.sr.raidEngine.xml.dataHolder;

import java.util.LinkedList;
import java.util.List;

import gr.sr.data.xml.AbstractHolder;
import gr.sr.raidEngine.RaidLocation;

/**
 * @author vGodFather
 */
public class RaidLocationsHolder extends AbstractHolder
{
	private static final RaidLocationsHolder _instance = new RaidLocationsHolder();
	public final List<RaidLocation> _locations = new LinkedList<>();
	
	public static RaidLocationsHolder getInstance()
	{
		return _instance;
	}
	
	public List<RaidLocation> getLocations()
	{
		return _locations;
	}
	
	@Override
	public int size()
	{
		return _locations.size();
	}
	
	@Override
	public void clear()
	{
		_locations.clear();
	}
}