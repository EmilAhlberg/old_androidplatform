package com.example.emil.Framework;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.emil.app.R;

public class ActivityHandler extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        Log.d("onCreate: ", "1");
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = createIntent(getIntent().getExtras());
                startActivity(intent);
                finish();
            }
        };
        myThread.start();
    }

    private Intent createIntent(Bundle extras) {
        int activity = extras.getInt("ActivityConstant");
        /*try {
            activity = getIntent().getExtras().getInt("activityID");
        } catch(Exception e) {
            e.printStackTrace();
            activity = 0;
        }*/
        Intent intent;
        Log.d("Activity", "" + activity);
        switch (activity) {
            case 2:
                intent = new Intent(getApplicationContext(), StartMenuActivity.class);
                break;
            case 3:
                intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("level", extras.getInt("level"));
                break;
            case 4:
                intent = new Intent(getApplicationContext(), GameOverActivity.class);
                intent.putExtra("level", extras.getInt("level"));
                break;
            case 5:
                intent = new Intent(getApplicationContext(), LevelClearedActivity.class);
                intent.putExtra("level", extras.getInt("level"));
                break;
            case 6:
                intent = new Intent(getApplicationContext(), SelectLevelActivity.class);
                break;
            default:
                intent = new Intent(getApplicationContext(), StartMenuActivity.class);
        }
        return intent;
    }
}
