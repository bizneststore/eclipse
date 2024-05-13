package gr.sr.achievementEngine.base;

public enum ConditionType
{
	LEVEL("Level:"),
	AMOUNT("Amount:"),
	WEAPON_ENCHANT("Weapon enchant:"),
	ITEMS_COUNT("Items count:"),
	SUBCLASSES("Subclasses:"),
	MIN_PVP_COUNT("Min PvP count:"),
	MIN_PK_COUNT("Min Pk count:"),
	MAX_HP("Max HP:"),
	MAX_MP("Max MP:"),
	MAX_CP("Max CP:"),
	MIN_KARMA("Min karma:"),
	MUST_BE_NOBLE("Must be nobles:"),
	MUST_BE_HERO("Must be hero:"),
	MUST_BE_MARRIED("Must be married:"),
	ACADEMY_MEMBER("Academy member:"),
	MUST_BE_MAGE("Must be mage:"),
	MUST_BE_SUMMONER("Must be summoner:"),
	COMMON_CRAFT("Common craft:"),
	DWARVEN_CRAFT("Dwarf craft:"),
	CLAN_LEADER("Clan leader:"),
	MIN_CLAN_LEVEL("Min clan level:"),
	CLAN_MEMBERS("Clan members:"),
	CRP_AMMOUNT("CRP amount:"),
	CASTLE_LORD("Castle lord:"),
	MOB_TO_HUNT("Mob to hunt:");
	
	private String _text;
	
	private ConditionType(String text)
	{
		_text = text;
	}
	
	public String getText()
	{
		return _text;
	}
}