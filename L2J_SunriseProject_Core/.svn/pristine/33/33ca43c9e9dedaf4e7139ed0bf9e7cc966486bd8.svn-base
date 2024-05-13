package gr.sr.fakeNpcs.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import l2r.Config;

import gr.sr.data.xml.AbstractFileParser;
import gr.sr.fakeNpcs.FakePc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * @author vGodFather
 */
public class FakeNpcsParser extends AbstractFileParser<FakeNpcsHolder>
{
	private static Logger _log = LoggerFactory.getLogger(FakeNpcsParser.class);
	private final String BUFFS_FILE_PATH = Config.DATAPACK_ROOT + "/data/xml/sunrise/fakeNpcs.xml";
	private static final FakeNpcsParser _instance = new FakeNpcsParser();
	
	public static FakeNpcsParser getInstance()
	{
		return _instance;
	}
	
	protected FakeNpcsParser()
	{
		super(FakeNpcsHolder.getInstance());
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
							if (d.getNodeName().equalsIgnoreCase("npc"))
							{
								final FakePc fpc = new FakePc();
								
								for (Node npctag = d.getFirstChild(); npctag != null; npctag = npctag.getNextSibling())
								{
									switch (npctag.getNodeName().toLowerCase())
									{
										case "npc_id":
											fpc.npcId = getNodeInt(npctag);
											break;
										case "race":
											fpc.race = getNodeInt(npctag);
											break;
										case "sex":
											fpc.sex = getNodeInt(npctag);
											break;
										case "class":
											fpc.clazz = getNodeInt(npctag);
											break;
										case "title":
											fpc.title = getNodeString(npctag);
											break;
										case "title_color":
											fpc.titleColor = Integer.decode("0x" + getNodeString(npctag));
											break;
											
										case "name":
											fpc.name = getNodeString(npctag);
											break;
										case "name_color":
											fpc.nameColor = Integer.decode("0x" + getNodeString(npctag));
											break;
											
										case "hair_style":
											fpc.hairStyle = getNodeInt(npctag);
											break;
										case "hair_color":
											fpc.hairColor = getNodeInt(npctag);
											break;
											
										case "face":
											fpc.face = getNodeInt(npctag);
											break;
										case "mount":
											fpc.mount = getNodeByte(npctag);
											break;
										case "team":
											fpc.team = getNodeByte(npctag);
											break;
										case "hero":
											fpc.hero = getNodeByte(npctag);
											break;
											
										case "pd_head":
											fpc.pdHead = getNodeInt(npctag);
											break;
										case "pd_head_aug":
											fpc.pdHeadAug = getNodeInt(npctag);
											break;
											
										case "pd_under":
											fpc.pdUnder = getNodeInt(npctag);
											break;
										case "pd_under_aug":
											fpc.pdUnderAug = getNodeInt(npctag);
											break;
											
										case "pd_rhand":
											fpc.pdRHand = getNodeInt(npctag);
											break;
										case "pd_rhand_aug":
											fpc.pdRHandAug = getNodeInt(npctag);
											break;
											
										case "pd_lhand":
											fpc.pdLHand = getNodeInt(npctag);
											break;
										case "pd_lhand_aug":
											fpc.pdLHandAug = getNodeInt(npctag);
											break;
											
										case "pd_gloves":
											fpc.pdGloves = getNodeInt(npctag);
											break;
										case "pd_gloves_aug":
											fpc.pdGlovesAug = getNodeInt(npctag);
											break;
											
										case "pd_chest":
											fpc.pdChest = getNodeInt(npctag);
											break;
										case "pd_chest_aug":
											fpc.pdChestAug = getNodeInt(npctag);
											break;
											
										case "pd_legs":
											fpc.pdLegs = getNodeInt(npctag);
											break;
										case "pd_legs_aug":
											fpc.pdLegsAug = getNodeInt(npctag);
											break;
											
										case "pd_feet":
											fpc.pdFeet = getNodeInt(npctag);
											break;
										case "pd_feet_aug":
											fpc.pdFeetAug = getNodeInt(npctag);
											break;
											
										case "pd_back":
											fpc.pdBack = getNodeInt(npctag);
											break;
										case "pd_back_aug":
											fpc.pdBackAug = getNodeInt(npctag);
											break;
											
										case "pd_lrhand":
											fpc.pdLRHand = getNodeInt(npctag);
											break;
										case "pd_lrhand_aug":
											fpc.pdLRHandAug = getNodeInt(npctag);
											break;
											
										case "pd_hair":
											fpc.pdHair = getNodeInt(npctag);
											break;
										case "pd_hair_aug":
											fpc.pdHairAug = getNodeInt(npctag);
											break;
											
										case "pd_hair2":
											fpc.pdHair2 = getNodeInt(npctag);
											break;
										case "pd_hair2_aug":
											fpc.pdHair2Aug = getNodeInt(npctag);
											break;
											
										case "pd_rbracelet":
											fpc.pdRBracelet = getNodeInt(npctag);
											break;
										case "pd_rbracelet_aug":
											fpc.pdRBraceletAug = getNodeInt(npctag);
											break;
											
										case "pd_lbracelet":
											fpc.pdLBracelet = getNodeInt(npctag);
											break;
										case "pd_lbracelet_aug":
											fpc.pdLBraceletAug = getNodeInt(npctag);
											break;
											
										case "pd_deco1":
											fpc.pdDeco1 = getNodeInt(npctag);
											break;
										case "pd_deco1_aug":
											fpc.pdDeco1Aug = getNodeInt(npctag);
											break;
											
										case "pd_deco2":
											fpc.pdDeco2 = getNodeInt(npctag);
											break;
										case "pd_deco2_aug":
											fpc.pdDeco2Aug = getNodeInt(npctag);
											break;
											
										case "pd_deco3":
											fpc.pdDeco3 = getNodeInt(npctag);
											break;
										case "pd_deco3_aug":
											fpc.pdDeco3Aug = getNodeInt(npctag);
											break;
											
										case "pd_deco4":
											fpc.pdDeco4 = getNodeInt(npctag);
											break;
										case "pd_deco4_aug":
											fpc.pdDeco4Aug = getNodeInt(npctag);
											break;
											
										case "pd_deco5":
											fpc.pdDeco5 = getNodeInt(npctag);
											break;
										case "pd_deco5_aug":
											fpc.pdDeco5Aug = getNodeInt(npctag);
											break;
											
										case "pd_deco6":
											fpc.pdDeco6 = getNodeInt(npctag);
											break;
										case "pd_deco6_aug":
											fpc.pdDeco6Aug = getNodeInt(npctag);
											break;
											
										case "enchant_effect":
											fpc.enchantEffect = getNodeInt(npctag);
											break;
										case "pvp_flag":
											fpc.pvpFlag = getNodeInt(npctag);
											break;
										case "karma":
											fpc.karma = getNodeInt(npctag);
											break;
										case "fishing_x":
											fpc.fishingX = getNodeInt(npctag);
											break;
										case "fishing_y":
											fpc.fishingY = getNodeInt(npctag);
											break;
										case "fishing_z":
											fpc.fishingZ = getNodeInt(npctag);
											break;
										case "invisible":
											fpc.invisible = getNodeByte(npctag);
											break;
									}
								}
								
								getHolder().addData(fpc);
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.warn("Fake Npcs: Error: " + e);
			e.printStackTrace();
		}
	}
	
	private String getNodeString(Node d)
	{
		return d.getTextContent();
	}
	
	private byte getNodeByte(Node d)
	{
		return Byte.parseByte(d.getTextContent());
	}
	
	private int getNodeInt(Node d)
	{
		return Integer.parseInt(d.getTextContent());
	}
}