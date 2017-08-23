package Game.Framework;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import Game.*;
import com.example.emil.Framework.ActivityConstants;
import com.example.emil.Framework.GameActivity;
import com.example.emil.Framework.GameDisplay;
import com.example.emil.Framework.ActivityHandler;

import java.util.List;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.MotionEvent;

import Game.Movers.Player;


/**
 * Superclass to the World class. This class should contain "general logic", such as start/pause game, canvas stuff etc, while World
 * as strictly as possible implements our "game logic".
 * Created by Emil on 21/08/2017.
 */

public class Game {

    protected GameActivity gameActivity;
    private GameDisplay display;
    protected Player player;

    public Game(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        display = new GameDisplay(gameActivity);
    }

    public void setGameBlocks(ArrayList<GameObject> blockList) {
        display.setGameBlocks(blockList);
    }

    public void pauseGame() {
        gameActivity.pauseGame();
    }

    public void gameOver() {
        pauseGame();
        Intent intent = new Intent(gameActivity.getApplicationContext(), ActivityHandler.class);
        intent.putExtra("level", World.Level);
        intent.putExtra("ActivityConstant", ActivityConstants.GAMEOVER);
        gameActivity.startActivity(intent);
        gameActivity.finish();
    }

    public void Draw(List<GameObject> objects) {
        display.drawWorld(objects);
    }

    //anropas av initlevel i world
    public void startGame() {
        gameActivity.startGame();
    }

    public Bitmap getBitmap() {
        return display.getBitmap();
    }

    public Canvas getCanvas() {
        return display.getCanvas();
    }

    public void decodeTouchEvent(MotionEvent event, Point p) {
        try {
            player.decodeTouchEvent(event, p);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}
