package GameEngine;

import GameEngine.utils.Point;

public class Sword extends StandardObject implements Collectable {
    private static final int ATTACKNUM = 5;

    public Sword(Point location) {
        super(location);
    }

    @Override
    public boolean getCollected(GameEngine engine, Inventory playerInventory) {
        playerInventory.setCount(Sword.class, ATTACKNUM);
        engine.removeGameObject(this);
        return true;
    }

}
