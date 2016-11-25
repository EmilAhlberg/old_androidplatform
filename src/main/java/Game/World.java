package Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.emil.app.Board;
import com.example.emil.app.GameLoop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil on 2016-11-25.
 */

public class World {

    private Board board;
    private LinearLayout ll;
    private Canvas canvas;
    private Handler h;
    private Handler s;
    private LevelCreator levelCreator;
    private Player player = new Player(300, 300);
    private ArrayList<GameObject> list;
    private GameLoop loop;
    private Bitmap bg;


    public World(Canvas canvas, LinearLayout ll, Board board, Bitmap bg) {
        this.bg = bg;
        this.ll = ll;
        this.canvas = canvas;
        this.board = board;
        handlerSetup();

        GameObject.setCanvas(canvas);
        list = new ArrayList<GameObject>();
        levelCreator = new LevelCreator(ll, s, player);
        setLevel();
        loop = new GameLoop(this, h);
        loop.startLoop();

    }
    private void handlerSetup() {

        s = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                list = levelCreator.getNewList();
            }
        };
        h = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {

                ll.setBackground(new BitmapDrawable(board.getResources(), bg));

            }
        };

    }

    public void updateWorld() {
        canvas.drawColor(Color.WHITE);
        //Fixar ConcurrentModificationException (tror jag), låt stå pls
        //////////////////////////////////////////////// number of '/' is too damn high! /Jimmy McMillan
        List<GameObject> temp = new ArrayList<GameObject>();
        temp.addAll(list);
        ////////////////////////////////////////////////
        for (GameObject gameObject : temp) {
            gameObject.update();
            gameObject.draw();
        }
    }

    public void setLevel() {
        levelCreator.setLevel();
    }

    public Player getPlayer() {
        return (Player)list.get(0);
    }


}
