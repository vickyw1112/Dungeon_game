package GameEngine;

import GameEngine.utils.Direction;
import GameEngine.utils.Point;

import java.util.LinkedList;

public abstract class Monster extends StandardObject implements Movable {
    private static final double SPEED = 2;
    static final PathGenerator DEFAULT_PATHGEN = null;

    transient LinkedList<Point> pathToDestination;
    transient PathGenerator pathGenerator;
    private Direction facing;



    public Monster(Point location) {
        super(location);
    }

    @Override
    public void initialize() {
        pathToDestination = new LinkedList<>();
        pathGenerator = getDefaultPathGenerator();
    }

    /**
     * Get current facing
     *
     * @return direction to go
     */
    @Override
    public Direction getFacing() {
        return facing;
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

        if (ret && pathToDestination.pop() != point) {
            // TODO: throw an exception here?
            System.err.println("Inconsistent move: " + this);
            System.exit(1);
        }

        // if location changed, determine new facing
        if (ret) {
            facing = determineFacing(pathToDestination.peek());
        }
        return ret;
    }

    /**
     * Determine current facing by looking at current location
     * and next point
     *
     * @pre assume next point is only one grid away from
     *      current position
     * @param next next point for movement
     * @return facing direction
     */
    private Direction determineFacing(Point next) {
        if (this.location.getX() > next.getX()) {
            return Direction.LEFT;
        } else if (this.location.getX() < next.getX()) {
            return Direction.RIGHT;
        } else if (this.location.getY() < next.getY()) {
            return Direction.DOWN;
        } else if (this.location.getY() < next.getY()) {
            return Direction.UP;
        }
        System.err.format("Cannot determine next location (%s) for :%s\n", next, this);
        System.exit(1);
        return null;
    }

    /**
     * Update monster's path by its strategy
     * also update facing to move towards first point in the path
     *
     * TODO: also update when somewhere on the path gets blocked!
     * @param map
     * @param player
     */
    public void updatePath(Map map, Player player) {
        pathToDestination = pathGenerator.generatePath(map, this, player.location);
        facing = determineFacing(pathToDestination.peek());
    }

    public abstract PathGenerator getDefaultPathGenerator();
}
