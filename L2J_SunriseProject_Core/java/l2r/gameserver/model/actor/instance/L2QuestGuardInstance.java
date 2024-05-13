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

import l2r.gameserver.enums.InstanceType;
import l2r.gameserver.model.actor.L2Attackable;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.actor.templates.L2NpcTemplate;
import l2r.gameserver.model.events.EventDispatcher;
import l2r.gameserver.model.events.EventType;
import l2r.gameserver.model.events.impl.character.npc.attackable.OnAttackableAttack;
import l2r.gameserver.model.events.impl.character.npc.attackable.OnAttackableKill;
import l2r.gameserver.model.skills.L2Skill;

/**
 * This class extends Guard class for quests, that require tracking of onAttack and onKill events from monsters' attacks.
 * @author GKR
 */
public final class L2QuestGuardInstance extends L2GuardInstance
{
	private boolean _isAutoAttackable = true;
	private boolean _isPassive = false;
	
	/**
	 * Creates a quest guard.
	 * @param template the quest guard NPC template
	 */
	public L2QuestGuardInstance(L2NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.L2QuestGuardInstance);
	}
	
	@Override
	public void addDamage(L2Character attacker, int damage, L2Skill skill)
	{
		super.addDamage(attacker, damage, skill);
		
		if (attacker.isAttackable() && EventDispatcher.getInstance().hasListener(EventType.ON_ATTACKABLE_ATTACK, this))
		{
			EventDispatcher.getInstance().notifyEventAsync(new OnAttackableAttack(null, this, damage, skill, false), this);
		}
	}
	
	@Override
	public boolean doDie(L2Character killer)
	{
		// Kill the L2NpcInstance (the corpse disappeared after 7 seconds)
		if (!super.doDie(killer))
		{
			return false;
		}
		
		if (killer instanceof L2Attackable)
		{
			// Delayed notification
			if (EventDispatcher.getInstance().hasListener(EventType.ON_ATTACKABLE_KILL, this))
			{
				EventDispatcher.getInstance().notifyEventAsyncDelayed(new OnAttackableKill(null, this, false), this, _onKillDelay);
			}
		}
		return true;
	}
	
	@Override
	public void addDamageHate(L2Character attacker, int damage, long aggro)
	{
		if (!_isPassive && !(attacker instanceof L2PcInstance))
		{
			super.addDamageHate(attacker, damage, aggro);
		}
	}
	
	public void setPassive(boolean state)
	{
		_isPassive = state;
	}
	
	@Override
	public boolean isAutoAttackable(L2Character attacker)
	{
		return _isAutoAttackable && !(attacker instanceof L2PcInstance);
	}
	
	@Override
	public void setAutoAttackable(boolean state)
	{
		_isAutoAttackable = state;
	}
	
	public boolean isPassive()
	{
		return _isPassive;
	}
}