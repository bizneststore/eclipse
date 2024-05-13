package gr.sr.voteEngine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import gr.sr.voteEngine.xml.VoteEngineHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vGodFather
 */
public class VoteDataRead
{
	private static final Logger _log = LoggerFactory.getLogger(VoteDataRead.class);
	
	public static long checkVotedIP(String IP)
	{
		long voteDate = 0;
		for (VoteData voteType : VoteEngineHolder.getInstance().get())
		{
			if (!voteType.isEnabled())
			{
				continue;
			}
			
			if (checkIfVoted(voteType, IP))
			{
				voteDate = System.currentTimeMillis() / 1000L;
			}
			else if (VoteDataConfigs.MUST_VOTE_ALL)
			{
				return 0;
			}
		}
		return voteDate;
	}
	
	public static boolean checkIfVoted(VoteData vote, String IP)
	{
		final String link = vote.getUrl().replace("%ip%", IP);
		boolean voted = false;
		
		final StringBuilder response = new StringBuilder();
		int responseCode = -1;
		
		try
		{
			final HttpURLConnection con = (HttpURLConnection) new URL(link).openConnection();
			
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			responseCode = con.getResponseCode();
			switch (responseCode)
			{
				case 200:
				{
					final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					
					String line;
					while ((line = in.readLine()) != null)
					{
						response.append(line);
					}
					in.close();
					
					if (response.toString().toLowerCase().contains(vote.getWord().toLowerCase()))
					{
						voted = true;
						break;
					}
					
					break;
				}
				case 400:
				default:
				{
					// Handle bad request
					final BufferedReader errorReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
					
					String inputLine;
					while ((inputLine = errorReader.readLine()) != null)
					{
						response.append(inputLine);
					}
					errorReader.close();
					voted = false;
					break;
				}
			}
			
			con.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			_log.error(VoteDataRead.class.getSimpleName() + ": Couldn't connect to " + link);
			if (VoteDataConfigs.ENABLE_FORCE_REWARD_ON_CONNECT_FAIL)
			{
				voted = true;
				_log.error(VoteDataRead.class.getSimpleName() + ": Check excluded.");
			}
		}
		
		if (VoteDataConfigs.DEBUG)
		{
			if (voted)
			{
				_log.info(VoteDataRead.class.getSimpleName() + ": SUCCESS for " + link + " IP: " + IP);
			}
			else
			{
				_log.info(VoteDataRead.class.getSimpleName() + ": FAILED for " + link + " IP: " + IP + " HTTP code: " + responseCode + " Response: " + response.toString());
			}
		}
		
		return voted;
	}
}