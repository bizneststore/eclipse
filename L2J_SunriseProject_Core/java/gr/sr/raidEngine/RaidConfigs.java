package gr.sr.raidEngine;

/**
 * @author vGodFather
 */
public class RaidConfigs
{
	private final boolean _enabled;
	private final long _duration;
	private final long _notifier;
	private final boolean _daily;
	private final int _day;
	private final int _hour;
	private final int _minutes;
	private final int _random;
	
	public RaidConfigs(boolean enabled, long duration, long notifier, boolean daily, int day, int hour, int minute, int random)
	{
		_enabled = enabled;
		_duration = duration;
		_notifier = notifier;
		_daily = daily;
		_day = day;
		_hour = hour;
		_minutes = minute;
		_random = random;
	}
	
	public boolean isEnabled()
	{
		return _enabled;
	}
	
	public long getDuration()
	{
		return _duration;
	}
	
	public long getNotifyDelay()
	{
		return _notifier;
	}
	
	public boolean isDaily()
	{
		return _daily;
	}
	
	public int getDay()
	{
		return _day;
	}
	
	public int getHour()
	{
		return _hour;
	}
	
	public int getMinutes()
	{
		return _minutes;
	}
	
	public int getRandomMins()
	{
		return _random;
	}
}