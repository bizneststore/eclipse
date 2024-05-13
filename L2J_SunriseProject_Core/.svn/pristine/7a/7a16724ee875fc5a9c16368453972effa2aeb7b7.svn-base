package l2r.gameserver.model.conditions;

import java.util.ArrayList;
import java.util.List;

import l2r.gameserver.instancemanager.InstanceManager;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.stats.Env;

public class ConditionPlayerReflectionEntry extends Condition
{
	private final int _type;
	@SuppressWarnings("unused")
	private final int _attempts;
	
	public ConditionPlayerReflectionEntry(final int type, final int attempts)
	{
		this._type = type;
		this._attempts = attempts;
	}
	
	@Override
	public boolean testImpl(final Env env)
	{
		final L2PcInstance player = env.getPlayer();
		if (player == null)
		{
			return false;
		}
		
		final List<Integer> instanceids = new ArrayList<>();
		
		// TODO unhardcode these
		switch (_type)
		{
			case 1:
				instanceids.add(57);
				instanceids.add(58);
				instanceids.add(60);
				instanceids.add(61);
				instanceids.add(63);
				instanceids.add(64);
				instanceids.add(66);
				instanceids.add(67);
				instanceids.add(69);
				instanceids.add(70);
				instanceids.add(72);
				break;
			case 2:
				instanceids.add(46);
				instanceids.add(47);
				instanceids.add(48);
				instanceids.add(49);
				instanceids.add(50);
				instanceids.add(51);
				instanceids.add(52);
				instanceids.add(53);
				instanceids.add(54);
				instanceids.add(55);
				instanceids.add(56);
				break;
			case 3:
				instanceids.add(59);
				instanceids.add(62);
				instanceids.add(65);
				instanceids.add(68);
				instanceids.add(71);
				break;
			case 4:
				instanceids.add(73);
				instanceids.add(74);
				instanceids.add(75);
				instanceids.add(76);
				instanceids.add(77);
				instanceids.add(78);
				instanceids.add(79);
				
				instanceids.add(134);
				break;
		}
		
		for (int instanceId : instanceids)
		{
			final Long reentertime = InstanceManager.getInstance().getInstanceTime(player.getObjectId(), instanceId);
			if (System.currentTimeMillis() < reentertime)
			{
				return true;
			}
		}
		
		// TODO correct message
		player.sendMessage("There is no active instance.");
		// TODO max attemps
		return false;
	}
}
