package com.example.emil.Framework;

import android.content.pm.ActivityInfo;
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

import Game.Framework.LevelCreator;
import Game.Framework.World;

public class GameActivity extends AppCompatActivity {

    private World world;
    private Handler gameLoopThread;
    //private Handler levelCreatorThread;
    private LinearLayout ll;
    private GameLoop gameLoop;
    private LevelCreator levelCreator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        World.Level = getIntent().getExtras().getInt("level");
        handlerSetup();
        setContentView(R.layout.activity_game);

        ll = (LinearLayout) findViewById(R.id.gameActivity);
        //https://www.youtube.com/watch?v=2xYaTGRvpv4
        world = new World(this);
        levelCreator = new LevelCreator(/*levelCreatorThread,*/ this, world);
        levelCreator.setLevel();
        gameLoop = new GameLoop(world, gameLoopThread);
        Log.d("GameActivity: ", "före");
        world.initLevel();
        Log.d("GameActivity: ", "efter");
    }

    private void handlerSetup() {
        /*levelCreatorThread = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                world.initLevel();
            }
        };*/
        gameLoopThread = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                ll.setBackground(new BitmapDrawable(getResources(), world.getBitmap()));
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

    /*public Bitmap getBitmap() {
        return bg;
    }*/

}
