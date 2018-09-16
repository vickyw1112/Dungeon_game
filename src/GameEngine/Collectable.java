package GameEngine;

/**
 * GameObject that is able to be collected by player
 */
public interface Collectable extends GameObject {

    /**
     * Constant Allow us to check Collectable state of collectable objects.
     */
    int COLLECTABLESTATE = 0;

    /**
     * getCollected method will add object to inventory class and remove from map
     * Sub class implementing this interface will override this for stacking
     */
    default boolean getCollected(GameEngine engine, Inventory playerInventory) {
        playerInventory.addObject(this);
        engine.removeGameObject(this);
        return true;
    }
}
