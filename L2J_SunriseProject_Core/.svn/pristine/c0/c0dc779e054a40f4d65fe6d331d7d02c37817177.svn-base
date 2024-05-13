package l2r.gameserver.network.clientpackets;

import l2r.Config;
import l2r.gameserver.enums.CtrlIntention;
import l2r.gameserver.model.Location;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.SystemMessageId;
import l2r.gameserver.network.serverpackets.ActionFailed;
import l2r.gameserver.network.serverpackets.MoveToLocation;
import l2r.gameserver.util.Util;

import dev.l2j.autobots.AutobotsManager;

public class MoveBackwardToLocation extends L2GameClientPacket
{
	private int _targetX;
	private int _targetY;
	private int _targetZ;
	private int _originX;
	private int _originY;
	private int _originZ;
	
	private boolean _keyboard;
	
	@Override
	protected void readImpl()
	{
		_targetX = readD();
		_targetY = readD();
		_targetZ = readD();
		_originX = readD();
		_originY = readD();
		_originZ = readD();
		if (_buf.hasRemaining())
		{
			_keyboard = readD() == 0;
		}
		else
		{
			if (Config.L2WALKER_PROTECTION)
			{
				L2PcInstance activeChar = getClient().getActiveChar();
				Util.handleIllegalPlayerAction(activeChar, "Player " + activeChar.getName() + " is trying to use L2Walker and got kicked.", Config.DEFAULT_PUNISH);
			}
		}
	}
	
	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
		{
			return;
		}
		
		if (_keyboard && !Config.ALLOW_KEYBOARD_MOVE)
		{
			activeChar.sendActionFailed();
			return;
		}
		
		if (activeChar.isAttackingNow())
		{
			activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		}
		
		if ((Config.PLAYER_MOVEMENT_BLOCK_TIME > 0) && !activeChar.isGM() && (activeChar.getNotMoveUntil() > System.currentTimeMillis()))
		{
			activeChar.sendPacket(SystemMessageId.CANNOT_MOVE_WHILE_SPEAKING_TO_AN_NPC);
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if ((_targetX == _originX) && (_targetY == _originY) && (_targetZ == _originZ))
		{
			if (_keyboard)
			{
				activeChar.stopMove();
			}
			else
			{
				activeChar.sendActionFailed();
			}
			return;
		}
		
		if (activeChar.getTeleMode() > 0)
		{
			if (activeChar.getTeleMode() == 1)
			{
				activeChar.setTeleMode(0);
			}
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			activeChar.teleToLocation(new Location(_targetX, _targetY, _targetZ));
			return;
		}
		
		if (activeChar.inObserverMode())
		{
			activeChar.sendPacket(new MoveToLocation(activeChar.getObjectId(), new Location(_originX, _originY, _originZ), new Location(_targetX, _targetY, _targetZ)));
			return;
		}
		
		// Can't move if character is confused, or trying to move a huge distance
		if (activeChar.isOutOfControl())
		{
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (AutobotsManager.INSTANCE.onMove(activeChar, _targetX, _targetY, _targetZ))
		{
			return;
		}
		
		activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new Location(_targetX, _targetY, _targetZ), _keyboard);
	}
}