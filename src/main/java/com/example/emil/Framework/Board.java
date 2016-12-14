package com.example.emil.Framework;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.emil.app.R;

import Game.World;

public class Board extends AppCompatActivity {

    private Canvas canvas;
    private World world;
    private Bitmap bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_board);
        int level = getIntent().getExtras().getInt("Level");

        LinearLayout ll = (LinearLayout) findViewById(R.id.board);
        bg = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bg);
        world = new World(canvas, ll, this, level);
    }


    public void setFullscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Gör så att appen kör i landscape-mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public boolean onTouchEvent(MotionEvent event) {
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        world.decodeTouchEvent(event, p);


        return true;
    }

    public Bitmap getBitmap() {
        return bg;
    }

}
