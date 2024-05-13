package gr.sr.mods.autorestart;

import java.util.Date;

import l2r.commons.time.cron.InvalidPatternException;
import l2r.commons.time.cron.SchedulingPattern;
import l2r.gameserver.Shutdown;
import l2r.gameserver.ThreadPoolManager;

import gr.sr.configsEngine.configs.impl.AutoRestartConfigs;
import gr.sr.logging.Log;

public class Restart
{
	private long _nextRestart;
	private static Restart _instance;
	
	protected Restart()
	{
		this.startCalculationOfNextRestartTime();
		if (this.getRestartNextTime() <= 0L)
		{
			Log.info("[Auto Restart]: System is disabled.");
		}
	}
	
	public long getRestartNextTime()
	{
		if (this._nextRestart > System.currentTimeMillis())
		{
			return (this._nextRestart - System.currentTimeMillis()) / 1000L;
		}
		return 0L;
	}
	
	private void startCalculationOfNextRestartTime()
	{
		try
		{
			SchedulingPattern cronTime;
			try
			{
				cronTime = new SchedulingPattern(AutoRestartConfigs.AUTO_RESTART_PATTERN);
			}
			catch (InvalidPatternException e)
			{
				return;
			}
			
			final long nextRestart = cronTime.next(System.currentTimeMillis());
			if (nextRestart > System.currentTimeMillis())
			{
				this._nextRestart = nextRestart;
				Log.info("[Auto Restart]: System activated.");
				Log.info("[Auto Restart]: Next restart - " + new Date(this._nextRestart));
				ThreadPoolManager.getInstance().scheduleGeneral(new RestartTask(), nextRestart - System.currentTimeMillis());
			}
		}
		catch (Exception e2)
		{
			Log.warning("[Auto Restart]: Has problem with the config file, please, check and correct it.!");
		}
	}
	
	public static Restart getInstance()
	{
		if (Restart._instance == null)
		{
			Restart._instance = new Restart();
		}
		return Restart._instance;
	}
	
	protected class RestartTask implements Runnable
	{
		@Override
		public void run()
		{
			Log.info("[Auto Restart]: Auto restart started.");
			Shutdown.getInstance().startShutdown(null, AutoRestartConfigs.AUTO_RESTART_TIME, true);
		}
	}
}
