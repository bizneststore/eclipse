package gr.sr.imageGeneratorEngine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import l2r.Config;
import l2r.gameserver.idfactory.IdFactory;
import l2r.gameserver.instancemanager.ServerVariables;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.serverpackets.PledgeCrest;
import l2r.gameserver.util.Util;

import gr.sr.utils.DDSConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalImagesCache
{
	private static final Logger _log = LoggerFactory.getLogger(GlobalImagesCache.class);
	private static final String CREST_IMAGE_KEY_WORD = "Crest.crest_" + Config.SERVER_ID + "_";
	private static final int[] SIZES =
	{
		1,
		2,
		4,
		8,
		16,
		32,
		64,
		128,
		256,
		512,
		1024
	};
	private static final int MAX_SIZE = SIZES[(SIZES.length - 1)];
	
	public static final Pattern HTML_PATTERN = Pattern.compile("%image:(.*?)%", 32);
	
	private static final GlobalImagesCache _instance = new GlobalImagesCache();
	
	private final Map<String, Integer> _imagesId = new ConcurrentHashMap<>();
	
	private final Map<Integer, byte[]> _images = new ConcurrentHashMap<>();
	
	public static final GlobalImagesCache getInstance()
	{
		return _instance;
	}
	
	private GlobalImagesCache()
	{
		load("data/sunrise/images");
	}
	
	public void load(String folder)
	{
		File dir = new File(Config.DATAPACK_ROOT, folder);
		if ((!dir.exists()) || (!dir.isDirectory()))
		{
			_log.info(getClass().getSimpleName() + ": Files missing, loading aborted.");
			return;
		}
		
		int count = 0;
		for (File file : dir.listFiles())
		{
			if (!file.isDirectory())
			{
				file = resizeImage(file);
				if (file != null)
				{
					count++;
					
					String fileName = file.getName();
					ByteBuffer bf = DDSConverter.convertToDDS(file);
					byte[] image = bf.array();
					int imageId = ServerVariables.getInt(file.getName(), IdFactory.getInstance().getNextId());
					
					if (!file.getName().startsWith("captcha"))
					{
						ServerVariables.set(file.getName(), imageId);
					}
					_imagesId.put(fileName.toLowerCase(), Integer.valueOf(imageId));
					_images.put(imageId, image);
				}
			}
		}
		_log.info(getClass().getSimpleName() + ": Loaded " + count + " images (" + folder + ").");
	}
	
	private static File resizeImage(File file)
	{
		BufferedImage image;
		try
		{
			image = ImageIO.read(file);
		}
		catch (IOException e)
		{
			_log.error(GlobalImagesCache.class.getSimpleName() + ": Error while resizing " + file.getName() + " image.", e);
			return null;
		}
		
		if (image == null)
		{
			return null;
		}
		int width = image.getWidth();
		int height = image.getHeight();
		
		boolean resizeWidth = true;
		if (width > MAX_SIZE)
		{
			image = image.getSubimage(0, 0, MAX_SIZE, height);
			resizeWidth = false;
		}
		
		boolean resizeHeight = true;
		if (height > MAX_SIZE)
		{
			image = image.getSubimage(0, 0, width, MAX_SIZE);
			resizeHeight = false;
		}
		
		int resizedWidth = width;
		if (resizeWidth)
		{
			for (int size : SIZES)
			{
				if (size >= width)
				{
					resizedWidth = size;
					break;
				}
			}
		}
		int resizedHeight = height;
		if (resizeHeight)
		{
			for (int size : SIZES)
			{
				if (size >= height)
				{
					resizedHeight = size;
					break;
				}
			}
		}
		if ((resizedWidth != width) || (resizedHeight != height))
		{
			for (int x = 0; x < resizedWidth; x++)
			{
				for (int y = 0; y < resizedHeight; y++)
				{
					image.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
			String filename = file.getName();
			String format = filename.substring(filename.lastIndexOf("."));
			try
			{
				ImageIO.write(image, format, file);
			}
			catch (IOException e)
			{
				_log.error(GlobalImagesCache.class.getSimpleName() + ": Error while resizing " + file.getName() + " image.", e);
				return null;
			}
		}
		return file;
	}
	
	/**
	 * Sending All Images that are needed to open HTML to the player
	 * @param html page that may contain images
	 * @param player that will receive images
	 */
	public void sendUsedImages(String html, L2PcInstance player)
	{
		char[] charArray = html.toCharArray();
		int lastIndex = 0;
		
		while (lastIndex != -1)
		{
			lastIndex = html.indexOf(CREST_IMAGE_KEY_WORD, lastIndex);
			
			if (lastIndex != -1)
			{
				int start = lastIndex + CREST_IMAGE_KEY_WORD.length();
				int end = getFileNameEnd(charArray, start);
				lastIndex = end;
				int imageId = Integer.parseInt(html.substring(start, end));
				
				sendImageToPlayer(player, imageId);
			}
		}
	}
	
	/**
	 * Sending Image as PledgeCrest to a player If image was already sent once to the player, it's skipping this part Saved images data is in player Quick Vars as Key: "Image"+imageId Value: true
	 * @param player that will receive image
	 * @param imageId Id of the image
	 */
	public void sendImageToPlayer(L2PcInstance player, int imageId)
	{
		if (player.wasImageLoaded(imageId))
		{
			return;
		}
		
		player.addLoadedImage(imageId);
		
		if (_images.containsKey(imageId))
		{
			player.sendPacket(new PledgeCrest(imageId, _images.get(imageId)));
		}
	}
	
	/**
	 * Getting end of Image File Name(name is always numbers)
	 * @param charArray whole text
	 * @param start place
	 * @return whole name
	 */
	private static int getFileNameEnd(char[] charArray, int start)
	{
		int stop = start;
		for (; stop < charArray.length; stop++)
		{
			if (!Util.isInteger(charArray[stop]))
			{
				return stop;
			}
		}
		return stop;
	}
	
	public Set<Integer> getAllImages()
	{
		return _images.keySet();
	}
	
	public int getImageId(String val)
	{
		int imageId = 0;
		if (_imagesId.get(val.toLowerCase()) != null)
		{
			imageId = _imagesId.get(val.toLowerCase()).intValue();
		}
		return imageId;
	}
	
	public byte[] getImage(int imageId)
	{
		return _images.get(imageId);
	}
}