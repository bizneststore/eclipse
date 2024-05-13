package gr.sr.javaBuffer;

/**
 * @author vGodFather
 */
public class BuffsInstance
{
	private int _id;
	private int _level;
	private int _customLevel;
	private String _name;
	private String _description;
	private BufferMenuCategories _category;
	
	public BuffsInstance()
	{
		// Dummy default
	}
	
	/**
	 * @param id
	 * @param level
	 * @param customLevel
	 * @param name
	 * @param description
	 * @param category
	 */
	public BuffsInstance(int id, int level, int customLevel, String name, String description, BufferMenuCategories category)
	{
		// If the custom level has not been set then set custom level as level.
		if (customLevel == 0)
		{
			customLevel = level;
		}
		
		_id = id;
		_level = level;
		_customLevel = customLevel;
		_name = name;
		_description = description;
		_category = category;
	}
	
	// ========================================================
	// XXX - GETTER METHODS
	// ========================================================
	public int getId()
	{
		return _id;
	}
	
	public int getLevel()
	{
		return _level;
	}
	
	public int getCustomLevel()
	{
		return _customLevel;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getDescription()
	{
		return _description;
	}
	
	public BufferMenuCategories getCategory()
	{
		return _category;
	}
}