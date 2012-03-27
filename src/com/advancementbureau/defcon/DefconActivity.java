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
	public int currentDefcon;
	public int infoDefcon;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        if (mGameSettings.contains(DEFCON)) {
			currentDefcon = mGameSettings.getInt(DEFCON, 0);
		}
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	editor.putBoolean("boot", firstBootDone);
            editor.commit();
            PopUp(R.string.pop_notif, R.string.pop_notif_info); 
            }
        
        LinearLayout defOne = (LinearLayout) findViewById(R.id.LinearLayout_DefconOne);
        defOne.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		storeIt(1);
        	}
        });
        defOne.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_one_pop, R.string.defcon_one_info);
        		return true;
        	}
        });
        
        LinearLayout defTwo = (LinearLayout) findViewById(R.id.LinearLayout_DefconTwo);
        defTwo.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		storeIt(2);
        	}
        });
        defTwo.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_two_pop, R.string.defcon_two_info);
        		return true;
        	}
        });
        
        LinearLayout defThree = (LinearLayout) findViewById(R.id.LinearLayout_DefconThree);
        defThree.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		storeIt(3);
        	}
        });
        defThree.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_three_pop, R.string.defcon_three_info);
        		return true;
        	}
        });
        
        LinearLayout defFour = (LinearLayout) findViewById(R.id.LinearLayout_DefconFour);
        defFour.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		storeIt(4);
        	}
        });
        defFour.setOnLongClickListener(new View.OnLongClickListener() {
        	public boolean onLongClick(View view) {
        		PopUp(R.string.defcon_four_pop, R.string.defcon_four_info);
        		return true;
        	}
        });
        
        LinearLayout defFive = (LinearLayout) findViewById(R.id.LinearLayout_DefconFive);
        defFive.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		storeIt(5);
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
    
    public void storeIt(int i) {
    	Toast.makeText(this, "Defcon " + i, 2000).show();
    	SharedPreferences defPref = getSharedPreferences(DEFCON, MODE_PRIVATE);
        SharedPreferences.Editor defEditor = defPref.edit();
    	defEditor.putInt("boot", i);
        defEditor.commit();
    }
    
    /*
     * Used to make a simple dialog box
     * Requires int id of a title string and message string
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
}