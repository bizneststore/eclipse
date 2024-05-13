package gr.sr.imageGeneratorEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import l2r.Config;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.serverpackets.PledgeCrest;
import l2r.gameserver.util.Util;

import gr.sr.utils.DDSConverter;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImagesCache
{
	private static final Logger _log = LoggerFactory.getLogger(ImagesCache.class);
	private static final String CREST_IMAGE_KEY_WORD = "Crest.crest_" + Config.SERVER_ID + "_";
	
	private static final ImagesCache _instance = new ImagesCache();
	
	private final Map<String, Integer> _imagesId = new ConcurrentHashMap<>();
	
	private final Map<Integer, byte[]> _images = new ConcurrentHashMap<>();
	
	public static final ImagesCache getInstance()
	{
		return _instance;
	}
	
	private ImagesCache()
	{
		load("data/sunrise/images/id_by_name");
	}
	
	private void load(String folder)
	{
		Map<Integer, File> imagesToLoad = getImagesToLoad(folder);
		
		for (Map.Entry<Integer, File> image : imagesToLoad.entrySet())
		{
			File file = image.getValue();
			byte[] data = DDSConverter.convertToDDS(file).array();
			_images.put(image.getKey(), data);
		}
		
		_log.info(ImagesCache.class.getSimpleName() + ": Loaded " + imagesToLoad.size() + " images (" + folder + ").");
	}
	
	private static Map<Integer, File> getImagesToLoad(String folderPath)
	{
		Map<Integer, File> files = new HashMap<>();
		File folder = new File(Config.DATAPACK_ROOT, folderPath);
		if (folder.exists())
		{
			File[] listOfFiles = folder.listFiles();
			if (listOfFiles == null)
			{
				return files;
			}
			
			for (File file : listOfFiles)
			{
				if (file.getName().endsWith(".png"))
				{
					try
					{
						String name = FilenameUtils.getBaseName(file.getName());
						files.put(Integer.parseInt(name), file);
					}
					catch (NumberFormatException e)
					{
						_log.error(ImagesCache.class.getSimpleName() + ": File " + file.getName() + " in id_by_name folder has invalid name!", e);
					}
				}
			}
		}
		else
		{
			_log.error(ImagesCache.class.getSimpleName() + ": Path " + folderPath + " doesn't exist!");
		}
		
		return files;
	}
	
	/**
	 * Sending All Images that are needed to open HTML to the player
	 * @param html page that may contain images
	 * @param player that will receive images
	 */
	public void sendHtmlImages(String html, L2PcInstance player)
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