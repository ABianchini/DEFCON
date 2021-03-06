package com.advancementbureau.defcon;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.advancementbureau.defconwork.R;

public class DefconActivity extends SuperDefconActivity {
	
	public boolean firstBootDone;
	Date date = Calendar.getInstance().getTime();
	private SoundPool soundPool;
	private int soundID;
	boolean loaded = false;
	private int soundChoice;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Defines SharedPreferences and editor for working with Shared Preferences in the onCreate method
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        
        //redefines colors for Defense Statuses on activity start
        colors(mGameSettings.getInt(DEFCON, 0));
        
        //defines variables for Defense Statuses for later work
        final LinearLayout defOne = (LinearLayout) findViewById(R.id.LinearLayout_DefconOne);
        final LinearLayout defTwo = (LinearLayout) findViewById(R.id.LinearLayout_DefconTwo);
        final LinearLayout defThree = (LinearLayout) findViewById(R.id.LinearLayout_DefconThree);
        final LinearLayout defFour = (LinearLayout) findViewById(R.id.LinearLayout_DefconFour);
        final LinearLayout defFive = (LinearLayout) findViewById(R.id.LinearLayout_DefconFive);
        
        //Displays on first boot Dialog Box. 
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	editor.putBoolean("boot", firstBootDone);
            editor.commit();
            PopUp(R.string.pop_notif, R.string.pop_notif_info); 
        }
        
        
        
        /*
         * The next 10 statements run every time the Defense Status is clicked or long clicked
         * Click: notification of status, changes colors of buttons, and records the log
         * LongClick: Dialog box with info about the Defense Status
         */
        defOne.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		//Notify of Defense Status change
        		defconNotify(1);
        		currentDefcon = 1;
        		//sets color scheme of Defense Status buttons to reflect current Defense Status
        		colors(currentDefcon);
        		defconAlarm();
        		//records Defense Status selection to a log
        		try {
					keepLog(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
        		lastDefcon = currentDefcon;
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
        		defconAlarm();
        		try {
					keepLog(2);
				} catch (IOException e) {
					e.printStackTrace();
				}
        		colors(currentDefcon);
        		lastDefcon = currentDefcon;
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
        		defconAlarm();
        		try {
					keepLog(3);
				} catch (IOException e) {
					e.printStackTrace();
				}
        		colors(currentDefcon);
        		lastDefcon = currentDefcon;
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
        		defconAlarm();
        		try {
					keepLog(4);
				} catch (IOException e) {
					e.printStackTrace();
				}
        		colors(currentDefcon);
        		lastDefcon = currentDefcon;
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
        		defconAlarm();
        		try {
					keepLog(5);
				} catch (IOException e) {
					e.printStackTrace();
				}
        		colors(currentDefcon);
        		lastDefcon = currentDefcon;
        	}
        });
        defFive.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_five_pop, R.string.defcon_five_info);
        		return true;
        	}
        }); 
    }
    
    /*
     * Saves current Defcon to DEFCON
     */
	@Override
    protected void onPause() {
    	super.onPause();
    	Editor editor = mGameSettings.edit();
    	editor.putInt(DEFCON, currentDefcon);
    	editor.putInt(LAST_CONDITION, currentDefcon);
    	editor.commit();
    	
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		//sets currentDefcon to the DEFCON preference and DEFCON to 0 if no defcon exists
        if (mGameSettings.contains(DEFCON)) {
			currentDefcon = mGameSettings.getInt(DEFCON, 0);
		}
        
        if (mGameSettings.contains(LAST_CONDITION)) {
			lastDefcon = mGameSettings.getInt(LAST_CONDITION, 0);
		}
		defconNotify(currentDefcon);
        try {
			keepLog(currentDefcon);
		} catch (IOException e1) {
		}
        soundChoice = mGameSettings.getInt(SOUND_CHOICE, 0);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
        	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        		loaded = true;
        	}
        });
        if (soundChoice == 0) soundID = soundPool.load(this, R.raw.alarm, 1);
        if (soundChoice == 1) soundID = soundPool.load(this, R.raw.alert, 1);
        if (soundChoice == 2) soundID = soundPool.load(this, R.raw.black_ops, 1);
	}
    
	/*
	 * inflates the Action Bar's option menu
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.mainoptions, menu);
    	menu.findItem(R.id.settings_menu_item).setIntent(new Intent(this, DefconSettingsActivity.class));
    	menu.findItem(R.id.changelog_menu_item).setIntent(new Intent(this, DefconChangelogActivity.class));
    	menu.findItem(R.id.about_menu_item);
    	menu.findItem(R.id.log_menu_item).setIntent(new Intent(this, DefconLogActivity.class));
    	return true;
    }
    
    /*
     * Actions that occur when each menu item is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	//Settings
    	if (item.getItemId() == R.id.settings_menu_item) {
			startActivity(item.getIntent()); }
    	//Changelog
    	if (item.getItemId() == R.id.changelog_menu_item) {
    		startActivity(item.getIntent()); }
    	//About
    	if (item.getItemId() == R.id.about_menu_item) {
    		PopUp(R.string.about, R.string.about_pop_info); }
    	//Log
    	if (item.getItemId() == R.id.log_menu_item) {
    		startActivity(item.getIntent());
    	}/*
    	if (item.getItemId() == R.id.current_menu_item) {
    		currentDefconToast();
    	}*/
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
    /*
     * Appends the selected Defense Status to a text file
     * @param i current Defense Status
     */
    public void keepLog(int i) throws IOException {
    	if (i != lastDefcon) {
    		String insertString = null;
			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND|MODE_PRIVATE);
	    	if (i ==1) {
	    		insertString = "Defcon 1 - " + date + "\n";
	    	}if (i ==2) {
	    		insertString = "Defcon 2 - " + date + "\n";
	    	}if (i ==3) {
	    		insertString = "Defcon 3 - " + date + "\n";
	    	}if (i ==4) {
	    		insertString = "Defcon 4 - " + date + "\n";
	    	}if (i ==5) {
	    		insertString = "Defcon 5 - " + date + "\n";
	    	}
	    	fos.write(insertString.getBytes());
	    	fos.close();
    	}
    }
    
    public void defconAlarm() {
    	if(mGameSettings.getBoolean(DEFCON_ALARM, false)) {
	    	AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = actualVolume / maxVolume;
			if (loaded) {
				soundPool.play(soundID, volume, volume, 1, 0, 1f);
			}
    	}
    }
    
    public void currentDefconToast() {
		ArrayList<String> twitterArray = fetchTwitterPublicTimeline();
		String toast = "";
		toast = twitterArray.get(twitterArray.size() - 1);
		Toast.makeText(this, toast, 1000).show();
    }
    
    public ArrayList<String> fetchTwitterPublicTimeline()
    {
        ArrayList<String> listItems = new ArrayList<String>();
        try {
            URL twitter = new URL("http://twitter.com/statuses/user_timeline/defconstatus.json");
            URLConnection tc = twitter.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
 
            String line;
            while ((line = in.readLine()) != null) {
                JSONArray ja = new JSONArray(line);
 
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    listItems.add(jo.getString("text"));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listItems;
    }
    
    /*
     * changes the colors of the Defense Statuses to reflect the current Defense Status
     * @param i identifies the currently selected defcon
     */
    public void colors(int i) {
    	//defines the objects for buttons
    	final LinearLayout defOne = (LinearLayout) findViewById(R.id.LinearLayout_DefconOne);
        final LinearLayout defTwo = (LinearLayout) findViewById(R.id.LinearLayout_DefconTwo);
        final LinearLayout defThree = (LinearLayout) findViewById(R.id.LinearLayout_DefconThree);
        final LinearLayout defFour = (LinearLayout) findViewById(R.id.LinearLayout_DefconFour);
        final LinearLayout defFive = (LinearLayout) findViewById(R.id.LinearLayout_DefconFive);
        
        //changes colors
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