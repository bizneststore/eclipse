package gr.sr.javaBuffer;

import l2r.gameserver.cache.HtmCache;
import l2r.gameserver.communitybbs.Managers.ServicesBBSManager;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.serverpackets.NpcHtmlMessage;
import l2r.gameserver.network.serverpackets.ShowBoard;
import l2r.gameserver.network.serverpackets.TutorialShowHtml;

/**
 * @author vGodFather
 */
public class BufferPacketSender
{
	/**
	 * Method to send the html to char
	 * @param player
	 * @param html
	 * @param packet
	 * @param objectId
	 */
	public static void sendPacket(L2PcInstance player, String html, BufferPacketCategories packet, int objectId)
	{
		NpcHtmlMessage msg = new NpcHtmlMessage();
		
		switch (packet)
		{
			case FILE:
				switch (objectId)
				{
					case 0:
						msg.setFile(player, player.getHtmlPrefix(), "/data/html/sunrise/ItemBuffer/" + html);
						player.sendPacket(msg);
						break;
					default:
						msg.setFile(player, player.getHtmlPrefix(), "/data/html/sunrise/NpcBuffer/" + html);
						msg.replace("%objectId%", String.valueOf(objectId));
						player.sendPacket(msg);
						break;
				}
				break;
			case DYNAMIC:
				switch (objectId)
				{
					case 0:
						msg.setHtml(html);
						player.sendPacket(msg);
						break;
					case 1:
						communityBoardPacketSender(player, html);
						break;
					default:
						msg.setHtml(html);
						msg.replace("%objectId%", String.valueOf(objectId));
						player.sendPacket(msg);
						break;
				}
				break;
			case LONG:
				if (objectId != 0)
				{
					html.replace("%objectId%", String.valueOf(objectId));
					player.sendPacket(new TutorialShowHtml(html));
				}
				break;
			case COMMUNITY:
				String content = HtmCache.getInstance().getHtm(player, player.getHtmlPrefix(), "data/html/CommunityBoard/services/buffer/" + html);
				communityBoardPacketSender(player, content);
				break;
		}
	}
	
	private static void communityBoardPacketSender(L2PcInstance player, String html)
	{
		html = html.replace("\t", "");
		html = html.replace("%command%", ServicesBBSManager.getInstance()._servicesBBSCommand);
		
		if (html.length() < 8180)
		{
			player.sendPacket(new ShowBoard(html, "101"));
			player.sendPacket(new ShowBoard(null, "102"));
			player.sendPacket(new ShowBoard(null, "103"));
		}
		else if (html.length() < (8180 * 2))
		{
			player.sendPacket(new ShowBoard(html.substring(0, 8180), "101"));
			player.sendPacket(new ShowBoard(html.substring(8180, html.length()), "102"));
			player.sendPacket(new ShowBoard(null, "103"));
		}
		else if (html.length() < (8180 * 3))
		{
			player.sendPacket(new ShowBoard(html.substring(0, 8180), "101"));
			player.sendPacket(new ShowBoard(html.substring(8180, 8180 * 2), "102"));
			player.sendPacket(new ShowBoard(html.substring(8180 * 2, html.length()), "103"));
		}
	}
}