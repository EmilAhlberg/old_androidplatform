package Game.Framework;

import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;

import Game.Framework.World;

/**
 * Created by Emil on 2016-11-04.
 */

public class GameLoop {

    private World world;
    private final int timeLimit = 15;
    private Handler handler;
    private boolean running;

    //private double elapsedTime;

    public GameLoop(World world, Handler handler) {
        this.world = world;
        this.handler = handler;
    }

    public void startLoop() {
        running = true;
        new Thread(new Runnable() {
            public void run() {
                double currentTime = System.currentTimeMillis();
                double newTime = 0;
                while (running) {
                    if (newTime - currentTime > timeLimit) {
                        updateLoop();
                        currentTime = newTime;
                    }
                    newTime = System.currentTimeMillis();
                }
            }
        }).start();
    }

    private void updateLoop() {
        //long millis = System.currentTimeMillis();
        world.updateWorld();
        //Log.d("updateLoop", "Update world: " + (System.currentTimeMillis() - millis));
        //millis = System.currentTimeMillis();
        Message m = handler.obtainMessage();
        m.sendToTarget();
        //Log.d("updateLoop", "Handle messages: " + (System.currentTimeMillis() - millis));
    }

    public void pauseLoop() {
        running = false;
    }


}
