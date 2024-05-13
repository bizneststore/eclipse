/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package gr.sr.leaderboards;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.L2World;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.SystemMessageId;
import l2r.gameserver.network.serverpackets.ItemList;
import l2r.gameserver.network.serverpackets.SystemMessage;
import l2r.gameserver.util.Broadcast;
import l2r.util.Util;

import gr.sr.configsEngine.configs.impl.LeaderboardsConfigs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vGodFather
 */
public class TvTLeaderboard
{
	private static TvTLeaderboard _instance;
	public Logger _log = LoggerFactory.getLogger(ArenaLeaderboard.class);
	public Map<Integer, TvTRank> _ranks = new ConcurrentHashMap<>();
	protected Future<?> _actionTask = null;
	protected int SAVETASK_DELAY = LeaderboardsConfigs.RANK_TVT_INTERVAL;
	protected Long nextTimeUpdateReward = 0L;
	
	public static TvTLeaderboard getInstance()
	{
		if (_instance == null)
		{
			_instance = new TvTLeaderboard();
		}
		
		return _instance;
	}
	
	public TvTLeaderboard()
	{
		engineInit();
	}
	
	public void onKill(int owner, String name)
	{
		TvTRank ar = null;
		if (_ranks.get(owner) == null)
		{
			ar = new TvTRank();
		}
		else
		{
			ar = _ranks.get(owner);
		}
		
		ar.pvp();
		ar.name = name;
		_ranks.put(owner, ar);
	}
	
	public void onDeath(int owner, String name)
	{
		TvTRank ar = null;
		if (_ranks.get(owner) == null)
		{
			ar = new TvTRank();
		}
		else
		{
			ar = _ranks.get(owner);
		}
		
		ar.death();
		ar.name = name;
		_ranks.put(owner, ar);
	}
	
