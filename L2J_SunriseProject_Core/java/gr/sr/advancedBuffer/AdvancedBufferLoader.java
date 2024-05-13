package gr.sr.advancedBuffer;

/**
 * @author vGodFather
 */
public class AdvancedBufferLoader
{
	public AdvancedBufferLoader()
	{
		BufferConfigs.getInstance().loadConfigs();
	}
	
	public static AdvancedBufferLoader getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final AdvancedBufferLoader _instance = new AdvancedBufferLoader();
	}
}
