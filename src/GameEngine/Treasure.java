package GameEngine;

import GameEngine.utils.Point;

public class Treasure extends StandardObject implements Collectable {

    public Treasure(Point location) {
        super(location);
    }

    @Override
    public boolean getCollected(GameEngine engine, Inventory playerInventory) {
        playerInventory.addObject(this);
        engine.removeGameObject(this);
        return true;
    }
}
