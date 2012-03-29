package com.advancementbureau.defcon;

import java.io.File;

import android.app.Activity;

public class SuperDefconActivity extends Activity {
	
	public static final String GAME_PREFERENCES = "GamePrefs";
	
	public static final String PREFERENCES_NOTIFICATION = "notif";
	public static final String FIRST_BOOT = "boot";
	public static final String DEFCON = "currentState";
	public static final String NOTIFICATION_PRESENT = "here";
	
	public int currentDefcon;
	String FILENAME = "log.txt";
	File logFile = new File(FILENAME);
	 
}