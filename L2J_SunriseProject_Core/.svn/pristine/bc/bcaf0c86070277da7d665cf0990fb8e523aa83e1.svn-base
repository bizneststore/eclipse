package l2r.gameserver.communitybbs.SunriseBoards;

import java.util.ArrayList;
import java.util.List;

import l2r.gameserver.data.sql.NpcTable;
import l2r.gameserver.instancemanager.RaidBossSpawnManager;
import l2r.gameserver.model.L2Spawn;
import l2r.gameserver.model.actor.L2Npc;
import l2r.gameserver.model.actor.templates.L2NpcTemplate;

import gr.sr.configsEngine.configs.impl.SmartCommunityConfigs;

/**
 * @author L2jSunrise Team
 * @Website www.l2jsunrise.com
 */
public class RaidList extends AbstractSunriseBoards
{
	private final List<Integer> _raids = new ArrayList<>();
	
	private final StringBuilder _list = new StringBuilder();
	
	public RaidList()
	{
		checklist();
	}
	
	private void checklist()
	{
		_raids.clear();
		_raids.addAll(RaidBossSpawnManager.getInstance().getSpawns().keySet());
		_raids.sort((o1, o2) -> NpcTable.getInstance().getTemplate(o1).getLevel() - NpcTable.getInstance().getTemplate(o2).getLevel());
	}
	
	@Override
	public void load(String rfid)
	{
		_list.setLength(0);
		
		checklist();
		
		final int page = Integer.parseInt(rfid);
		final int startpoint = ((page - 1) * SmartCommunityConfigs.RAID_LIST_RESULTS);
		final int checkpoint = (page * SmartCommunityConfigs.RAID_LIST_RESULTS);
		final int endpoint = (checkpoint > _raids.size() ? _raids.size() : checkpoint);
		int pos = startpoint;
		
		for (int i = startpoint; i < endpoint; i++)
		{
			final L2NpcTemplate raid = NpcTable.getInstance().getTemplate(_raids.get(i));
			int npcid = raid.getId();
			String npcname = raid.getName();
			int rlevel = raid.getLevel();
			
			if (!RaidBossSpawnManager.getInstance().isDefined(npcid))
			{
				continue;
			}
			
			final L2Spawn spawn = RaidBossSpawnManager.getInstance().getSpawn(npcid);
			
			boolean isDead = true;
			final L2Npc npc = spawn.getLastSpawn();
			if (npc != null)
			{
				isDead = npc.isDead();
			}
			
			final int mindelay = spawn.getRespawnMinDelay() / 1000 / 60 / 100;
			final int maxdelay = spawn.getRespawnMaxDelay() / 1000 / 60 / 100;
			
			addRaidToList(pos, npcname, rlevel, mindelay, maxdelay, !isDead);
			
			pos++;
		}
	}
	
	private void addRaidToList(int pos, String npcname, int rlevel, int mindelay, int maxdelay, boolean rstatus)
	{
		_list.append("<table border=0 cellspacing=0 cellpadding=0  bgcolor=111111 width=680 height=" + SmartCommunityConfigs.RAID_LIST_ROW_HEIGHT + ">");
		_list.append("<tr>");
		_list.append("<td FIXWIDTH=5></td>");
		_list.append("<td FIXWIDTH=20>" + pos + "</td>");
		_list.append("<td FIXWIDTH=270>" + npcname + "</td>");
		_list.append("<td FIXWIDTH=50>" + rlevel + "</td>");
		_list.append("<td FIXWIDTH=120 align=center>" + mindelay + " - " + maxdelay + "</td>");
		_list.append("<td FIXWIDTH=50 align=center>" + ((rstatus) ? "<font color=99FF00>Alive</font>" : "<font color=CC0000>Dead</font>") + "</td>");
		_list.append("<td FIXWIDTH=5></td>");
		_list.append("</tr>");
		_list.append("</table>");
		_list.append("<img src=\"L2UI.Squaregray\" width=\"680\" height=\"1\">");
	}
	
	@Override
	public String getList()
	{
		return _list.toString();
	}
	
	public static RaidList getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final RaidList _instance = new RaidList();
	}
}
