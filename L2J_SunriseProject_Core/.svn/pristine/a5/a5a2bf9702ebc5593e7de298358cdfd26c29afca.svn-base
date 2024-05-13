package gr.sr.premiumEngine.runnable;

import java.util.Calendar;

import l2r.gameserver.model.L2World;
import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.premiumEngine.PremiumHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class used to check every X minutes current online players premium status
 * @author vGodFather
 */
public class PremiumChecker implements Runnable
{
	private final static Logger _log = LoggerFactory.getLogger(PremiumChecker.class);
	
	@Override
	public void run()
	{
		long startTime = Calendar.getInstance().getTimeInMillis();
		for (L2PcInstance player : L2World.getInstance().getPlayers())
		{
			if (player.isOnline() && (player.getClient() != null) && !player.getClient().isDetached())
			{
				PremiumHandler.restorePremServiceData(player, player.getAccountName());
			}
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		_log.info("Premium Engine : Services reloaded in " + (endTime - startTime) + " ms.");
	}
}