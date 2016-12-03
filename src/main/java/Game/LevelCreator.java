package Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.emil.app.Board;
import com.example.emil.app.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Created by Emil on 2016-11-22.
 */

public class LevelCreator {


    private ArrayList<GameObject> newList;
    private Handler s;
    private Player player;
    private Board board;

    public LevelCreator(Handler s, Player player, Board board) {
        this.s = s;
        this.player = player;
        this.board = board;
    }

    public void setLevel() {
        newList = new ArrayList<GameObject>();
        newList.add(player);

        new Thread(new Runnable() {
            public void run() {
//                for (int y = 0; y <= 460; y += 20) {
//                    for (int x = 0; x <= 780; x += 20) {
//                        if (y == 0 || x == 0 || y == 460 || x == 780) {
//                            newList.add(new EdgeBlock(new Position(x, y)));
//                        }
//                        if (y == 300 && x > 200 && x < 580) {
//                            newList.add(new GameBlock(new Position(x, y)));
//                        }
//                    }
//                }

                mapDecoder(newList, getStringMapArray());


            }
        }).start();
        s.obtainMessage().sendToTarget();
    }

    private void mapDecoder(ArrayList<GameObject> newList, String[] mapString) {
        for (int i= 0; i < mapString.length; i++) {
            for (int k = 0; k<mapString[i].length(); k++)
            switch (mapString[i].charAt(k)) {
                case 'b': newList.add(new GameBlock(new Position(k*20,i*20)));
                    break;
                case 'B': newList.add(new EdgeBlock(new Position(k*20,i*20)));
                    break;
                //default: throw new IllegalArgumentException();
            }
        }

    }





    private String[] getStringMapArray() {
        String[] map;
        try {
            map = getStringArrayFromFile(R.raw.level1);
        } catch(Exception e) {
            Log.d("LevelCreator Error", "getStringFromFile Failed");
            map = null;
        }
        return map;
    }

    private String[] convertStreamToStringArray(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ArrayList<String> strings = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.charAt(0) == '§') {         //skalar bort ram, unviker null vid tom rad
                strings.add(line.substring(1));
                //sb.append(line.substring(1)).append("\n");
            }
        }
        reader.close();
        Object[] objects = strings.toArray();
        String[] stringArray = Arrays.copyOf(objects, objects.length, String[].class);
        //return sb.toString();
        return stringArray;
    }

    private String[] getStringArrayFromFile (int id) throws Exception {
        InputStream fin = board.getResources().openRawResource(id);
        //FileInputStream fin = new FileInputStream(fl);
        String[] ret = convertStreamToStringArray(fin);
        fin.close();
        return ret;
    }

    public ArrayList<GameObject> getNewList() {
        return newList;
    }


}
