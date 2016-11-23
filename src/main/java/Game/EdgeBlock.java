package Game;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Emil on 2016-11-22.
 */

public class EdgeBlock extends Block {
    protected static Paint paint = new Paint();

    public EdgeBlock(int x, int y) {
        super(x,y);
        paint.setColor(Color.BLACK);
    }

    @Override
    public void draw() {
        canvas.drawRect((float)x,(float)y,(float)(x+18),(float)(y+18),paint);

    }

    @Override
    public void update() {

    }
}
