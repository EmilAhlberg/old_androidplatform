package com.example.emil.app;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Emil on 2016-11-04.
 */

public class GameLoop {

    private Board board;
    private final int timeLimit = 30;
    private Handler handler;

    private double elapsedTime;

    public GameLoop(Board board, Handler handler) {

        this.board = board;
        this.handler = handler;
    }

    public void startLoop() {

        new Thread(new Runnable() {
            public void run() {
                double currentTime = System.currentTimeMillis();
                double newTime = 0;
                while (true) {
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
        board.update();
        Message m = handler.obtainMessage();
        m.sendToTarget();
    }


}
