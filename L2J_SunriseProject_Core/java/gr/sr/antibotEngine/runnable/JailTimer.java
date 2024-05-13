package gr.sr.antibotEngine.runnable;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.antibotEngine.AntibotSystem;

public class JailTimer implements Runnable
{
	L2PcInstance activeChar;
	
	public JailTimer(L2PcInstance player)
	{
		activeChar = player;
	}
	
	@Override
	public void run()
	{
		if (activeChar.isFarmBot())
		{
			// here will run method to jail player
			AntibotSystem.jailPlayer(activeChar, "time");
		}
	}
}