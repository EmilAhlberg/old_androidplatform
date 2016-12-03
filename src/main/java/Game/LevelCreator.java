package Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.emil.app.Board;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
                for (int y = 0; y <= 460; y += 20) {
                    for (int x = 0; x <= 780; x += 20) {
                        if (y == 0 || x == 0 || y == 460 || x == 780) {
                            newList.add(new EdgeBlock(new Position(x, y)));
                        }
                        if (y == 300 && x > 200 && x < 580) {
                            newList.add(new GameBlock(new Position(x, y)));
                        }
                    }
                }
                //setNewLevel(1);


            }
        }).start();
        s.obtainMessage().sendToTarget();
    }

    public ArrayList<GameObject> getNewList() {
        return newList;
    }

    public String[] setNewLevel(int level) {
        List<String> levelList = new ArrayList<String>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(board.getResources().getAssets().open("level2.xml")));
            String mLine = reader.readLine();
            while(mLine != null) {
                levelList.add(mLine);
                mLine=reader.readLine();
            }

        } catch(IOException e) {
            Log.d("LevelCreator error", "FileNotFound?");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    Log.d("LevelCreator error", "ReaderError?");
                }
            }
        }
//        File file = new File("file:///android_asset/level1.txt");
//
//        InputStream inStream = new FileInputStream();

        for (String s : levelList) {
            System.out.println(s);
        }
        return (String[]) levelList.toArray();

    }


}
