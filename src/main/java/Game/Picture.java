package Game;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import Game.Util.IDHandler;
import Game.Util.IDs;
import Game.Util.Position;

/**
 * Created by Emil on 23/08/2017.
 */

public abstract class Picture {

    public Sprite sprite;
    protected Rect rect;
    protected IDs id;

    public Picture(Rect rect)
    {
        id = IDHandler.getID(this.getClass());
        this.rect = rect;
        sprite = new Sprite(rect, id);
    }

    public IDs getID() {
        return id;
    }

    public double getX() {
        return rect.left;
    }

    public double getY() {
        return rect.top;
    }

    public int getHeight() {
        return rect.height();
    }

    public int getWidth() {
        return rect.height();
    }

    public Rect getRect() {
        return rect;
    }



    public void draw(Canvas canvas) {
        sprite.setSpriteRect(rect);
        sprite.draw(canvas);
    }

}
