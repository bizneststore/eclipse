package l2r.gameserver.instancemanager.games.monsterrace;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import l2r.L2DatabaseFactory;
import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.data.SpawnTable;
import l2r.gameserver.data.sql.NpcTable;
import l2r.gameserver.enums.audio.Sound;
import l2r.gameserver.model.L2Spawn;
import l2r.gameserver.model.actor.L2Npc;
import l2r.gameserver.model.actor.templates.L2NpcTemplate;
import l2r.gameserver.network.SystemMessageId;
import l2r.gameserver.network.serverpackets.DeleteObject;
import l2r.gameserver.network.serverpackets.L2GameServerPacket;
import l2r.gameserver.network.serverpackets.MonRaceInfo;
import l2r.gameserver.network.serverpackets.PlaySound;
import l2r.gameserver.network.serverpackets.SystemMessage;
import l2r.gameserver.util.Broadcast;

import gr.sr.logging.Log;
import gr.sr.utils.Rnd;

/**
 * @author vGodFather
 */
public class MonsterRaceManager
{
	protected static final PlaySound SOUND_1;
	protected static final PlaySound SOUND_2;
	protected static final int[][] CODES;
	private final List<Integer> _npcTemplates;
	protected final List<HistoryInfoTemplate> _history;
	private final Map<Integer, Long> _betsPerLane;
	protected final List<Double> _odds;
	protected L2Npc _manager;
	protected int _finalCountdown;
	protected RaceState _state;
	protected MonRaceInfo _packet;
	private final L2Npc[] _monsters;
	private Constructor<?> _constructor;
	private int[][] _speeds;
	private final int[] _first;
	private final int[] _second;
	
