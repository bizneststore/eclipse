package gr.sr.achievementEngine.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vGodFather
 */
public class Achievement
{
	private final int _id;
	private final String _name;
	private final String _reward;
	private String _description = "No Description!";
	private final boolean _repeatable;
	
	private final Map<Integer, Long> _rewardList = new ConcurrentHashMap<>();
	private List<Condition> _conditions = new ArrayList<>();
	
	private static Logger _log = LoggerFactory.getLogger(Achievement.class);
	
	public Achievement(int id, String name, String description, String reward, boolean repeatable, List<Condition> conditions)
	{
		_id = id;
		_name = name;
		_description = description;
		_reward = reward;
		_conditions = conditions;
		_repeatable = repeatable;
		
		createRewardList();
	}
	
	private void createRewardList()
	{
		for (String s : _reward.split(";"))
		{
			if ((s == null) || s.isEmpty())
			{
				continue;
			}
			
			String[] split = s.split(",");
			Integer item = 0;
			Long count = new Long(0);
			try
			{
				item = Integer.valueOf(split[0]);
				count = Long.valueOf(split[1]);
			}
			catch (NumberFormatException nfe)
			{
				_log.warn("[AchievementsEngine] Error: Wrong reward " + nfe);
			}
			_rewardList.put(item, count);
		}
	}
	
	public boolean meetAchievementRequirements(L2PcInstance player)
	{
		for (Condition c : getConditions())
		{
			if (!c.meetConditionRequirements(player))
			{
				return false;
			}
		}
		return true;
	}
	
	// ========================================================
	// XXX - GETTER METHODS
	// ========================================================
	
	public int getId()
	{
		return _id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getDescription()
	{
		return _description;
	}
	
	public String getReward()
	{
		return _reward;
	}
	
	public boolean isRepeatable()
	{
		return _repeatable;
	}
	
	public Map<Integer, Long> getRewardList()
	{
		return _rewardList;
	}
	
	public List<Condition> getConditions()
	{
		return _conditions;
	}
}