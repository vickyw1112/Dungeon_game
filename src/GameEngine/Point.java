package GameEngine;

public class Point {
	private int x;
	private int y;

    /**
     * Constructor
     * @param x
     * @param y
     */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

    /**
     * Copy constructor
     * @param p existing point
     */
	public Point(Point p){
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
	
	public void translate(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
