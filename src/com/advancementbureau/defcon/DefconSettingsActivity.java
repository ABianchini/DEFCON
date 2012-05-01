package com.advancementbureau.defcon;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.advancementbureau.defconwork.R;

public class DefconSettingsActivity extends SuperDefconActivity {
	
	public boolean notifChecked;
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
		
	}
    
    /*
     * Converts the state of the checkbox to the notifChecked variable
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
    
    /*//pushes log.txt to the root of the SD Card
    public void onSavetoSDClick(View view) {
    	InputStream iFile;
    	
		try {
			try {
				iFile = openFileInput(FILENAME);
				strFile = inputStreamToString(iFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File myFile = new File("/sdcard/DEFCON_Log.txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(strFile);
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(), "'DEFCON_Log.txt' has been written to SD", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
    }*/
    
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