package GameEngine;

import GameEngine.utils.Point;

public class Key extends StandardObject implements Collectable {

    /**
     * constructor for Key
     * takes in location parameter
     * @param location
     */
    public Key(Point location) {
        super(location);
    }

    /**
     * getCollected method for Key object
     * takes in engine and playerInventory parameters
     * returns a boolean depending if object is collected
     * @param engine
     * @param playerInventory
     * @return
     *          boolean
     */
    @Override
    public boolean getCollected(GameEngine engine, Inventory playerInventory) {
        if (playerInventory.getCount(this.getClass()) == 0) {
            playerInventory.addObject(this);
            engine.removeGameObject(this);
            return true;
        }
        return false;
    }
}
