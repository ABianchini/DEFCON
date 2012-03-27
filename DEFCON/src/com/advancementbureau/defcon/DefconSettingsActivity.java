package com.advancementbureau.defcon;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;

public class DefconSettingsActivity extends SuperDefconActivity {
	SharedPreferences mGameSettings;
	public boolean notifChecked;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        ActionBar actionBar2 = getActionBar();
        actionBar2.setDisplayHomeAsUpEnabled(true);
    }
    @Override
	protected void onPause() {
		super.onPause();
		
		final CheckBox checkBox = (CheckBox) findViewById(R.id.CheckBox_Notification);
        if (checkBox.isChecked()) {
            notifChecked = true;
        }
		
		Editor editor = mGameSettings.edit();
		editor.putBoolean(PREFERENCES_NOTIFICATION, notifChecked);

		editor.commit();
	}
    
    
    
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