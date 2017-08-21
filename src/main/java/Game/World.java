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

public class World extends Game {


    private ArrayList<GameObject> list;
    public static int LEVEL;
    protected Player player;

    public World(GameActivity gameActivity, int level) {
        super(gameActivity);
        LEVEL = level; //needed?
        list = new ArrayList<GameObject>();

    }

    public void initLevel() {
        list = LevelCreator.newList;
        player = (Player)list.get(0);
        startGame();
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
        intent.putExtra("level", LEVEL);
        intent.putExtra("activityID", ActivityConstants.GAMEOVER);
        gameActivity.startActivity(intent);
    }

    public void updateWorld() {
        //ConcurrentModificationException fix
        List<GameObject> temp = createTempGameObjects();
        for (GameObject gameObject : temp) {
            gameObject.update();
        }
        Draw(temp);
    }
    //OBS
    //objects should handle their own death animation --> these 2 methods can be removed
    public void addObject(GameObject g) {
        list.add(g);
    }

    public void removeObject(GameObject g) {
        list.remove(g);
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

}
