package gr.sr.javaBuffer.runnable;

import l2r.gameserver.model.actor.instance.L2PcInstance;

import gr.sr.javaBuffer.PlayerMethods;

/**
 * The class used to delete user selected buffs from one profile
 * @author vGodFather
 */
public class BuffDeleter implements Runnable
{
	private final L2PcInstance _player;
	private final int _objectId;
	private final String _profile;
	private final int _buffId;
	
	public BuffDeleter(L2PcInstance player, String profile, int buffId, int objectId)
	{
		_player = player;
		_profile = profile;
		_buffId = buffId;
		_objectId = objectId;
	}
	
	@Override
	public void run()
	{
		PlayerMethods.delBuffFromProfile(_profile, _buffId, _player);
		switch (_objectId)
		{
			case 0:
				gr.sr.javaBuffer.buffItem.dynamicHtmls.GenerateHtmls.showBuffsToDelete(_player, _profile, "removeBuffs");
				break;
			case 1:
				gr.sr.javaBuffer.buffCommunity.dynamicHtmls.GenerateHtmls.showBuffsToDelete(_player, _profile, "removeBuffs");
				break;
			default:
				gr.sr.javaBuffer.buffNpc.dynamicHtmls.GenerateHtmls.showBuffsToDelete(_player, _profile, "removeBuffs", _objectId);
				break;
		}
	}
}