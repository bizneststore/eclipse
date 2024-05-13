package gr.sr.raidEngine.xml.dataParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import l2r.gameserver.model.Location;

import gr.sr.data.xml.AbstractFileParser;
import gr.sr.raidEngine.RaidLocation;
import gr.sr.raidEngine.xml.dataHolder.RaidLocationsHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * @author vGodFather
 */
public class RaidLocationsParser extends AbstractFileParser<RaidLocationsHolder>
{
	private static Logger _log = LoggerFactory.getLogger(RaidLocationsParser.class);
	private final String RAID_LOCATIONS_FILE_PATH = "./config/sunrise/event/raidEngine/RaidsLocations.xml";
	private static final RaidLocationsParser _instance = new RaidLocationsParser();
	
	public static RaidLocationsParser getInstance()
	{
		return _instance;
	}
	
	protected RaidLocationsParser()
	{
		super(RaidLocationsHolder.getInstance());
	}
	
	@Override
	public File getXMLFile()
	{
		return new File(RAID_LOCATIONS_FILE_PATH);
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
							if (d.getNodeName().equalsIgnoreCase("raid"))
							{
								String name = String.valueOf(d.getAttributes().getNamedItem("locName").getNodeValue());
								int x = Integer.parseInt(d.getAttributes().getNamedItem("x").getNodeValue());
								int y = Integer.parseInt(d.getAttributes().getNamedItem("y").getNodeValue());
								int z = Integer.parseInt(d.getAttributes().getNamedItem("z").getNodeValue());
								
								getHolder()._locations.add(new RaidLocation(name, new Location(x, y, z)));
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