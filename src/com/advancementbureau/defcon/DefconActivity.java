package com.advancementbureau.defcon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DefconActivity extends SuperDefconActivity {
	
	SharedPreferences mGameSettings;
	public boolean firstBootDone;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	editor.putBoolean("boot", firstBootDone);
            editor.commit();
            PopUp(); 
            }
        LinearLayout defOne = (LinearLayout) findViewById(R.id.LinearLayout_DefconOne);
        
        defOne.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		toastIt();
        	}
        });
        
    }
    
    
    
    /*public void onClickDefconOne(View view) {
    	Toast.makeText(this, "click", 2000).show();
    }*/
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.mainoptions, menu);
    	menu.findItem(R.id.settings_menu_item).setIntent(new Intent(this, DefconSettingsActivity.class));
    	menu.findItem(R.id.changelog_menu_item).setIntent(new Intent(this, DefconChangelogActivity.class));
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	startActivity(item.getIntent());
    	return true;
    }
    
    public void toastIt() {
    	Toast.makeText(this, "click", 2000).show();
    }
    
    public void PopUp(){
        new AlertDialog.Builder(this)
        .setTitle(R.string.pop_notif)
        .setMessage(R.string.pop_notif_info)
        .setPositiveButton("Close", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            	firstBootDone = false;
            }
        }).show();
    }
}