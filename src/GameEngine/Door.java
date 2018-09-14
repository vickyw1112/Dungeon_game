package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.Point;

public class Door extends StandardObject implements Pairable {
    private Key key;
    public static final int OPEN = 1;
    public static final int CLOSED = 0;

    public Door(Point location) {
        super(location);
        this.state = CLOSED;
    }

    /**
     * helper function for open door
     * 
     * @param key
     * @return
     */
    private boolean isMatchKey(Key key) {
        if (this.key.equals(key))
            return true;
        return false;
    }

    public GameObject getPair() {
        return key;
    }

    public void setPair(GameObject pair) {
        key = (Key) pair;
    }

    /**
     * set corresponding key to door
     * 
     * @param key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * 
     */
    public Key getKey() {
        return this.key;
    }

    /**
     * open door check whether the key is match to the door or not if matching set
     * door's status to open otherwise return false
     * 
     * @param key
     * @return
     */
    public boolean openTheDoor(Key key) {
        if (isMatchKey(key)) {
            this.changeState(OPEN);
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlocking() {
        return this.state == Door.CLOSED;
    }

    /**
     * Collision handle method override for Player/Door interaction
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {

        //player and door
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Player.class),
              new DoorPlayerCollisionHandler());

        // closed door should reject all movable object
        // for all movable object: can through open door
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Movable.class),
                new DoorMovableCollisionHandler());
    }
}
