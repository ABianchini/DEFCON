package com.advancementbureau.defcon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class DefconSplashActivity extends Activity {
	
	public static final String FIRST_BOOT = "boot";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences bootPref = getSharedPreferences(FIRST_BOOT, MODE_PRIVATE);
        SharedPreferences.Editor editor = bootPref.edit();
        if (bootPref.getBoolean(FIRST_BOOT, true)) {
        	Animate();
        	editor.putBoolean("boot", false);
            editor.commit();
        } else {
        	startActivity(new Intent(DefconSplashActivity.this, DefconActivity.class));
    		DefconSplashActivity.this.finish();
        }
    }
    
    private void Animate() {
    	TextView FRC = (TextView) findViewById(R.id.TextView_DefconTitle);
    	Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
    	FRC.startAnimation(fade1);
    	ImageView logo = (ImageView) findViewById(R.id.ImageView_DefconLogo);
    	Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
    	logo.startAnimation(fade2);
    	fade2.setAnimationListener(new AnimationListener() {
        	public void onAnimationEnd(Animation animation) {
        		try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		startActivity(new Intent(DefconSplashActivity.this, DefconActivity.class));
        		DefconSplashActivity.this.finish();
        	}
        	public void onAnimationRepeat(Animation animation) {
        	}
        	public void onAnimationStart(Animation animation) {
        	}
        });
    }
}