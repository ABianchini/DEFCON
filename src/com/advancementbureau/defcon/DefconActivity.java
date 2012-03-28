package com.advancementbureau.defcon;

import android.app.AlertDialog;
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
	public int currentDefcon;
	public int infoDefcon;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        //SharedPreferences defPref = getSharedPreferences(DEFCON, MODE_PRIVATE);
        //SharedPreferences.Editor defEditor = defPref.edit();
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
        		currentDefcon = 1;
        		toastIt(currentDefcon);
        		colors(1);
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
        		currentDefcon = 2;
        		toastIt(currentDefcon);
        		colors(2);
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
        		currentDefcon = 3;
        		toastIt(currentDefcon);
        		colors(3);
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
        		currentDefcon = 4;
        		toastIt(currentDefcon);
        		colors(4);
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
        		currentDefcon = 5;
        		toastIt(currentDefcon);
        		colors(5);
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