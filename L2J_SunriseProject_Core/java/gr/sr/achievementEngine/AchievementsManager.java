package gr.sr.achievementEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import l2r.Config;
import l2r.L2DatabaseFactory;
import l2r.gameserver.data.sql.NpcTable;
import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.achievementEngine.base.Achievement;
import gr.sr.achievementEngine.base.Condition;
import gr.sr.achievementEngine.base.ConditionType;
import gr.sr.achievementEngine.conditions.Adena;
import gr.sr.achievementEngine.conditions.Clan;
import gr.sr.achievementEngine.conditions.ItemsCount;
import gr.sr.achievementEngine.conditions.Level;
import gr.sr.achievementEngine.conditions.MobToHunt;
import gr.sr.achievementEngine.conditions.Stats;
import gr.sr.achievementEngine.conditions.Status;
import gr.sr.achievementEngine.conditions.Subclass;
import gr.sr.achievementEngine.conditions.WeaponEnchant;
import gr.sr.securityEngine.SecurityActions;
import gr.sr.securityEngine.SecurityType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class AchievementsManager
{
	private final Map<Integer, Achievement> _achievementList = new ConcurrentHashMap<>();
	
	private final String ACHIEVEMENTS_FILE_PATH = Config.DATAPACK_ROOT + "/data/xml/sunrise/achievements.xml";
	
	private static Logger _log = LoggerFactory.getLogger(AchievementsManager.class);
	
	public AchievementsManager()
	{
		// Dummy default
	}
	
	public void loadAchievements()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringComments(true);
		
		final File file = new File(ACHIEVEMENTS_FILE_PATH);
		if (!file.exists())
		{
			_log.warn("Achievements Engine: achievements.xml file does not exist, check directory!");
			return;
		}
		
		try
		{
			try (final InputStreamReader stream = new InputStreamReader(new FileInputStream(file), "UTF-8"))
			{
				final InputSource in = new InputSource(stream);
				in.setEncoding("UTF-8");
				Document doc = factory.newDocumentBuilder().parse(in);
				
				for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
				{
					if (n.getNodeName().equalsIgnoreCase("list"))
					{
						for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
						{
							if (d.getNodeName().equalsIgnoreCase("achievement"))
							{
								int id = checkInt(d, "id");
								
								String name = String.valueOf(d.getAttributes().getNamedItem("name").getNodeValue());
								String description = String.valueOf(d.getAttributes().getNamedItem("description").getNodeValue());
								String reward = String.valueOf(d.getAttributes().getNamedItem("reward").getNodeValue());
								boolean repeat = checkBoolean(d, "repeatable");
								
								List<Condition> conditions = conditionList(d.getAttributes());
								
								_achievementList.put(id, new Achievement(id, name, description, reward, repeat, conditions));
								alterTable(id);
							}
						}
					}
				}
			}
			
			_log.info("Achievements Engine: Loaded " + getAchievementList().size() + " achievements from xml!");
		}
		catch (Exception e)
		{
			_log.warn("Achievements Engine: Error: " + e);
			e.printStackTrace();
		}
	}
	
	public void rewardForAchievement(int achievementID, L2PcInstance player)
	{
		Achievement achievement = _achievementList.get(achievementID);
		
		for (int id : achievement.getRewardList().keySet())
		{
			if (id == -1)
			{
				player.setFame(player.getFame() + achievement.getRewardList().get(id).intValue());
			}
			else if (id == -2)
			{
				// TODO: add support for reputation
			}
			else
			{
				player.addItem(achievement.getName(), id, achievement.getRewardList().get(id), player, true);
			}
		}
	}
	
	public static boolean checkConditions(int achievementID, L2PcInstance player)
	{
		Achievement a = AchievementsManager.getInstance().getAchievementList().get(achievementID);
		
		if (!a.meetAchievementRequirements(player))
		{
			player.sendMessage("You do not meet the criteria in order to take reward.");
			return false;
		}
		
		if (player.getCompletedAchievements().contains(achievementID))
		{
			SecurityActions.startSecurity(player, SecurityType.ACHIEVEMENT_SYSTEM);
			return false;
		}
		
		return true;
	}
	
	public List<Condition> conditionList(NamedNodeMap attributesList)
	{
		final List<Condition> conditions = new LinkedList<>();
		for (int j = 0; j < attributesList.getLength(); j++)
		{
			addToConditionList(attributesList.item(j).getNodeName(), attributesList.item(j).getNodeValue(), conditions);
		}
		return conditions;
	}
	
	private boolean checkBoolean(Node d, String nodename)
	{
		boolean b = false;
		
		try
		{
			b = Boolean.valueOf(d.getAttributes().getNamedItem(nodename).getNodeValue());
		}
		catch (Exception e)
		{
		
		}
		return b;
	}
	
	private int checkInt(Node d, String nodename)
	{
		int i = 0;
		
		try
		{
			i = Integer.valueOf(d.getAttributes().getNamedItem(nodename).getNodeValue());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return i;
	}
	
	/**
	 * Alter table, catch exception if already exist.
	 * @param fieldID
	 */
	private void alterTable(int fieldID)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			Statement statement = con.createStatement())
		{
			statement.executeUpdate("ALTER TABLE achievements ADD a" + fieldID + " INT DEFAULT 0");
		}
		catch (SQLException e)
		{
		
		}
	}
	
	public Map<Integer, Achievement> getAchievementList()
	{
		return _achievementList;
	}
	
	private void addToConditionList(String nodeName, Object value, List<Condition> conditions)
	{
		if (nodeName.equals("minLevel"))
		{
			conditions.add(new Level(value, ConditionType.LEVEL));
		}
		else if (nodeName.equals("minAdenaCount"))
		{
			conditions.add(new Adena(value, ConditionType.AMOUNT));
		}
		else if (nodeName.equals("itemAmmount"))
		{
			conditions.add(new ItemsCount(value, ConditionType.ITEMS_COUNT));
		}
		else if (nodeName.equals("minSubclassCount"))
		{
			conditions.add(new Subclass(value, ConditionType.SUBCLASSES));
		}
		else if (nodeName.equals("minWeaponEnchant"))
		{
			conditions.add(new WeaponEnchant(value, ConditionType.WEAPON_ENCHANT));
		}
		else if (nodeName.equals("mustBeHero"))
		{
			conditions.add(new Status(value, ConditionType.MUST_BE_HERO));
		}
		else if (nodeName.equals("mustBeNoble"))
		{
			conditions.add(new Status(value, ConditionType.MUST_BE_NOBLE));
		}
		else if (nodeName.equals("mustBeClanLeader"))
		{
			conditions.add(new Status(value, ConditionType.CLAN_LEADER));
		}
		else if (nodeName.equals("mustBeAcademyMember"))
		{
			conditions.add(new Status(value, ConditionType.ACADEMY_MEMBER));
		}
		else if (nodeName.equals("mustBeMarried"))
		{
			conditions.add(new Status(value, ConditionType.MUST_BE_MARRIED));
		}
		else if (nodeName.equals("mustBeMageClass"))
		{
			conditions.add(new Status(value, ConditionType.MUST_BE_MAGE));
		}
		else if (nodeName.equals("mustBeSummoner"))
		{
			conditions.add(new Status(value, ConditionType.MUST_BE_SUMMONER));
		}
		else if (nodeName.equals("hasCommonCraft"))
		{
			conditions.add(new Status(value, ConditionType.COMMON_CRAFT));
		}
		else if (nodeName.equals("hasDwarvenCraft"))
		{
			conditions.add(new Status(value, ConditionType.DWARVEN_CRAFT));
		}
		else if (nodeName.equals("maxHP"))
		{
			conditions.add(new Stats(value, ConditionType.MAX_HP));
		}
		else if (nodeName.equals("maxMP"))
		{
			conditions.add(new Stats(value, ConditionType.MAX_MP));
		}
		else if (nodeName.equals("maxCP"))
		{
			conditions.add(new Stats(value, ConditionType.MAX_CP));
		}
		else if (nodeName.equals("minPkCount"))
		{
			conditions.add(new Stats(value, ConditionType.MIN_PK_COUNT));
		}
		else if (nodeName.equals("minPvPCount"))
		{
			conditions.add(new Stats(value, ConditionType.MIN_PVP_COUNT));
		}
		else if (nodeName.equals("minKarmaCount"))
		{
			conditions.add(new Stats(value, ConditionType.MIN_KARMA));
		}
		else if (nodeName.equals("minClanLevel"))
		{
			conditions.add(new Clan(value, ConditionType.MIN_CLAN_LEVEL));
		}
		else if (nodeName.equals("minClanMembersCount"))
		{
			conditions.add(new Clan(value, ConditionType.CLAN_MEMBERS));
		}
		else if (nodeName.equals("crpAmmount"))
		{
			conditions.add(new Clan(value, ConditionType.CRP_AMMOUNT));
		}
		else if (nodeName.equals("mustbelordOfCastle"))
		{
			conditions.add(new Clan(value, ConditionType.CASTLE_LORD));
		}
		else if (nodeName.equals("mobToHunt"))
		{
			conditions.add(new MobToHunt(NpcTable.getInstance().getTemplate(Integer.parseInt(value.toString())).getName(), ConditionType.MOB_TO_HUNT));
			setKillMobId(Integer.parseInt(value.toString()));
		}
	}
	
	// Temp solution for specific mob Kill Id
	private int killMobId = 0;
	
	public void setKillMobId(int mobid)
	{
		killMobId = mobid;
	}
	
	public int getMobId()
	{
		return killMobId;
	}
	
	public static AchievementsManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final AchievementsManager _instance = new AchievementsManager();
	}
}