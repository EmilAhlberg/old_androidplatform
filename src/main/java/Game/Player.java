package Game;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Emil on 2016-11-24.
 */

public class Player extends Mover {

    private Paint paint = new Paint();
    //huh? är det inte onödigt att ha final här när värdet som används ändå är mutable? Bättre att använda denna variabeln eller ändra speed i Mover till final
    private final static int PLAYER_SPEED = 8;
    private double clickX =  y;
    private double clickY =  x;



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
        clickX = cx;
        clickY = cy;
    }


    @Override
    protected void updatePosition() {
        double dx = clickX - x;
        double dy = clickY - y;

        if (Math.abs(dx) <= speed &&(Math.abs(dy)<= speed)) {

        } else {
            double xMov = speed * dx / (Math.abs(dx)+Math.abs(dy));
            double yMov = speed * dy / (Math.abs(dx)+Math.abs(dy));

            //Log.d("WAAAA", "xMov= " + xMov + " : yMov= " + yMov + " : xMov + yMov = " + (Math.abs(xMov) + Math.abs(yMov)));

            move(x+xMov, y+yMov);

            /*if (dx > 0) {
                move(x+speed, y);
            } else if (dx<0) {
                move(x-speed, y);
            }
            if (dy > 0) {
                move(x, y+speed);
            } else if (dy<0) {
                move(x, y-speed);
            }*/
        }







    }

    @Override
    protected boolean edgeCollision() {
        return false;
    }
}
