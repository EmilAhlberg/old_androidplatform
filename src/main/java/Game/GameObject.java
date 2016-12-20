package Game;

import android.graphics.Canvas;

import com.example.emil.Framework.GameActivity;

/**
 * Created by Emil on 2016-11-20.
 */

public abstract class GameObject {

    protected static Canvas canvas;
    protected static World world;
    protected static GameActivity gameActivity;
    protected Position position;
    protected int width;
    protected int height;

    public GameObject(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }
    public Position getPosition() {
        return position;
    }

    public void move(double x, double y) {
        position.setX(x);
        position.setY(y);
    }

    public abstract void draw();

    public abstract void update();

    public static void initialize(Canvas c, World w, GameActivity b) {
        canvas = c;
        world = w;
        gameActivity = b;
    }

}
