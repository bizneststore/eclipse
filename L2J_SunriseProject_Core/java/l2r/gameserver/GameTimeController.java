/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package l2r.gameserver;

import java.util.Calendar;
import java.util.concurrent.Future;

import l2r.gameserver.instancemanager.DayNightSpawnManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Game Time controller class.
 * @author vGodFather
 */
public final class GameTimeController
{
	private static final Logger _log = LoggerFactory.getLogger(GameTimeController.class);
	
	public static final int TICKS_PER_SECOND = 10; // not able to change this without checking through code
	public static final int MILLIS_IN_TICK = 1000 / TICKS_PER_SECOND;
	public static final int IG_DAYS_PER_DAY = 6;
	public static final int MILLIS_PER_IG_DAY = (3600000 * 24) / IG_DAYS_PER_DAY;
	public static final int SECONDS_PER_IG_DAY = MILLIS_PER_IG_DAY / 1000;
	public static final int MINUTES_PER_IG_DAY = SECONDS_PER_IG_DAY / 60;
	public static final int TICKS_PER_IG_DAY = SECONDS_PER_IG_DAY * TICKS_PER_SECOND;
	public static final int TICKS_PER_IG_HOUR = TICKS_PER_IG_DAY / 24;
	public static final int TICKS_PER_IG_MINUTE = TICKS_PER_IG_HOUR / 60;
	public static final int TICKS_SUN_STATE_CHANGE = TICKS_PER_IG_DAY / 4;
	
	private final long _referenceTime;
	private final long _startTime;
	
	private Future<?> _task;
	
	private boolean _isNight;
	
	protected GameTimeController()
	{
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		_referenceTime = c.getTimeInMillis();
		_startTime = System.currentTimeMillis();
		
		_isNight = isNight();
		if (_isNight)
		{
			ThreadPoolManager.getInstance().executeAi(() -> DayNightSpawnManager.getInstance().notifyChangeMode());
		}
		
		// Check for day night changes every 10 seconds
		_task = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(() -> check(), 0, 10 * 1000);
		_log.info(getClass().getSimpleName() + ": Started.");
	}
	
	public final int getGameTime()
	{
		return (getGameTicks() % TICKS_PER_IG_DAY) / TICKS_PER_IG_MINUTE;
	}
	
	public final int getGameHour()
	{
		return getGameTime() / 60;
	}
	
	public final int getGameMinute()
	{
		return getGameTime() % 60;
	}
	
	public final boolean isNight()
	{
		return getGameHour() < 6;
	}
	
	/**
	 * The true GameTime tick. Directly taken from current time. This represents the tick of the time.
	 * @return
	 */
	public final int getGameTicks()
	{
		return (int) ((System.currentTimeMillis() - _referenceTime) / MILLIS_IN_TICK);
	}
	
	public final void stopTimer()
	{
		if (_task != null)
		{
			_task.cancel(true);
			_task = null;
		}
		_log.info("Stopping " + getClass().getSimpleName());
	}
	
	private void check()
	{
		if (isNight() != _isNight)
		{
			_isNight = !_isNight;
			ThreadPoolManager.getInstance().executeAi(() -> DayNightSpawnManager.getInstance().notifyChangeMode());
		}
	}
	
	public int getServerRunTime()
	{
		long currentTime = System.currentTimeMillis();
		
		return (int) ((currentTime - _startTime) / 1000);
	}
	
	public static GameTimeController getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final GameTimeController _instance = new GameTimeController();
	}
}
