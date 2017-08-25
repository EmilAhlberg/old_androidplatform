package Game.InAnimates;

import android.graphics.Rect;

import Game.*;
import Game.Framework.World;
import Game.Util.Position;

/**
 * Created by Emil on 12/10/2016.
 */
public abstract class Hazard extends GameObject{

    public Hazard(Position position, int width, int height, World world) {
        super(new Rect((int)position.getX(), (int)position.getY(), (int)position.getX() + width, (int)position.getY() +height), world);
    }

    public abstract void affectPlayer();
}
