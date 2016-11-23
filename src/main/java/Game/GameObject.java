package Game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Emil on 2016-11-20.
 */

public abstract class GameObject {

    protected double x;
    protected double y;
    protected static Canvas canvas;




    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw();

    public abstract void update();

    public static void setCanvas(Canvas c) {
        canvas = c;
    }


}
