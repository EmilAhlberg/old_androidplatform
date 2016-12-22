package com.example.emil.Framework;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.emil.app.R;

import Game.World;

public class GameActivity extends AppCompatActivity {

    private Canvas canvas;
    private World world;
    private Bitmap bg;
    private Handler gameLoopThread;
    private Handler levelCreatorThread;
    private LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_game);
        int level = getIntent().getExtras().getInt("level");
        handlerSetup();

        ll = (LinearLayout) findViewById(R.id.gameActivity);
        bg = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bg);
        world = new World(canvas, this, level, gameLoopThread,levelCreatorThread);
    }

    private void handlerSetup() {
        levelCreatorThread = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                world.updateList();
            }
        };
        gameLoopThread = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                ll.setBackground(new BitmapDrawable(getResources(), world.getFinalBitmap()));
            }
        };
    }


    public void setFullscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide(); //denna är farlig
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
