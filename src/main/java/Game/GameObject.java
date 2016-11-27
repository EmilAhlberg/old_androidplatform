package Game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.emil.app.Board;

/**
 * Created by Emil on 2016-11-20.
 */

public abstract class GameObject {

    protected Position position;
    protected static Canvas canvas;
    protected static World world;
    protected static Board board;

    public GameObject(Position position) {
       this.position = position;
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

    public static void initialize(Canvas c, World w, Board b) {
        canvas = c;
        world = w;
        board = b;
    }

}
