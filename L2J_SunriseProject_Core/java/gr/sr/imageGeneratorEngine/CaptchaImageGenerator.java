package gr.sr.imageGeneratorEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import l2r.Config;
import l2r.gameserver.model.actor.instance.L2PcInstance;
import l2r.gameserver.network.serverpackets.PledgeCrest;

import gr.sr.utils.DDSConverter;

/**
 * @author vGodFather
 */
public class CaptchaImageGenerator
{
	public StringBuilder finalString = new StringBuilder();
	
	public StringBuilder getFinalString()
	{
		return finalString;
	}
	
	public BufferedImage generateCaptcha()
	{
		Color textColor = new Color(98, 213, 43);
		Color circleColor = new Color(98, 213, 43);
		Font textFont = new Font("comic sans ms", Font.BOLD, 24);
		int charsToPrint = 5;
		int width = 256;
		int height = 64;
		int circlesToDraw = 8;
		float horizMargin = 20.0f;
		double rotationRange = 0.7; // this is radians
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
		
		// Draw an oval
		g.setColor(new Color(30, 31, 31));
		g.fillRect(0, 0, width, height);
		
		// lets make some noisy circles
		g.setColor(circleColor);
		for (int i = 0; i < circlesToDraw; i++)
		{
			int circleRadius = (int) ((Math.random() * height) / 2.0);
			int circleX = (int) ((Math.random() * width) - circleRadius);
			int circleY = (int) ((Math.random() * height) - circleRadius);
			g.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
		}
		
		g.setColor(textColor);
		g.setFont(textFont);
		
		FontMetrics fontMetrics = g.getFontMetrics();
		int maxAdvance = fontMetrics.getMaxAdvance();
		int fontHeight = fontMetrics.getHeight();
		
		// Suggestions ----------------------------------------------------------------------
		// i removed 1 and l and i because there are confusing to users...
		// Z, z, and N also get confusing when rotated
		// 0, O, and o are also confusing...
		// lowercase G looks a lot like a 9 so i killed it
		// this should ideally be done for every language...
		// i like controlling the characters though because it helps prevent confusion
		// So recommended chars are:
		// String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYabcdefhjkmnpqrstuvwxy23456789";
		// Suggestions ----------------------------------------------------------------------
		// String elegibleChars = "ABCDEFGHJKLMPQRSTUVWXYZ";
		String elegibleChars = "0123456789";
		char[] chars = elegibleChars.toCharArray();
		
		float spaceForLetters = (-horizMargin * 2) + width;
		float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);
		
		for (int i = 0; i < charsToPrint; i++)
		{
			double randomValue = Math.random();
			int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
			char characterToShow = chars[randomIndex];
			finalString.append(characterToShow);
			
			// this is a separate canvas used for the character so that
			// we can rotate it independently
			int charWidth = fontMetrics.charWidth(characterToShow);
			int charDim = Math.max(maxAdvance, fontHeight);
			int halfCharDim = (charDim / 2);
			
			BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
			Graphics2D charGraphics = charImage.createGraphics();
			charGraphics.translate(halfCharDim, halfCharDim);
			double angle = (Math.random() - 0.5) * rotationRange;
			charGraphics.transform(AffineTransform.getRotateInstance(angle));
			charGraphics.translate(-halfCharDim, -halfCharDim);
			charGraphics.setColor(textColor);
			charGraphics.setFont(textFont);
			
			int charX = (int) ((0.5 * charDim) - (0.5 * charWidth));
			charGraphics.drawString("" + characterToShow, charX, (((charDim - fontMetrics.getAscent()) / 2) + fontMetrics.getAscent()));
			
			float x = (horizMargin + (spacePerChar * (i))) - (charDim / 2.0f);
			int y = ((height - charDim) / 2);
			g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
			
			charGraphics.dispose();
		}
		
		g.dispose();
		
		return bufferedImage;
	}
	
	public void captchaLogo(L2PcInstance player, int imgId)
	{
		// Conversion from .png to .dds, and crest packed send
		try
		{
			File captcha = new File("data/sunrise/images/captcha.png");
			ImageIO.write(CaptchaImageGenerator.getInstance().generateCaptcha(), "png", captcha);
			PledgeCrest packet = new PledgeCrest(imgId, DDSConverter.convertToDDS(captcha).array()); // Convertion to DDS where is antybot
			player.sendPacket(packet);
		}
		catch (Exception e)
		{
			if (Config.DEBUG)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static CaptchaImageGenerator getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final CaptchaImageGenerator _instance = new CaptchaImageGenerator();
	}
}