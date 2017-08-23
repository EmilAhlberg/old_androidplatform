package Game.Framework;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.emil.Framework.ActivityConstants;
import com.example.emil.Framework.ActivityHandler;
import com.example.emil.Framework.GameActivity;
import java.util.ArrayList;
import java.util.List;
import Game.*;
import Game.Movers.Player;

/**
 * Game logic.
 * Created by Emil on 2016-11-25.
 */

public class World {

    protected Player player;
    protected GameActivity gameActivity;
    private ArrayList<GameObject> list;
    public static int Level;

    public World(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    public void initLevel() {
        list = LevelCreator.GameObjectList;
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

    public void updateWorld() {
        //ConcurrentModificationException fix
        List<GameObject> temp = createTempGameObjects();
        for (GameObject gameObject : temp) {
            gameObject.updateObject();
        }

        gameActivity.draw();
    }

    public void drawWorld(Canvas canvas) {
        List<GameObject> temp = createTempGameObjects();
        for (GameObject gameObject : temp) {
            gameObject.getDrawable().draw(canvas);
        }
    }

    public Player getPlayer() {
        return (Player) list.get(0); //farlig?
    }

    public void decodeTouchEvent(MotionEvent event, Point p) {
        try {
            player.decodeTouchEvent(event, p);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void gameOver() {
        pauseGame();
        Intent intent = new Intent(gameActivity.getApplicationContext(), ActivityHandler.class);
        intent.putExtra("level", World.Level);
        intent.putExtra("ActivityConstant", ActivityConstants.GAMEOVER);
        gameActivity.startActivity(intent);
        gameActivity.finish();
    }

    //OBS
    //objects should handle their own death animation? --> these 2 methods can be removed
    public void addObject(GameObject g) {
        list.add(g);
    }

    public void removeObject(GameObject g) {
        list.remove(g);
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public void startGame() {
        gameActivity.startGame();
    }

    public void pauseGame() {
        gameActivity.pauseGame();
    }
}
