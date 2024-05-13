package gr.sr.raidEngine.xml.dataParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import gr.sr.data.xml.AbstractFileParser;
import gr.sr.raidEngine.RaidDrop;
import gr.sr.raidEngine.RaidGroup;
import gr.sr.raidEngine.RaidType;
import gr.sr.raidEngine.xml.dataHolder.RaidAndDropsHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * @author vGodFather
 */
public class RaidAndDropsParser extends AbstractFileParser<RaidAndDropsHolder>
{
	private static Logger _log = LoggerFactory.getLogger(RaidAndDropsParser.class);
	private final String RAID_AND_DROPS_FILE_PATH = "./config/sunrise/event/raidEngine/RaidsAndDrops.xml";
	private static final RaidAndDropsParser _instance = new RaidAndDropsParser();
	
	public static RaidAndDropsParser getInstance()
	{
		return _instance;
	}
	
	protected RaidAndDropsParser()
	{
		super(RaidAndDropsHolder.getInstance());
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
							if (d.getNodeName().equalsIgnoreCase("spawnChanceGroup"))
							{
								float groupChance = Float.parseFloat(d.getAttributes().getNamedItem("groupChance").getNodeValue());
								RaidType type = Enum.valueOf(RaidType.class, d.getAttributes().getNamedItem("type").getNodeValue());
								RaidGroup raidGroup = new RaidGroup(groupChance, type);
								
								for (Node e = d.getFirstChild(); e != null; e = e.getNextSibling())
								{
									if (e.getNodeName().equalsIgnoreCase("raid"))
									{
										int raidId = Integer.parseInt(e.getAttributes().getNamedItem("id").getNodeValue());
										int raidMaxDrops = Integer.parseInt(e.getAttributes().getNamedItem("maxDrops").getNodeValue());
										raidGroup.getRaids().put(raidId, raidMaxDrops);
									}
									else if (e.getNodeName().equalsIgnoreCase("dropLists"))
									{
										for (Node a = e.getFirstChild(); a != null; a = a.getNextSibling())
										{
											if (a.getNodeName().equalsIgnoreCase("item"))
											{
												int itemId = Integer.parseInt(a.getAttributes().getNamedItem("id").getNodeValue());
												int min = Integer.parseInt(a.getAttributes().getNamedItem("min").getNodeValue());
												int max = Integer.parseInt(a.getAttributes().getNamedItem("max").getNodeValue());
												float dropChance = Float.parseFloat(a.getAttributes().getNamedItem("chance").getNodeValue());
												int maxOccurs = Integer.parseInt(a.getAttributes().getNamedItem("maxOccurs").getNodeValue());
												int minOccurs = Integer.parseInt(a.getAttributes().getNamedItem("minOccurs").getNodeValue());
												
												raidGroup.getDrops().add(new RaidDrop(itemId, min, max, dropChance, maxOccurs, minOccurs));
											}
										}
									}
								}
								getHolder()._raids.add(raidGroup);
							}
						}
					}
				}
			}
			Collections.sort(getHolder()._raids, getHolder().compareByChance);
		}
		catch (Exception e)
		{
			_log.warn("Raid Engine: Error: " + e);
			e.printStackTrace();
		}
	}
}