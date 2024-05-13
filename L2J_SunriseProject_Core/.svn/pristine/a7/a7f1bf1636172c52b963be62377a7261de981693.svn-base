package l2r.gameserver.model.quest;

import java.util.List;
import java.util.Map;

import l2r.gameserver.model.StatsSet;

public class QuestTemplate
{
	private final int _id;
	private final String _nameEn;
	private final String _nameRu;
	private final int _minLvl;
	private final int _maxLvl;
	private final QuestExperience _experience;
	private final List<QuestRewardItem> _rewards;
	private final Map<Integer, List<QuestRewardItem>> _variantRewards;
	private final Map<Integer, List<QuestDropItem>> _dropList;
	private final boolean _rateable;
	private final StatsSet _params;
	
	public QuestTemplate(final int id, final String nameEn, final String nameRu, final int minLvl, final int maxLvl, final Map<Integer, List<QuestDropItem>> dropList, final QuestExperience experience, final List<QuestRewardItem> rewards, final Map<Integer, List<QuestRewardItem>> variantRewards, final boolean rateable, final StatsSet params)
	{
		this._id = id;
		this._nameEn = nameEn;
		this._nameRu = nameRu;
		this._minLvl = minLvl;
		this._maxLvl = maxLvl;
		this._dropList = dropList;
		this._experience = experience;
		this._rewards = rewards;
		this._variantRewards = variantRewards;
		this._rateable = rateable;
		this._params = params;
	}
	
	public int getId()
	{
		return this._id;
	}
	
	public String getName(final String lang)
	{
		return ((lang != null) && !lang.equalsIgnoreCase("en")) ? this._nameRu : this._nameEn;
	}
	
	public int getMinLvl()
	{
		return this._minLvl;
	}
	
	public int getMaxLvl()
	{
		return this._maxLvl;
	}
	
	public Map<Integer, List<QuestDropItem>> getDropList()
	{
		return this._dropList;
	}
	
	public QuestExperience getExperienceRewards()
	{
		return this._experience;
	}
	
	public List<QuestRewardItem> getRewards()
	{
		return this._rewards;
	}
	
	public Map<Integer, List<QuestRewardItem>> getVariantRewards()
	{
		return this._variantRewards;
	}
	
	public boolean isRateable()
	{
		return this._rateable;
	}
	
	public StatsSet getParams()
	{
		return this._params;
	}
}
