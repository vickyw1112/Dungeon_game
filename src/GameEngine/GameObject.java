package GameEngine;

import GameEngine.utils.*;

public interface GameObject extends Observable {
    /**
     * This is called when the game actual load the dungeon in the first mode Rather
     * than GameObject being instantiated.
     */
    default void initialize() {

    }

    /**
     * Get object id
     * 
     * @return objID
     */
    int getObjID();

    /**
     * Get location
     * 
     * @return location
     */
    public Point getLocation();

    /**
     * Change a new location for an object return true if the location changed,
     * otherwise false
     *
     * @see Map#map
     * @param point
     *            new location
     * @return whether location changed
     */
    public boolean setLocation(Point point);

    /**
     * Change state and notify observer (front end)
     *
     * @param state
     */
    public void changeState(int state);

    /**
     * Get the Object's current state
     *
     * @return state
     */
    public int getState();

    /**
     * Register collision handler related to this object
     *
     * @param gameEngine
     *            the game engine
     */
    public void registerCollisionHandler(GameEngine gameEngine);


    /**
     * Test if the current GameObject is blocking movements of other objects
     * This gets overwritten by boulder and wall to return true
     * also overwritten by Door which the return value will depends on the state of the door
     *
     * @see Door#isBlocking()
     * @return boolean value
     */
    default boolean isBlocking() {
        return false;
    }
}
