package com.advancementbureau.defcon;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.advancementbureau.defconwork.R;

public class DefconSettingsActivity extends SuperDefconActivity {
	
	public boolean notifChecked;
	public boolean alarmChecked;
	String FILENAME = "log.txt";
	String strFile = "You never set a defense posture.";
	Editable saveLoc;
	int soundChoice = 0;
	private SoundPool soundPool;
	private int soundID;
	boolean loaded;
	
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
	protected void onStart() {
		super.onStart();
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
        	checkBoxAlarm.setChecked(false);
        	alarmChecked = false;
        }
	}
    
    @Override
    protected void onPause() {
    	super.onPause();
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
    
    public void onCheckBoxAlarmClicked(View v) {
    	final CheckBox checkBoxAlarm = (CheckBox) findViewById(R.id.CheckBox_Alarm);
        if (checkBoxAlarm.isChecked()) {
            alarmChecked = true;
            soundChoiceDialog();
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
    
    public void soundChoiceDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(R.string.choose_sound);
        builder.setIcon(R.drawable.sound);
        builder.setItems(R.array.sound_choice, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	soundChoice = item;
            	Editor editor = mGameSettings.edit();
        		editor.putInt(SOUND_CHOICE, soundChoice);
        		editor.commit();
            }
        });
        builder.setNegativeButton("Nevermind", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				final CheckBox checkBoxAlarm = (CheckBox) findViewById(R.id.CheckBox_Alarm);
				checkBoxAlarm.setChecked(false);
	        	alarmChecked = false;
			}
        });
        builder.show();
        //AlertDialog alert = builder.create();
    	
    	/*
    	Context mContext = getApplicationContext();
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
    	View layout = inflater.inflate(R.layout.sound_dialog, (ViewGroup) findViewById(R.id.layout_root));
    	
    	Spinner soundSpinner = (Spinner) layout.findViewById(R.id.Sound_Spinner);
        ArrayAdapter<CharSequence> preTextAdapter = ArrayAdapter.createFromResource(this, R.array.sound_choice, android.R.layout.simple_spinner_item);
        preTextAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundSpinner.setAdapter(preTextAdapter);
    	class PreTextItemSelectedListener implements OnItemSelectedListener {
    	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	    	soundChoice = pos;
    	    }
			public void onNothingSelected(AdapterView<?> arg0) {
			}
    	}
    	soundSpinner.getPrompt();
    	soundSpinner.setOnItemSelectedListener(new PreTextItemSelectedListener());
    	
    	/*sampleButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		sampleSound(soundChoice);
        	}
        });*/ 
    	/*
    	new AlertDialog.Builder(this)
        .setTitle(R.string.choose_sound)
        .setIcon(R.drawable.sound)
        .setView(layout)
        .setPositiveButton("Save", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            	Editor editor = mGameSettings.edit();
        		editor.putInt(SOUND_CHOICE, soundChoice);
        		editor.commit();
            }
        }).setNegativeButton("Nevermind", new OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				final CheckBox checkBoxAlarm = (CheckBox) findViewById(R.id.CheckBox_Alarm);
				checkBoxAlarm.setChecked(false);
	        	alarmChecked = false;
			}
        }).show();
    	*/
    }
    /*
	private void sampleSound(int i) {
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
	    	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
	    		loaded = true;
	    	}
	    });
	    soundID = 4;
	    if (i == 0) soundID = soundPool.load(this, R.raw.alarm, 1);
	    if (i == 1) soundID = soundPool.load(this, R.raw.alert, 1);
	    if (i == 2) soundID = soundPool.load(this, R.raw.black_ops, 1);
	    if (soundID != 4) {
			AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = actualVolume / maxVolume;
			if (loaded) {
				soundPool.play(soundID, volume, volume, 1, 0, 1f);
			}
	    } else {
	    	Toast.makeText(this, "You haven't selected a sound to sample... moron", 1000).show();
	    }
	}*/
	
}