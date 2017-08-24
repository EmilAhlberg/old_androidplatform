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

import Game.Framework.GameDisplay;
import Game.Framework.GameLoop;
import Game.Framework.LevelCreator;
import Game.Framework.World;
import Game.Movers.Player;
import Game.Util.Position;

public class GameActivity extends AppActivity {

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

    public boolean onTouchEvent(MotionEvent event) {
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p); //behvös denna?
        world.decodeTouchEvent(event, p);


        return true;
    }

    public void draw() {
        Player player = world.getPlayer();
        display.beginDraw(player.getX(), player.getY());
        world.drawWorld(display.getCanvas());
        display.endDraw();
    }

    /*public Bitmap getBitmap() {
        return bg;
    }*/

}
