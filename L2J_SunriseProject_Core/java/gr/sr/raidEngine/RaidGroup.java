package gr.sr.raidEngine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import l2r.util.Rnd;

/**
 * Created on 20/12/2015 - 00:29 by Demetrios
 */
public class RaidGroup
{
	private final float _groupChance;
	private final Map<Integer, Integer> _raids;
	private final List<RaidDrop> _drops;
	private final RaidType _type;
	
	public RaidGroup(float groupChance, RaidType type)
	{
		_groupChance = groupChance;
		_raids = new LinkedHashMap<>();
		_drops = new ArrayList<>();
		_type = type;
	}
	
	public float getGroupChance()
	{
		return _groupChance;
	}
	
	public Map<Integer, Integer> getRaids()
	{
		return _raids;
	}
	
	/*
	 * public void addRaidDrops(int raidId, List<RaidsDrop> drops) { _raids.put(raidId, drops); }
	 */
	
	public List<RaidDrop> getDrops()
	{
		return _drops;
	}
	
	public String getTypeName()
	{
		return _type.getName();
	}
	
	public List<RaidDrop> getRandomDrops(int raidId)
	{
		List<RaidDrop> randomDrops = new ArrayList<>();
		_drops.stream().filter(rd -> rd.getMinOccurs() > 0).forEach(rd1 ->
		{
			for (int i = 0; i < rd1.getMinOccurs(); i++)
			{
				randomDrops.add(rd1);
			}
		});
		
		int randomDropCount = 0;
		int raidMaxDrops = _raids.get(raidId) + 1;
		if (randomDrops.size() < raidMaxDrops)
		{
			randomDropCount = Rnd.get(randomDrops.size(), raidMaxDrops);
		}
		else
		{
			randomDropCount = Rnd.get(randomDrops.size(), randomDrops.size() + 1);
		}
		
		while (randomDrops.size() < randomDropCount)
		{
			RaidDrop newDrop = _drops.get(Rnd.get(_drops.size()));
			if (newDrop.getMaxOccurs() == 0)
			{
				randomDrops.add(newDrop);
			}
			else if (randomDrops.stream().filter(raidsDrop -> raidsDrop.getItemId() == newDrop.getItemId()).count() < newDrop.getMaxOccurs())
			{
				randomDrops.add(newDrop);
			}
		}
		
		return randomDrops;
	}
}
