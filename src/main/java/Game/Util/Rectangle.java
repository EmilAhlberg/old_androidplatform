package Game.Util;

/**
 * Created by Emil on 22/08/2017.
 */

public class Rectangle {

    private double x, y;
    private int width;
    private int height;

    public Rectangle(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean intersects(Rectangle r)
       {
           return r.width > 0 && r.height > 0 && width > 0 && height > 0 && r.x < x + width && r.x + r.width > x && r.y < y + height && r.y + r.height > y;
        }
}
