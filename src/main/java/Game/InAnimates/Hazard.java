package Game.InAnimates;

import Game.*;
import Game.Framework.World;
import Game.Util.Position;
import Game.Util.Rectangle;

/**
 * Created by Emil on 12/10/2016.
 */
public abstract class Hazard extends GameObject{

    public Hazard(Position position, int width, int height, World world) {
        super(new Rectangle(position, width, height), world);
    }

    public abstract void affectPlayer();
}
