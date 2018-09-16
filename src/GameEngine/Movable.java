package GameEngine;

import GameEngine.utils.Direction;

/**
 * Interface for all object that is capable of moving
 */
public interface Movable extends GameObject {

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
}
