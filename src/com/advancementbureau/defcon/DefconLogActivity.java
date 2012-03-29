package com.advancementbureau.defcon;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DefconLogActivity extends Activity {
	
	String FILENAME = "log.txt";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);
        ActionBar actionBar2 = getActionBar();
        actionBar2.setDisplayHomeAsUpEnabled(true);
        
        InputStream iFile;
		try {
			iFile = openFileInput(FILENAME);
			TextView logText = (TextView) findViewById(R.id.TextView_LogText);
        	String strFile = inputStreamToString(iFile);
        	logText.setText(strFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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