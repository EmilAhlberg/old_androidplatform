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


    private LinearLayout ll;
    private ArrayList<GameObject> newList;
    private Handler s;

    public LevelCreator(View ll, Handler s) {
        this.ll = (LinearLayout) ll;
        this.s = s;
    }

    public void setLevel() {
        newList = new ArrayList<GameObject>();

        new Thread(new Runnable() {
            public void run() {
                for (int y = 0; y <= 780; y += 20) {
                    for (int x = 0; x <= 460; x += 20) {
                        if (y == 0 || x == 0 || y == 780 || x == 460) {
                            newList.add(new EdgeBlock(x, y));
                        }
                        if (y == 600 && x > 100 && x < 400) {
                            newList.add(new GameBlock(x, y));
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
