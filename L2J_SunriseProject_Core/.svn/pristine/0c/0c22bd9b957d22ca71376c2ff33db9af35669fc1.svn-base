package gr.sr.advancedBuffer;

import java.util.ArrayList;
import java.util.List;

import gr.sr.configsEngine.AbstractConfigs;

/**
 * @author vGodFather
 */
public class BufferConfigs extends AbstractConfigs
{
	// Scheme Buffer
	public static boolean NpcBuffer_VIP;
	public static int NpcBuffer_VIP_ALV;
	public static boolean NpcBuffer_EnableBuff;
	public static boolean NpcBuffer_EnableScheme;
	public static boolean NpcBuffer_EnableHeal;
	public static boolean NpcBuffer_EnableBuffs;
	public static boolean NpcBuffer_EnableResist;
	public static boolean NpcBuffer_EnableSong;
	public static boolean NpcBuffer_EnableDance;
	public static boolean NpcBuffer_EnableChant;
	public static boolean NpcBuffer_EnableOther;
	public static boolean NpcBuffer_EnableSpecial;
	public static boolean NpcBuffer_EnableCubic;
	public static boolean NpcBuffer_EnableCancel;
	public static boolean NpcBuffer_EnableBuffSet;
	public static boolean NpcBuffer_EnableBuffPK;
	public static boolean NpcBuffer_EnableFreeBuffs;
	public static boolean NpcBuffer_EnableTimeOut;
	public static int NpcBuffer_TimeOutTime;
	public static int NpcBuffer_MinLevel;
	public static int NpcBuffer_PriceCancel;
	public static int NpcBuffer_PriceHeal;
	public static int NpcBuffer_PriceBuffs;
	public static int NpcBuffer_PriceResist;
	public static int NpcBuffer_PriceSong;
	public static int NpcBuffer_PriceDance;
	public static int NpcBuffer_PriceChant;
	public static int NpcBuffer_PriceOther;
	public static int NpcBuffer_PriceSpecial;
	public static int NpcBuffer_PriceCubic;
	public static int NpcBuffer_PriceSet;
	public static int NpcBuffer_PriceScheme;
	public static int NpcBuffer_MaxScheme;
	public static boolean SCHEME_ALLOW_FLAG;
	public static List<int[]> NpcBuffer_BuffSetMage = new ArrayList<>();
	public static List<int[]> NpcBuffer_BuffSetFighter = new ArrayList<>();
	public static List<int[]> NpcBuffer_BuffSetDagger = new ArrayList<>();
	public static List<int[]> NpcBuffer_BuffSetSupport = new ArrayList<>();
	public static List<int[]> NpcBuffer_BuffSetTank = new ArrayList<>();
	public static List<int[]> NpcBuffer_BuffSetArcher = new ArrayList<>();
	
	public static boolean ALLOW_BUFFS_INSIDE_INSTANCES;
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/npcbuffer.ini");
		
