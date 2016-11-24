package Game;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Emil on 2016-11-24.
 */

public class Player extends Mover {

    private Paint paint = new Paint();
    private final static int PLAYER_SPEED = 2;
    private double clickX =  y;
    private double clickY=  x;



    public Player(int x, int y) {
        super(x,y, PLAYER_SPEED, 0);
        paint.setColor(Color.GREEN);
    }



    @Override
    public void draw() {
        canvas.drawCircle((int)x, (int)y, 20, paint);

    }

    @Override
    public void update() {
        updatePosition();

    }

    public void updateClickPosition(double cx, double cy) {
        clickX =cx;
        clickY = cy;
    }


    @Override
    protected void updatePosition() {
        double dx = clickX - x;
        double dy = clickY - y;

        if (Math.abs(dx) <=2 &&(Math.abs(dy)<=2)) {

        } else {
            if (dx > 0) {
                move(x+speed, y);
            } else if (dx<0) {
                move(x-speed, y);
            }
            if (dy > 0) {
                move(x, y+speed);
            } else if (dy<0) {
                move(x, y-speed);
            }
        }







    }

    @Override
    protected boolean edgeCollision() {
        return false;
    }
}
