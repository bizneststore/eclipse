package gr.sr.raidEngine.xml.dataHolder;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import gr.sr.data.xml.AbstractHolder;
import gr.sr.raidEngine.RaidGroup;

/**
 * @author vGodFather
 */
public class RaidAndDropsHolder extends AbstractHolder
{
	private static final RaidAndDropsHolder _instance = new RaidAndDropsHolder();
	public final List<RaidGroup> _raids = new LinkedList<>();
	
	public static RaidAndDropsHolder getInstance()
	{
		return _instance;
	}
	
	/*
	 * public List<RaidsDrop> getDrops(int raidId) { for (RaidGroup raidGroup : _raids) { return raidGroup.getRaids().get(raidId); } return new ArrayList<>(); }
	 */
	
	public List<RaidGroup> getRaidGroups()
	{
		return _raids;
	}
	
	public Comparator<RaidGroup> compareByChance = (o1, o2) ->
	{
		double cha1 = o1.getGroupChance();
		double cha2 = o2.getGroupChance();
		
		return cha1 > cha2 ? 1 : cha1 == cha2 ? 0 : -1;
	};
	
	@Override
	public int size()
	{
		return _raids.size();
	}
	
	@Override
	public void clear()
	{
		_raids.clear();
	}
}