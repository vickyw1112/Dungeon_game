package GameEngine.utils;

import java.io.Serializable;

public class Point implements Serializable {
    private int x;
    private int y;

    /**
     * Constructor
     * 
     * @param x x
     * @param y y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor
     * 
     * @param p
     *            existing point
     */
    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point))
            return false;
        return this.x == ((Point) obj).x && this.y == ((Point) obj).y;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x) ^ Integer.hashCode(y);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Point clone() {
        return new Point(this);
    }
}
