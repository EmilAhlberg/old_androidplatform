package com.example.emil.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import java.util.ArrayList;

import Game.Circle;
import Game.GameBlock;
import Game.GameObject;
import Game.LevelCreator;

public class Board extends AppCompatActivity {

    private Canvas canvas;
    private GameLoop loop;
    private Paint paint;
    private Bitmap bg;
    private LinearLayout ll;
    private Handler h;
    private ArrayList<GameObject> list;
    private int clickCap = 10;
    private LevelCreator levelCreator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Handler h = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {

                ll.setBackground(new BitmapDrawable(getResources(), bg));

            }
        };
        Handler s = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                list = levelCreator.getNewList();
            }
        };

        bg = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bg);
        GameObject.setCanvas(canvas);

        ll = (LinearLayout) findViewById(R.id.board);
        loop = new GameLoop(this, h);
        loop.startLoop();



        list = new ArrayList<GameObject>();
        levelCreator = new LevelCreator(ll, s);
        setLevel();

        list.add(new Circle(100,100,50));
    }

    public boolean onTouchEvent(MotionEvent event) {
        double clickX = event.getRawX()-180;
        double clickY = event.getRawY()-600;
        if (clickCap<0) {
            list.add(new Circle((int)clickX,(int)clickY,40));
            clickCap = 10;
        }
        return true;

    }

    public void update() {
        clickCap--;
        canvas.drawColor(Color.WHITE);
        for (GameObject gameObject : list) {
            gameObject.update();
            gameObject.draw();
        }
    }

    public void setLevel() {
        levelCreator.setLevel();


        //AsyncTaskCompat.executeParallel(levelCreator,coords[0],coords[1],coords[2],coords[3]);
        //levelCreator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,coords[0],coords[1],coords[2],coords[3] );
    }
}
