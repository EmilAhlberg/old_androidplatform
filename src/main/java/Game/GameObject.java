package Game;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import Game.*;
import Game.Framework.World;
import Game.Util.IDs;
import Game.Util.Position;


/**
 * Created by Emil on 2016-11-20.
 */

public abstract class GameObject extends Picture {

    protected World world;

    public GameObject(Rect rect, World world) {
        super(rect);
        this.world = world;
    }

    /**
     * Moves the gameObject to the coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public void move(double x, double y) {
        rect.offset((int)(x - rect.left), (int)(y - rect.top));
    }

    public void updateObject() {
        update();
    }
    public abstract void update();

}
