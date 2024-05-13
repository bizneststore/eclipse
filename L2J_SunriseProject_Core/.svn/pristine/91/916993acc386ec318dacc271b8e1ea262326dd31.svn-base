package gr.sr.configsEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import l2r.log.filter.Log;

import gr.sr.utils.ABConfigs;
import gr.sr.utils.FileProperties;
import gr.sr.utils.PropertiesParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfigs extends ABConfigs
{
	public static final Logger _log = LoggerFactory.getLogger(AbstractConfigs.class);
	
	public static final String OVERRIDE_CONFIG_FILE = "./config/override.properties";
	
	protected FileProperties _settings = new FileProperties();
	protected Properties _override = null;
	
	@Override
	public String getOverrideFile()
	{
		return OVERRIDE_CONFIG_FILE;
	}
	
	@Override
	public void loadFile(String path)
	{
		final File file = new File(path);
		try (InputStream is = new FileInputStream(file))
		{
			_settings.load(is);
		}
		catch (Exception e)
		{
			Log.error("Error while loading " + path + " settings!", e);
		}
	}
	
	@Override
	public void loadOverride()
	{
		final File overrideFile = new File(OVERRIDE_CONFIG_FILE);
		try
		{
			if (overrideFile.exists())
			{
				try (final InputStream overrideInput = new FileInputStream(overrideFile))
				{
					_override = new Properties();
					_override.load(overrideInput);
				}
			}
		}
		catch (Exception e)
		{
		
		}
	}
	
	@Override
	public void loadConfigs()
	{
	
	}
	
	@Override
	public void resetConfigs()
	{
	
	}
	
	@Override
	public <T extends Enum<T>> T getEnum(final PropertiesParser properties, final Properties override, final String key, Class<T> clazz, T defaultValue)
	{
		String value = getString(properties, override, key, null);
		if (value == null)
		{
			return defaultValue;
		}
		
		try
		{
			return Enum.valueOf(clazz, value);
		}
		catch (IllegalArgumentException e)
		{
			return defaultValue;
		}
	}
	
	@Override
	public boolean getBoolean(final PropertiesParser properties, final Properties override, final String key, final boolean defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Boolean.parseBoolean(value);
	}
	
	@Override
	public long getLong(final PropertiesParser properties, final Properties override, final String key, final long defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Long.parseLong(value);
	}
	
	@Override
	public int getInt(final PropertiesParser properties, final Properties override, final String key, final int defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Integer.parseInt(value);
	}
	
	@Override
	public byte getByte(final PropertiesParser properties, final Properties override, final String key, final byte defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Byte.parseByte(value);
	}
	
	@Override
	public float getFloat(final PropertiesParser properties, final Properties override, final String key, final float defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Float.parseFloat(value);
	}
	
	@Override
	public double getDouble(final PropertiesParser properties, final Properties override, final String key, final double defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Double.parseDouble(value);
	}
	
	@Override
	public String getString(final PropertiesParser properties, final Properties override, final String key, final String defaultValue)
	{
		String value = null;
		
		if (override != null)
		{
			value = override.getProperty(key);
		}
		
		if (value == null)
		{
			value = properties.getString(key, null);
		}
		
		if (value == null)
		{
			return defaultValue;
		}
		return value;
	}
	
	@Override
	public <T extends Enum<T>> T getEnum(final FileProperties properties, final Properties override, final String key, Class<T> clazz, T defaultValue)
	{
		String value = getString(properties, override, key, null);
		if (value == null)
		{
			return defaultValue;
		}
		
		try
		{
			return Enum.valueOf(clazz, value);
		}
		catch (IllegalArgumentException e)
		{
			return defaultValue;
		}
	}
	
	@Override
	public boolean getBoolean(final FileProperties properties, final Properties override, final String key, final boolean defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Boolean.parseBoolean(value);
	}
	
	@Override
	public long getLong(final FileProperties properties, final Properties override, final String key, final long defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Long.parseLong(value);
	}
	
	@Override
	public int getInt(final FileProperties properties, final Properties override, final String key, final int defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Integer.parseInt(value);
	}
	
	@Override
	public byte getByte(final FileProperties properties, final Properties override, final String key, final byte defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Byte.parseByte(value);
	}
	
	@Override
	public float getFloat(final FileProperties properties, final Properties override, final String key, final float defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Float.parseFloat(value);
	}
	
	@Override
	public double getDouble(final FileProperties properties, final Properties override, final String key, final double defaultValue)
	{
		final String value = getString(properties, override, key, null);
		
		if (value == null)
		{
			return defaultValue;
		}
		
		return Double.parseDouble(value);
	}
	
	@Override
	public String getString(final FileProperties properties, final Properties override, final String key, final String defaultValue)
	{
		String value = null;
		
		if (override != null)
		{
			value = override.getProperty(key);
		}
		
		if (value == null)
		{
			value = properties.getProperty(key, null);
		}
		
		if (value == null)
		{
			return defaultValue;
		}
		return value;
	}
	
	@Override
	public String getString(final FileProperties properties, final Properties override, final String key, final String defaultValue, boolean log)
	{
		String value = null;
		
		if (override != null)
		{
			value = override.getProperty(key);
		}
		
		if (value == null)
		{
			value = properties.getProperty(key, null, log);
		}
		
		if (value == null)
		{
			return defaultValue;
		}
		return value;
	}
}