package gr.sr.raidEngine.xml.dataParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import gr.sr.data.xml.AbstractFileParser;
import gr.sr.raidEngine.RaidConfigs;
import gr.sr.raidEngine.xml.dataHolder.RaidConfigsHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * @author vGodFather
 */
public class RaidConfigsParser extends AbstractFileParser<RaidConfigsHolder>
{
	private static Logger _log = LoggerFactory.getLogger(RaidConfigsParser.class);
	private final String RAID_AND_DROPS_FILE_PATH = "./config/sunrise/event/raidEngine/configs.xml";
	private static final RaidConfigsParser _instance = new RaidConfigsParser();
	
	public static RaidConfigsParser getInstance()
	{
		return _instance;
	}
	
	protected RaidConfigsParser()
	{
		super(RaidConfigsHolder.getInstance());
	}
	
	@Override
	public File getXMLFile()
	{
		return new File(RAID_AND_DROPS_FILE_PATH);
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
							if (d.getNodeName().equalsIgnoreCase("config"))
							{
								boolean enabled = Boolean.parseBoolean(d.getAttributes().getNamedItem("ENABLED").getNodeValue());
								boolean daily = Boolean.parseBoolean(d.getAttributes().getNamedItem("DAILY").getNodeValue());
								long duration = Integer.parseInt(d.getAttributes().getNamedItem("DURATION").getNodeValue()) * 60 * 1000;
								long notifier = Integer.parseInt(d.getAttributes().getNamedItem("NOTIFY_DELAY").getNodeValue()) * 60 * 1000;
								int day = Integer.parseInt(d.getAttributes().getNamedItem("DAY").getNodeValue());
								int hour = Integer.parseInt(d.getAttributes().getNamedItem("HOUR").getNodeValue());
								int minute = Integer.parseInt(d.getAttributes().getNamedItem("MINUTE").getNodeValue());
								int random = Integer.parseInt(d.getAttributes().getNamedItem("RANDOM_MINUTE").getNodeValue());
								
								getHolder()._configs.add(new RaidConfigs(enabled, duration, notifier, daily, day, hour, minute, random));
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.warn("Raid Engine: Error: " + e);
			e.printStackTrace();
		}
	}
}