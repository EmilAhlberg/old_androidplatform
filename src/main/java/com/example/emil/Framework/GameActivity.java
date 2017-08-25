package com.example.emil.Framework;

import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.emil.app.R;

import Game.Framework.GameDisplay;
import Game.Framework.GameLoop;
import Game.Framework.LevelCreator;
import Game.Framework.World;
import Game.Movers.Player;
import Game.Util.IDHandler;
import Game.Util.IDs;

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
        loadDrawables();
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

    private void loadDrawables() {
        if (IDHandler.drawables[0] == null) { //makes sure images are only loaded once (during first gameActivity onCreate)
            IDHandler.drawables[IDs.DEFAULT.ordinal()] = getResources().getDrawable(R.drawable.startscreen); //default
            IDHandler.drawables[IDs.PLAYER.ordinal()] = getResources().getDrawable(R.drawable.player);
            IDHandler.drawables[IDs.BLOCK.ordinal()] = getResources().getDrawable(R.drawable.block1);
            IDHandler.drawables[IDs.FIRE.ordinal()] = getResources().getDrawable(R.drawable.hot_fire);
            IDHandler.drawables[IDs.GOAL.ordinal()] = getResources().getDrawable(R.drawable.loading);
            IDHandler.drawables[IDs.CAT.ordinal()] = getResources().getDrawable(R.drawable.cat);
            IDHandler.drawables[IDs.VETERINARIAN.ordinal()] = getResources().getDrawable(R.drawable.vet);
            IDHandler.drawables[IDs.SYRINGE.ordinal()] = getResources().getDrawable(R.drawable.syringe);
        }
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
        world.draw(display.getCanvas());
        display.endDraw();
    }
}
