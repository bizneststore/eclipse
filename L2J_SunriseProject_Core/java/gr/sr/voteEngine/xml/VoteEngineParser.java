package gr.sr.voteEngine.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import gr.sr.data.xml.AbstractFileParser;
import gr.sr.voteEngine.VoteData;
import gr.sr.voteEngine.VoteDataConfigs;
import gr.sr.voteEngine.VoteDataReward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * @author vGodFather
 */
public class VoteEngineParser extends AbstractFileParser<VoteEngineHolder>
{
	private static Logger _log = LoggerFactory.getLogger(VoteEngineParser.class);
	private final String FILE_PATH = "./config/sunrise/votesystems/VoteEngine.xml";
	private static final VoteEngineParser _instance = new VoteEngineParser();
	
	public static VoteEngineParser getInstance()
	{
		return _instance;
	}
	
	protected VoteEngineParser()
	{
		super(VoteEngineHolder.getInstance());
	}
	
	@Override
	public File getXMLFile()
	{
		return new File(FILE_PATH);
	}
	
	@Override
	protected void readData()
	{
		try
		{
			try (final InputStreamReader stream = new InputStreamReader(new FileInputStream(getXMLFile()), getEncoding().get()))
			{
				final InputSource in = new InputSource(stream);
				final Document doc = getFactory().newDocumentBuilder().parse(in);
				
				for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
				{
					if (n.getNodeName().equalsIgnoreCase("list"))
					{
						for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
						{
							if (d.getNodeName().equalsIgnoreCase("global"))
							{
								for (Node e = d.getFirstChild(); e != null; e = e.getNextSibling())
								{
									if (e.getNodeName().equalsIgnoreCase("enabled"))
									{
										VoteDataConfigs.ENABLE_VOTE_SYSTEM = Boolean.parseBoolean(e.getTextContent());
									}
									else if (e.getNodeName().equalsIgnoreCase("debug"))
									{
										VoteDataConfigs.DEBUG = Boolean.parseBoolean(e.getTextContent());
									}
									else if (e.getNodeName().equalsIgnoreCase("minlevel"))
									{
										VoteDataConfigs.MIN_LEVEL = Integer.parseInt(e.getTextContent());
									}
									else if (e.getNodeName().equalsIgnoreCase("hwidcheck"))
									{
										VoteDataConfigs.ENABLE_HWID_CHECK = Boolean.parseBoolean(e.getTextContent());
									}
									else if (e.getNodeName().equalsIgnoreCase("onconnectfailforcereward"))
									{
										VoteDataConfigs.ENABLE_FORCE_REWARD_ON_CONNECT_FAIL = Boolean.parseBoolean(e.getTextContent());
									}
									else if (e.getNodeName().equalsIgnoreCase("globalrewards"))
									{
										VoteDataConfigs.ENABLE_GLOBAL_REWARDS = Boolean.parseBoolean(e.getTextContent());
									}
									else if (e.getNodeName().equalsIgnoreCase("mustVotedAll"))
									{
										VoteDataConfigs.MUST_VOTE_ALL = Boolean.parseBoolean(e.getTextContent());
									}
									else if (e.getNodeName().equalsIgnoreCase("voicecommand"))
									{
										VoteDataConfigs.VOICE_COMMAND = e.getTextContent();
									}
									else if (e.getNodeName().equalsIgnoreCase("serversite"))
									{
										VoteDataConfigs.SERVER_SITE = e.getTextContent();
									}
									else if (e.getNodeName().equalsIgnoreCase("messagefail"))
									{
										VoteDataConfigs.MESSAGE_FAIL = e.getTextContent();
									}
									else if (e.getNodeName().equalsIgnoreCase("messagesuccess"))
									{
										VoteDataConfigs.MESSAGE_SUCCESS = e.getTextContent();
									}
									else if (e.getNodeName().equalsIgnoreCase("messagevoting"))
									{
										VoteDataConfigs.MESSAGE_VOTING = e.getTextContent();
									}
									else if (e.getNodeName().equalsIgnoreCase("messagebusy"))
									{
										VoteDataConfigs.MESSAGE_BUSY = e.getTextContent();
									}
									else if (e.getNodeName().equalsIgnoreCase("messageminlevel"))
									{
										VoteDataConfigs.MESSAGE_MIN_LEVEL = e.getTextContent();
									}
									else if (e.getNodeName().equalsIgnoreCase("rewards"))
									{
										for (Node a = e.getFirstChild(); a != null; a = a.getNextSibling())
										{
											if (a.getNodeName().equalsIgnoreCase("item"))
											{
												final int itemId = Integer.parseInt(a.getAttributes().getNamedItem("id").getNodeValue());
												final int count = Integer.parseInt(a.getAttributes().getNamedItem("count").getNodeValue());
												final float chance = Float.parseFloat(a.getAttributes().getNamedItem("chance").getNodeValue());
												
												final VoteDataReward reward = new VoteDataReward(itemId, count, chance);
												VoteDataConfigs.GLOBAL_REWARDS.add(reward);
											}
										}
									}
								}
							}
							else if (d.getNodeName().equalsIgnoreCase("topsite"))
							{
								final String name = d.getAttributes().getNamedItem("name").getNodeValue();
								final boolean enabled = Boolean.parseBoolean(d.getAttributes().getNamedItem("enabled").getNodeValue());
								
								final VoteData voteData = new VoteData(name, enabled);
								
								for (Node e = d.getFirstChild(); e != null; e = e.getNextSibling())
								{
									if (e.getNodeName().equalsIgnoreCase("checkWord"))
									{
										final String checkWord = String.valueOf(e.getTextContent());
										voteData.setCheckWord(checkWord);
									}
									else if (e.getNodeName().equalsIgnoreCase("api"))
									{
										final String url = e.getAttributes().getNamedItem("url").getNodeValue();
										voteData.setUrl(url);
									}
									else if (e.getNodeName().equalsIgnoreCase("rewards"))
									{
										for (Node a = e.getFirstChild(); a != null; a = a.getNextSibling())
										{
											if (a.getNodeName().equalsIgnoreCase("item"))
											{
												final int itemId = Integer.parseInt(a.getAttributes().getNamedItem("id").getNodeValue());
												final int count = Integer.parseInt(a.getAttributes().getNamedItem("count").getNodeValue());
												final float chance = Float.parseFloat(a.getAttributes().getNamedItem("chance").getNodeValue());
												
												final VoteDataReward reward = new VoteDataReward(itemId, count, chance);
												voteData.addReward(reward);
											}
										}
									}
								}
								
								getHolder().add(voteData);
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.warn("VoteEngineParser: Error: " + e);
			e.printStackTrace();
		}
	}
}