package gr.sr.achievementEngine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import l2r.L2DatabaseFactory;
import l2r.gameserver.model.actor.instance.L2PcInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vGodFather
 */
public class AchievementsHandler
{
	private static Logger _log = LoggerFactory.getLogger(AchievementsHandler.class);
	
	public static void getAchievemntData(L2PcInstance activeChar)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			try (PreparedStatement statement = con.prepareStatement("SELECT * from achievements WHERE owner_id=" + activeChar.getObjectId()))
			{
				
				try (ResultSet rs = statement.executeQuery())
				{
					String values = "owner_id";
					String in = Integer.toString(activeChar.getObjectId());
					String questionMarks = in;
					int ilosc = AchievementsManager.getInstance().getAchievementList().size();
					
					if (rs.next())
					{
						activeChar._completedAchievements.clear();
						for (int i = 1; i <= ilosc; i++)
						{
							int a = rs.getInt("a" + i);
							
							if (!activeChar._completedAchievements.contains(i))
							{
								if (a == 1)
								{
									activeChar._completedAchievements.add(i);
								}
							}
						}
					}
					else
					{
						// Player hasnt entry in database, means we have to create it.
						
						for (int i = 1; i <= ilosc; i++)
						{
							values += ", a" + i;
							questionMarks += ", 0";
						}
						
						String s = "INSERT INTO achievements(" + values + ") VALUES (" + questionMarks + ")";
						try (PreparedStatement insertStatement = con.prepareStatement(s))
						{
							insertStatement.execute();
							insertStatement.close();
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			_log.warn("[ACHIEVEMENTS ENGINE GETDATA]" + e);
		}
	}
	
	public static void saveAchievementData(L2PcInstance activeChar, int achievementID)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			Statement statement = con.createStatement())
		{
			statement.executeUpdate("UPDATE achievements SET a" + achievementID + "=1 WHERE owner_id=" + activeChar.getObjectId());
			
			if (!activeChar._completedAchievements.contains(achievementID))
			{
				activeChar._completedAchievements.add(achievementID);
			}
		}
		catch (SQLException e)
		{
			_log.warn("[ACHIEVEMENTS SAVE GETDATA]" + e);
		}
	}
}