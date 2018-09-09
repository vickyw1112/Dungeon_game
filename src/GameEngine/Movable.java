package GameEngine;

import java.awt.*;

public interface Movable {
    /**
     * Set a new location for a movable object
     * return true if the location changed, otherwise false
     *
     * @param point new location
     * @return whether location changed
     */
    default boolean setLocation(Point point){
        ((GameObject)this).location = point;
        // TODO fix this
        return false;
    }

    /**
     * Get current facing of a movable object
     * @return facing direction
     */
    public Direction getFacing();

    /**
     * Get current speed of the movable object
     * @return speed
     */
    public double getSpeed();
}
