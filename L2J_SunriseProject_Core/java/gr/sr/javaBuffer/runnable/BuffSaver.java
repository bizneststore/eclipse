package gr.sr.javaBuffer.runnable;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.javaBuffer.BufferMenuCategories;
import gr.sr.javaBuffer.JavaBufferBypass;
import gr.sr.javaBuffer.PlayerMethods;

/**
 * The class used to add user selected buffs in one profile
 * @author vGodFather
 */
public class BuffSaver implements Runnable
{
	private final L2PcInstance _player;
	private final int _objectId;
	private final BufferMenuCategories _category;
	private final String _profile;
	private final int _buffId;
	
	public BuffSaver(L2PcInstance player, BufferMenuCategories category, String profile, int buffId, int objectId)
	{
		_player = player;
		_category = category;
		_profile = profile;
		_buffId = buffId;
		_objectId = objectId;
	}
	
	@Override
	public void run()
	{
		PlayerMethods.addBuffToProfile(_profile, _buffId, _player);
		JavaBufferBypass.callBuffToAdd(_category, _player, _profile, _objectId);
	}
}