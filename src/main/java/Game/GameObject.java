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

    public void move(double x, double y) {
        //borde kunna lösas enklare
        double dx = rect.left - x;
        double dy = rect.top -y;
        rect.left= (int)x;
        rect.right = rect.right - (int)dx;
        rect.top = (int)(y);
        rect.bottom = rect.bottom - (int)dy;
    }

    public void updateObject() {
        update();
    }
    public abstract void update();

}
