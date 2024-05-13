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
public class VoteDataConfigs
{
	public static boolean ENABLE_VOTE_SYSTEM;
	public static boolean DEBUG;
	public static int MIN_LEVEL;
	
	public static String VOICE_COMMAND;
	
	public static String MESSAGE_SUCCESS;
	public static String MESSAGE_FAIL;
	public static String MESSAGE_VOTING;
	public static String MESSAGE_BUSY;
	public static String MESSAGE_MIN_LEVEL;
	public static String SERVER_SITE;
	public static boolean ENABLE_HWID_CHECK;
	public static boolean ENABLE_FORCE_REWARD_ON_CONNECT_FAIL;
	public static boolean MUST_VOTE_ALL;
	
	public static boolean ENABLE_GLOBAL_REWARDS;
	
	public static final List<VoteDataReward> GLOBAL_REWARDS = new ArrayList<>();
}
