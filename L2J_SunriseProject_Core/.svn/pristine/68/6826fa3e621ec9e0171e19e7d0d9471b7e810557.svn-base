package l2r.gameserver.model.events.impl.character.player;

import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.events.EventType;
import l2r.gameserver.model.events.impl.IBaseEvent;

/**
 * @author vGodFather
 */
public class OnPlayerLearnSkillRequested implements IBaseEvent
{
	private final L2PcInstance _activeChar;
	
	public OnPlayerLearnSkillRequested(L2PcInstance activeChar)
	{
		_activeChar = activeChar;
	}
	
	public L2PcInstance getActiveChar()
	{
		return _activeChar;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_LEARN_SKILL_REQUESTED;
	}
}
