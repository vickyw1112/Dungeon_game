package GameEngine;

import GameEngine.utils.Direction;

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
