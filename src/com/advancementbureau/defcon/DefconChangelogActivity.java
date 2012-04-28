package com.advancementbureau.defcon;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import com.advancementbureau.defconwork.R;

public class DefconChangelogActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changelog);
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
        }
        
        final ScrollView logScroller = (ScrollView) findViewById(R.id.ScrollViewSettings);
        logScroller.post(new Runnable() { 
            public void run() { 
                logScroller.fullScroll(ScrollView.FOCUS_DOWN); 
            } 
        }); 
        
        InputStream iFile = getResources().openRawResource(R.raw.changelog);
        try {
        	TextView changeLog = (TextView) findViewById(R.id.TextView_ChangelogText);
        	String strFile = inputStreamToString(iFile);
        	changeLog.setText(strFile);
        } catch (Exception e) {
        }
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