package gr.sr.player;

import l2r.gameserver.model.actor.instance.L2PcInstance;

public class PcExtention
{
	private L2PcInstance _activeChar = null;
	
	public PcExtention(L2PcInstance activeChar)
	{
		_activeChar = activeChar;
	}
	
	protected L2PcInstance getChar()
	{
		return _activeChar;
	}
	
	public void destroy()
	{
		_activeChar = null;
	}
}
