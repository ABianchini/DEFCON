package com.advancementbureau.defcon;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.advancementbureau.defconwork.R;

public class DefconSettingsActivity extends SuperDefconActivity {
	
	public boolean notifChecked;
	public boolean alarmChecked;
	String FILENAME = "log.txt";
	String strFile = "You never set a defense posture.";
	Editable saveLoc;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        //Shared preferences defining
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
        }
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		//checks or unchecks the checkbox depending on the status of PREFERENCES_NOTIFICATION
        final CheckBox checkBox = (CheckBox) findViewById(R.id.CheckBox_Notification);
        if (mGameSettings.getBoolean(PREFERENCES_NOTIFICATION, true)) {
            checkBox.setChecked(true);
            notifChecked = true;
        } else {
        	checkBox.setChecked(false);
        	notifChecked = false;
        }
        final CheckBox checkBoxAlarm = (CheckBox) findViewById(R.id.CheckBox_Alarm);
        if (mGameSettings.getBoolean(DEFCON_ALARM, false)) {
            checkBoxAlarm.setChecked(true);
            alarmChecked = true;
        } else {
        	checkBox.setChecked(false);
        	alarmChecked = false;
        }
	}
    
    public void onCheckBoxAlarmClicked(View v) {
    	final CheckBox checkBoxAlarm = (CheckBox) findViewById(R.id.CheckBox_Alarm);
        if (checkBoxAlarm.isChecked()) {
            alarmChecked = true;
        } else {
        	alarmChecked = false;
        }
        Editor editor = mGameSettings.edit();
		editor.putBoolean(DEFCON_ALARM, alarmChecked);
		editor.commit();
    }
    
    /*
     * Converts the state of the checkbox to the notifChecked variable and commits it to SharedPreferences
     */
    public void onCheckBoxNotificationsClicked(View v) {
    	if (mGameSettings.contains(DEFCON)) {
			currentDefcon = mGameSettings.getInt(DEFCON, 0);
		}
    	
    	final CheckBox checkBox = (CheckBox) findViewById(R.id.CheckBox_Notification);
        if (checkBox.isChecked()) {
            notifChecked = true;
        } else {
        	notifChecked = false;
        }
        Editor editor = mGameSettings.edit();
		editor.putBoolean(PREFERENCES_NOTIFICATION, notifChecked);
		editor.commit();
		
        defconNotify(currentDefcon);
    }
    
    //for the "up" button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	if (item.getItemId() == android.R.id.home) {
			Intent intent2 = new Intent(this, DefconActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2); }
    	return true;
    }
    
    public String inputStreamToString(InputStream is) throws IOException {
    	StringBuffer sBuffer = new StringBuffer();
    	DataInputStream dataIO = new DataInputStream(is);
    	String strLine = null;
    	while ((strLine = dataIO.readLine()) != null) {
    		sBuffer.append(strLine + "\n");
    	}
    	dataIO.close();
    	is.close();
    	return sBuffer.toString();
    }
}