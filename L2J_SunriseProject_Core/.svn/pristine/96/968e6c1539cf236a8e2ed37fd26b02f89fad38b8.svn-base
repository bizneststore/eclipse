/*
 * Copyright (C) L2J Sunrise
 * This file is part of L2J Sunrise.
 */
package gr.sr.voteEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vGodFather
 */
public class VoteData
{
	private String _url;
	private String _checkWord;
	private final String _name;
	private final boolean _enabled;
	
	private final List<VoteDataReward> _rewards = new ArrayList<>();
	
	public VoteData(String name, boolean enabled)
	{
		_name = name;
		_enabled = enabled;
	}
	
	public String getUrl()
	{
		return _url;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getWord()
	{
		return _checkWord;
	}
	
	public boolean isEnabled()
	{
		return _enabled;
	}
	
	public void setUrl(String url)
	{
		_url = url;
	}
	
	public void setCheckWord(String value)
	{
		_checkWord = value;
	}
	
	public void addReward(VoteDataReward reward)
	{
		_rewards.add(reward);
	}
	
	public List<VoteDataReward> getRewards()
	{
		return _rewards;
	}
}
