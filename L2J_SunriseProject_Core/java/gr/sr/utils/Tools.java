package gr.sr.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Tools
{
	protected static final Logger _log = LoggerFactory.getLogger(Tools.class);
	
	public static String insertPeriodically(String text, String insert, int period)
	{
		StringBuilder builder = new StringBuilder(text.length() + (insert.length() * (text.length() / period)) + 1);
		
		int index = 0;
		String prefix = "";
		while (index < text.length())
		{
			// Don't put the insert in the very first iteration.
			// This is easier than appending it *after* each substring
			builder.append(prefix);
			prefix = insert;
			builder.append(text.substring(index, Math.min(index + period, text.length())));
			index += period;
		}
		return builder.toString();
	}
	
	public static long convertStringToDate(String dateString)
	{
		SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		java.util.Date date;
		try
		{
			date = s.parse(dateString);
		}
		catch (ParseException e)
		{
			return 0;
		}
		
		return date.getTime();
	}
	
	public static String convertDateToString(long time)
	{
		Date dt = new Date(time);
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String stringDate = s.format(dt);
		return stringDate;
	}
	
	public static String convertHourToString(long time)
	{
		Date dt = new Date(time);
		SimpleDateFormat s = new SimpleDateFormat("HH:mm");
		String stringDate = s.format(dt);
		return stringDate;
	}
	
	public static String convertMinuteToString(long time)
	{
		Date dt = new Date(time);
		SimpleDateFormat s = new SimpleDateFormat("mm:ss");
		String stringDate = s.format(dt);
		return stringDate;
	}
	
	public static boolean isDualBox(L2PcInstance player1, L2PcInstance player2)
	{
		return isDualBox(player1, player2, false);
	}
	
	/**
	 * Method to check dual box per pc ip
	 * @param player1
	 * @param player2
	 * @param recordDualBox
	 * @return
	 */
	public static boolean isDualBox(L2PcInstance player1, L2PcInstance player2, boolean recordDualBox)
	{
		if ((player1.getClient() == null) || player1.getClient().isDetached() || (player2.getClient() == null) || player2.getClient().isDetached())
		{
			return false;
		}
		
		try
		{
			String ip_net1 = getNetIp(player1);
			String ip_net2 = getNetIp(player2);
			
			String ip_pc1 = getPcIp(player1);
			String ip_pc2 = getPcIp(player2);
			
			if (ip_net1.equals(ip_net2) && ip_pc1.equals(ip_pc2))
			{
				if (recordDualBox)
				{
					_log.warn("Dual Box System: " + player1 + " (" + ip_net1 + "/ " + ip_pc1 + ") Dual Box Detected!");
					_log.warn("Dual Box System: " + player2 + " (" + ip_net2 + "/ " + ip_pc2 + ") Dual Box Detected!");
				}
				return true;
			}
		}
		catch (Exception e)
		{
			// _log.warn(Tools.class.getSimpleName() + ": Exception while rying to get external IP.", e.getMessage());
			return false;
		}
		
		return false;
	}
	
	public static String getPcIp(L2PcInstance player)
	{
		if ((player.getClient() == null) || player.getClient().isDetached())
		{
			return null;
		}
		
		String pcIp = "";
		int[][] trace1 = player.getClient().getTrace();
		for (int o = 0; o < trace1[0].length; o++)
		{
			pcIp = pcIp + trace1[0][o];
			if (o != (trace1[0].length - 1))
			{
				pcIp = pcIp + ".";
			}
		}
		return pcIp;
	}
	
	public static String getNetIp(L2PcInstance player)
	{
		if ((player.getClient() == null) || player.getClient().isDetached())
		{
			return null;
		}
		
		return player.getClient().getInetAddress().getHostAddress();
	}
	
	public static final void sleep(final long time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e)
		{
		
		}
	}
	
	public static String calc(String text)
	{
		
		return "";
	}
}
