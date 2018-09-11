package GameEngine;

public class Treasure extends GameObject implements Collectable {

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
