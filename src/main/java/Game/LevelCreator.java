package Game;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Emil on 2016-11-22.
 */

public class LevelCreator {


    private ArrayList<GameObject> newList;
    private Handler s;
    private Player player;

    public LevelCreator(Handler s, Player player) {
        this.s = s;
        this.player = player;
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


            }
        }).start();
        s.obtainMessage().sendToTarget();
    }

    public ArrayList<GameObject> getNewList() {
        return newList;
    }


}
