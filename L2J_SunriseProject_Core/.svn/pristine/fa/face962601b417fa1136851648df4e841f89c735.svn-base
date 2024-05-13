package gr.sr.javaBuffer.xml.dataParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import l2r.Config;

import gr.sr.data.xml.AbstractFileParser;
import gr.sr.javaBuffer.BufferMenuCategories;
import gr.sr.javaBuffer.BuffsInstance;
import gr.sr.javaBuffer.xml.dataHolder.BuffsHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * @author vGodFather
 */
public class BuffsParser extends AbstractFileParser<BuffsHolder>
{
	private static Logger _log = LoggerFactory.getLogger(BuffsParser.class);
	private final String BUFFS_FILE_PATH = Config.DATAPACK_ROOT + "/data/xml/sunrise/JavaBuffer.xml";
	private static final BuffsParser _instance = new BuffsParser();
	
	public static BuffsParser getInstance()
	{
		return _instance;
	}
	
	protected BuffsParser()
	{
		super(BuffsHolder.getInstance());
	}
	
	@Override
	public File getXMLFile()
	{
		return new File(BUFFS_FILE_PATH);
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
							if (d.getNodeName().equalsIgnoreCase("buffs"))
							{
								String name = String.valueOf(d.getAttributes().getNamedItem("name").getNodeValue());
								String category = String.valueOf(d.getAttributes().getNamedItem("category").getNodeValue());
								String buff_id = String.valueOf(d.getAttributes().getNamedItem("buff_id").getNodeValue());
								String buff_lvl = String.valueOf(d.getAttributes().getNamedItem("buff_level").getNodeValue());
								String custom_lvl = String.valueOf(d.getAttributes().getNamedItem("custom_level").getNodeValue());
								String description = String.valueOf(d.getAttributes().getNamedItem("description").getNodeValue());
								
								BuffsInstance buffInst = new BuffsInstance(Integer.valueOf(buff_id), Integer.valueOf(buff_lvl), Integer.valueOf(custom_lvl), name, description, BufferMenuCategories.valueOf(category));
								
								getHolder()._buffs.put(buffInst.getId(), buffInst);
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.warn("Buffer Engine: Error: " + e);
			e.printStackTrace();
		}
	}
}