package GameEngine;

public class Potion extends GameObject implements Collectable{

    public Potion(Point location) {
        super(location);
    }
    
    @Override
    public boolean getCollected(GameEngine engine, Inventory playerInventory) {
        engine.removeGameObject((GameObject) this);
        return true;
    }
}
