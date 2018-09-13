package GameEngine;

import GameEngine.utils.Point;

public interface GameObject {
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
     * Call front end hook {@link StateChanger#changeState}
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
     * Return class name Used for finding count for specific type of object in
     * player's inventory
     *
     * @see Inventory#getCount
     * @return class name
     */
    default String getClassName() {
        return getClass().getSimpleName();
    }
}
