package gr.sr.aioItem.runnable;

import l2r.gameserver.model.actor.instance.L2PcInstance;

/**
 * The class used to quickly transform and untransform the player.
 * @author vGodFather
 */
public class TransformFinalizer implements Runnable
{
	private final L2PcInstance _activeChar;
	
	public TransformFinalizer(L2PcInstance activeChar)
	{
		_activeChar = activeChar;
	}
	
	@Override
	public void run()
	{
		_activeChar.untransform();
	}
}