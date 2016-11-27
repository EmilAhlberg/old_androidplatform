package com.example.emil.app;

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

import Game.World;

public class Board extends AppCompatActivity {

    private Canvas canvas;
    private long time = System.currentTimeMillis();
    private World world;
    private Bitmap bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_board);

        LinearLayout ll = (LinearLayout) findViewById(R.id.board);
        bg = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bg);
        world = new World(canvas, ll, this);

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
        double clickX = event.getRawX() * canvas.getWidth() / p.x;
        double clickY = event.getRawY() * canvas.getHeight() / p.y;


        world.decodeTouchEvent(event, p);
        world.setClickPosition(clickX, clickY);


        //Log.d("X : Y", "onTouchEvent: X= " + clickX + " : Y= " + clickY + " Maxsize = " + p.x + " : " + p.y);

        if (time - System.currentTimeMillis() > 30) {
            //cirklar borttagna
            time = System.currentTimeMillis();
        }
        return true;
    }

    public Bitmap getBitmap() {
        return bg;
    }

}
