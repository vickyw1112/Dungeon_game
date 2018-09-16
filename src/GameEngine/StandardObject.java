package GameEngine;

import GameEngine.utils.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class StandardObject implements GameObject, Serializable {
    public static final int DEFAULT_STATE = 0;

    private static int objCount = 0;
    private final int objId;
    Point location;
    private transient List<GameObjectObserver> observers;

    /**
     * State of a object, specific to each type of object
     */
    int state;


    /**
     * Constructor for GameObject Auto generate objId
     *
     * @param location
     *            initial location of the object
     */
    public StandardObject(Point location) {
        this.location = location;
        this.objId = objCount++;
        this.observers = new LinkedList<>();
    }

    @Override
    public void initialize() {
        observers = new LinkedList<>();
        this.state = DEFAULT_STATE;
    }

    @Override
    public String toString() {
        return String.format("<%s|%s>", this.getClass().getName(), this.location.toString());
    }

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
        this.location = point.clone();
        return true;
    }

    /**
     * Change object's state and notify all observers (front end)
     * @param state new state
     */
    @Override
    public void changeState(int state) {
        this.state = state;
        notifyObservers();
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

    @Override
    public void addObserver(GameObjectObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
    }

    /**
     * Object register no collision handlers by default
     * @param gameEngine game engine
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine){

    }
}
