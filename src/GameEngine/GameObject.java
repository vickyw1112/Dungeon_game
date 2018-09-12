package GameEngine;

public abstract class GameObject {
    private static int objCount = 0;

    protected int objId;
    protected Point location;
    /**
     * State of a object, specific to each type of object
     */
    protected int state;


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
     * Change a new location for an object
     * return true if the location changed, otherwise false
     *
     * @see Map#map
     * @param point new location
     * @return whether location changed
     */
    public boolean setLocation(Point point){
        if(location.equals(point)){
            return false;
        }
        this.location = point;
        return true;
    }


    /**
     * Call front end hook
     * {@link StateChanger#changeState}
     *
     * @param state
     */
    public void changeState(int state){
        this.state = state;
//        stateChanger.changeState(this, state);
    }
    
    public int getState() {
		return this.state;
	}

    /**
     * Register collision handler related to this object
     *
     * @param gameEngine the game engine
     */
    public void registerCollisionHandler(GameEngine gameEngine) {
        // defult do sth
        
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
