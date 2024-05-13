package gr.sr.logsViewer.runnable;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.logsViewer.LogsViewer;

/**
 * @author NeverMore
 */
public class Capture implements Runnable
{
	private final L2PcInstance activeChar;
	private final String file;
	
	public Capture(L2PcInstance player, String log)
	{
		activeChar = player;
		file = log;
	}
	
	@Override
	public void run()
	{
		LogsViewer.startLogViewer(activeChar, file);
	}
}