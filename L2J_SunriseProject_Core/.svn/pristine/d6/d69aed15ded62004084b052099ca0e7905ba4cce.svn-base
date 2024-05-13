package l2r.gameserver.data.sql;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import l2r.gameserver.data.xml.impl.NpcTableXmlImpl;
import l2r.gameserver.model.StatsSet;
import l2r.gameserver.model.actor.templates.L2NpcTemplate;

import org.w3c.dom.Document;

/**
 * @author vGodFather
 */
public class NpcTable extends ABNpcTable
{
	public NpcTable()
	{
	
	}
	
	@Override
	public synchronized void load()
	{
		throw new UnsupportedOperationException();
	}
	
	public void reloadNpc(int id)
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Save npc.
	 * @param npc the npc
	 */
	public void saveNpc(StatsSet npc)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		throw new UnsupportedOperationException();
	}
	
	public int getClanId(String clanName)
	{
		throw new UnsupportedOperationException();
	}
	
	public L2NpcTemplate getTemplate(int id)
	{
		throw new UnsupportedOperationException();
	}
	
	public L2NpcTemplate getTemplateByName(String name)
	{
		throw new UnsupportedOperationException();
	}
	
	public List<L2NpcTemplate> getTemplates(Predicate<L2NpcTemplate> filter)
	{
		throw new UnsupportedOperationException();
	}
	
	public List<L2NpcTemplate> getAllOfLevel(int... lvls)
	{
		throw new UnsupportedOperationException();
	}
	
	public List<L2NpcTemplate> getAllMonstersOfLevel(int... lvls)
	{
		throw new UnsupportedOperationException();
	}
	
	public List<L2NpcTemplate> getAllNpcStartingWith(String text)
	{
		throw new UnsupportedOperationException();
	}
	
	public List<L2NpcTemplate> getAllNpcOfClassType(String... classTypes)
	{
		throw new UnsupportedOperationException();
	}
	
	public void loadNpcsSkillLearn()
	{
		throw new UnsupportedOperationException();
	}
	
	public Map<Integer, L2NpcTemplate> getNpcs()
	{
		throw new UnsupportedOperationException();
	}
	
	public static NpcTable getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final NpcTable _instance = new NpcTableXmlImpl();
	}
}
