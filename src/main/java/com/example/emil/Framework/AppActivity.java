package com.example.emil.Framework;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Emil on 8/23/2017.
 */

public class AppActivity extends AppCompatActivity{

    protected void setFullscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Gör så att appen kör i landscape-mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
