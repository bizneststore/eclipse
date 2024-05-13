package l2r.gameserver.model.quest.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import l2r.gameserver.model.StatsSet;
import l2r.gameserver.model.quest.QuestDropItem;
import l2r.gameserver.model.quest.QuestExperience;
import l2r.gameserver.model.quest.QuestRewardItem;
import l2r.gameserver.model.quest.QuestTemplate;
import l2r.util.data.xml.IXmlReader.IXmlReader;

import gr.sr.logging.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class QuestsParser implements IXmlReader
{
	private final Map<Integer, QuestTemplate> _quests = new HashMap<>();
	
	protected QuestsParser()
	{
		this.load();
	}
	
	@Override
	public void load()
	{
		this._quests.clear();
		this.parseDirectory("data/xml/stats/quests", false);
		Log.info(this.getClass().getSimpleName() + ": Loaded " + this._quests.size() + " quest templates.");
	}
	
	@Override
	public void parseDocument(Document doc)
	{
		for (Node list = doc.getFirstChild().getFirstChild(); list != null; list = list.getNextSibling())
		{
			if (list.getNodeName().equalsIgnoreCase("quest"))
			{
				NamedNodeMap node = list.getAttributes();
				final int id = Integer.valueOf(node.getNamedItem("id").getNodeValue());
				final String nameEn = node.getNamedItem("nameEn").getNodeValue();
				final String nameRu = node.getNamedItem("nameRu").getNodeValue();
				long expReward = 0L;
				long spReward = 0L;
				double expRate = 0.0;
				double spRate = 0.0;
				boolean expRateable = false;
				boolean spRateable = false;
				QuestExperience experience = null;
				List<QuestRewardItem> rewards = null;
				final Map<Integer, List<QuestRewardItem>> groupRewards = new HashMap<>();
				List<QuestRewardItem> rewardList = null;
				Map<Integer, List<QuestDropItem>> dropList = null;
				List<QuestDropItem> itemList = null;
				int minLvl = 0;
				int maxLvl = 0;
				final StatsSet params = new StatsSet();
				for (Node quest = list.getFirstChild(); quest != null; quest = quest.getNextSibling())
				{
					if (quest.getNodeName().equalsIgnoreCase("level"))
					{
						node = quest.getAttributes();
						minLvl = ((node.getNamedItem("min") != null) ? Integer.parseInt(node.getNamedItem("min").getNodeValue()) : 1);
						maxLvl = ((node.getNamedItem("max") != null) ? Integer.parseInt(node.getNamedItem("max").getNodeValue()) : 85);
					}
					else if (quest.getNodeName().equalsIgnoreCase("expirience"))
					{
						for (Node exp = quest.getFirstChild(); exp != null; exp = exp.getNextSibling())
						{
							if (exp.getNodeName().equalsIgnoreCase("rewardExp"))
							{
								node = exp.getAttributes();
								expRate = ((node.getNamedItem("rate") != null) ? Double.valueOf(node.getNamedItem("rate").getNodeValue()) : 1.0);
								expReward = Long.valueOf(node.getNamedItem("val").getNodeValue());
								expRateable = ((node.getNamedItem("rateable") != null) && Boolean.parseBoolean(node.getNamedItem("rateable").getNodeValue()));
							}
							else if (exp.getNodeName().equalsIgnoreCase("rewardSp"))
							{
								node = exp.getAttributes();
								spRate = ((node.getNamedItem("rate") != null) ? Double.valueOf(node.getNamedItem("rate").getNodeValue()) : 1.0);
								spReward = Long.valueOf(node.getNamedItem("val").getNodeValue());
								spRateable = ((node.getNamedItem("rateable") != null) && Boolean.parseBoolean(node.getNamedItem("rateable").getNodeValue()));
							}
						}
					}
					else if (quest.getNodeName().equalsIgnoreCase("droplist"))
					{
						dropList = new HashMap<>();
						for (Node drop = quest.getFirstChild(); drop != null; drop = drop.getNextSibling())
						{
							if (drop.getNodeName().equalsIgnoreCase("npc"))
							{
								itemList = new ArrayList<>();
								final int npcId = Integer.valueOf(drop.getAttributes().getNamedItem("id").getNodeValue());
								for (Node group = drop.getFirstChild(); group != null; group = group.getNextSibling())
								{
									if (group.getNodeName().equalsIgnoreCase("item"))
									{
										node = group.getAttributes();
										final int itemId = Integer.valueOf(node.getNamedItem("id").getNodeValue());
										final double rate = (node.getNamedItem("rate") != null) ? Double.valueOf(node.getNamedItem("rate").getNodeValue()) : 1.0;
										final long min = Integer.valueOf(node.getNamedItem("min").getNodeValue());
										final long max = (node.getNamedItem("max") != null) ? Integer.valueOf(node.getNamedItem("max").getNodeValue()) : 0L;
										final double chance = (node.getNamedItem("chance") != null) ? Double.valueOf(node.getNamedItem("chance").getNodeValue()) : 1.0;
										final boolean isRateable = (node.getNamedItem("rateable") != null) && Boolean.parseBoolean(node.getNamedItem("rateable").getNodeValue());
										itemList.add(new QuestDropItem(itemId, rate, min, max, chance, isRateable));
									}
								}
								dropList.put(npcId, itemList);
							}
						}
					}
					else if (quest.getNodeName().equalsIgnoreCase("add_parameters"))
					{
						for (Node sp = quest.getFirstChild(); sp != null; sp = sp.getNextSibling())
						{
							if ("set".equalsIgnoreCase(sp.getNodeName()))
							{
								params.set(sp.getAttributes().getNamedItem("name").getNodeValue(), sp.getAttributes().getNamedItem("value").getNodeValue());
							}
						}
					}
					else if (quest.getNodeName().equalsIgnoreCase("rewardlist"))
					{
						rewards = new ArrayList<>();
						for (Node reward = quest.getFirstChild(); reward != null; reward = reward.getNextSibling())
						{
							if (reward.getNodeName().equalsIgnoreCase("item"))
							{
								node = reward.getAttributes();
								final int itemId2 = Integer.valueOf(node.getNamedItem("id").getNodeValue());
								final double rate2 = (node.getNamedItem("rate") != null) ? Double.valueOf(node.getNamedItem("rate").getNodeValue()) : 1.0;
								final long min2 = Integer.valueOf(node.getNamedItem("min").getNodeValue());
								final long max2 = (node.getNamedItem("max") != null) ? Integer.valueOf(node.getNamedItem("max").getNodeValue()) : 0L;
								final boolean isRateable2 = (node.getNamedItem("rateable") != null) && Boolean.parseBoolean(node.getNamedItem("rateable").getNodeValue());
								rewards.add(new QuestRewardItem(itemId2, rate2, min2, max2, isRateable2));
							}
							else if (reward.getNodeName().equalsIgnoreCase("variant"))
							{
								rewardList = new ArrayList<>();
								final int varId = Integer.valueOf(reward.getAttributes().getNamedItem("id").getNodeValue());
								for (Node group = reward.getFirstChild(); group != null; group = group.getNextSibling())
								{
									if (group.getNodeName().equalsIgnoreCase("item"))
									{
										node = group.getAttributes();
										final int itemId = Integer.valueOf(node.getNamedItem("id").getNodeValue());
										final double rate = (node.getNamedItem("rate") != null) ? Double.valueOf(node.getNamedItem("rate").getNodeValue()) : 1.0;
										final long min = Integer.valueOf(node.getNamedItem("min").getNodeValue());
										final long max = (node.getNamedItem("max") != null) ? Integer.valueOf(node.getNamedItem("max").getNodeValue()) : 0L;
										final boolean isRateable3 = (node.getNamedItem("rateable") != null) && Boolean.parseBoolean(node.getNamedItem("rateable").getNodeValue());
										rewardList.add(new QuestRewardItem(itemId, rate, min, max, isRateable3));
									}
								}
								groupRewards.put(varId, rewardList);
							}
						}
					}
				}
				if ((expReward != 0L) || (spReward != 0L))
				{
					experience = new QuestExperience(expReward, spReward, expRate, spRate, expRateable, spRateable);
				}
				final QuestTemplate template = new QuestTemplate(id, nameEn, nameRu, minLvl, maxLvl, dropList, experience, rewards, groupRewards, false, params);
				this._quests.put(id, template);
			}
		}
	}
	
	public QuestTemplate getTemplate(final int id)
	{
		return this._quests.get(id);
	}
	
	public static QuestsParser getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final QuestsParser _instance;
		
		static
		{
			_instance = new QuestsParser();
		}
	}
}
