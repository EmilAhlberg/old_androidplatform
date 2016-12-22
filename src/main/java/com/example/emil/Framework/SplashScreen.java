package com.example.emil.Framework;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.emil.app.R;

public class SplashScreen extends AppCompatActivity implements ActivityConstants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = createIntent();
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    private Intent createIntent() {
        int activity;
        try {
            activity = getIntent().getExtras().getInt("activityID");
        } catch(Exception e) {
            e.printStackTrace();
            activity = 0;
        }
        Intent intent;
        switch (activity) {
            case 2:
                intent = new Intent(getApplicationContext(), StartMenuActivity.class);
                break;
            case 3:
                intent = new Intent(getApplicationContext(), GameActivity.class);
                break;
            case 4:
                intent = new Intent(getApplicationContext(), GameOverActivity.class);
                intent.putExtra("level", getIntent().getExtras().getInt("level"));
                break;
            case 5:
                intent = new Intent(getApplicationContext(), LevelClearedActivity.class);
                intent.putExtra("level", getIntent().getExtras().getInt("level"));
                break;
            default:
                intent = new Intent(getApplicationContext(), StartMenuActivity.class);
        }
        return intent;
    }
}
