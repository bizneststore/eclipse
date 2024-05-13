/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package l2r.gameserver.model.actor.instance;

import java.util.List;
import java.util.Locale;

import l2r.gameserver.enums.InstanceType;
import l2r.gameserver.idfactory.IdFactory;
import l2r.gameserver.instancemanager.games.monsterrace.HistoryInfoTemplate;
import l2r.gameserver.instancemanager.games.monsterrace.MonsterRaceManager;
import l2r.gameserver.model.actor.L2Npc;
import l2r.gameserver.model.actor.knownlist.RaceManagerKnownList;
import l2r.gameserver.model.actor.templates.L2NpcTemplate;
import l2r.gameserver.model.items.instance.L2ItemInstance;
import l2r.gameserver.network.SystemMessageId;
import l2r.gameserver.network.serverpackets.NpcHtmlMessage;
import l2r.gameserver.network.serverpackets.SystemMessage;

public class L2RaceManagerInstance extends L2Npc
{
	protected static final int[] TICKET_PRICES;
	
	public L2RaceManagerInstance(L2NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.L2RaceManagerInstance);
	}
	
	@Override
	public final RaceManagerKnownList getKnownList()
	{
		return (RaceManagerKnownList) super.getKnownList();
	}
	
	@Override
	public void initKnownList()
	{
		setKnownList(new RaceManagerKnownList(this));
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		if (command.startsWith("BuyTicket"))
		{
			if (MonsterRaceManager.getInstance().getCurrentRaceState() != MonsterRaceManager.RaceState.ACCEPTING_BETS)
			{
				player.sendPacket(SystemMessageId.MONSRACE_TICKETS_NOT_AVAILABLE);
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			int val = Integer.parseInt(command.substring(10));
			if (val == 0)
			{
				player.setRace(0, 0);
				player.setRace(1, 0);
			}
			if (((val == 10) && (player.getRace(0) == 0)) || ((val == 20) && (player.getRace(0) == 0) && (player.getRace(1) == 0)))
			{
				val = 0;
			}
			final int npcId = this.getTemplate().getId();
			final NpcHtmlMessage html = new NpcHtmlMessage(this.getObjectId());
			if (val < 10)
			{
				html.setFile(player, player.getLang(), this.getHtmlPath(npcId, 2));
				for (int i = 0; i < 8; ++i)
				{
					final int n = i + 1;
					final String search = "Mob" + n;
					html.replace(search, MonsterRaceManager.getInstance().getMonsters()[i].getTemplate().getName());
				}
				final String search = "No1";
				if (val == 0)
				{
					html.replace(search, "");
				}
				else
				{
					html.replace(search, val);
					player.setRace(0, val);
				}
			}
			else if (val < 20)
			{
				if (player.getRace(0) == 0)
				{
					return;
				}
				html.setFile(player, player.getLang(), this.getHtmlPath(npcId, 3));
				html.replace("0place", player.getRace(0));
				String search = "Mob1";
				final String replace = MonsterRaceManager.getInstance().getMonsters()[player.getRace(0) - 1].getTemplate().getName();
				html.replace(search, replace);
				search = "0adena";
				if (val == 10)
				{
					html.replace(search, "");
				}
				else
				{
					html.replace(search, TICKET_PRICES[val - 11]);
					player.setRace(1, val - 10);
				}
			}
			else if (val == 20)
			{
				if ((player.getRace(0) == 0) || (player.getRace(1) == 0))
				{
					return;
				}
				html.setFile(player, player.getLang(), this.getHtmlPath(npcId, 4));
				html.replace("0place", player.getRace(0));
				String search = "Mob1";
				final String replace = MonsterRaceManager.getInstance().getMonsters()[player.getRace(0) - 1].getTemplate().getName();
				html.replace(search, replace);
				search = "0adena";
				final int price = TICKET_PRICES[player.getRace(1) - 1];
				html.replace(search, price);
				search = "0tax";
				final int tax = 0;
				html.replace(search, "" + tax);
				search = "0total";
				final int total = price + 0;
				html.replace(search, total);
			}
			else
			{
				if ((player.getRace(0) == 0) || (player.getRace(1) == 0))
				{
					return;
				}
				final int ticket = player.getRace(0);
				final int priceId = player.getRace(1);
				if (!player.reduceAdena("Race", TICKET_PRICES[priceId - 1], this, true))
				{
					return;
				}
				player.setRace(0, 0);
				player.setRace(1, 0);
				final L2ItemInstance item = new L2ItemInstance(IdFactory.getInstance().getNextId(), 4443);
				item.setCount(1L);
				item.setEnchantLevel(MonsterRaceManager.getInstance().getRaceNumber());
				item.setCustomType1(ticket);
				item.setCustomType2(TICKET_PRICES[priceId - 1] / 100);
				player.addItem("Race", item, player, false);
				player.sendPacket((SystemMessage.getSystemMessage(SystemMessageId.ACQUIRED_S1_S2).addInt(MonsterRaceManager.getInstance().getRaceNumber())).addItemName(4443));
				MonsterRaceManager.getInstance().setBetOnLane(ticket, TICKET_PRICES[priceId - 1], true);
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			html.replace("1race", MonsterRaceManager.getInstance().getRaceNumber());
			html.replace("%objectId%", this.getObjectId());
			player.sendPacket(html);
		}
		else if (command.equals("ShowOdds"))
		{
			if (MonsterRaceManager.getInstance().getCurrentRaceState() == MonsterRaceManager.RaceState.ACCEPTING_BETS)
			{
				player.sendPacket(SystemMessageId.MONSRACE_NO_PAYOUT_INFO);
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			final NpcHtmlMessage html2 = new NpcHtmlMessage(this.getObjectId());
			html2.setFile(player, player.getLang(), this.getHtmlPath(this.getTemplate().getId(), 5));
			for (int j = 0; j < 8; ++j)
			{
				final int n2 = j + 1;
				html2.replace("Mob" + n2, MonsterRaceManager.getInstance().getMonsters()[j].getTemplate().getName());
				final double odd = MonsterRaceManager.getInstance().getOdds().get(j);
				html2.replace("Odd" + n2, (odd > 0.0) ? String.format(Locale.ENGLISH, "%.1f", odd) : "&$804;");
			}
			html2.replace("1race", MonsterRaceManager.getInstance().getRaceNumber());
			html2.replace("%objectId%", this.getObjectId());
			player.sendPacket(html2);
		}
		else if (command.equals("ShowInfo"))
		{
			final NpcHtmlMessage html2 = new NpcHtmlMessage(this.getObjectId());
			html2.setFile(player, player.getLang(), this.getHtmlPath(this.getTemplate().getId(), 6));
			for (int j = 0; j < 8; ++j)
			{
				final int n2 = j + 1;
				final String search2 = "Mob" + n2;
				html2.replace(search2, MonsterRaceManager.getInstance().getMonsters()[j].getTemplate().getName());
			}
			html2.replace("%objectId%", this.getObjectId());
			player.sendPacket(html2);
		}
		else if (command.equals("ShowTickets"))
		{
			final StringBuilder sb = new StringBuilder();
			for (final L2ItemInstance ticket2 : player.getInventory().getAllItemsByItemId(4443))
			{
				if (ticket2.getEnchantLevel() != MonsterRaceManager.getInstance().getRaceNumber())
				{
					append(sb, new Object[]
					{
						"<tr><td><a action=\"bypass -h npc_%objectId%_ShowTicket ",
						ticket2.getObjectId(),
						"\">",
						ticket2.getEnchantLevel(),
						" Race Number</a></td><td align=right><font color=\"LEVEL\">",
						ticket2.getCustomType1(),
						"</font> Number</td><td align=right><font color=\"LEVEL\">",
						ticket2.getCustomType2() * 100,
						"</font> Adena</td></tr>"
					});
				}
			}
			final NpcHtmlMessage html3 = new NpcHtmlMessage(this.getObjectId());
			html3.setFile(player, player.getLang(), this.getHtmlPath(this.getTemplate().getId(), 7));
			html3.replace("%tickets%", sb.toString());
			html3.replace("%objectId%", this.getObjectId());
			player.sendPacket(html3);
		}
		else if (command.startsWith("ShowTicket"))
		{
			final int val = Integer.parseInt(command.substring(11));
			if (val == 0)
			{
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			final L2ItemInstance ticket3 = player.getInventory().getItemByObjectId(val);
			if (ticket3 == null)
			{
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			final int raceId = ticket3.getEnchantLevel();
			final int lane = ticket3.getCustomType1();
			final int bet = ticket3.getCustomType2() * 100;
			final HistoryInfoTemplate info = MonsterRaceManager.getInstance().getHistory().get(raceId - 1);
			if (info == null)
			{
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			final NpcHtmlMessage html4 = new NpcHtmlMessage(this.getObjectId());
			html4.setFile(player, player.getLang(), this.getHtmlPath(this.getTemplate().getId(), 8));
			html4.replace("%raceId%", raceId);
			html4.replace("%lane%", lane);
			html4.replace("%bet%", bet);
			html4.replace("%firstLane%", info.getFirst());
			html4.replace("%odd%", (lane == info.getFirst()) ? String.format(Locale.ENGLISH, "%.2f", info.getOddRate()) : "0.01");
			html4.replace("%objectId%", this.getObjectId());
			html4.replace("%ticketObjectId%", val);
			player.sendPacket(html4);
		}
		else if (command.startsWith("CalculateWin"))
		{
			final int val = Integer.parseInt(command.substring(13));
			if (val == 0)
			{
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			final L2ItemInstance ticket3 = player.getInventory().getItemByObjectId(val);
			if (ticket3 == null)
			{
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			final int raceId = ticket3.getEnchantLevel();
			final int lane = ticket3.getCustomType1();
			final int bet = ticket3.getCustomType2() * 100;
			final HistoryInfoTemplate info = MonsterRaceManager.getInstance().getHistory().get(raceId - 1);
			if (info == null)
			{
				super.onBypassFeedback(player, "Chat 0");
				return;
			}
			if (player.destroyItem("MonsterTrack", ticket3, this, true))
			{
				player.addAdena("MonsterTrack", (int) (bet * ((lane == info.getFirst()) ? info.getOddRate() : 0.01)), this, true);
			}
			super.onBypassFeedback(player, "Chat 0");
		}
		else if (command.equals("ViewHistory"))
		{
			final StringBuilder sb = new StringBuilder();
			final List<HistoryInfoTemplate> history = MonsterRaceManager.getInstance().getHistory();
			for (int k = history.size() - 1; k >= Math.max(0, history.size() - 7); --k)
			{
				final HistoryInfoTemplate info2 = history.get(k);
				append(sb, new Object[]
				{
					"<tr><td><font color=\"LEVEL\">",
					info2.getRaceId(),
					"</font> th</td><td><font color=\"LEVEL\">",
					info2.getFirst(),
					"</font> Lane </td><td><font color=\"LEVEL\">",
					info2.getSecond(),
					"</font> Lane</td><td align=right><font color=00ffff>",
					String.format(Locale.ENGLISH, "%.2f", info2.getOddRate()),
					"</font> Times</td></tr>"
				});
			}
			final NpcHtmlMessage html5 = new NpcHtmlMessage(this.getObjectId());
			html5.setFile(player, player.getLang(), this.getHtmlPath(this.getTemplate().getId(), 9));
			html5.replace("%infos%", sb.toString());
			html5.replace("%objectId%", this.getObjectId());
			player.sendPacket(html5);
		}
		else
		{
			super.onBypassFeedback(player, command);
		}
	}
	
	public static void append(StringBuilder sb, Object... content)
	{
		for (Object obj : content)
		{
			sb.append((obj == null) ? null : obj.toString());
		}
	}
	
	static
	{
		TICKET_PRICES = new int[]
		{
			100,
			500,
			1000,
			5000,
			10000,
			20000,
			50000,
			100000
		};
	}
}
