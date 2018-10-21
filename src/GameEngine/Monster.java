package GameEngine;

import GameEngine.utils.Direction;
import GameEngine.utils.Point;

import java.util.LinkedList;

/**
 * Super class for all Monster instance
 */
public abstract class Monster extends StandardObject implements Movable {
    /**
     * monster speed default is 2
     */
    private static final double SPEED = 2;

    /**
     * monster has a default path generator
     * initially set to null
     */
    static final PathGenerator DEFAULT_PATHGEN = null;

    /**
     * LinkedList of points that represent the path for monsters
     */
    transient LinkedList<Point> pathToDestination;

    /**
     * defining which pathGenerator to use
     */
    transient PathGenerator pathGenerator;

    /**
     * variable to check the direction monster is facing
     */
    private Direction facing;

    /**
     * bool set true when need to update the path
     * path is delayed updated after monster finish current move
     * to the next grid
     */
    private boolean needUpdatePath;

    private Map map;
    private Player player;

    /**
     * Constructor for Monster
     * takes in location parameter
     * @param location
     */
    public Monster(Point location) {
        super(location);
    }

    /**
     * initialize method for Monster
     */
    @Override
    public void initialize() {
        super.initialize();
        pathToDestination = new LinkedList<>();
        pathGenerator = getDefaultPathGenerator();
        needUpdatePath = false;
    }

    @Override
    public int getMovingScheme() {
        return EXACT;
    }

    /**
     * getPath method
     * returns a linked list of points as path for monster
     * @return
     */
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
     */
    @Override
    public void onUpdatingLocation(GameEngine engine) {
        if(pathToDestination.size() > 0 &&
                pathToDestination.peek().equals(location))
            pathToDestination.pop();
        // if location changed, determine new facing
        if(pathToDestination.size() > 0) {
            facing = determineFacing(pathToDestination.peek());
        }
        delayedUpdate();
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
//        System.exit(1);
        return null;
    }

    /**
     * Update monster's path by its strategy
     * also update facing to move towards first point in the path
     *
     * @param map
     * @param player
     */
    public void updatePath(Map map, Player player) {
        this.map = map;
        this.player = player;
        if(pathToDestination.size() == 0) {
            delayedUpdate();
            return;
        }
        needUpdatePath = true;
    }

    private void delayedUpdate() {
        pathToDestination = pathGenerator.generatePath(map, this, player);
        if(pathToDestination.peek() != null)
            facing = determineFacing(pathToDestination.peek());
        needUpdatePath = false;
        System.out.format("%s updated path: %s\n", this, this.pathToDestination);
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

}
