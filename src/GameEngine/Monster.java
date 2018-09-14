package GameEngine;

import GameEngine.utils.Direction;
import GameEngine.utils.Point;

import java.util.LinkedList;

public abstract class Monster extends StandardObject implements Movable {
    public static final double SPEED = 2;

    private LinkedList<Point> pathToDestination;
    protected PathGenerator pathGenerator;

    public Monster(Point location) {
        super(location);
    }

    @Override
    public void initialize() {
        pathToDestination = new LinkedList<>();
    }

    /**
     * Get current facing by looking at where to go for next grid in
     * pathToDestination
     *
     * @pre assume next point in pathToDestination is only one grid away from
     *      current position
     * @return direction to go
     */
    @Override
    public Direction getFacing() {
        Point next = pathToDestination.peek();
        if (this.location.getX() > next.getX()) {
            return Direction.LEFT;
        } else if (this.location.getX() < next.getX()) {
            return Direction.RIGHT;
        } else if (this.location.getY() < next.getY()) {
            return Direction.DOWN;
        } else if (this.location.getY() < next.getY()) {
            return Direction.UP;
        }

        System.err.println("Error when trying to find direction for Monster: " + this);
        return Direction.UP;
    }

    @Override
    public double getSpeed() {
        return pathToDestination.size() == 0 ? 0 : SPEED;
    }

    /**
     * Pop entry off from pathToDestination when location changed
     *
     * @param point
     *            new location
     * @return whether location changed
     */
    @Override
    public boolean setLocation(Point point) {
        boolean ret = super.setLocation(point);
        if (ret && pathToDestination.pop() != point)
            // TODO: throw an exception here?
            System.err.println("Inconsistent move");
        return ret;
    }

    public void updatePath(Map map, Player player) {
        pathToDestination = pathGenerator.generatePath(map, this, player.location);
    }
}
