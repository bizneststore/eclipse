package gr.sr.main.license;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import l2r.gameserver.ThreadPoolManager;

import gr.sr.network.handler.ServerTypeConfigs;
import gr.sr.utils.Tools;
import gr.sr.validator.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vGodFather
 */
public class LicenseVersion
{
	private static final Logger _log = LoggerFactory.getLogger(LicenseVersion.class);
	
	// Revisions Info
	private static final int _coreRev = 1180;
	private static final int _datapackRev = 1001;
	private static final int _engineRev = 181;
	private static final int _geoengine = 96;
	private static final int _eventsRev = 57;
	private static final int _protocolRev = 109;
	private static final int _utils = 31;
	private static final int _licence = 32;
	private static final String _sunriseRev = _coreRev + "." + _datapackRev + "." + _engineRev + "." + _eventsRev + "." + _geoengine + "." + _protocolRev + "." + _utils + "." + _licence;
	
	// Project Chronicle and Sunrise Chronicle
	private static final String _protocols = ServerTypeConfigs.SERVER_TYPE.getProtocols().toString();
	private static final String _chronicle = "High Five Part 5 (CT2.6)";
	
	// Contact
	private static final String _website = "https://www.l2jsunrise.com/";
	private static final String _website_panel = "https://account.l2jsunrise.com/";
	
	// Project Info
	private static final String _copyRights = "Sunrise - Coding Team";
	private static final String _years = "2015-2023";
	private static final String _owners = "vGodFather - vNeverMore";
	
	//@formatter:off
	private static final String[] _logo =
	{
		"########################################################\n",
		"#           #####                  #####################\n",
		"#          #####   ########         ####################\n",
		"#         #####   ########           ###################\n",
		"#        #####   ##    ##             ##################\n",
		"#       #####         ##               ###           ###\n",
		"#      #####         ##                 ### Lineage2 ###\n",
		"#     #####         ##                   ###  Java   ###\n",
		"#    #####         #########              ### Server ###\n",
		"#   #####         #########                ###       ###\n",
		"#  ##############                           ############\n",
		"# ############## ########                    ###########\n",
		"#                                             ##########\n",
		"#                                              #########\n",
		"#                                               ########\n",
		"#                                                #######\n",
		"#                                                 ######\n",
		"#                                                  #####\n",
		"#                                                   ####\n",
		"#                                                    ###\n",
		"#                                                     ##\n",
		"# www.L2jSunrise.com                                   #\n",
		"########################################################\n"
	};
	//@formatter:on
	
	protected LicenseVersion()
	{
		print();
		
		Validator.load();
		
		ThreadPoolManager.getInstance().executeGeneral(() -> loadLicense());
	}
	
	public static void print()
	{
		_log.info("=====================================================");
		_log.info("Copyrights: .............: " + _copyRights);
		_log.info("Years: ..................: " + _years);
		_log.info("Website: ................: " + _website);
		_log.info("Website panel: ..........: " + _website_panel);
		_log.info("Project Owners: .........: " + _owners);
		_log.info("Chronicle: ..............: " + _chronicle);
		_log.info("Protocols: ..............: " + _protocols);
		_log.info("Utils: ..................: " + _utils);
		_log.info("Licence: ................: " + _licence);
		_log.info("Sunrise Revision: .......: ver. " + _sunriseRev);
		printMemUsage();
		_log.info("=====================================================");
	}
	
	private static void loadLicense()
	{
		try
		{
			License.getInstance();
		}
		catch (Exception e)
		{
		
		}
		catch (Error e)
		{
		
		}
	}
	
	private static void printMemUsage()
	{
		for (String line : getMemoryUsageStatistics())
		{
			_log.info(line);
		}
	}
	
	private static String[] getMemoryUsageStatistics()
	{
		double max = Runtime.getRuntime().maxMemory() / 1024 / 1024; // maxMemory is the upper limit the jvm can use
		double allocated = Runtime.getRuntime().totalMemory() / 1024 / 1024; // totalMemory the size of the current allocation pool
		double nonAllocated = max - allocated; // non allocated memory till jvm limit
		double cached = Runtime.getRuntime().freeMemory() / 1024 / 1024; // freeMemory the unused memory in the allocation pool
		double used = allocated - cached; // really used memory
		double useable = max - used; // allocated, but non-used and non-allocated memory
		
		SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
		DecimalFormat df = new DecimalFormat(" (0.0000'%')");
		DecimalFormat df2 = new DecimalFormat(" # 'MB'");
		
		return new String[]
		{
			"+----", // ...
			"| Global Memory Informations at " + sdf.format(new Date()) + ":", // ...
			"|    |", // ...
			"| Allowed Memory:" + df2.format(max),
			"|    |= Allocated Memory:" + df2.format(allocated) + df.format((allocated / max) * 100),
			"|    |= Non-Allocated Memory:" + df2.format(nonAllocated) + df.format((nonAllocated / max) * 100),
			"| Allocated Memory:" + df2.format(allocated),
			"|    |= Used Memory:" + df2.format(used) + df.format((used / max) * 100),
			"|    |= Unused (cached) Memory:" + df2.format(cached) + df.format((cached / max) * 100),
			"| Useable Memory:" + df2.format(useable) + df.format((useable / max) * 100), // ...
			"+----"
		};
	}
	
	@SuppressWarnings("unused")
	private static final void printLogo(final String[] args)
	{
		if (args.length > 0)
		{
			for (final String arg : args)
			{
				if (arg.equals("nologo"))
				{
					return;
				}
			}
		}
		
		for (final String line : _logo)
		{
			for (int i = 0; i < line.length(); i++)
			{
				char c = line.charAt(i);
				System.out.print(c);
				
				switch (c)
				{
					case ' ':
					case '\n':
						break;
						
					default:
						Tools.sleep(5);
						break;
				}
			}
		}
		
		Tools.sleep(2000);
	}
	
	public static LicenseVersion getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final LicenseVersion _instance = new LicenseVersion();
	}
}
