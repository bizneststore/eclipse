package gr.sr.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import l2r.L2DatabaseFactory;
import l2r.gameserver.data.sql.ClanTable;
import l2r.gameserver.model.L2Clan;

import gr.sr.configsEngine.configs.impl.CustomServerConfigs;
import gr.sr.configsEngine.configs.impl.SmartCommunityConfigs;
import gr.sr.dataHolder.PlayersTopData;
import gr.sr.javaBuffer.xml.dataParser.BuffsParser;
import gr.sr.utils.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vGodFather
 */
public class TopListsLoader
{
	private final static Logger _log = LoggerFactory.getLogger(TopListsLoader.class);
	
	/* Lists and Arrays to hold the data */
	private static Map<Integer, Integer[]> _teleports = new ConcurrentHashMap<>();
	private static List<PlayersTopData> _topPvp = new ArrayList<>();
	private static List<PlayersTopData> _topPk = new ArrayList<>();
	private static List<PlayersTopData> _topCurrency = new ArrayList<>();
	private static List<PlayersTopData> _topClan = new ArrayList<>();
	private static List<PlayersTopData> _topOnlineTime = new ArrayList<>();
	private static String _lastUpdate = "N/A";
	private static Long _lastUpdateInMs = 0L;
	
	public TopListsLoader()
	{
		BuffsParser.getInstance().load();
		loadTeleportData();
		loadPvp();
		loadPk();
		loadTopCurrency();
		loadClan();
		loadOnlineTime();
		
		long currentTime = System.currentTimeMillis();
		String date = Tools.convertHourToString(currentTime);
		setLastUpdate(date);
		setLastUpdateInMs(currentTime);
	}
	
	/**
	 * Method to load teleport data from database
	 */
	public static void loadTeleportData()
	{
		_teleports.clear();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT id, x, y, z, onlyForNoble, itemIdToGet, teleportPrice FROM custom_teleports"))
		{
			try (ResultSet rset = statement.executeQuery())
			{
				while (rset.next())
				{
					int tpcategory = rset.getInt("id");
					Integer[] coords = new Integer[6];
					coords[0] = rset.getInt("x");
					coords[1] = rset.getInt("y");
					coords[2] = rset.getInt("z");
					coords[3] = rset.getInt("onlyForNoble");
					coords[4] = rset.getInt("itemIdToGet");
					coords[5] = rset.getInt("teleportPrice");
					
					_teleports.put(tpcategory, coords);
				}
			}
		}
		catch (SQLException e)
		{
			_log.error(TopListsLoader.class.getSimpleName() + ": Error while loading teleport data", e);
		}
		
		_log.info("Loaded " + _teleports.keySet().size() + " teleports for the Custom Npcs-Items.");
	}
	
	/**
	 * Method to load PvP stats from database
	 */
	public void loadPvp()
	{
		_topPvp.clear();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT char_name,clanid,pvpkills FROM characters where accesslevel = 0 order by pvpkills DESC LIMIT 20;"))
		{
			try (ResultSet rset = statement.executeQuery())
			{
				while (rset.next())
				{
					String clanName = "No Clan";
					int clanid = rset.getInt("clanid");
					int pvps = rset.getInt("pvpkills");
					String name = rset.getString("char_name");
					
					if (clanid != 0)
					{
						L2Clan clan = ClanTable.getInstance().getClan(clanid);
						
						// Just in case checking for null pointer
						if (clan != null)
						{
							clanName = clan.getName();
						}
					}
					PlayersTopData playerData = new PlayersTopData(name, clanName, pvps, 0, 0, 0, 0);
					
					_topPvp.add(playerData);
				}
			}
		}
		catch (Exception e)
		{
			_log.error(TopListsLoader.class.getSimpleName() + ": Error while loading top pvp", e);
		}
	}
	
	/**
	 * Method to load Pk stats from database
	 */
	public void loadPk()
	{
		_topPk.clear();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT char_name,clanid,pkkills FROM characters where accesslevel = 0 order by pkkills DESC LIMIT 20;"))
		{
			try (ResultSet rset = statement.executeQuery())
			{
				while (rset.next())
				{
					String clanName = "No Clan";
					int clanid = rset.getInt("clanid");
					int pks = rset.getInt("pkkills");
					String name = rset.getString("char_name");
					
					if (clanid != 0)
					{
						L2Clan clan = ClanTable.getInstance().getClan(clanid);
						
						// Just in case checking for null pointer
						if (clan != null)
						{
							clanName = clan.getName();
						}
					}
					PlayersTopData playerData = new PlayersTopData(name, clanName, 0, pks, 0, 0, 0);
					
					_topPk.add(playerData);
				}
			}
		}
		catch (Exception e)
		{
			_log.error(TopListsLoader.class.getSimpleName() + ": Error while loading top pk", e);
		}
	}
	
