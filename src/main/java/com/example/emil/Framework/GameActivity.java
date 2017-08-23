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
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.emil.app.R;

import java.util.List;

import Game.Framework.GameDisplay;
import Game.Framework.GameLoop;
import Game.Framework.LevelCreator;
import Game.Framework.World;
import Game.GameObject;
import Game.Util.WindowSize;

public class GameActivity extends AppCompatActivity {

    private World world;
    private Handler gameLoopThread;
    //private Handler levelCreatorThread;
    private LinearLayout ll;
    private GameLoop gameLoop;
    private LevelCreator levelCreator;
    private GameDisplay display;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        World.Level = getIntent().getExtras().getInt("level");
        handlerSetup();
        setContentView(R.layout.activity_game);

        ll = (LinearLayout) findViewById(R.id.gameActivity);
        //https://www.youtube.com/watch?v=2xYaTGRvpv4
        display = new GameDisplay(this);

        WindowSize.WINDOW_WIDTH = display.getCanvas().getWidth();
        WindowSize.WINDOW_HEIGHT = display.getCanvas().getHeight();

        world = new World(this);
        levelCreator = new LevelCreator(/*levelCreatorThread,*/ this, world);
        levelCreator.setLevel();
        gameLoop = new GameLoop(world, gameLoopThread);
        world.initLevel();
    }


    private void handlerSetup() {
        /*levelCreatorThread = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                world.initLevel();
            }
        };*/
        gameLoopThread = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                ll.setBackground(new BitmapDrawable(getResources(), display.getBitmap()));
            }
        };
    }

    public void startGame() {
        gameLoop.startLoop();
    }

    public void pauseGame() {
        gameLoop.pauseLoop();
    }

    public void setFullscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Gör så att appen kör i landscape-mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public boolean onTouchEvent(MotionEvent event) {
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p); //behvös denna?
        world.decodeTouchEvent(event, p);


        return true;
    }

    public void draw() {
        display.beginDraw(world.getPlayer().getPosition());
        world.drawWorld(display.getCanvas());
        display.endDraw();
    }

    /*public Bitmap getBitmap() {
        return bg;
    }*/

}
