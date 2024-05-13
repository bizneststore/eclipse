package handlers.bypasshandlers.clan;

import java.util.StringTokenizer;

import l2r.gameserver.enums.ClanUnitTypes;
import l2r.gameserver.model.actor.L2Character;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.model.actor.instance.L2VillageMasterInstance;

/**
 * @author vGodFather
 */
public class CreateRoyal extends AbstractClan
{
	private static final String[] _command =
	{
		"create_royal"
	};
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2VillageMasterInstance))
		{
			return false;
		}
		
		StringTokenizer st = new StringTokenizer(command, " ");
		st.nextToken(); // _command
		if (st.countTokens() < 2)
		{
			return false;
		}
		
		createSubPledge(activeChar, st.nextToken(), st.nextToken(), ClanUnitTypes.SUBUNIT_ROYAL1.getId(), 6);
		return true;
	}
	
	@Override
	public String[] getBypassList()
	{
		return _command;
	}
}
