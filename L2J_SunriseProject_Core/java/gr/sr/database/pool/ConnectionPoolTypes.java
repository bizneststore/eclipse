package gr.sr.database.pool;

/**
 * @author vGodFather
 */
public enum ConnectionPoolTypes
{
	HIKARICP("HIKARICP"),
	C3P0("C3P0"),
	BONECP("BONECP"),
	APACHE("APACHE");
	
	private String _name;
	
	ConnectionPoolTypes(String name)
	{
		_name = name;
	}
	
	public String getName()
	{
		return _name;
	}
}
