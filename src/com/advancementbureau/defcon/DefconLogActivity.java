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
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.advancementbureau.defconwork.R;

public class DefconLogActivity extends SuperDefconActivity {
	
	String strFile = "You never set a Defense Condition";
	String FILENAME = "log.txt";
	static final int SAVE_LOCATION_ID = 0;
	static final int DELETE_ID = 1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);
        if (Build.VERSION.SDK_INT >= 11) {
	        ActionBar actionBar2 = getActionBar();
	        actionBar2.setDisplayHomeAsUpEnabled(true);
        }
        mGameSettings = getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        
        //Sets the Scrollview at bottom of txt file
        final ScrollView logScroller = (ScrollView) findViewById(R.id.ScrollViewLog);
        logScroller.post(new Runnable() { 
            public void run() { 
                logScroller.fullScroll(ScrollView.FOCUS_DOWN); 
            } 
        }); 
        
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
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.logoptions, menu);
    	menu.findItem(R.id.clear_log_menu_item);
    	menu.findItem(R.id.save_log_menu_item);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	if (item.getItemId() == android.R.id.home) {
			Intent intent2 = new Intent(this, DefconActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2); }
    	if (item.getItemId() == R.id.clear_log_menu_item) {
			showDialog(DELETE_ID); }
    	if (item.getItemId() == R.id.save_log_menu_item) {
    		showDialog(SAVE_LOCATION_ID);
    	}
    	return true;
    }
    
    @Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case SAVE_LOCATION_ID:
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.save_dialog, (ViewGroup) findViewById(R.id.root));
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setView(layout);
				builder.setNegativeButton(R.string.nevermind, new DialogInterface.OnClickListener() {
					@SuppressWarnings("deprecation")
					public void onClick(DialogInterface dialog,
							int whichButton) {
						// We forcefully dismiss and remove the Dialog, so
						// it
						// cannot be used again (no cached info)
						DefconLogActivity.this.removeDialog(SAVE_LOCATION_ID);
					}
				});
				builder.setPositiveButton(R.string.doit, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
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
					}
				});
				AlertDialog saveDialog = builder.create();
				return saveDialog;
			case DELETE_ID:
				LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout1 = inflater1.inflate(R.layout.save_dialog, (ViewGroup) findViewById(R.id.root));
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
				builder1.setView(layout1);
				builder1.setNegativeButton(R.string.nevermind, new DialogInterface.OnClickListener() {
					@SuppressWarnings("deprecation")
					public void onClick(DialogInterface dialog,
							int whichButton) {
						// We forcefully dismiss and remove the Dialog, so
						// it
						// cannot be used again (no cached info)
						DefconLogActivity.this.removeDialog(DELETE_ID);
					}
				});
				builder1.setPositiveButton(R.string.doit, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String insertString = "";
				    	FileOutputStream fos;
						try {
							fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
							fos.write(insertString.getBytes());
							fos.close();
						} catch (FileNotFoundException e2) {
							e2.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
				    	
				    	Editor editor = mGameSettings.edit();
				    	editor.putInt(LAST_CONDITION, 0);
				    	editor.commit();
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
				});
				AlertDialog deleteDialog = builder1.create();
				return deleteDialog;
			}
			return null;
		}
		
			
    
    public void clearLog() throws IOException {
    	String insertString = "";
    	FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
    	fos.write(insertString.getBytes());
    	fos.close();
    	Editor editor = mGameSettings.edit();
    	editor.putInt(LAST_CONDITION, 0);
    	editor.commit();
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
}