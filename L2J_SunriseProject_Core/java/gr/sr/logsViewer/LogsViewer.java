/*
 * Copyright (C) 2004-2015 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package gr.sr.logsViewer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import l2r.gameserver.ThreadPoolManager;
import l2r.gameserver.cache.HtmCache;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.serverpackets.ShowBoard;

import gr.sr.logsViewer.runnable.Capture;

/**
 * @author NeverMore
 */
public class LogsViewer
{
	private static int _linesToShow = 23;
	
	private static int getFileLinesCount(String patch)
	{
		int number = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(patch)))
		{
			@SuppressWarnings("unused")
			String sCurrentLine;
			
			while ((sCurrentLine = br.readLine()) != null)
			{
				number++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return number;
	}
	
	private static String getLastLines(String patch, int lines)
	{
		int counter = 0;
		String html = "";
		try (BufferedReader br = new BufferedReader(new FileReader(patch)))
		{
			String sCurrentLine;
			
			while ((sCurrentLine = br.readLine()) != null)
			{
				if (lines < counter)
				{
					html += "<tr><td align=left valign=top><font color=66FFFF>" + sCurrentLine + "</font></td></tr>";
					lines++;
				}
				counter++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return html;
	}
	
	public static void startLogViewer(L2PcInstance player, String file)
	{
		LogsViewer.sendCbWindow(player, file);
		player._captureTask = ThreadPoolManager.getInstance().scheduleGeneral(new Capture(player, file), 1 * 500); // every 500 ms
	}
	
	public static void stopLogViewer(L2PcInstance player, String file)
	{
		if (player._captureTask != null)
		{
			player._captureTask.cancel(true);
		}
		sendCbWindow(player, file);
	}
	
	public static void sendCbWindow(L2PcInstance activeChar, String file)
	{
		String content = "";
		content = HtmCache.getInstance().getHtm(activeChar, activeChar.getHtmlPrefix(), "data/html/sunrise/logViewer/main.htm");
		content = content.replace("%file%", file);
		if ((activeChar._captureTask != null) && !activeChar._captureTask.isDone())
		{
			content = content.replace("%stop_button_fore%", "L2UI_CT1.Button_DF.Gauge_DF_Attribute_Fire");
			content = content.replace("%stop_button_back%", "L2UI_CT1.Button_DF.Gauge_DF_Attribute_Fire_bg");
		}
		else
		{
			content = content.replace("%stop_button_fore%", "L2UI_CT1.Button_DF.Button_DF");
			content = content.replace("%stop_button_back%", "L2UI_CT1.Button_DF.Button_DF_Down");
		}
		content = content.replace("%logs%", getLastLines(getPath(file), getFileLinesCount(getPath(file)) - _linesToShow));
		separateAndSend(content, activeChar);
	}
	
	protected static void separateAndSend(String html, L2PcInstance acha)
	{
		if (html == null)
		{
			return;
		}
		if (html.length() < 4096)
		{
			acha.sendPacket(new ShowBoard(html, "101"));
			acha.sendPacket(new ShowBoard(null, "102"));
			acha.sendPacket(new ShowBoard(null, "103"));
		}
		else if (html.length() < 8192)
		{
			acha.sendPacket(new ShowBoard(html.substring(0, 4096), "101"));
			acha.sendPacket(new ShowBoard(html.substring(4096), "102"));
			acha.sendPacket(new ShowBoard(null, "103"));
		}
		else if (html.length() < 16384)
		{
			acha.sendPacket(new ShowBoard(html.substring(0, 4096), "101"));
			acha.sendPacket(new ShowBoard(html.substring(4096, 8192), "102"));
			acha.sendPacket(new ShowBoard(html.substring(8192), "103"));
		}
	}
	
	private static String getPath(String file)
	{
		String path = "";
		switch (file)
		{
			case "java.log":
				path = "./log/main/" + file;
				break;
			case "game.log":
				path = "./log/main/" + file;
				break;
			case "errors.log":
				path = "./log/main/" + file;
				break;
			case "warnings.log":
				path = "./log/main/" + file;
				break;
			default:
				path = "./log/" + file;
				break;
		}
		return path;
	}
}