	public void startTask()
	{
		if (_actionTask == null)
		{
			_actionTask = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new TvTTask(), 1000, SAVETASK_DELAY * 60000);
		}
	}
	
	public void stopTask()
	{
		if (_actionTask != null)
		{
			_actionTask.cancel(true);
		}
		
		_actionTask = null;
	}
	
	public class TvTTask implements Runnable
	{
		@Override
		public void run()
		{
			_log.info("TvTManager: Autotask init.");
			formRank();
			nextTimeUpdateReward = System.currentTimeMillis() + (SAVETASK_DELAY * 60000);
		}
	}
	
	public void formRank()
	{
		Map<Integer, Integer> scores = new ConcurrentHashMap<>();
		for (int obj : _ranks.keySet())
		{
			TvTRank ar = _ranks.get(obj);
			scores.put(obj, ar.kills - ar.death);
		}
		
		int Top = -1;
		int idTop = 0;
		for (int id : scores.keySet())
		{
			if (scores.get(id) > Top)
			{
				idTop = id;
				Top = scores.get(id);
			}
		}
		
		TvTRank arTop = _ranks.get(idTop);
		
		if (arTop == null)
		{
			Broadcast.toAllOnlinePlayers("TvTMaster: No winners at this time!");
			_ranks.clear();
			return;
		}
		
		L2PcInstance winner = (L2PcInstance) L2World.getInstance().findObject(idTop);
		
		Broadcast.toAllOnlinePlayers("TvTMaster: " + arTop.name + " is the winner for this time with " + arTop.kills + "/" + arTop.death + ". Next calculation in " + LeaderboardsConfigs.RANK_TVT_INTERVAL + " min(s).");
		if ((winner != null) && (LeaderboardsConfigs.RANK_TVT_REWARD_ID > 0) && (LeaderboardsConfigs.RANK_TVT_REWARD_COUNT > 0))
		{
			winner.getInventory().addItem("TvTManager", LeaderboardsConfigs.RANK_TVT_REWARD_ID, LeaderboardsConfigs.RANK_TVT_REWARD_COUNT, winner, null);
			if (LeaderboardsConfigs.RANK_TVT_REWARD_COUNT > 1)
			{
				winner.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.EARNED_S2_S1_S).addItemName(LeaderboardsConfigs.RANK_TVT_REWARD_ID).addInt(LeaderboardsConfigs.RANK_TVT_REWARD_COUNT));
			}
			else
			{
				winner.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.EARNED_ITEM_S1).addItemName(LeaderboardsConfigs.RANK_TVT_REWARD_ID));
			}
			winner.sendPacket(new ItemList(winner, false));
		}
		_ranks.clear();
	}
	
	public String showHtm(int owner)
	{
		Map<Integer, Integer> scores = new ConcurrentHashMap<>();
		for (int obj : _ranks.keySet())
		{
			TvTRank ar = _ranks.get(obj);
			scores.put(obj, ar.kills - ar.death);
		}
		
		scores = Util.sortMap(scores, false);
		
		int counter = 0, max = 20;
		String pt = "<html><body><center>" + "<font color=\"cc00ad\">TvT TOP " + max + " Players</font><br>";
		
		pt += "<table width=260 border=0 cellspacing=0 cellpadding=0 bgcolor=333333>";
		pt += "<tr> <td align=center>No.</td> <td align=center>Name</td> <td align=center>Kills</td> <td align=center>Deaths</td> </tr>";
		pt += "<tr> <td align=center>&nbsp;</td> <td align=center>&nbsp;</td> <td align=center></td> <td align=center></td> </tr>";
		boolean inTop = false;
		for (int id : scores.keySet())
		{
			if (counter < max)
			{
				TvTRank ar = _ranks.get(id);
				pt += tx(counter, ar.name, ar.kills, ar.death, id == owner);
				if (id == owner)
				{
					inTop = true;
				}
				
				counter++;
			}
			else
			{
				break;
			}
		}
		
		if (!inTop)
		{
			TvTRank arMe = _ranks.get(owner);
			if (arMe != null)
			{
				pt += "<tr> <td align=center>...</td> <td align=center>...</td> <td align=center>...</td> <td align=center>...</td> </tr>";
				int placeMe = 0;
				for (int idMe : scores.keySet())
				{
					placeMe++;
					if (idMe == owner)
					{
						break;
					}
				}
				pt += tx(placeMe, arMe.name, arMe.kills, arMe.death, true);
			}
		}
		
		pt += "</table>";
		pt += "<br><br>";
		if ((LeaderboardsConfigs.RANK_TVT_REWARD_ID > 0) && (LeaderboardsConfigs.RANK_TVT_REWARD_COUNT > 0))
		{
			pt += "Next Reward Time in <font color=\"LEVEL\">" + calcMinTo() + " min(s)</font><br1>";
			pt += "<font color=\"aadd77\">" + LeaderboardsConfigs.RANK_TVT_REWARD_COUNT + " &#" + LeaderboardsConfigs.RANK_TVT_REWARD_ID + ";</font>";
		}
		
		pt += "</center></body></html>";
		
		return pt;
	}
	
	private int calcMinTo()
	{
		return ((int) (nextTimeUpdateReward - System.currentTimeMillis())) / 60000;
	}
	
	private String tx(int counter, String name, int kills, int deaths, boolean mi)
	{
		String t = "";
		
		t += "	<tr>" + "<td align=center>" + (mi ? "<font color=\"LEVEL\">" : "") + (counter + 1) + ".</td>" + "<td align=center>" + name + "</td>" + "<td align=center>" + kills + "</td>" + "<td align=center>" + deaths + "" + (mi ? "</font>" : "") + " </td>" + "</tr>";
		
		return t;
	}
	
	public void engineInit()
	{
		startTask();
		_log.info(getClass().getSimpleName() + ": Initialized");
	}
	
	public class TvTRank
	{
		public int kills, death, classId;
		public String name;
		
		public TvTRank()
		{
			kills = 0;
			death = 0;
		}
		
		public void pvp()
		{
			kills++;
		}
		
		public void death()
		{
			death++;
		}
	}
}