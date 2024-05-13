package gr.sr.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import l2r.L2DatabaseFactory;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.util.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vGodFather
 */
public class PcVariables
{
	protected static final Logger _log = LoggerFactory.getLogger(PcVariables.class.getName());
	
	private final Map<String, String> user_variables = new ConcurrentHashMap<>();
	private final Map<String, Object> quickVars = new ConcurrentHashMap<>();
	
	private L2PcInstance _activeChar = null;
	
	public PcVariables(L2PcInstance activeChar)
	{
		_activeChar = activeChar;
	}
	
	public void setVar(String name, String value)
	{
		user_variables.put(name, value);
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("REPLACE INTO sunrise_variables (obj_id, type, name, value, expire_time) VALUES (?,'user-var',?,?,-1)"))
		{
			statement.setInt(1, _activeChar.getObjectId());
			statement.setString(2, name);
			statement.setString(3, value);
			statement.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void unsetVar(String name)
	{
		if (name == null)
		{
			return;
		}
		
		if (user_variables.remove(name) != null)
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection();
				PreparedStatement statement = con.prepareStatement("DELETE FROM `sunrise_variables` WHERE `obj_id`=? AND `type`='user-var' AND `name`=? LIMIT 1"))
			{
				statement.setInt(1, _activeChar.getObjectId());
				statement.setString(2, name);
				statement.execute();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public String getVar(String name, String defaultVal)
	{
		String var = user_variables.get(name);
		if (var == null)
		{
			return defaultVal;
		}
		return user_variables.get(name);
	}
	
	public int getVarI(String name, int defaultValue)
	{
		String var = user_variables.get(name);
		if (var == null)
		{
			return defaultValue;
		}
		return Integer.parseInt(var);
	}
	
	public long getVarL(String name, long defaultValue)
	{
		String var = user_variables.get(name);
		if (var == null)
		{
			return defaultValue;
		}
		return Long.parseLong(var);
	}
	
	public boolean getVarB(String name, boolean defaultVal)
	{
		String var = user_variables.get(name);
		if (var == null)
		{
			return defaultVal;
		}
		return !(var.equals("0") || var.equalsIgnoreCase("false"));
	}
	
	public boolean getVarB(String name)
	{
		String var = user_variables.get(name);
		return !((var == null) || var.equals("0") || var.equalsIgnoreCase("false"));
	}
	
	public Map<String, String> getVars()
	{
		return user_variables;
	}
	
	public void loadVariables()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement offline = con.prepareStatement("SELECT * FROM sunrise_variables WHERE obj_id = ?"))
		{
			offline.setInt(1, _activeChar.getObjectId());
			try (ResultSet rs = offline.executeQuery())
			{
				while (rs.next())
				{
					String name = rs.getString("name");
					String value = Strings.stripSlashes(rs.getString("value"));
					user_variables.put(name, value);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Quick vars
	/**
	 * Adding Variable to Map<Name, Value>. It's not saved to database. Value can be taken back by {@link #getQuickVarO(String, Object...)} method.
	 * @param name key
	 * @param value value
	 */
	public void setQuickVar(String name, Object value)
	{
		quickVars.put(name, value);
	}
	
	/**
	 * Getting back String Value located in quickVars Map<Name, Value>. If value doesn't exist, defaultValue is returned. If value isn't String type, throws Error
	 * @param name key
	 * @param defaultValue Value returned when <code>name</code> key doesn't exist
	 * @return value
	 */
	public String getQuickVar(String name, String... defaultValue)
	{
		if (!quickVars.containsKey(name))
		{
			if (defaultValue.length > 0)
			{
				return defaultValue[0];
			}
			return null;
		}
		return (String) quickVars.get(name);
	}
	
	/**
	 * Getting back String Value located in quickVars Map<Name, Value>. If value doesn't exist, defaultValue is returned. If value isn't Boolean type, throws Error
	 * @param name key
	 * @param defaultValue Value returned when <code>name</code> key doesn't exist
	 * @return value
	 */
	public boolean getQuickVarB(String name, boolean... defaultValue)
	{
		if (!quickVars.containsKey(name))
		{
			if (defaultValue.length > 0)
			{
				return defaultValue[0];
			}
			return false;
		}
		return ((Boolean) quickVars.get(name)).booleanValue();
	}
	
	/**
	 * Getting back Integer Value located in quickVars Map<Name, Value>. If value doesn't exist, defaultValue is returned. If value isn't Integer type, throws Error
	 * @param name key
	 * @param defaultValue Value returned when <code>name</code> key doesn't exist
	 * @return value
	 */
	public int getQuickVarI(String name, int... defaultValue)
	{
		if (!quickVars.containsKey(name))
		{
			if (defaultValue.length > 0)
			{
				return defaultValue[0];
			}
			return -1;
		}
		return ((Integer) quickVars.get(name)).intValue();
	}
	
	/**
	 * Getting back Long Value located in quickVars Map<Name, Value>. If value doesn't exist, defaultValue is returned. If value isn't Long type, throws Error
	 * @param name key
	 * @param defaultValue Value returned when <code>name</code> key doesn't exist
	 * @return value
	 */
	public long getQuickVarL(String name, long... defaultValue)
	{
		if (!quickVars.containsKey(name))
		{
			if (defaultValue.length > 0)
			{
				return defaultValue[0];
			}
			return -1L;
		}
		return ((Long) quickVars.get(name)).longValue();
	}
	
	/**
	 * Getting back Object Value located in quickVars Map<Name, Value>. If value doesn't exist, defaultValue is returned.
	 * @param name key
	 * @param defaultValue Value returned when <code>name</code> key doesn't exist
	 * @return value
	 */
	public Object getQuickVarO(String name, Object... defaultValue)
	{
		if (!quickVars.containsKey(name))
		{
			if (defaultValue.length > 0)
			{
				return defaultValue[0];
			}
			return null;
		}
		return quickVars.get(name);
	}
	
	/**
	 * Checking if quickVars Map<Name, Value> contains a name as a Key
	 * @param name key
	 * @return contains name
	 */
	public boolean containsQuickVar(String name)
	{
		return quickVars.containsKey(name);
	}
	
	/**
	 * Removing Key from quickVars Map
	 * @param name - key
	 */
	public void deleteQuickVar(String name)
	{
		quickVars.remove(name);
	}
	
	public L2PcInstance getPlayer()
	{
		return _activeChar;
	}
}
