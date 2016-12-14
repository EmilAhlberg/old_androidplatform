package Game;

/**
 * Created by Emil on 12/10/2016.
 */
public abstract class Hazard extends GameObject{

    public Hazard(Position position, int width, int height) {
        super(position, width, height);
    }

    protected abstract void affectPlayer();
}
