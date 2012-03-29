package com.advancementbureau.defcon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DefconActivity extends SuperDefconActivity {
	
	SharedPreferences mGameSettings;
	public boolean firstBootDone;
	public int infoDefcon;
	Date date = Calendar.getInstance().getTime();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        
        colors(mGameSettings.getInt(DEFCON, 0));
        
        final LinearLayout defOne = (LinearLayout) findViewById(R.id.LinearLayout_DefconOne);
        final LinearLayout defTwo = (LinearLayout) findViewById(R.id.LinearLayout_DefconTwo);
        final LinearLayout defThree = (LinearLayout) findViewById(R.id.LinearLayout_DefconThree);
        final LinearLayout defFour = (LinearLayout) findViewById(R.id.LinearLayout_DefconFour);
        final LinearLayout defFive = (LinearLayout) findViewById(R.id.LinearLayout_DefconFive);
        
        if (mGameSettings.contains(DEFCON)) {
			currentDefcon = mGameSettings.getInt(DEFCON, 0);
		}
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	editor.putBoolean("boot", firstBootDone);
            editor.commit();
            PopUp(R.string.pop_notif, R.string.pop_notif_info); 
        }
        
        defOne.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		defconNotify(1);
        		currentDefcon = 1;
        		//toastIt(currentDefcon);
        		colors(currentDefcon);
        		try {
					keepLog(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        });
        defOne.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_one_pop, R.string.defcon_one_info);
        		return true;
        	}
        });
        
        defTwo.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		defconNotify(2);
        		currentDefcon = 2;
        		//toastIt(currentDefcon);
        		colors(currentDefcon);
        	}
        });
        defTwo.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_two_pop, R.string.defcon_two_info);
        		return true;
        	}
        });
        
        defThree.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		defconNotify(3);
        		currentDefcon = 3;
        		//toastIt(currentDefcon);
        		colors(currentDefcon);
        	}
        });
        defThree.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_three_pop, R.string.defcon_three_info);
        		return true;
        	}
        });
        
        defFour.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		defconNotify(4);
        		currentDefcon = 4;
        		//toastIt(currentDefcon);
        		colors(currentDefcon);
        	}
        });
        defFour.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_four_pop, R.string.defcon_four_info);
        		return true;
        	}
        });
        
        defFive.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		defconNotify(5);
        		currentDefcon = 5;
        		//toastIt(currentDefcon);
        		colors(currentDefcon);
        	}
        });
        defFive.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_five_pop, R.string.defcon_five_info);
        		return true;
        	}
        });
        
    }
    
	@Override
    protected void onPause() {
    	super.onPause();
    	Editor editor = mGameSettings.edit();
    	editor.putInt(DEFCON, currentDefcon);
    	editor.commit();
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.mainoptions, menu);
    	menu.findItem(R.id.settings_menu_item).setIntent(new Intent(this, DefconSettingsActivity.class));
    	menu.findItem(R.id.changelog_menu_item).setIntent(new Intent(this, DefconChangelogActivity.class));
    	menu.findItem(R.id.about_menu_item);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	if (item.getItemId() == R.id.settings_menu_item) {
			startActivity(item.getIntent()); }
    	if (item.getItemId() == R.id.changelog_menu_item) {
    		startActivity(item.getIntent()); }
    	if (item.getItemId() == R.id.about_menu_item) {
    		PopUp(R.string.about, R.string.about_pop_info);
    	}
    	return true;
    }
    
    /*
     * Makes a dialog box with a title and a message. Used for simple notifications
     * @param title string id for the title
     * @param message string id for the message
    */
    public void PopUp(int title, int message){
        new AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Close", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            	firstBootDone = false;
            }
        }).show();
    }
    
    public void toastIt(int i) {
    	Toast.makeText(this, "Defcon "+i, 1000).show();
    }
    
    public void defconNotify(int i) {
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
    	
    	Notification notificationDefOne = new Notification(R.drawable.defcon_one, "Defcon 1", System.currentTimeMillis());
		Context context1 = getApplicationContext();
		Intent notificationIntent1 = new Intent(this, DefconActivity.class);
		PendingIntent contentIntent1 = PendingIntent.getActivity(this, 0, notificationIntent1, 0);
		notificationDefOne.setLatestEventInfo(context1, "Defcon 1", "SAVE WHAT YOU CAN AND GET OUT.", contentIntent1);
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
		notificationDefThree.setLatestEventInfo(context2, "Defcon 3", "REMAIN VIGILANT.", contentIntent3);
		notificationDefThree.flags = Notification.FLAG_ONGOING_EVENT;
		final int DEF_THREE = 3;
		
		Notification notificationDefFour = new Notification(R.drawable.defcon_four, "Defcon 4", System.currentTimeMillis());
		Context context4 = getApplicationContext();
		Intent notificationIntent4 = new Intent(this, DefconActivity.class);
		PendingIntent contentIntent4 = PendingIntent.getActivity(this, 0, notificationIntent4, 0);
		notificationDefFour.setLatestEventInfo(context2, "Defcon 4", "REMAIN ALERT.", contentIntent4);
		notificationDefFour.flags = Notification.FLAG_ONGOING_EVENT;
		final int DEF_FOUR = 4;
		
		Notification notificationDefFive = new Notification(R.drawable.defcon_five, "Defcon 5", System.currentTimeMillis());
		Context context5 = getApplicationContext();
		Intent notificationIntent5 = new Intent(this, DefconActivity.class);
		PendingIntent contentIntent5 = PendingIntent.getActivity(this, 0, notificationIntent5, 0);
		notificationDefFive.setLatestEventInfo(context2, "Defcon 5", "Remain ALERT.", contentIntent5);
		notificationDefFive.flags = Notification.FLAG_ONGOING_EVENT;
		final int DEF_FIVE = 5;
	
		
		mNotificationManager.cancel(DEF_ONE);
		mNotificationManager.cancel(DEF_TWO);
		mNotificationManager.cancel(DEF_THREE);
		mNotificationManager.cancel(DEF_FOUR);
		mNotificationManager.cancel(DEF_FIVE);
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
    	} else {
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
    
    public void keepLog(int i) throws IOException {
    	String insertString = null;
		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND|MODE_PRIVATE);
    	if (i ==1) {
    		insertString = "Defcon 1 " + date + "\n";
    	}if (i ==2) {
    		insertString = "Defcon 2 " + date + "\n";
    	}if (i ==3) {
    		insertString = "Defcon 3 " + date + "\n";
    	}if (i ==4) {
    		insertString = "Defcon 4 " + date + "\n";
    	}if (i ==5) {
    		insertString = "Defcon 5 " + date + "\n";
    	}
    	fos.write(insertString.getBytes());
    	fos.close();
    }
    
    public void colors(int i) {
    	final LinearLayout defOne = (LinearLayout) findViewById(R.id.LinearLayout_DefconOne);
        final LinearLayout defTwo = (LinearLayout) findViewById(R.id.LinearLayout_DefconTwo);
        final LinearLayout defThree = (LinearLayout) findViewById(R.id.LinearLayout_DefconThree);
        final LinearLayout defFour = (LinearLayout) findViewById(R.id.LinearLayout_DefconFour);
        final LinearLayout defFive = (LinearLayout) findViewById(R.id.LinearLayout_DefconFive);
        
    	switch (i) {
    		case 0: defOne.setBackgroundResource(R.color.one_color_fade);
					defTwo.setBackgroundResource(R.color.two_color_fade);
					defThree.setBackgroundResource(R.color.three_color_fade);
			    	defFour.setBackgroundResource(R.color.four_color_fade);
			    	defFive.setBackgroundResource(R.color.five_color_fade);
			    	break;
	    	case 1: defOne.setBackgroundResource(R.color.one_color);
	    			defTwo.setBackgroundResource(R.color.two_color_fade);
	    			defThree.setBackgroundResource(R.color.three_color_fade);
			    	defFour.setBackgroundResource(R.color.four_color_fade);
			    	defFive.setBackgroundResource(R.color.five_color_fade);
			    	break;
	    	case 2: defOne.setBackgroundResource(R.color.one_color_fade);
					defTwo.setBackgroundResource(R.color.two_color);
					defThree.setBackgroundResource(R.color.three_color_fade);
			    	defFour.setBackgroundResource(R.color.four_color_fade);
			    	defFive.setBackgroundResource(R.color.five_color_fade);
			    	break;
	    	case 3: defOne.setBackgroundResource(R.color.one_color_fade);
					defTwo.setBackgroundResource(R.color.two_color_fade);
					defThree.setBackgroundResource(R.color.three_color);
			    	defFour.setBackgroundResource(R.color.four_color_fade);
			    	defFive.setBackgroundResource(R.color.five_color_fade);
			    	break;
	    	case 4: defOne.setBackgroundResource(R.color.one_color_fade);
					defTwo.setBackgroundResource(R.color.two_color_fade);
					defThree.setBackgroundResource(R.color.three_color_fade);
			    	defFour.setBackgroundResource(R.color.four_color);
			    	defFive.setBackgroundResource(R.color.five_color_fade);
			    	break;
	    	case 5: defOne.setBackgroundResource(R.color.one_color_fade);
					defTwo.setBackgroundResource(R.color.two_color_fade);
					defThree.setBackgroundResource(R.color.three_color_fade);
			    	defFour.setBackgroundResource(R.color.four_color_fade);
			    	defFive.setBackgroundResource(R.color.five_color);
			    	break;
    	}
    }
}