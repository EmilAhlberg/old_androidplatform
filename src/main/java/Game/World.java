package Game;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.emil.Framework.GameActivity;
import com.example.emil.Framework.GameLoop;
import com.example.emil.Framework.GameOverActivity;
import com.example.emil.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil on 2016-11-25.
 */

public class World {

    private GameActivity gameActivity;
    private Canvas canvas;
    private Canvas finalCanvas;
    private Bitmap finalBitmap;
    private int level;
    private LevelCreator levelCreator;
    private Player player;
    private ArrayList<GameObject> list;
    private GameLoop loop;
    private Drawable background;


    public World(Canvas canvas, GameActivity gameActivity, int level, Handler gameLoopThread, Handler levelCreatorThread) {
        this.canvas = canvas;
        this.gameActivity = gameActivity;
        this.level = level;
        Bitmap temp = gameActivity.getBitmap();
        finalBitmap = Bitmap.createBitmap(temp.getWidth(), temp.getHeight(), Bitmap.Config.ARGB_8888);
        finalCanvas = new Canvas(finalBitmap);

        GameObject.initialize(canvas, this, gameActivity);
        player = new Player(new Position(100,800));
        list = new ArrayList<GameObject>();
        levelCreator = new LevelCreator(levelCreatorThread, player, gameActivity);
        nextLevel();
        background = gameActivity.getResources().getDrawable(R.drawable.background);
        background.setBounds(0, 0, 2000, 1000); //(left, top, right, bottom)
        loop = new GameLoop(this, gameLoopThread);
        loop.startLoop();


    }

    public void updateList() {
        list = levelCreator.getNewList();
    }

    public Bitmap getFinalBitmap() {
        return finalBitmap;
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
    public void gameOver() {
        canvas.drawColor(Color.BLACK);
        pauseGame();
        Intent intent = new Intent(gameActivity, GameOverActivity.class);
        intent.putExtra("Level", getLevel());
        gameActivity.startActivity(intent);
    }

    public void updateWorld() {
        background.draw(canvas);
        //canvas.drawColor(Color.WHITE);
        //ConcurrentModificationException fix
        List<GameObject> temp = createTempGameObjects();
        for (GameObject gameObject : temp) {
            gameObject.update();
            gameObject.draw();
        }
        finalCanvas.drawBitmap(gameActivity.getBitmap(), 0, 0, new Paint());
    }

    public void addObject(GameObject g) {
        list.add(g);
    }

    public void removeObject(GameObject g) {
        list.remove(g);
    }

    public void pauseGame() {
        loop.pause();
    }
    public void startGame() {
        loop.startLoop();
    }

    //filthy set-methods
    public void nextLevel() {
        levelCreator.setLevel(level);
    }
    public int getLevel() {
        return level;
    }

    public void decodeTouchEvent(MotionEvent event, Point p) {
        player.decodeTouchEvent(event, p);
    }
}
