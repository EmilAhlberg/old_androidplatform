package Game;

import android.graphics.Canvas;

import com.example.emil.Framework.GameActivity;
import com.example.emil.Framework.GameDisplay;

import java.util.List;
import java.util.ArrayList;
import android.graphics.Bitmap;


/**
 * Created by Emil on 21/08/2017.
 */

public class Game {

    protected GameActivity gameActivity;
    private GameDisplay display;

    public Game(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        display = new GameDisplay(gameActivity);
    }

    public void createBackground(ArrayList<GameObject> blockList) {
        display.createBackground(blockList);
    }

    public void pauseGame() {
        gameActivity.pauseGame();
    }

    public void Draw(List<GameObject> objects) {
        display.drawWorld(objects);
    }

    public void startGame() {
        gameActivity.startGame();
    }

    public Bitmap getBitmap() {
        return display.getBitmap();
    }

    public void nextLevel() {
        gameActivity.setLevel();
    }

    public Canvas getCanvas() {
        return display.getCanvas();
    }
}
