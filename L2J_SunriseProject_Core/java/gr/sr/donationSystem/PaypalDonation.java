package gr.sr.donationSystem;

import gr.sr.donationSystem.enums.ActionResponse;
import gr.sr.donationSystem.enums.ActionResult;
import gr.sr.donationSystem.enums.InvoiceResponse;
import gr.sr.donationSystem.enums.RewardResponse;
import gr.sr.donationSystem.impl.PaypalManager;
import gr.sr.donationSystem.model.PayerData;
import gr.sr.logging.Log;

/**
 * @author vGodFather
 */
public class PaypalDonation extends PaypalManager
{
	public PaypalDonation()
	{
		super();
	}
	
	@Override
	public void onInvoiceResponse(PayerData activeChar, ActionResult result, InvoiceResponse response)
	{
		switch (result)
		{
			case ERROR:
				switch (response)
				{
					case CREATED:
						activeChar.sendMessage("There was an error while creating the invoice, please try again later!");
						break;
					case CANCELED:
						activeChar.sendMessage("There was an error while canceling the invoice, please try again later!");
						break;
				}
				return;
			case FAILED:
				switch (response)
				{
					case EXISTS:
						activeChar.sendMessageS("There is a pending invoice, cancel it or pay in order to continue.", 3);
						activeChar.sendMessage("There is a pending invoice, cancel it or pay in order to continue.");
						break;
					case CANCELED:
						activeChar.sendMessage("You cannot cancel a paid or partially paid invoiced.");
						break;
				}
				return;
			case SUCCESS:
				switch (response)
				{
					case CREATED:
						activeChar.sendMessage("Your invoice generated succesfully!");
						break;
					case CANCELED:
						activeChar.sendMessage("Your invoice canceled succesfully!");
						break;
				}
				break;
		}
	}
	
	@Override
	public void onRewardResponse(PayerData activeChar, RewardResponse response, int amount)
	{
		switch (response)
		{
			case SUCCESS:
				// This case is called if invoice is paid and we must reward player
				// amount is the credits that he bought
				// if PaypalCreditPrice = 1 then amount will be equal to what he donated
				// you can change reward(s) to your needs
				activeChar.addItem("Donation", DonationConfigs.PAYPAL_REWARD_ID, amount, activeChar, true);
				activeChar.sendMessage("Thanks for your donation. Successfully rewarded!");
				break;
			case ALREADY_RECEIVE:
				activeChar.sendMessage("You have already receive your reward for this invoice.");
				break;
			case FAILED:
				activeChar.sendMessage("You did not paid the invoice, check your paypal account or your email, pay the invoice and request reward again.");
				break;
		}
	}
	
	@Override
	public void onResponse(PayerData activeChar, ActionResult result, ActionResponse response, Exception e)
	{
		switch (result)
		{
			// Error always have exception
			case ERROR:
				if (activeChar != null)
				{
					activeChar.sendMessage(result + " " + response + ": Try again later, if this problem remains, please contact an administrator.");
				}
				Log.info(result + " " + response + ": Try again later, if this problem remains, please contact an administrator.");
				e.printStackTrace();
				return;
			case FAILED:
				activeChar.sendMessage(result + " " + response + ": Try again later, if this problem remains, please contact an administrator.");
				Log.info(result + " " + response + ": Try again later, if this problem remains, please contact an administrator.");
				return;
			case SUCCESS:
				break;
		}
	}
	
	public static PaypalDonation getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PaypalDonation _instance = new PaypalDonation();
	}
}
