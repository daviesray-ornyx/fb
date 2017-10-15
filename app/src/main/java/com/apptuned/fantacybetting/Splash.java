package com.apptuned.fantacybetting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {

    private static final String APP_INITIALIZED = "Application Initialized";
    private SharedPreferences spConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        spConfig = getSharedPreferences("com.apptuned.fantacybetting.Config", this.MODE_PRIVATE);
        if(spConfig.getBoolean(APP_INITIALIZED, false)){
            // App initialized. Go to Leagues selection activity
            Intent intent = new Intent(getApplicationContext(), LeagueSelectionActivity.class);
            startActivity(intent);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(intent);
                }
            }, 3000);
        }




    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //TODO show Fantacy Betting in slow

    }

}
