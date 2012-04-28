package com.advancementbureau.defcon;

import java.io.File;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.advancementbureau.defconwork.R;

public class SuperDefconActivity extends Activity {
	
	SharedPreferences mGameSettings;
	
	public static final String GAME_PREFERENCES = "GamePrefs";
	
	public static final String PREFERENCES_NOTIFICATION = "notif";
	//if true the first boot has not yet happened
	public static final String FIRST_BOOT = "boot";
	public static final String DEFCON = "currentState";
	public static final String LAST_CONDITION = "lastDefcon";
	
	public int currentDefcon;
	public int lastDefcon;
	
	String FILENAME = "log.txt";
	File logFile = new File(FILENAME);
	
	/*
     * Makes Toast Notification for current Defcon if Task Bar Notifications are off
     * @param i for current Defcon
     */
	public void toastIt(int i) {
    	Toast.makeText(this, "Defcon "+i, 1000).show();
    }
	 
	/*
     * Notifications for current Defcon (Toast and task bar)
     * @param i current Defcon
     */
	public void defconNotify(int i) {
    	//defines varaibles
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
    	
    	//Creates notification with icon and text
    	Notification notificationDefOne = new Notification(R.drawable.defcon_one, "Defcon 1", System.currentTimeMillis());
		Context context1 = getApplicationContext();
		//intent for starting this activity when the notification is clicked
		Intent notificationIntent1 = new Intent(this, DefconActivity.class);
		PendingIntent contentIntent1 = PendingIntent.getActivity(this, 0, notificationIntent1, 0);
		//content of the notification after the initial notifying
		notificationDefOne.setLatestEventInfo(context1, "Defcon 1", "SAVE WHAT YOU CAN AND GET OUT.", contentIntent1);
		//Makes the notification ongoing
		notificationDefOne.flags = Notification.FLAG_ONGOING_EVENT; 
		final int DEF_ONE = 1;
		
		Notification notificationDefTwo = new Notification(R.drawable.defcon_two, "Defcon 2", System.currentTimeMillis());
		Context context2 = getApplicationContext();
		Intent notificationIntent2 = new Intent(this, DefconActivity.class);
		PendingIntent contentIntent2 = PendingIntent.getActivity(this, 0, notificationIntent2, 0);
		notificationDefTwo.setLatestEventInfo(context2, "Defcon 2", "STOP EVERYTHING AND RESTORE STABILITY.", contentIntent2);
		notificationDefTwo.flags = Notification.FLAG_ONGOING_EVENT;
		final int DEF_TWO = 2;
		
		Notification notificationDefThree = new Notification(R.drawable.defcon_three, "Defcon 3", System.currentTimeMillis());
		Context context3 = getApplicationContext();
		Intent notificationIntent3 = new Intent(this, DefconActivity.class);
		PendingIntent contentIntent3 = PendingIntent.getActivity(this, 0, notificationIntent3, 0);
		notificationDefThree.setLatestEventInfo(context3, "Defcon 3", "REMAIN VIGILANT.", contentIntent3);
		notificationDefThree.flags = Notification.FLAG_ONGOING_EVENT;
		final int DEF_THREE = 3;
		
		Notification notificationDefFour = new Notification(R.drawable.defcon_four, "Defcon 4", System.currentTimeMillis());
		Context context4 = getApplicationContext();
		Intent notificationIntent4 = new Intent(this, DefconActivity.class);
		PendingIntent contentIntent4 = PendingIntent.getActivity(this, 0, notificationIntent4, 0);
		notificationDefFour.setLatestEventInfo(context4, "Defcon 4", "REMAIN ALERT.", contentIntent4);
		notificationDefFour.flags = Notification.FLAG_ONGOING_EVENT;
		final int DEF_FOUR = 4;
		
		Notification notificationDefFive = new Notification(R.drawable.defcon_five, "Defcon 5", System.currentTimeMillis());
		Context context5 = getApplicationContext();
		Intent notificationIntent5 = new Intent(this, DefconActivity.class);
		PendingIntent contentIntent5 = PendingIntent.getActivity(this, 0, notificationIntent5, 0);
		notificationDefFive.setLatestEventInfo(context5, "Defcon 5", "Remain ALERT.", contentIntent5);
		notificationDefFive.flags = Notification.FLAG_ONGOING_EVENT;
		final int DEF_FIVE = 5;
	
		//cancels all other notifications about Defense statuses before creating a new one
		mNotificationManager.cancel(DEF_ONE);
		mNotificationManager.cancel(DEF_TWO);
		mNotificationManager.cancel(DEF_THREE);
		mNotificationManager.cancel(DEF_FOUR);
		mNotificationManager.cancel(DEF_FIVE);
		
		//notifies the user of what defcon your in
		if (mGameSettings.getBoolean(PREFERENCES_NOTIFICATION, true)) {
	    	if (i == 1) {
	    		mNotificationManager.notify(DEF_ONE, notificationDefOne);
	    	}
	    	if (i == 2) {
	    		mNotificationManager.notify(DEF_TWO, notificationDefTwo);
	    	}
	    	if (i == 3) {
	    		mNotificationManager.notify(DEF_THREE, notificationDefThree);
	    	}
	    	if (i == 4) {
	    		mNotificationManager.notify(DEF_FOUR, notificationDefFour);
	    	}
	    	if (i == 5) {
	    		mNotificationManager.notify(DEF_FIVE, notificationDefFive);
	    	}
    	} //if there is no taskbar notification, the toast runs
		else {
    		if (i == 1) {
    			toastIt(1);
    		} if (i == 2) {
    			toastIt(2);
    		} if (i == 3) {
    			toastIt(3);
    		} if (i == 4) {
    			toastIt(4);
    		} if (i == 5) {
    			toastIt(5);
    		}
    	}
    }
}