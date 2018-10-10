package GameEngine;

import GameEngine.utils.*;

/**
 * Super interface for all GameObject
 * Implementation for most of methods is defined in {@link StandardObject}
 */
public interface GameObject extends Observable {
    /**
     * This is called when the game actual load the dungeon in the first mode
     * Rather than GameObject being instantiated.
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
    Point getLocation();

    /**
     * Change a new location for an object return true if the location changed,
     * otherwise false
     *
     * @see Map#map
     * @param point
     *            new location
     * @return whether location changed
     */
    boolean setLocation(Point point);

    /**
     * Change state and notify observer (front end)
     *
     * @param state new state
     */
    void changeState(int state);

    /**
     * Get the Object's current state
     *
     * @return state
     */
    int getState();

    /**
     * Register collision handler related to this object
     *
     * @param gameEngine
     *            the game engine
     */
    void registerCollisionHandler(GameEngine gameEngine);


    /**
     * Test if the current GameObject will block movement of other object if collision occurs
     * This gets overwritten by boulder and wall to return true
     * also overwritten by Door which the return value will depends on the state of the door
     *
     * @see GameEngine.CollisionHandler.GameObjectMovableCollisionHandler
     * @see Door#isBlocking()
     * @return boolean value
     */
    default boolean isBlocking() {
        return false;
    }

    /**
     * If current GameObject is considered as a candidate point for path generation
     * By default is opposite of isBlocking
     * Monster override this is true
     * Pit override this is false
     *
     * @see Map#canMoveThrough(Point)
     * @see Map#getNonBlockAdjacentPoints(Point)
     * @return
     */
    default boolean canMoveThrough() {
        return !isBlocking();
    }

    /**
     * This is called when the object's position is changing to another grid
     *
     * @param engine game engine
     */
    default void onUpdatingLocation(GameEngine engine) {

    }

    /**
     * Get the class name
     *
     * @return classname
     */
    default String getClassName(){
        return getClass().getSimpleName();
    }


}
