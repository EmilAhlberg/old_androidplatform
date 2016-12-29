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

import com.example.emil.Framework.ActivityConstants;
import com.example.emil.Framework.GameActivity;
import com.example.emil.Framework.GameDisplay;
import com.example.emil.Framework.GameLoop;
import com.example.emil.Framework.GameOverActivity;
import com.example.emil.Framework.SplashScreen;
import com.example.emil.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil on 2016-11-25.
 */

public class World {

    private GameActivity gameActivity;
   /* private Canvas canvas;
    private Bitmap bitmap;*/
    private int level;
    private LevelCreator levelCreator;
    private Player player;
    private ArrayList<GameObject> list;
    private GameLoop loop;
 /*   private Drawable background;*/
    private GameDisplay display;


    public World(GameActivity gameActivity, int level, Handler gameLoopThread, Handler levelCreatorThread) {
        this.gameActivity = gameActivity;
        this.level = level;

       /* bitmap = Bitmap.createBitmap(800, 480, Bitmap.Config.RGB_565);
        canvas = new Canvas(bitmap);*/
       /* display = new GameDisplay(gameActivity,canvas, bitmap);*/
        display = new GameDisplay(gameActivity);

  /*      GameObject.initialize(canvas, this, gameActivity);*/
       /* GameObject.initialize(this);*/

        /*player = new Player(new Position(0,0));*/
        list = new ArrayList<GameObject>();
        levelCreator = new LevelCreator(levelCreatorThread, gameActivity, this);
        nextLevel();
       /* background = gameActivity.getResources().getDrawable(R.drawable.background);
        background.setBounds(0, 0, 2000, 1000); //(left, top, right, bottom)*/
        loop = new GameLoop(this, gameLoopThread);
        //loop.startLoop();
    }

    public void updateList() {
        list = levelCreator.getNewList();
        loop.startLoop();


    }

    public Bitmap getBitmap() {
        return display.getBitmap();
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
        pauseGame();
        Intent intent = new Intent(gameActivity, SplashScreen.class);
        intent.putExtra("level", getLevel());
        intent.putExtra("activityID", ActivityConstants.GAMEOVER);
        gameActivity.startActivity(intent);
    }

    public void updateWorld() {
       /* Bitmap tempMap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas.setBitmap(tempMap);
        background.draw(canvas);*/
        //canvas.drawColor(Color.WHITE);
        //ConcurrentModificationException fix
        List<GameObject> temp = createTempGameObjects();
        for (GameObject gameObject : temp) {
            gameObject.update();
           /* gameObject.draw();*/
        }
        display.drawWorld(temp);
       /* bitmap = tempMap;*/
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
    public void setPlayer(Player player) {
        this.player = player;
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
        try {
            player.decodeTouchEvent(event, p);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
    public GameActivity getGameActivity() {
        return gameActivity;
    }
    public Canvas getCanvas() {
        return display.getCanvas();
    }
}
