package gr.sr.raidEngine.tasks;

import l2r.gameserver.model.Location;
import l2r.gameserver.model.actor.L2Character;

/**
 * @author vGodFather
 */
public class TeleportBack implements Runnable
{
	L2Character _character;
	private final Location _loc;
	
	public TeleportBack(L2Character character, Location loc)
	{
		_character = character;
		_loc = loc;
	}
	
	@Override
	public void run()
	{
		_character.teleToLocation(_loc, true);
		_character.setIsCastingNow(false);
		_character.setCurrentHpMp(_character.getMaxHp(), _character.getMaxMp());
		_character.enableAllSkills();
		_character.setIsPorting(false);
	}
}