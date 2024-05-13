package gr.sr.donateEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.StringTokenizer;

import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.L2GameClient;
import l2r.gameserver.util.Util;

/**
 * @author vGodFather
 */
public class DonateHandler
{
	private DonateHandler()
	{
		// Dummy default
	}
	
	public static void sendDonateForm(L2PcInstance player, String command)
	{
		String value = command.substring(14);
		StringTokenizer s = new StringTokenizer(value, " ");
		L2GameClient info = player.getClient();
		String pin1 = "";
		String pin2 = "";
		String pin3 = "";
		String pin4 = "";
		String amount = "";
		
		try
		{
			pin1 = s.nextToken();
			pin2 = s.nextToken();
			pin3 = s.nextToken();
			pin4 = s.nextToken();
			amount = s.nextToken();
			
			if ((pin1 == "") || (pin1 == null) || (pin2 == "") || (pin2 == null) || (pin3 == "") || (pin3 == null) || (pin4 == "") || (pin4 == null) || (amount == "") || (amount == null))
			{
				player.sendMessage("Complete all the fields please.");
				return;
			}
			if ((pin1.length() > 4) || (pin2.length() > 4) || (pin3.length() > 4) || (pin4.length() > 4))
			{
				player.sendMessage("Pin boxes cannot contain more than 4 digits.");
				return;
			}
			if (!Util.isDigit(pin1) || !Util.isDigit(pin2) || !Util.isDigit(pin3) || !Util.isDigit(pin4) || !Util.isDigit(amount))
			{
				player.sendMessage("Pin code and amount can only contain numbers.");
				return;
			}
			
			String fname = "data/sunrise/donates/" + player.getName() + ".txt";
			File file = new File(fname);
			boolean exist = file.createNewFile();
			if (!exist)
			{
				player.sendMessage("You have already sent a donate form to a gm, he must check it first.");
				return;
			}
			try (FileWriter fstream = new FileWriter(fname);
				BufferedWriter out = new BufferedWriter(fstream))
			{
				out.write("Character Info: " + info + "\r\nPaysafe Pin: " + pin1 + "-" + pin2 + "-" + pin3 + "-" + pin4 + "\r\nAmount: " + amount + "\r");
				player.sendMessage("Done, wait for a gm to check and apply your donation form. We will contact with you soon.");
			}
		}
		catch (Exception e)
		{
			player.sendMessage("Cannot send an empty donate form.");
		}
	}
}