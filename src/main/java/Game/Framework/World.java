package Game.Framework;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import com.example.emil.Framework.ActivityConstants;
import com.example.emil.Framework.ActivityHandler;
import com.example.emil.Framework.GameActivity;
import java.util.ArrayList;
import java.util.List;
import Game.*;
import Game.Movers.Collidable;
import Game.Movers.Mover;
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
        player = (Player)list.get(0); //farlig?
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
        long millis = System.currentTimeMillis();
        List<GameObject> temp = createTempGameObjects();
        Log.d("updateWorld ", "createObjects: " + (System.currentTimeMillis() - millis));
        millis = System.currentTimeMillis();
        for (GameObject gameObject : temp) {
            //long millis2 = System.currentTimeMillis();
            gameObject.updateObject();
            //if (gameObject instanceof Mover)
                //Log.d("updateWorld ", "updateObject: " + gameObject.toString() + " : " + (System.currentTimeMillis() - millis2) + "\n//////////////////////////////////////////////////////////////");
        }
        Log.d("updateWorld ", "updateObjects: " + (System.currentTimeMillis() - millis));
        millis = System.currentTimeMillis();

        //HandleAllCollisions(temp);

        gameActivity.draw(temp); //Draws all objects in list + background
        Log.d("updateWorld ", "drawGame: " + (System.currentTimeMillis() - millis));
    }

    //setup for collisionHandler, could break up list param into several lists
    //vill vi slippa foreach-loopen, kan vi i world ha collidable-listan som attribut kanske (dock bara n tidskomplexitet)
    private void HandleAllCollisions(List<GameObject> temp) {
        List<Collidable> cs = new ArrayList<Collidable>();
        for (GameObject g : temp) {
            if (g instanceof Collidable) {
                cs.add((Collidable)g);
            }
        }
        CollisionHandler.CheckCollisions(cs);
    }

    public void drawWorld(Canvas canvas, List<GameObject> temp) {
        for (int i = 0; i < temp.size(); i++) {
            GameObject g = temp.get(i);
            if (Math.sqrt(Math.pow(player.getX() - g.getX(), 2) + Math.pow(player.getY() - g.getY(), 2)) < GameDisplay.WINDOW_HEIGHT + GameDisplay.WINDOW_WIDTH)
                g.draw(canvas);
        }
    }

    public Player getPlayer() {
        return player;
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
