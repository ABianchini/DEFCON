package com.advancementbureau.defcon;

import java.io.File;

import android.app.Activity;

public class SuperDefconActivity extends Activity {
	
	public static final String GAME_PREFERENCES = "GamePrefs";
	
	public static final String PREFERENCES_NOTIFICATION = "notif";
	//if true the first boot has not yet happened
	public static final String FIRST_BOOT = "boot";
	public static final String DEFCON = "currentState";
	
	public int currentDefcon;
	
	String FILENAME = "log.txt";
	File logFile = new File(FILENAME);
	 
}