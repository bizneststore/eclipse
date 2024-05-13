package gr.sr.configsEngine.configs.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import gr.sr.configsEngine.AbstractConfigs;

public class ColorSystemConfigs extends AbstractConfigs
{
	public static boolean ENABLE_COLOR_SYSTEM;
	public static boolean ENABLE_NAME_COLOR;
	public static String COLOR_NAME_DEPENDS;
	public static Map<Integer, Integer> NAME_COLORS = new LinkedHashMap<>();
	public static boolean ENABLE_TITLE_COLOR;
	public static String COLOR_TITLE_DEPENDS;
	public static Map<Integer, Integer> TITLE_COLORS = new LinkedHashMap<>();
	
	@Override
	public void loadConfigs()
	{
		loadFile("./config/sunrise/ColorSystem.ini");
		
		NAME_COLORS.clear();
		TITLE_COLORS.clear();
		
		ENABLE_COLOR_SYSTEM = Boolean.parseBoolean(getString(_settings, _override, "EnableColorSystem", "false"));
		// Name Color System configs
		ENABLE_NAME_COLOR = Boolean.parseBoolean(getString(_settings, _override, "EnableNameColorSystem", "false"));
		COLOR_NAME_DEPENDS = getString(_settings, _override, "NameColorDepends", "PVP");
		String NAME_COLOR = getString(_settings, _override, "NameColor", "100,00FF00;200,FF0000;300,0000FF;");
		String[] name_color_splitted = NAME_COLOR.split(";");
		for (String s : name_color_splitted)
		{
			String[] i = s.split(",");
			NAME_COLORS.put(Integer.parseInt(i[0]), Integer.decode("0x" + i[1]));
		}
		
		// Title Color System configs
		ENABLE_TITLE_COLOR = Boolean.parseBoolean(getString(_settings, _override, "EnableTitleColorSystem", "false"));
		COLOR_TITLE_DEPENDS = getString(_settings, _override, "TitleColorDepends", "PK");
		String TITLE_COLOR = getString(_settings, _override, "TitleColor", "100,00FF00;200,FF0000;300,0000FF;");
		String[] title_color_splitted = TITLE_COLOR.split(";");
		for (String s : title_color_splitted)
		{
			String[] i = s.split(",");
			TITLE_COLORS.put(Integer.parseInt(i[0]), Integer.decode("0x" + i[1]));
		}
	}
	
	public static ColorSystemConfigs getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final ColorSystemConfigs _instance = new ColorSystemConfigs();
	}
}