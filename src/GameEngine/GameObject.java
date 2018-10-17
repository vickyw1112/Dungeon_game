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

    /**
     * Build a game object by class name and args
     *
     * @return game object
     */
    public static GameObject build(String classname, Point location) {
        Class<?> clazz;
        try {
            // build fully-qualified class name
            clazz = Class.forName(GameObject.class.getPackage().getName() + "." + classname);
            return (GameObject) clazz.getConstructor(Point.class).newInstance(location);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if a given simple class name is valid
     */
    public static boolean isValidClass(String simpleName){
        String FQCN = GameObject.class.getPackage().getName() + "." + simpleName;
        try {
            Class.forName(FQCN);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    default public GameObject cloneObject(){
        return build(this.getClassName(), this.getLocation());
    }
}
