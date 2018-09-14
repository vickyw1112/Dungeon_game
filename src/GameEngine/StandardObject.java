package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.utils.Point;

import java.io.Serializable;

public abstract class StandardObject implements GameObject, Serializable {
    public static final int DEFAULT_STATE = 0;

    static int objCount = 0;
    protected int objId;
    protected Point location;
    /**
     * State of a object, specific to each type of object
     */
    protected int state;

    /**
     * Constructor for GameObject Auto generate objId
     *
     * @param location
     *            initial location of the object
     */
    public StandardObject(Point location) {
        this.location = location;
        this.objId = objCount++;
        this.state = DEFAULT_STATE;
    }

    // debug only
    @Override
    public String toString() {
        return String.format("<%s|%s>", this.getClass().getName(), this.location.toString());
    }

    /**
     * Front end provided hook to change a state (view/style) of a GameObject
     */
    static StateChanger stateChanger;

    /**
     * Get object id
     * 
     * @return objID
     */
    @Override
    public int getObjID() {
        return objId;
    }

    /**
     * Get location
     * 
     * @return location
     */
    @Override
    public Point getLocation() {
        return location;
    }

    /**
     * Change a new location for an object return true if the location changed,
     * otherwise false
     *
     * @see Map#map
     * @param point
     *            new location
     * @return whether location changed
     */
    @Override
    public boolean setLocation(Point point) {
        if (location.equals(point)) {
            return false;
        }
        this.location = point;
        return true;
    }

    /**
     * Call front end hook {@link StateChanger#changeState}
     *
     * @param state
     */
    @Override
    public void changeState(int state) {
        this.state = state;
        // TODO: check observer pattern for this
        // stateChanger.changeState(this, state);
    }

    /**
     * Get the Object's current state
     *
     * @return state
     */
    @Override
    public int getState() {
        return this.state;
    }

    /**
     * Register collision handler related to this object
     *
     * @param gameEngine
     *            the game engine
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        // defult do sth

    }

    /**
     * Return object's category name which could be the class name of a parent class
     * depends on specific game object By default, this is same as
     * {@link GameObject#getClassName} Used for finding collision handler
     *
     * TODO: delete this if verified no use
     * 
     * @deprecated now using collisionHandlerMap with a fall back mechanism
     * @see GameEngine#getCollisionHandler(CollisionEntities)
     * @return category name
     */
    public String getCategoryName() {
        return this.getClassName();
    }

}
