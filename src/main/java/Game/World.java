package Game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.emil.app.Board;
import com.example.emil.app.GameLoop;
import com.example.emil.app.R;

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
    private Player player;
    private ArrayList<GameObject> list;
    private GameLoop loop;
    private Drawable background;


    public World(Canvas canvas, LinearLayout ll, Board board) {
        this.ll = ll;
        this.canvas = canvas;
        this.board = board;
        handlerSetup();

        GameObject.initialize(canvas, this, board);
        player = new Player(new Position(300,200));
        list = new ArrayList<GameObject>();
        levelCreator = new LevelCreator(s, player);
        setLevel();
        loop = new GameLoop(this, h);
        loop.startLoop();

        background = board.getResources().getDrawable(R.drawable.textur);
        background.setBounds(0, 0, 800, 480);
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
        background.draw(canvas);
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

    public void decodeTouchEvent(MotionEvent event, Point p) {
        player.decodeTouchEvent(event, p);
    }
}
