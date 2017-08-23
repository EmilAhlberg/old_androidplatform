package Game.Framework;

import android.os.Handler;

import com.example.emil.Framework.GameActivity;
import com.example.emil.app.R;
import Game.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import Game.InAnimates.Block;
import Game.InAnimates.Fire;
import Game.InAnimates.Goal;
import Game.Movers.Player;
import Game.Movers.StandardCat;
import Game.Movers.Veterinarian;
import Game.Util.Position;

/**
 * Created by Emil on 2016-11-22.
 */

public class LevelCreator {


    public static ArrayList<GameObject> GameObjectList, BlockList;
    private Handler s;
   /* private Player player;*/
    private GameActivity gameActivity;
    private World world;

    public LevelCreator(Handler s, GameActivity gameActivity, World world) {
        this.s = s;
        this.world = world;
    /*    this.player = player;*/
        this.gameActivity = gameActivity;
    }

    public void setLevel() {
        GameObjectList = new ArrayList<GameObject>();
        BlockList = new ArrayList<GameObject>();
        final int newLevel = World.Level;

        new Thread(new Runnable() {
            public void run() {
                createLevel(GameObjectList, BlockList, getLevelArray(newLevel));
                s.obtainMessage().sendToTarget();
            }
        }).start();

    }

    private void createLevel(ArrayList<GameObject> newList, ArrayList<GameObject> blockList, String[] mapString) {
        for (int i = 0; i < mapString.length; i++) {
            for (int k = 0; k < mapString[i].length(); k++) {
                Position p = new Position((k-1) * 20, i * 20); //k-1 vänsterorienterar objekt
                switch (mapString[i].charAt(k)) {
                    case 'b':
                        Block b = new Block(p, 2, world);
                        newList.add(b);
                        blockList.add(b);
                        break;
                    case 'B':
                        Block bl = new Block(p, 1, world);
                        newList.add(bl);
                        blockList.add(bl);
                        break;
                    case 'g':
                        newList.add(new Goal(p, world));
                        break;
                    case 'F':
                        newList.add(new Fire(p, world));
                        break;
                    case 'C':
                        newList.add(new StandardCat(p, world));
                        break;
                    case 'V':
                        p.setY(p.getY()-20);
                        Veterinarian v = new Veterinarian(p, world);
                        newList.add(v);
                        //newList.add(v.getSyringe());
                        break;
                    case 'P':
                        newList.add(0, new Player(p, world)); //added to front of list
                       /* player.move((k-1)*20,(i*20));*/
                        break;
                }
            }
        }
        world.setGameBlocks(blockList);
    }

    private String[] getLevelArray(int level) {
        String[] map;
        try {
            switch (level) {
                case 1:
                    map = getStringArrayFromFile(R.raw.level1);
                    break;
                case 2:
                    map = getStringArrayFromFile(R.raw.level2);
                    break;
                case 3:
                    map = getStringArrayFromFile(R.raw.level3);
                    break;
                default:
                    map = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            map = null;
        }

//        try {
//            map = getStringArrayFromFile(R.raw.level1);
//        } catch(Exception e) {
//            Log.d("LevelCreator Error", "getStringFromFile Failed");
//            map = null;
//        }
        return map;
    }

    private String[] convertStreamToStringArray(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ArrayList<String> strings = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.charAt(4) == '§') {         //skalar bort ram, unviker null vid tom rad
                strings.add(line.substring(4));
                //sb.append(line.substring(1)).append("\n");
            }
        }
        reader.close();
        Object[] objects = strings.toArray();
        String[] stringArray = Arrays.copyOf(objects, objects.length, String[].class);
        //return sb.toString();
        return stringArray;
    }

    private String[] getStringArrayFromFile(int id) throws Exception {
        InputStream fin = gameActivity.getResources().openRawResource(id);
        //FileInputStream fin = new FileInputStream(fl);
        String[] ret = convertStreamToStringArray(fin);
        fin.close();
        return ret;
    }

//    public ArrayList<GameObject> getNewList() {
//        return GameObjectList;
//    }


}
