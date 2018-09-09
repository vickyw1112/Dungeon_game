package GameEngine;

import java.awt.*;

public abstract class GameObject {
    protected int objId;
    protected Point location;


    /**
     * Constructor for GameObject
     * Auto generate objId
     *
     * @param location initial location of the object
     */
    public GameObject(Point location) {
        this.location = location;
        this.objId = objCount++;
    }


    // debug only
    @Override
    public String toString() {
        return String.format("<%s|%s>", this.getClass().getName(), this.location.toString());
    }


    /**
     * Front end provided hook to change a state (view/style)
     * of a GameObject
     */
    static StateChanger stateChanger;

    /**
     * Get object id
     * @return objID
     */
    public int getObjID() {
        return objId;
    }

    /**
     * Get location
     * @return location
     */
    public Point getLocation() {
        return location;
    }


    /**
     * Call front end hook
     * {@link StateChanger#changeState}
     *
     * @param state
     */
    public void changeState(int state){
        stateChanger.changeState(this, state);
    }

    /**
     * Register collision handler related to this object
     *
     * @param gameEngine the game engine
     */
    public void registerCollisionHandler(GameEngine gameEngine) {
        // by default do nothing
    }

    /**
     * Return class name
     * Used for finding count for specific type of object
     * in player's inventory
     *
     * @see Inventory#getCount
     * @return class name
     */
    public String getClassName(){
        return getClass().getSimpleName();

    }

    /**
     * Return object's category name which could be the class name
     * of a parent class depends on specific game object
     * By default, this is same as {@link GameObject#getClassName}
     * Used for finding collision handler
     *
     * @see GameEngine#collisionHandlerMap
     * @return category name
     */
    public String getCategoryName(){
        return this.getClassName();
    }

}
