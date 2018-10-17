package GameEngine;

import GameEngine.utils.Direction;

/**
 * Interface for all object that is capable of moving
 */
public interface Movable extends GameObject {
    public static final int APPROX = 0;
    public static final int EXACT = 1;

    /**
     * Get current facing of a movable object
     * 
     * @return facing direction
     */
    Direction getFacing();

    /**
     * Get current speed of the movable object
     * 
     * @return speed in grid per second
     */
    double getSpeed();

    /**
     * Get moving scheme of this object
     * APPROX means backend location will update
     *      to the position that the object is nearest to
     * EXACT means backend location will only update
     *      when the object actually reach the next point
     *      in it's facing direction
     * @return
     */
    default int getMovingScheme(){
        return APPROX;
    }
}