	/**
	 * Method to load top currency stats from database
	 */
	public void loadTopCurrency()
	{
		_topCurrency.clear();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			try (PreparedStatement statement = con.prepareStatement("SELECT owner_id,count FROM items WHERE item_id = " + String.valueOf(SmartCommunityConfigs.TOP_CURRENCY_ID) + " AND loc = 'INVENTORY' order by count DESC LIMIT 20;"))
			{
				try (ResultSet rset = statement.executeQuery())
				{
					while (rset.next())
					{
						String charId = rset.getString("owner_id");
						long count = rset.getLong("count");
						
						try (PreparedStatement statement2 = con.prepareStatement("SELECT char_name,clanid,online,classid FROM characters WHERE accesslevel = 0 and charId=" + charId))
						{
							try (ResultSet rset2 = statement2.executeQuery())
							{
								while (rset2.next())
								{
									String clanName = "No Clan";
									String name = rset2.getString("char_name");
									
									PlayersTopData playerData = new PlayersTopData(name, clanName, 0, 0, count, 0, 0);
									
									_topCurrency.add(playerData);
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.error(getClass() + ": Could not load top currency.", e);
		}
	}
	
	/**
	 * Method to load Clan stats from database
	 */
	public void loadClan()
	{
		_topClan.clear();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT clan_id,clan_name,clan_level FROM clan_data order by clan_level DESC LIMIT 20;"))
		{
			try (ResultSet rset = statement.executeQuery())
			{
				while (rset.next())
				{
					String clanLeader = null;
					String clanName = rset.getString("clan_name");
					int clanid = rset.getInt("clan_id");
					int clanLevel = rset.getInt("clan_level");
					
					if (clanid != 0)
					{
						L2Clan clan = ClanTable.getInstance().getClan(clanid);
						
						// Just in case checking for null pointer
						if (clan != null)
						{
							clanLeader = clan.getLeaderName();
						}
					}
					PlayersTopData playerData = new PlayersTopData(clanLeader, clanName, 0, 0, 0, clanLevel, 0);
					
					_topClan.add(playerData);
				}
			}
		}
		catch (Exception e)
		{
			_log.error(TopListsLoader.class.getSimpleName() + ": Error while loading top clan", e);
		}
	}
	
	/**
	 * Method to load top online stats from database
	 */
	public void loadOnlineTime()
	{
		_topOnlineTime.clear();
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT char_name,clanid,onlinetime FROM characters where accesslevel = 0 order by onlinetime DESC LIMIT 20;"))
		{
			try (ResultSet rset = statement.executeQuery())
			{
				while (rset.next())
				{
					String clanName = "No Clan";
					int clanid = rset.getInt("clanid");
					int onlineTime = rset.getInt("onlinetime");
					String name = rset.getString("char_name");
					
					if (clanid != 0)
					{
						L2Clan clan = ClanTable.getInstance().getClan(clanid);
						
						// Just in case checking for null pointer
						if (clan != null)
						{
							clanName = clan.getName();
						}
					}
					PlayersTopData playerData = new PlayersTopData(name, clanName, 0, 0, 0, 0, onlineTime);
					
					_topOnlineTime.add(playerData);
				}
			}
		}
		catch (Exception e)
		{
			_log.error(TopListsLoader.class.getSimpleName() + ": Error while loading top online time", e);
		}
	}
	
	public List<PlayersTopData> getTopPvp()
	{
		return _topPvp;
	}
	
	public List<PlayersTopData> getTopPk()
	{
		return _topPk;
	}
	
	public List<PlayersTopData> getTopCurrency()
	{
		return _topCurrency;
	}
	
	public List<PlayersTopData> getTopClan()
	{
		return _topClan;
	}
	
	public List<PlayersTopData> getTopOnlineTime()
	{
		return _topOnlineTime;
	}
	
	public Integer[] getTeleportInfo(int tpId)
	{
		return _teleports.get(tpId);
	}
	
	public Map<Integer, Integer[]> getPorts()
	{
		return _teleports;
	}
	
	public String getLastUpdate()
	{
		return _lastUpdate;
	}
	
	public void setLastUpdate(String lastupdate)
	{
		_lastUpdate = lastupdate;
	}
	
	public Long getLastUpdateInM()
	{
		return _lastUpdateInMs;
	}
	
	public void setLastUpdateInMs(long ms)
	{
		_lastUpdateInMs = ms;
	}
	
	public String getNextUpdate()
	{
		String nextUpdate = "N/A";
		if (_lastUpdateInMs > 0)
		{
			long nextUpdateInMs = _lastUpdateInMs + (CustomServerConfigs.TOP_LISTS_RELOAD_TIME * 60 * 1000);
			nextUpdateInMs -= System.currentTimeMillis();
			nextUpdate = Tools.convertMinuteToString(nextUpdateInMs);
		}
		return nextUpdate;
	}
	
	public static TopListsLoader getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final TopListsLoader _instance = new TopListsLoader();
	}
}