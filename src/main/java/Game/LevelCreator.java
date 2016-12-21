package Game;

import android.os.Handler;
import android.util.Log;

import com.example.emil.Framework.GameActivity;
import com.example.emil.app.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Emil on 2016-11-22.
 */

public class LevelCreator {


    private ArrayList<GameObject> newList;
    private Handler s;
    private Player player;
    private GameActivity gameActivity;

    public LevelCreator(Handler s, Player player, GameActivity gameActivity) {
        this.s = s;
        this.player = player;
        this.gameActivity = gameActivity;
    }

    public void setLevel(int level) {
        newList = new ArrayList<GameObject>();
        final int newLevel = level;
        newList.add(player);

        new Thread(new Runnable() {
            public void run() {
                createLevel(newList, getLevelArray(newLevel));
            }
        }).start();
        s.obtainMessage().sendToTarget();
    }

    private void createLevel(ArrayList<GameObject> newList, String[] mapString) {
        for (int i = 0; i < mapString.length; i++) {
            for (int k = 0; k < mapString[i].length(); k++) {
                Position p = new Position((k-1) * 20, i * 20); //k-1 vänsterorienterar objekt
                switch (mapString[i].charAt(k)) {
                    case 'b':
                        newList.add(new Block(p, 2));
                        break;
                    case 'B':
                        newList.add(new Block(p, 1));
                        break;
                    case 'g':
                        newList.add(new Goal(p));
                        break;
                    case 'F':
                        newList.add(new Fire(p));
                        break;
                    case 'C':
                        newList.add(new Cat(p));
                        break;
                    case 'V':
                        p.setY(p.getY()-20);
                        newList.add(new Vetrinarian(p));
                        //default: throw new IllegalArgumentException();
                }
            }
        }

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
                default:
                    map = null;
            }

        } catch (Exception e) {
            Log.d("LevelCreator Error", "getStringFromFile Failed");
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

    public ArrayList<GameObject> getNewList() {
        return newList;
    }


}
