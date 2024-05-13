package gr.sr.pvpColorEngine;

import java.util.Set;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.configsEngine.configs.impl.ColorSystemConfigs;
import gr.sr.interf.SunriseEvents;

/**
 * @author vGodFather
 */
public class ColorSystemHandler
{
	public ColorSystemHandler()
	{
		// Dummy default
	}
	
	public void updateColor(L2PcInstance activeChar)
	{
		if (ColorSystemConfigs.ENABLE_COLOR_SYSTEM)
		{
			if (ColorSystemConfigs.ENABLE_NAME_COLOR)
			{
				ColorSystemHandler.getInstance().updateNameColor(activeChar);
			}
			
			if (ColorSystemConfigs.ENABLE_TITLE_COLOR)
			{
				ColorSystemHandler.getInstance().updateTitleColor(activeChar);
			}
		}
	}
	
	public void updateNameColor(L2PcInstance activeChar)
	{
		if (activeChar.isGM() || SunriseEvents.isInEvent(activeChar))
		{
			return;
		}
		
		switch (ColorSystemConfigs.COLOR_NAME_DEPENDS)
		{
			case "PK":
				Set<Integer> pks = ColorSystemConfigs.NAME_COLORS.keySet();
				
				for (int i : pks)
				{
					if (activeChar.getPkKills() >= i)
					{
						activeChar.getAppearance().setNameColor(ColorSystemConfigs.NAME_COLORS.get(i));
						activeChar.broadcastUserInfo();
					}
				}
				break;
			case "PVP":
				Set<Integer> pvps = ColorSystemConfigs.NAME_COLORS.keySet();
				
				for (int i : pvps)
				{
					if (activeChar.getPvpKills() >= i)
					{
						activeChar.getAppearance().setNameColor(ColorSystemConfigs.NAME_COLORS.get(i));
						activeChar.broadcastUserInfo();
					}
				}
				break;
			default:
				break;
		}
	}
	
	public void updateTitleColor(L2PcInstance activeChar)
	{
		if (activeChar.isGM() || SunriseEvents.isInEvent(activeChar))
		{
			return;
		}
		
		switch (ColorSystemConfigs.COLOR_TITLE_DEPENDS)
		{
			case "PK":
				Set<Integer> pks = ColorSystemConfigs.TITLE_COLORS.keySet();
				
				for (int i : pks)
				{
					if (activeChar.getPkKills() >= i)
					{
						activeChar.getAppearance().setTitleColor(ColorSystemConfigs.TITLE_COLORS.get(i));
						activeChar.broadcastUserInfo();
					}
				}
				break;
			case "PVP":
				Set<Integer> pvps = ColorSystemConfigs.TITLE_COLORS.keySet();
				
				for (int i : pvps)
				{
					if (activeChar.getPvpKills() >= i)
					{
						activeChar.getAppearance().setTitleColor(ColorSystemConfigs.TITLE_COLORS.get(i));
						activeChar.broadcastUserInfo();
					}
				}
				break;
			default:
				break;
		}
	}
	
	public static ColorSystemHandler getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final ColorSystemHandler _instance = new ColorSystemHandler();
	}
}