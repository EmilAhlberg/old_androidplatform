package Game;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import Game.*;
import Game.Framework.World;
import Game.Util.Position;
import Game.Util.Rectangle;

/**
 * Created by Emil on 2016-11-20.
 */

public abstract class GameObject extends Picture {

    protected World world;



    public GameObject(Rectangle rect, World world) {
        super(rect);
        this.world = world;
    }


    public void move(double x, double y) {
        rect.setX(x);
        rect.setY(y);
    }

    public void updateObject() {
        update();
    }
    public abstract void update();

}