		// Buffer settings
		NpcBuffer_VIP = getBoolean(_settings, _override, "EnableVIP", false);
		NpcBuffer_VIP_ALV = getInt(_settings, _override, "VipAccesLevel", 1);
		NpcBuffer_EnableBuff = getBoolean(_settings, _override, "EnableBuffSection", true);
		NpcBuffer_EnableScheme = getBoolean(_settings, _override, "EnableScheme", true);
		NpcBuffer_EnableHeal = getBoolean(_settings, _override, "EnableHeal", true);
		NpcBuffer_EnableBuffs = getBoolean(_settings, _override, "EnableBuffs", true);
		NpcBuffer_EnableResist = getBoolean(_settings, _override, "EnableResist", true);
		NpcBuffer_EnableSong = getBoolean(_settings, _override, "EnableSongs", true);
		NpcBuffer_EnableDance = getBoolean(_settings, _override, "EnableDances", true);
		NpcBuffer_EnableChant = getBoolean(_settings, _override, "EnableChants", true);
		NpcBuffer_EnableOther = getBoolean(_settings, _override, "EnableOther", true);
		NpcBuffer_EnableSpecial = getBoolean(_settings, _override, "EnableSpecial", true);
		NpcBuffer_EnableCubic = getBoolean(_settings, _override, "EnableCubic", false);
		NpcBuffer_EnableCancel = getBoolean(_settings, _override, "EnableRemoveBuffs", true);
		NpcBuffer_EnableBuffSet = getBoolean(_settings, _override, "EnableBuffSet", true);
		NpcBuffer_EnableBuffPK = getBoolean(_settings, _override, "EnableBuffForPK", false);
		NpcBuffer_EnableFreeBuffs = getBoolean(_settings, _override, "EnableFreeBuffs", true);
		NpcBuffer_EnableTimeOut = getBoolean(_settings, _override, "EnableTimeOut", true);
		SCHEME_ALLOW_FLAG = getBoolean(_settings, _override, "EnableBuffforFlag", false);
		NpcBuffer_TimeOutTime = getInt(_settings, _override, "TimeoutTime", 10);
		NpcBuffer_MinLevel = getInt(_settings, _override, "MinimumLevel", 20);
		NpcBuffer_PriceCancel = getInt(_settings, _override, "RemoveBuffsPrice", 100000);
		NpcBuffer_PriceHeal = getInt(_settings, _override, "HealPrice", 100000);
		NpcBuffer_PriceBuffs = getInt(_settings, _override, "BuffsPrice", 100000);
		NpcBuffer_PriceResist = getInt(_settings, _override, "ResistPrice", 100000);
		NpcBuffer_PriceSong = getInt(_settings, _override, "SongPrice", 100000);
		NpcBuffer_PriceDance = getInt(_settings, _override, "DancePrice", 100000);
		NpcBuffer_PriceChant = getInt(_settings, _override, "ChantsPrice", 100000);
		NpcBuffer_PriceOther = getInt(_settings, _override, "OtherPrice", 100000);
		NpcBuffer_PriceSpecial = getInt(_settings, _override, "SpecialPrice", 100000);
		NpcBuffer_PriceCubic = getInt(_settings, _override, "CubicPrice", 100000);
		NpcBuffer_PriceSet = getInt(_settings, _override, "SetPrice", 100000);
		NpcBuffer_PriceScheme = getInt(_settings, _override, "SchemePrice", 100000);
		NpcBuffer_MaxScheme = getInt(_settings, _override, "MaxScheme", 4);
		
		ALLOW_BUFFS_INSIDE_INSTANCES = getBoolean(_settings, _override, "AllowBuffsInsideInstances", true);
		
		String[] parts;
		String[] skills = getString(_settings, _override, "BuffSetMage", "192,1").split(";");
		for (String sk : skills)
		{
			parts = sk.split(",");
			NpcBuffer_BuffSetMage.add(new int[]
			{
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1])
			});
		}
		
		skills = getString(_settings, _override, "BuffSetFighter", "192,1").split(";");
		for (String sk : skills)
		{
			parts = sk.split(",");
			NpcBuffer_BuffSetFighter.add(new int[]
			{
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1])
			});
		}
		
		skills = getString(_settings, _override, "BuffSetDagger", "192,1").split(";");
		for (String sk : skills)
		{
			parts = sk.split(",");
			NpcBuffer_BuffSetDagger.add(new int[]
			{
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1])
			});
		}
		
		skills = getString(_settings, _override, "BuffSetSupport", "192,1").split(";");
		for (String sk : skills)
		{
			parts = sk.split(",");
			NpcBuffer_BuffSetSupport.add(new int[]
			{
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1])
			});
		}
		
		skills = getString(_settings, _override, "BuffSetTank", "192,1").split(";");
		for (String sk : skills)
		{
			parts = sk.split(",");
			NpcBuffer_BuffSetTank.add(new int[]
			{
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1])
			});
		}
		
		skills = getString(_settings, _override, "BuffSetArcher", "192,1").split(";");
		for (String sk : skills)
		{
			parts = sk.split(",");
			NpcBuffer_BuffSetArcher.add(new int[]
			{
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1])
			});
		}
	}
	
	public static BufferConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final BufferConfigs _instance = new BufferConfigs();
	}
}