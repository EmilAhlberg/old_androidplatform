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

    public Rect getRect() {
        return rect;
    }

    public void draw(Canvas canvas) {
        sprite.setSpriteRect(rect);
        sprite.draw(canvas);
    }

}
