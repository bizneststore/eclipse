/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package gr.sr.raidEngine.handler.voice;

import l2r.gameserver.enums.ZoneIdType;
import l2r.gameserver.handler.IVoicedCommandHandler;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.clientpackets.Say2;
import l2r.gameserver.network.serverpackets.CreatureSay;
import l2r.gameserver.network.serverpackets.RadarControl;

import gr.sr.interf.SunriseEvents;
import gr.sr.raidEngine.manager.RaidManager;

/**
 * @author vGodFather
 */
public class RaidVCmd implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"observeraid",
		"findraid",
		"hideraid"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		if ((RaidManager.getInstance()._raid == null) && (RaidManager.getInstance()._currentLocation == null))
		{
			activeChar.sendMessage("There is no Event Raid available at the moment.");
			return false;
		}
		
		switch (command)
		{
			case "observeraid":
				if (activeChar.inObserverMode())
				{
					return false;
				}
				
				if (!activeChar.isInsideZone(ZoneIdType.PEACE))
				{
					activeChar.sendMessage("You cannot use observe outside of peace zones.");
					return false;
				}
				
				if (SunriseEvents.isRegistered(activeChar) || SunriseEvents.isInEvent(activeChar))
				{
					activeChar.sendMessage("You cannot use observe while in event.");
					return false;
				}
				
				activeChar.enterObserverMode(RaidManager.getInstance()._currentLocation.getLocation());
				break;
			case "findraid":
				activeChar.sendPacket(new CreatureSay(1, Say2.PARTYROOM_COMMANDER, "Info", "Open Map to see Location."));
				activeChar.sendPacket(new RadarControl(0, 1, RaidManager.getInstance()._currentLocation.getLocation().getX(), RaidManager.getInstance()._currentLocation.getLocation().getY(), RaidManager.getInstance()._currentLocation.getLocation().getZ()));
				break;
			case "hideraid":
				activeChar.sendPacket(new RadarControl(2, 2, 0, 0, 0));
				break;
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}
