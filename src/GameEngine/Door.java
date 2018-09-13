package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.CollisionHandler.CollisionResult;
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

    /**
     * Collision handle method override for Player/Door interaction
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Player.class),
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    // Have to check instance type here
                    Player player = (Player) (obj1 instanceof Player ? obj1 : obj2);
                    Door door = (Door) (obj1 instanceof Door ? obj1 : obj2);

                    CollisionResult res = new CollisionResult(0);

                    // Check if obj1 is the door && Door is closed
                    if (door.getState() == CLOSED) {
                        // Collision result regardless if player has key is REJECT
                        res.addFlag(CollisionResult.REJECT);
                        Key key = getKey();
                        Inventory inventory = player.getInventory();
                        if (inventory.contains(key))
                            openTheDoor(key);
                    }
                    return res;
                });

        // closed door should reject all movable object
        // for all movable object: can through open door
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Movable.class),
                (GameEngine engine, GameObject obj1, GameObject obj2) -> {
                    CollisionResult res = new CollisionResult(0);
                    Door door = (Door) (obj1 instanceof Door ? obj1 : obj2);
                    if (door.state == CLOSED)
                        res.addFlag(CollisionResult.REJECT);
                    else
                        res.addFlag(CollisionResult.HANDLED);
                    return res;
                });
    }
}
