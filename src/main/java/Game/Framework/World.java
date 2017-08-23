package Game.Framework;

import com.example.emil.Framework.GameActivity;

import java.util.ArrayList;
import java.util.List;
import Game.*;
import Game.Framework.Game;
import Game.Framework.LevelCreator;
import Game.Movers.Player;

/**
 * Game logic.
 * Created by Emil on 2016-11-25.
 */

public class World extends Game {


    private ArrayList<GameObject> list;
    public static int Level;

    public World(GameActivity gameActivity) {
        super(gameActivity);
    }

    public void initLevel() {
        list = LevelCreator.GameObjectList;
        setGameBlocks(LevelCreator.BlockList);
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
        Draw(temp);
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

}
