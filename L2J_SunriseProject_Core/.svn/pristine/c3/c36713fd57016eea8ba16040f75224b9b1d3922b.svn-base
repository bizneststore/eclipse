package gr.sr.voteEngine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import l2r.L2DatabaseFactory;
import l2r.gameserver.cache.HtmCache;
import l2r.gameserver.data.xml.impl.ItemData;
import l2r.gameserver.handler.IVoicedCommandHandler;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.serverpackets.NpcHtmlMessage;
import l2r.util.Rnd;

import gr.sr.utils.db.DbUtils;
import gr.sr.voteEngine.xml.VoteEngineHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RewardVote implements IVoicedCommandHandler
{
	private static enum ValueType
	{
		ACCOUNT_NAME,
		IP_ADRESS,
		HWID
	}
	
	private static final Logger _log = LoggerFactory.getLogger(RewardVote.class);
	
	private static final String[] COMMANDS_LIST = new String[]
	{
		VoteDataConfigs.VOICE_COMMAND,
		"getRewardList",
		"getRewardInfo",
		"getRewardRequest"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		if (activeChar == null) // No null pointers
		{
			return false;
		}
		if (command.equalsIgnoreCase(VoteDataConfigs.VOICE_COMMAND))
		{
			sendPanel(activeChar, "main", null, null);
		}
		else if (command.equalsIgnoreCase("getRewardInfo"))
		{
			sendPanel(activeChar, "information", "%url%", VoteDataConfigs.SERVER_SITE);
		}
		else if (command.equalsIgnoreCase("getRewardList"))
		{
			String _rewardList = "";
			
			final String rewardTemplatePath = "data/html/sunrise/votesystem/templateReward.htm";
			final String siteTemplatePath = "data/html/sunrise/votesystem/templateSite.htm";
			
			for (VoteData voteData : VoteEngineHolder.getInstance().get())
			{
				if (!voteData.isEnabled())
				{
					continue;
				}
				
				String siteTemplate = HtmCache.getInstance().getHtm(activeChar, activeChar.getHtmlPrefix(), siteTemplatePath);
				siteTemplate = siteTemplate.replace("{%}site{%}", String.valueOf(voteData.getName()));
				
				for (VoteDataReward element : voteData.getRewards())
				{
					final int itemId = element.getId();
					final int itemAmount = element.getCount();
					final float itemChance = element.getChance();
					
					String rewardtemplate = HtmCache.getInstance().getHtm(activeChar, activeChar.getHtmlPrefix(), rewardTemplatePath);
					
					rewardtemplate = rewardtemplate.replace("{%}icon{%}", ItemData.getInstance().getTemplate(itemId).getIcon());
					rewardtemplate = rewardtemplate.replace("{%}name{%}", ItemData.getInstance().getTemplate(itemId).getName());
					rewardtemplate = rewardtemplate.replace("{%}count{%}", String.valueOf(itemAmount));
					rewardtemplate = rewardtemplate.replace("{%}chance{%}", String.valueOf(itemChance));
					
					siteTemplate += rewardtemplate;
				}
				
				_rewardList += siteTemplate;
			}
			
			sendPanel(activeChar, "rewardList", "%rewardlist%", _rewardList);
		}
		else if (command.equalsIgnoreCase("getRewardRequest"))
		{
			if (activeChar.getLevel() < VoteDataConfigs.MIN_LEVEL)
			{
				activeChar.sendMessage(VoteDataConfigs.MESSAGE_MIN_LEVEL);
				sendPanel(activeChar, "main", null, null);
				return false;
			}
			
			final boolean isbusy = activeChar.getQuickVarB("isbusyvoting", false);
			
			if (isbusy)
			{
				activeChar.sendMessage(VoteDataConfigs.MESSAGE_BUSY);
				sendPanel(activeChar, "main", null, null);
				// return false;
			}
			
			activeChar.sendMessage(VoteDataConfigs.MESSAGE_VOTING);
			
			activeChar.setQuickVar("isbusyvoting", true);
			
			// getting IP of client, here we will have to check for HWID
			String IPClient = activeChar.getClient().getConnectionAddress().getHostAddress();
			
			String uniqueID = activeChar.getClient().getHWID();
			if (uniqueID == null)
			{
				uniqueID = "";
			}
			
			// TODO we need to clean this part of code
			// to much duplicate code
			if (VoteDataConfigs.MUST_VOTE_ALL)
			{
				boolean hasVoted = true;
				for (VoteData voteData : VoteEngineHolder.getInstance().get())
				{
					if (!voteData.isEnabled())
					{
						continue;
					}
					
					final boolean voted = VoteDataRead.checkIfVoted(voteData, IPClient);
					if (!voted)
					{
						hasVoted = false;
						break;
					}
				}
				
				if (!hasVoted)
				{
					activeChar.sendMessage(VoteDataConfigs.MESSAGE_FAIL);
					return false;
				}
				
				boolean cantakeglobal = true;
				for (VoteData voteData : VoteEngineHolder.getInstance().get())
				{
					if (!voteData.isEnabled())
					{
						continue;
					}
					
					// Return 0 if he didnt voted. Date when he voted on website
					long dateHeVotedOnWebsite = System.currentTimeMillis() / 1000L;
					// Calculate if he can take reward
					final boolean canTakeReward = canTakeReward(dateHeVotedOnWebsite, IPClient, activeChar, voteData.getName(), uniqueID);
					if (!canTakeReward)
					{
						cantakeglobal = false;
					}
					
					if (canTakeReward)
					{
						insertInDataBase(dateHeVotedOnWebsite, IPClient, activeChar, voteData.getName(), uniqueID);
						
						for (VoteDataReward element : voteData.getRewards())
						{
							if ((Rnd.get(100) > element.getChance()))
							{
								continue;
							}
							
							activeChar.addItem("VoteReward", element.getId(), element.getCount(), activeChar, true);
						}
						
						sendPanel(activeChar, "main", null, null);
						
						_log.info("Vote Reward: " + activeChar.getName() + "[IP:" + IPClient + "][SITE: " + voteData.getName() + "] rewarded for voting.");
					}
				}
				
				if (VoteDataConfigs.ENABLE_GLOBAL_REWARDS && cantakeglobal)
				{
					for (VoteDataReward element : VoteDataConfigs.GLOBAL_REWARDS)
					{
						if ((Rnd.get(100) > element.getChance()))
						{
							continue;
						}
						
						activeChar.addItem("VoteReward", element.getId(), element.getCount(), activeChar, true);
					}
					
					activeChar.sendMessage(VoteDataConfigs.MESSAGE_SUCCESS);
				}
			}
			else
			{
				boolean hasVotedAtLeastOne = false;
				for (VoteData voteData : VoteEngineHolder.getInstance().get())
				{
					if (!voteData.isEnabled())
					{
						continue;
					}
					
					final boolean voted = VoteDataRead.checkIfVoted(voteData, IPClient);
					if (!voted)
					{
						continue;
					}
					
					// Return 0 if he didnt voted. Date when he voted on website
					long dateHeVotedOnWebsite = System.currentTimeMillis() / 1000L;
					// Calculate if he can take reward
					if (canTakeReward(dateHeVotedOnWebsite, IPClient, activeChar, voteData.getName(), uniqueID))
					{
						insertInDataBase(dateHeVotedOnWebsite, IPClient, activeChar, voteData.getName(), uniqueID);
						
						for (VoteDataReward element : voteData.getRewards())
						{
							if ((Rnd.get(100) > element.getChance()))
							{
								continue;
							}
							
							activeChar.addItem("VoteReward", element.getId(), element.getCount(), activeChar, true);
						}
						
						hasVotedAtLeastOne = true;
						
						sendPanel(activeChar, "main", null, null);
						
						_log.info("Vote Reward: " + activeChar.getName() + "[IP:" + IPClient + "][SITE: " + voteData + "] got rewarded for voting.");
					}
				}
				
				if (!hasVotedAtLeastOne)
				{
					activeChar.sendMessage(VoteDataConfigs.MESSAGE_FAIL);
				}
				else
				{
					activeChar.sendMessage(VoteDataConfigs.MESSAGE_SUCCESS);
				}
			}
			
			activeChar.setQuickVar("isbusyvoting", false);
			return true;
		}
		return false;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return COMMANDS_LIST;
	}
	
	/**
	 * @param dateHeVotedOnWebsite
	 * @param IPClient
	 * @param activeChar
	 * @param votetype
	 * @param HwID
	 **/
	private static void insertInDataBase(long dateHeVotedOnWebsite, String IPClient, L2PcInstance activeChar, String votetype, String HwID)
	{
		insertInDataBase(dateHeVotedOnWebsite, activeChar.getAccountName(), votetype, ValueType.ACCOUNT_NAME);
		insertInDataBase(dateHeVotedOnWebsite, IPClient, votetype, ValueType.IP_ADRESS);
		if (VoteDataConfigs.ENABLE_HWID_CHECK)
		{
			insertInDataBase(dateHeVotedOnWebsite, HwID, votetype, ValueType.HWID);
		}
	}
	
	private static void insertInDataBase(long dateHeVotedOnWebsite, String value, String votetype, ValueType type)
	{
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("SELECT * FROM character_votes WHERE value=? AND value_type=? AND votetype=?");
			statement.setString(1, value);
			statement.setInt(2, type.ordinal());
			statement.setString(3, votetype);
			rset = statement.executeQuery();
			
			if (rset.next()) // He already exit in database because he voted before.
			{
				int count = rset.getInt("vote_count");
				PreparedStatement statement2 = null;
				try
				{
					statement2 = con.prepareStatement("UPDATE character_votes SET date_voted_website=?, date_take_reward_in_game=?, vote_count=? WHERE value=? AND value_type=? AND votetype=?");
					statement2.setLong(1, dateHeVotedOnWebsite);
					statement2.setLong(2, (System.currentTimeMillis() / 1000L));
					statement2.setInt(3, (count + 1));
					statement2.setString(4, value);
					statement2.setInt(5, type.ordinal());
					statement2.setString(6, votetype);
					statement2.executeUpdate();
				}
				catch (SQLException e)
				{
					_log.error("RewardVote:insertInDataBase(long,String,ValueType): " + e, e);
				}
				finally
				{
					DbUtils.closeQuietly(statement2);
				}
			}
			else
			{
				PreparedStatement statement2 = null;
				try
				{
					statement2 = con.prepareStatement("INSERT INTO character_votes(value, value_type, votetype, date_voted_website, date_take_reward_in_game, vote_count) VALUES (?, ?, ?, ?, ?, ?)");
					statement2.setString(1, value);
					statement2.setInt(2, type.ordinal());
					statement2.setString(3, votetype);
					statement2.setLong(4, dateHeVotedOnWebsite);
					statement2.setLong(5, (System.currentTimeMillis() / 1000L));
					statement2.setInt(6, 1);
					statement2.execute();
				}
				catch (SQLException e)
				{
					_log.error("RewardVote:insertInDataBase(long,String,ValueType): " + e, e);
				}
				finally
				{
					DbUtils.closeQuietly(statement2);
				}
			}
		}
		catch (SQLException e)
		{
			_log.error("RewardVote:insertInDataBase(long,String,ValueType): " + e, e);
		}
		finally
		{
			DbUtils.closeQuietly(con, statement, rset);
		}
	}
	
	private static boolean canTakeReward(long dateHeVotedOnWebsites, String IPClient, L2PcInstance activeChar, String votetype, String HwID)
	{
		int whenCanVoteACC = canTakeReward(dateHeVotedOnWebsites, activeChar.getAccountName(), votetype, ValueType.ACCOUNT_NAME);
		int whenCanVoteIP = canTakeReward(dateHeVotedOnWebsites, IPClient, votetype, ValueType.IP_ADRESS);
		int whenCanVoteHWID = canTakeReward(dateHeVotedOnWebsites, HwID, votetype, ValueType.HWID);
		
		whenCanVoteACC = Math.max(whenCanVoteACC, Math.max(whenCanVoteIP, whenCanVoteHWID));
		
		if (whenCanVoteACC > 0)
		{
			if (whenCanVoteACC > 60)
			{
				activeChar.sendMessage("You can vote only once at 12 hours and 5 minutes. You still have to wait " + (whenCanVoteACC / 60) + " hours " + (whenCanVoteACC % 60) + " minutes.");
			}
			else
			{
				activeChar.sendMessage("You can vote only once at 12 hours and 5 minutes. You still have to wait " + whenCanVoteACC + " minutes.");
			}
			return false;
		}
		return true;
	}
	
	private static int canTakeReward(long dateHeVotedOnWebsite, String value, String votetype, ValueType type)
	{
		int dateLastVote = 0; // Date When he last voted on server
		int whenCanVote = 0; // The number of minutes when he can vote
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("SELECT date_take_reward_in_game FROM character_votes WHERE value=? AND votetype=? AND value_type=?");
			statement.setString(1, value);
			statement.setString(2, votetype);
			statement.setInt(3, type.ordinal());
			rset = statement.executeQuery();
			
			if (rset.next())
			{
				dateLastVote = rset.getInt("date_take_reward_in_game");
			}
		}
		catch (SQLException e)
		{
			_log.error("RewardVote:canTakeReward(long,String,String): " + e, e);
		}
		finally
		{
			DbUtils.closeQuietly(con, statement, rset);
		}
		
		// The number of minutes when he can vote
		if (dateLastVote == 0)
		{
			whenCanVote = (int) ((dateHeVotedOnWebsite - (System.currentTimeMillis() / 1000L)) / 60);
		}
		else
		{
			whenCanVote = (int) (((dateLastVote + (12 * 60 * 60) + 300) - (System.currentTimeMillis() / 1000L)) / 60);
		}
		
		return whenCanVote;
	}
	
	private void sendPanel(L2PcInstance activeChar, String html, String varToReplace, String valueToReplace)
	{
		String htmFile = "data/html/sunrise/votesystem/" + html + ".htm";
		
		NpcHtmlMessage msg = new NpcHtmlMessage();
		msg.setFile(activeChar, activeChar.getHtmlPrefix(), htmFile);
		if (varToReplace != null)
		{
			msg.replace(varToReplace, valueToReplace);
		}
		activeChar.sendPacket(msg);
	}
}