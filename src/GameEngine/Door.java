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

    @Override
    public String getPairingObjectClassName() {
        return Key.class.getSimpleName();
    }

    @Override
    public void setPair(GameObject pair) {
        key = (Key) pair;
    }

    @Override
    public GameObject getPair() {
        return key;
    }

    /**
     * set corresponding key to door
     * 
     * @param key key to set
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
     * Open door check whether the key is match to the door or not if matching set
     * door's status to open otherwise return false
     * If the door is successfully opened, also update all monster's path
     *
     * @param engine game engine
     * @param key the key that the player is trying to use to open the door
     * @return whether the door is opened
     */
    public boolean openTheDoor(GameEngine engine, Key key) {
        if (this.key.equals(key)) {
            this.changeState(OPEN);
            engine.updateMonstersPath();
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlocking() {
        return this.state == Door.CLOSED;
    }

    /**
     * method override for Player/Door interaction
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        //player and door
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Player.class),
              new DoorPlayerCollisionHandler());
    }
}
