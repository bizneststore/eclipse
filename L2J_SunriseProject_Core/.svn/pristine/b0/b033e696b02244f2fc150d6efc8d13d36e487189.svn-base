package gr.sr.data.xml.holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gr.sr.data.xml.AbstractHolder;
import gr.sr.main.TopListsLoader;

/**
 * @author vGodFather
 */
public class TeleportsHolder extends AbstractHolder
{
	private static final TeleportsHolder _instance = new TeleportsHolder();
	private final Map<Integer, Integer[]> _teleportInfo = new ConcurrentHashMap<>();
	
	public static TeleportsHolder getInstance()
	{
		return _instance;
	}
	
	public Integer[] getTeleportInfo(int coords)
	{
		return _teleportInfo.get(coords);
	}
	
	public void addTeleport(int tpcategory, Integer[] coords)
	{
		_teleportInfo.put(tpcategory, coords);
	}
	
	public Map<Integer, Integer[]> get()
	{
		return TopListsLoader.getInstance().getPorts();
	}
	
	@Override
	public int size()
	{
		return _teleportInfo.size();
	}
	
	@Override
	public void clear()
	{
		_teleportInfo.clear();
	}
}