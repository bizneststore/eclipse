package l2r.gameserver.model.conditions;

import l2r.gameserver.model.stats.Env;

/**
 * @author vGodFather
 */
public class op_need_agathion extends Condition
{
	private final boolean expectedValue;
	
	public op_need_agathion(boolean expectedValue)
	{
		this.expectedValue = expectedValue;
	}
	
	@Override
	public boolean testImpl(Env env)
	{
		return expectedValue == (env.getCharacter().isPlayer() && (env.getCharacter().getActingPlayer().getAgathionId() > 0));
	}
}
