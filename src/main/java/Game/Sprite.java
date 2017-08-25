package Game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import Game.Util.IDHandler;
import Game.Util.IDs;

/**
 * Created by Emil on 25/08/2017.
 */

public class Sprite {

    private Drawable drawable;
    private Rect rect;
    //private Rectangle rect;

    public Sprite(Rect rect, IDs id) {
        this.rect = rect;
        drawable = IDHandler.getDrawable(id);
    }

    public void animate() {

    }

    public void draw(Canvas canvas) {
        animate();
        drawable.setBounds(rect);
        //drawable.setBounds(0, 100, 200, 900);
        drawable.draw(canvas);
    }

    public void setSpriteRect(Rect rect)  {
        this.rect = rect;
    }
}
