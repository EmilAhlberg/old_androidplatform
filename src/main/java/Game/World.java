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
    private Player player = new Player(new Position(300,300));
    private ArrayList<GameObject> list;
    private GameLoop loop;


    public World(Canvas canvas, LinearLayout ll, Board board) {
        this.ll = ll;
        this.canvas = canvas;
        this.board = board;
        handlerSetup();

        GameObject.initialize(canvas, this, board);
        list = new ArrayList<GameObject>();
        levelCreator = new LevelCreator(s, player);
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

                ll.setBackground(new BitmapDrawable(board.getResources(), board.getBitmap()));

            }
        };

    }

    /**
     * Returns a temporary list instance of Mover objects.
     * @return tempMovers
     */
    public ArrayList<Mover> createTempMovers() {
        ArrayList<GameObject> tempGameObjects = createTempGameObjects();
        ArrayList<Mover> tempMovers = new ArrayList<Mover>();

        for (GameObject gameObject : list) {
            if (gameObject instanceof Mover) {
                tempMovers.add((Mover) gameObject);
            }
        }
        return tempMovers;
    }

    /**
     * Returns a temporary list instance of GameObjects.
     * @return tempGameObjects
     */
    public ArrayList<GameObject> createTempGameObjects() {
        ArrayList<GameObject> tempGameObjects = new ArrayList<GameObject>();
        tempGameObjects.addAll(list);
        return tempGameObjects;
    }

    public void updateWorld() {
        canvas.drawColor(Color.WHITE);
        //ConcurrentModificationException fix
        List<GameObject> temp = createTempGameObjects();
        for (GameObject gameObject : temp) {
            gameObject.update();
            gameObject.draw();
        }
    }

    //filthy set-methods
    public void setLevel() {
        levelCreator.setLevel();
    }

    public void setClickPosition(double clickX, double clickY) {
        player.updateClickPosition(clickX, clickY);
    }

    public void decodeTouchEvent(MotionEvent event, Point p) {
        player.decodeTouchEvent(event, p);
    }
    
}
