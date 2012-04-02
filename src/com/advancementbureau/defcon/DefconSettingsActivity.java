package com.advancementbureau.defcon;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import com.advancementbureau.defconwork.R;

public class DefconSettingsActivity extends SuperDefconActivity {
	SharedPreferences mGameSettings;
	public boolean notifChecked;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        //Shared preferences defining
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        //ActionBar actionBar2 = getActionBar();
        //actionBar2.setDisplayHomeAsUpEnabled(true);
        
        //checks or unchecks the checkbox depending on the status of PREFERENCES_NOTIFICATION
        final CheckBox checkBox = (CheckBox) findViewById(R.id.CheckBox_Notification);
        if (mGameSettings.getBoolean(PREFERENCES_NOTIFICATION, true)) {
            checkBox.setChecked(true);
            notifChecked = true;
        } else {
        	checkBox.setChecked(false);
        	notifChecked = false;
        }
    }
    @Override
	protected void onPause() {
		super.onPause();
		
		//commits notifChecked to PREFERENCES_NOTIFICATION
		Editor editor = mGameSettings.edit();
		editor.putBoolean(PREFERENCES_NOTIFICATION, notifChecked);

		editor.commit();
	}
    
    /*
     * Converts the state of the checkbox to the notifChecked variable
     */
    public void onCheckBoxNotificationsClicked(View v) {
    	final CheckBox checkBox = (CheckBox) findViewById(R.id.CheckBox_Notification);
        if (checkBox.isChecked()) {
            notifChecked = true;
        } else {
        	notifChecked = false;
        }
        PopUp(R.string.reselect, R.string.reselect_info);
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
            public void onClick(DialogInterface arg0, int arg1) {}
        }).show();
    }
    
    //TODO push log.txt to a folder on the SD Card
    public void onSavetoSDClick(View view) {
    	
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
}