	protected MonsterRaceManager()
	{
		this._npcTemplates = new ArrayList<>();
		this._history = new ArrayList<>();
		this._betsPerLane = new ConcurrentHashMap<>();
		this._odds = new ArrayList<>();
		this._manager = null;
		this._finalCountdown = 0;
		this._state = RaceState.RACE_END;
		this.loadHistory();
		this.loadBets();
		for (int i = 31003; i < 31027; ++i)
		{
			this._npcTemplates.add(i);
		}
		this._monsters = new L2Npc[8];
		this._speeds = new int[8][20];
		this._first = new int[2];
		this._second = new int[2];
		for (final L2Spawn spawn : SpawnTable.getInstance().getSpawns(30995))
		{
			if (spawn != null)
			{
				this._manager = spawn.getLastSpawn();
			}
		}
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Announcement(), 0L, 1000);
	}
	
	public L2Npc[] getMonsters()
	{
		return this._monsters;
	}
	
	public int[][] getSpeeds()
	{
		return this._speeds;
	}
	
	public int getFirstPlace()
	{
		return this._first[0];
	}
	
	public int getSecondPlace()
	{
		return this._second[0];
	}
	
	public MonRaceInfo getRacePacket()
	{
		return this._packet;
	}
	
	public RaceState getCurrentRaceState()
	{
		return this._state;
	}
	
	public int getRaceNumber()
	{
		return _history.size() + 1;
	}
	
	public List<HistoryInfoTemplate> getHistory()
	{
		return this._history;
	}
	
	public List<Double> getOdds()
	{
		return this._odds;
	}
	
	public void newRace()
	{
		this._history.add(new HistoryInfoTemplate(getRaceNumber(), 0, 0, 0.0));
		Collections.shuffle(this._npcTemplates);
		for (int i = 0; i < 8; ++i)
		{
			try
			{
				final L2NpcTemplate template = NpcTable.getInstance().getTemplate(this._npcTemplates.get(i));
				final String className = "l2r.gameserver.model.actor.instance." + template.getType() + "Instance";
				_constructor = Class.forName(className).asSubclass(L2Npc.class).getConstructor(L2NpcTemplate.class);
				
				this._monsters[i] = (L2Npc) this._constructor.newInstance(template);
			}
			catch (Exception e)
			{
				Log.error("Failed generating MonsterRace monster.", e);
			}
		}
	}
	
	public void newSpeeds()
	{
		this._speeds = new int[8][20];
		int total = 0;
		this._first[1] = 0;
		this._second[1] = 0;
		for (int i = 0; i < 8; ++i)
		{
			total = 0;
			for (int j = 0; j < 20; ++j)
			{
				if (j == 19)
				{
					this._speeds[i][j] = 100;
				}
				else
				{
					this._speeds[i][j] = Rnd.get(60) + 65;
				}
				total += this._speeds[i][j];
			}
			if (total >= this._first[1])
			{
				this._second[0] = this._first[0];
				this._second[1] = this._first[1];
				this._first[0] = 8 - i;
				this._first[1] = total;
			}
			else if (total >= this._second[1])
			{
				this._second[0] = 8 - i;
				this._second[1] = total;
			}
		}
	}
	
	protected void loadHistory()
	{
		try (final Connection con = L2DatabaseFactory.getInstance().getConnection();
			final PreparedStatement ps = con.prepareStatement("SELECT * FROM monster_race_history");
			final ResultSet rs = ps.executeQuery())
		{
			while (rs.next())
			{
				this._history.add(new HistoryInfoTemplate(rs.getInt("race_id"), rs.getInt("first"), rs.getInt("second"), rs.getDouble("odd_rate")));
			}
		}
		catch (SQLException e)
		{
			Log.error("Can't load Monster Race history.", e);
		}
		Log.info("Loaded " + this._history.size() + " Monster Race records, currently on race #" + this.getRaceNumber());
	}
	
	protected void saveHistory(final HistoryInfoTemplate history)
	{
		try (final Connection con = L2DatabaseFactory.getInstance().getConnection();
			final PreparedStatement ps = con.prepareStatement("INSERT INTO monster_race_history (race_id, first, second, odd_rate) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE first=?, second=?, odd_rate=?"))
		{
			ps.setInt(1, history.getRaceId());
			ps.setInt(2, history.getFirst());
			ps.setInt(3, history.getSecond());
			ps.setDouble(4, history.getOddRate());
			
			ps.setInt(5, history.getFirst());
			ps.setInt(6, history.getSecond());
			ps.setDouble(7, history.getOddRate());
			ps.execute();
		}
		catch (SQLException e)
		{
			Log.error("Can't save Monster Race history.", e);
		}
	}
	
	protected void loadBets()
	{
		try (final Connection con = L2DatabaseFactory.getInstance().getConnection();
			final PreparedStatement ps = con.prepareStatement("SELECT * FROM monster_race_bets");
			final ResultSet rs = ps.executeQuery())
		{
			while (rs.next())
			{
				this.setBetOnLane(rs.getInt("lane_id"), rs.getLong("bet"), false);
			}
		}
		catch (SQLException e)
		{
			Log.error("Can't load Monster Race bets.", e);
		}
	}
	
	protected void saveBet(final int lane, final long sum)
	{
		try (final Connection con = L2DatabaseFactory.getInstance().getConnection();
			final PreparedStatement ps = con.prepareStatement("REPLACE INTO monster_race_bets (lane_id, bet) VALUES (?,?)"))
		{
			ps.setInt(1, lane);
			ps.setLong(2, sum);
			ps.execute();
		}
		catch (SQLException e)
		{
			Log.error("Can't save Monster Race bet.", e);
		}
	}
	
	protected void clearBets()
	{
		for (final int key : this._betsPerLane.keySet())
		{
			this._betsPerLane.put(key, 0L);
		}
		try (final Connection con = L2DatabaseFactory.getInstance().getConnection();
			final PreparedStatement ps = con.prepareStatement("UPDATE monster_race_bets SET bet = 0"))
		{
			ps.execute();
		}
		catch (SQLException e)
		{
			Log.error("Can't clear Monster Race bets.", e);
		}
	}
	
	public void setBetOnLane(final int lane, final long amount, final boolean saveOnDb)
	{
		final long sum = this._betsPerLane.getOrDefault(lane, 0L) + amount;
		this._betsPerLane.put(lane, sum);
		if (saveOnDb)
		{
			this.saveBet(lane, sum);
		}
	}
	
	protected void calculateOdds()
	{
		this._odds.clear();
		final Map<Integer, Long> sortedLanes = new TreeMap<>(this._betsPerLane);
		long sumOfAllLanes = 0L;
		for (final long amount : sortedLanes.values())
		{
			sumOfAllLanes += amount;
		}
		for (final long amount : sortedLanes.values())
		{
			this._odds.add((amount == 0L) ? 0.0 : Math.max(1.25, (sumOfAllLanes * 0.7) / amount));
		}
	}
	
	static
	{
		SOUND_1 = Sound.S_Race.getPacket();
		SOUND_2 = Sound.ITEMSOUND2_RACE_START.getPacket();
		CODES = new int[][]
		{
			{
				-1,
				0
			},
			{
				0,
				15322
			},
			{
				13765,
				-1
			}
		};
	}
	
	public enum RaceState
	{
		ACCEPTING_BETS,
		WAITING,
		STARTING_RACE,
		RACE_END;
	}
	
	protected class Announcement implements Runnable
	{
		@Override
		public void run()
		{
			if (_manager == null)
			{
				return;
			}
			
			if (_finalCountdown > 1200)
			{
				_finalCountdown = 0;
			}
			
			switch (_finalCountdown)
			{
				case 0:
				{
					newRace();
					newSpeeds();
					_state = RaceState.ACCEPTING_BETS;
					_packet = new MonRaceInfo(MonsterRaceManager.CODES[0][0], MonsterRaceManager.CODES[0][1], getMonsters(), getSpeeds());
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						_packet,
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_AVAILABLE_FOR_S1_RACE)).addInt(getRaceNumber())
					});
					break;
				}
				case 30:
				case 60:
				case 90:
				case 120:
				case 150:
				case 180:
				case 210:
				case 240:
				case 270:
				case 330:
				case 360:
				case 390:
				case 420:
				case 450:
				case 480:
				case 510:
				case 540:
				case 570:
				case 630:
				case 660:
				case 690:
				case 720:
				case 750:
				case 780:
				case 810:
				case 870:
				{
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_NOW_AVAILABLE_FOR_S1_RACE)).addInt(getRaceNumber())
					});
					break;
				}
				case 300:
				{
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_NOW_AVAILABLE_FOR_S1_RACE)).addInt(getRaceNumber()),
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_STOP_IN_S1_MINUTES)).addInt(10)
					});
					break;
				}
				case 600:
				{
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_NOW_AVAILABLE_FOR_S1_RACE)).addInt(getRaceNumber()),
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_STOP_IN_S1_MINUTES)).addInt(5)
					});
					break;
				}
				case 840:
				{
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_NOW_AVAILABLE_FOR_S1_RACE)).addInt(getRaceNumber()),
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_STOP_IN_S1_MINUTES)).addInt(1)
					});
					break;
				}
				case 900:
				{
					_state = RaceState.WAITING;
					calculateOdds();
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_TICKETS_NOW_AVAILABLE_FOR_S1_RACE)).addInt(getRaceNumber()),
						SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_S1_TICKET_SALES_CLOSED)
					});
					break;
				}
				case 960:
				case 1020:
				{
					final int minutes = (_finalCountdown == 960) ? 2 : 1;
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_S2_BEGINS_IN_S1_MINUTES)).addInt(getRaceNumber()).addInt(minutes)
					});
					break;
				}
				case 1050:
				{
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_S1_BEGINS_IN_30_SECONDS)
					});
					break;
				}
				case 1070:
				{
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_S1_COUNTDOWN_IN_FIVE_SECONDS)
					});
					break;
				}
				case 1075:
				case 1076:
				case 1077:
				case 1078:
				case 1079:
				{
					final int seconds = 1080 - _finalCountdown;
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_BEGINS_IN_S1_SECONDS)).addInt(seconds)
					});
					break;
				}
				case 1080:
				{
					_state = RaceState.STARTING_RACE;
					_packet = new MonRaceInfo(MonsterRaceManager.CODES[1][0], MonsterRaceManager.CODES[1][1], getMonsters(), getSpeeds());
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_RACE_START),
						MonsterRaceManager.SOUND_1,
						MonsterRaceManager.SOUND_2,
						_packet
					});
					break;
				}
				case 1085:
				{
					_packet = new MonRaceInfo(MonsterRaceManager.CODES[2][0], MonsterRaceManager.CODES[2][1], getMonsters(), getSpeeds());
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						_packet
					});
					break;
				}
				case 1115:
				{
					_state = RaceState.RACE_END;
					final HistoryInfoTemplate info = _history.get(_history.size() - 1);
					info.setFirst(getFirstPlace());
					info.setSecond(getSecondPlace());
					
					if (_odds.isEmpty())
					{
						info.setOddRate(0);
					}
					else
					{
						info.setOddRate(_odds.get(getFirstPlace() - 1));
					}
					
					saveHistory(info);
					clearBets();
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_FIRST_PLACE_S1_SECOND_S2).addInt(getFirstPlace())).addInt(getSecondPlace()),
						(SystemMessage.getSystemMessage(SystemMessageId.MONSRACE_S1_RACE_END)).addInt(getRaceNumber())
					});
					break;
				}
				case 1140:
				{
					Broadcast.toKnownPlayersInRadius(_manager, 4000, new L2GameServerPacket[]
					{
						new DeleteObject(getMonsters()[0]),
						new DeleteObject(getMonsters()[1]),
						new DeleteObject(getMonsters()[2]),
						new DeleteObject(getMonsters()[3]),
						new DeleteObject(getMonsters()[4]),
						new DeleteObject(getMonsters()[5]),
						new DeleteObject(getMonsters()[6]),
						new DeleteObject(getMonsters()[7])
					});
					break;
				}
			}
			++_finalCountdown;
		}
	}
	
	public static MonsterRaceManager getInstance()
	{
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder
	{
		protected static final MonsterRaceManager instance;
		
		static
		{
			instance = new MonsterRaceManager();
		}
	}
}
