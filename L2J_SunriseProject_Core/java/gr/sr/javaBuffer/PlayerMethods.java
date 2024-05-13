package gr.sr.javaBuffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import l2r.Config;
import l2r.L2DatabaseFactory;
import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.util.Util;

import gr.sr.configsEngine.configs.impl.BufferConfigs;
import gr.sr.configsEngine.configs.impl.PremiumServiceConfigs;
import gr.sr.javaBuffer.runnable.BuffDelay;
import gr.sr.main.Conditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerMethods
{
	private static Logger _log = LoggerFactory.getLogger(PlayerMethods.class);
	
	// @formatter:off
	private static final int[] danceSongList =
	{
		264, 265, 266, 267, 268, 269, 270, 271, 272,
		273, 274, 275, 276, 277, 304, 305, 306, 307,
		308, 309, 310, 311, 349, 363, 364, 365, 366,
		529, 530, 914, 915
	};
	// @formatter:on
	
	// XXX: Buffer Methods and loads
	public static void reloadProfileBuffs(L2PcInstance activeChar)
	{
		clearProfiles(activeChar);
		loadProfileBuffs(activeChar);
	}
	
	// Returns the buffs of the specified profile
	public static void loadProfileBuffs(L2PcInstance activeChar)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			try (PreparedStatement statement = con.prepareStatement("SELECT buff_id, profile FROM aio_scheme_profiles_buffs WHERE charId = ?"))
			{
				statement.setInt(1, activeChar.getObjectId());
				
				try (ResultSet rset = statement.executeQuery())
				{
					while (rset.next())
					{
						String profileName = rset.getString("profile");
						int buffId = rset.getInt("buff_id");
						
						addBuffToProfile(profileName, buffId, false, activeChar);
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void addBuffToProfile(String profileName, int buffId, L2PcInstance activeChar)
	{
		addBuffToProfile(profileName, buffId, true, activeChar);
	}
	
	public static void addBuffToProfile(String profileName, int buffId, boolean updateDB, L2PcInstance activeChar)
	{
		List<Integer> buffList = activeChar._profileBuffs.get(profileName);
		
		if (buffList == null)
		{
			buffList = new ArrayList<>();
		}
		
		buffList.add(buffId);
		addProfile(profileName, buffList, activeChar);
		
		if (updateDB)
		{
			saveBuff(activeChar, profileName, buffId);
		}
	}
	
	public static void delBuffFromProfile(String profileName, int buffId, L2PcInstance activeChar)
	{
		List<Integer> buffList = activeChar._profileBuffs.get(profileName);
		if (buffList.contains(buffId))
		{
			buffList.remove(new Integer(buffId));
			addProfile(profileName, buffList, activeChar);
			deleteBuff(activeChar, profileName, buffId);
		}
	}
	
	public static boolean createProfile(String profileName, L2PcInstance activeChar)
	{
		if (!saveProfile(activeChar, profileName))
		{
			return false;
		}
		
		addProfile(profileName, new ArrayList<>(), activeChar);
		return true;
	}
	
	public static void addProfile(String profileName, List<Integer> list, L2PcInstance activeChar)
	{
		activeChar._profileBuffs.put(profileName, list);
	}
	
	public static void clearProfiles(L2PcInstance activeChar)
	{
		activeChar._profileBuffs.keySet().forEach(p -> delProfile(p, false, false, activeChar));
		activeChar._profileBuffs.clear();
	}
	
	public static void delProfile(String profileName, L2PcInstance activeChar)
	{
		delProfile(profileName, true, true, activeChar);
	}
	
	public static void delProfile(String profileName, boolean delete, boolean updateDB, L2PcInstance activeChar)
	{
		List<Integer> buffList = activeChar._profileBuffs.get(profileName);
		buffList.clear();
		
		if (delete)
		{
			activeChar._profileBuffs.remove(profileName);
		}
		
		if (updateDB)
		{
			deleteProfile(activeChar, profileName);
		}
		
		activeChar.sendMessage("Scheme: " + profileName + " deleted.");
	}
	
	public static List<String> getProfiles(L2PcInstance activeChar)
	{
		List<String> returnProfiles = new LinkedList<>();
		for (String profileName : activeChar._profileBuffs.keySet())
		{
			returnProfiles.add(profileName);
		}
		return returnProfiles;
	}
	
	public static int getProfileSize(String profile, L2PcInstance activeChar)
	{
		return activeChar._profileBuffs.size();
	}
	
	public static boolean checkDanceAmount(L2PcInstance player, String profile, BufferMenuCategories category, int objectId)
	{
		if (getDanceSongCount(profile, player) >= BufferConfigs.MAX_DANCE_PERPROFILE)
		{
			player.sendMessage("You cannot add more than " + BufferConfigs.MAX_DANCE_PERPROFILE + " dances-songs.");
			JavaBufferBypass.callBuffToAdd(category, player, profile, objectId);
			return false;
		}
		return true;
	}
	
	public static boolean checkBuffsAmount(L2PcInstance player, String profile, BufferMenuCategories category, int objectId)
	{
		if (getOtherBuffCount(profile, player) >= BufferConfigs.MAX_BUFFS_PERPROFILE)
		{
			player.sendMessage("You cannot add more than " + BufferConfigs.MAX_BUFFS_PERPROFILE + " buffs.");
			JavaBufferBypass.callBuffToAdd(category, player, profile, objectId);
			return false;
		}
		return true;
	}
	
	private static int getDanceSongCount(String profile, L2PcInstance activeChar)
	{
		int buffCount = 0;
		
		try
		{
			if (profile == null)
			{
				if (Config.DEBUG)
				{
					_log.warn("PROFILE IS NULL!! REPORT TO REUNION TEAM!");
				}
				activeChar.sendMessage("Please restart your char something was wrong.");
				return buffCount;
			}
			
			if ((activeChar._profileBuffs == null) || (activeChar._profileBuffs.get(profile) == null))
			{
				if (Config.DEBUG)
				{
					_log.warn("PLAYER PROFILE BUFFS IS NULL!! REPORT TO REUNION TEAM!");
				}
				activeChar.sendMessage("Please restart your char something was wrong.");
				return buffCount;
			}
			
			for (int buffId : danceSongList)
			{
				if (activeChar._profileBuffs.get(profile).contains(buffId))
				{
					buffCount++;
				}
			}
		}
		catch (Exception e)
		{
			if (Config.DEBUG)
			{
				_log.warn("PLAYER PROFILE BUFFS IS NULL!! REPORT TO REUNION TEAM!");
			}
			activeChar.sendMessage("Please restart your char something was wrong.");
			return buffCount;
		}
		
		return buffCount;
	}
	
	private static int getOtherBuffCount(String profile, L2PcInstance activeChar)
	{
		int buffCount = 0;
		
		try
		{
			if (profile == null)
			{
				if (Config.DEBUG)
				{
					_log.warn("PROFILE IS NULL!! REPORT TO REUNION TEAM!");
				}
				activeChar.sendMessage("Please restart your char something was wrong.");
				return buffCount;
			}
			
			if ((activeChar._profileBuffs == null) || (activeChar._profileBuffs.get(profile) == null))
			{
				if (Config.DEBUG)
				{
					_log.warn("PLAYER PROFILE BUFFS IS NULL!! REPORT TO REUNION TEAM!");
				}
				activeChar.sendMessage("Please restart your char something was wrong.");
				return buffCount;
			}
			
			List<Integer> buffList = activeChar._profileBuffs.get(profile);
			buffCount = buffList.size();
			
			for (int buffId : danceSongList)
			{
				if (buffList.contains(buffId))
				{
					buffCount--;
				}
			}
		}
		catch (Exception e)
		{
			if (Config.DEBUG)
			{
				_log.warn("PLAYER PROFILE BUFFS IS NULL!! REPORT TO REUNION TEAM!");
			}
			activeChar.sendMessage("Please restart your char something was wrong.");
			return buffCount;
		}
		
		return buffCount;
	}
	
	public static void addDelay(L2PcInstance player)
	{
		if (BufferConfigs.BUFFER_ENABLE_DELAY)
		{
			ThreadPoolManager.getInstance().executeGeneral(new BuffDelay(player));
		}
	}
	
	public static boolean checkDelay(L2PcInstance player)
	{
		if (BufferConfigs.BUFFER_ENABLE_DELAY && BuffDelay._delayers.contains(player))
		{
			if (BufferConfigs.BUFFER_DELAY_SENDMESSAGE)
			{
				player.sendMessage("In order to use buffer functions again, you will have to wait " + BufferConfigs.BUFFER_DELAY + "!");
			}
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method to save profile to database
	 * @param player
	 * @param profile
	 * @return
	 */
	public static boolean saveProfile(L2PcInstance player, String profile)
	{
		if (!Util.isAlphaNumeric(profile.substring(1)))
		{
			player.sendMessage("Profile name must be alpha-numeric.");
			return false;
		}
		
		if (!isValidName(profile.substring(1)))
		{
			player.sendMessage("Profile name must be alpha-numeric.");
			return false;
		}
		
		if (PlayerMethods.hasProfile(profile, player))
		{
			player.sendMessage("Your profile is already in use.");
			return false;
		}
		
		if (player.isPremium())
		{
			if (PlayerMethods.getProfileSize(profile, player) >= PremiumServiceConfigs.PREMIUM_MAX_SCHEME)
			{
				player.sendMessage("Cannot create more profiles.");
				return false;
			}
		}
		else
		{
			if (PlayerMethods.getProfileSize(profile, player) >= BufferConfigs.MAX_SCHEME_PROFILES)
			{
				player.sendMessage("Cannot create more profiles.");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Method to save player selected buff to scheme profile
	 * @param player
	 * @param profile
	 * @param buffId
	 */
	public static void saveBuff(L2PcInstance player, String profile, int buffId)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("INSERT INTO aio_scheme_profiles_buffs VALUES (?, ?, ?)"))
		{
			statement.setInt(1, player.getObjectId());
			statement.setString(2, profile);
			statement.setInt(3, buffId);
			statement.execute();
		}
		catch (SQLException e)
		{
			player.sendMessage("Something went wrong check your profile name, if problem still exists please rr your char.");
		}
	}
	
	/**
	 * Method to delete player selected scheme profile from database
	 * @param player
	 * @param profile
	 */
	public static void deleteProfile(L2PcInstance player, String profile)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("DELETE FROM aio_scheme_profiles_buffs WHERE charId = ? AND profile = ?"))
		{
			statement.setInt(1, player.getObjectId());
			statement.setString(2, profile);
			statement.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to delete player selected buff from scheme profile
	 * @param player
	 * @param profile
	 * @param buffId
	 */
	public static void deleteBuff(L2PcInstance player, String profile, int buffId)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = con.prepareStatement("DELETE FROM aio_scheme_profiles_buffs WHERE charId = ? AND profile = ? AND buff_id = ?"))
		{
			statement.setInt(1, player.getObjectId());
			statement.setString(2, profile);
			statement.setInt(3, buffId);
			statement.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean isValidName(String text)
	{
		Pattern pattern = Pattern.compile("[A-Za-z0-9]*");
		Matcher regexp = pattern.matcher(text);
		if (!regexp.matches())
		{
			return false;
		}
		return true;
	}
	
	public static boolean checkPriceConsume(L2PcInstance player, int buffsCount)
	{
		boolean willTakeItems = true;
		if ((BufferConfigs.FREE_BUFFS_TILL_LEVEL > 0) && (player.getLevel() <= BufferConfigs.FREE_BUFFS_TILL_LEVEL))
		{
			willTakeItems = false;
		}
		
		if (willTakeItems)
		{
			if (!Conditions.checkPlayerItemCount(player, BufferConfigs.BUFF_ITEM_ID, buffsCount * BufferConfigs.PRICE_PERBUFF))
			{
				return false;
			}
			player.destroyItemByItemId("Scheme system", BufferConfigs.BUFF_ITEM_ID, buffsCount * BufferConfigs.PRICE_PERBUFF, player, true);
		}
		return true;
	}
	
	public static boolean hasProfile(String profile, L2PcInstance activeChar)
	{
		return activeChar._profileBuffs.containsKey(profile);
	}
	
	public static List<Integer> getProfileBuffs(String profile, L2PcInstance activeChar)
	{
		return activeChar._profileBuffs.get(profile);
	}
}