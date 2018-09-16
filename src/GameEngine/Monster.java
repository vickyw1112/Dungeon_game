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

    public LinkedList<Point> getPath() {
        return new LinkedList<>(pathToDestination);
    }

    /**
     * Monster will block movement if collision occurs
     * @return true
     */
    @Override
    public boolean isBlocking() {
        return true;
    }


    /**
     * Point with Monster on it should be considered as
     * a candidate point for path generation
     *
     * @return true
     */
    @Override
    public boolean canMoveThrough() {
        return true;
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

        if (ret && !pathToDestination.pop().equals(point)) {
            System.err.println("Inconsistent move: " + this);
            System.exit(1);
        }

        // if location changed, determine new facing
        if (ret && pathToDestination.size() > 0) {
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
        } else if (this.location.getY() > next.getY()) {
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
        pathToDestination = pathGenerator.generatePath(map, this, player);
        if(pathToDestination.peek() != null)
            facing = determineFacing(pathToDestination.peek());
    }

    public abstract PathGenerator getDefaultPathGenerator();

    /**
     * Compare function for how monsters order should be when stored in GameEngine
     * Make sure Hunter is before all Hound since Hound's path generation
     * is dependent on Hunter
     */
    public static int compare(Monster m1, Monster m2) {
        if(m1.getClass().equals(Hunter.class) && m2.getClass().equals(Hound.class)){
            return -1;
        }
        if(m2.getClass().equals(Hunter.class) && m1.getClass().equals(Hound.class)){
            return 1;
        }
        // ignore everything else
        return 0;
    }

    /**
     * Whenever the monster is changing location
     * update path if using run away generator
     * since it only returns one point at a time
     *
     * @see RunAwayPathGenerator#generatePath(Map, Monster, Player)
     * @param engine game engine
     */
    @Override
    public void onUpdatingLocation(GameEngine engine) {
        updatePath(engine.getMap(), engine.player);
    }
}
