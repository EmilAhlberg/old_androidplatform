package Game.Util;

/**
 * Created by Emil on 22/08/2017.
 */

public class Rectangle {

    private Position p;
    private int width;
    private int height;

    public Rectangle(Position p, int width, int height) {
        this.p = p;
        this.width = width;
        this.height = height;
    }

    public Position getPosition() {
        return p;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
