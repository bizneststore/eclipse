package gr.sr.main;

import l2r.gameserver.communitybbs.SunriseBoards.CastleStatus;
import l2r.gameserver.communitybbs.SunriseBoards.GrandBossList;
import l2r.gameserver.communitybbs.SunriseBoards.HeroeList;
import l2r.gameserver.communitybbs.SunriseBoards.TopClan;
import l2r.gameserver.communitybbs.SunriseBoards.TopOnlinePlayers;
import l2r.gameserver.communitybbs.SunriseBoards.TopPkPlayers;
import l2r.gameserver.communitybbs.SunriseBoards.TopPvpPlayers;

import gr.sr.utils.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class use to reload top lists data from database. Current lists: Top pvp, top daily pvp, top pk, top rpc, top fa, top clan.
 * @author vGodFather
 */
public class TopListsUpdater implements Runnable
{
	private final static Logger _log = LoggerFactory.getLogger(TopListsUpdater.class);
	
	@Override
	public void run()
	{
		long startTime = System.currentTimeMillis();
		
		TopListsLoader.getInstance().loadPvp();
		TopListsLoader.getInstance().loadPk();
		TopListsLoader.getInstance().loadTopCurrency();
		TopListsLoader.getInstance().loadClan();
		TopListsLoader.getInstance().loadOnlineTime();
		
		// Load info for community
		TopPvpPlayers.getInstance().load();
		TopPkPlayers.getInstance().load();
		// TopCurrency.getInstance().load();
		TopClan.getInstance().load();
		TopOnlinePlayers.getInstance().load();
		
		HeroeList.getInstance().load();
		CastleStatus.getInstance().load();
		GrandBossList.getInstance().load();
		
		_log.info("TopListsUpdater: Data reloaded in " + (System.currentTimeMillis() - startTime) + " ms.");
		
		long currentTime = System.currentTimeMillis();
		String date = Tools.convertMinuteToString(currentTime);
		TopListsLoader.getInstance().setLastUpdate(date);
		TopListsLoader.getInstance().setLastUpdateInMs(currentTime);
	}
}