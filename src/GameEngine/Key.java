package GameEngine;

import java.util.List;

public class Key extends GameObject implements Collectable {

	public Key(Point location) {
		super(location);
	}

	@Override
	public boolean getCollected(GameEngine engine, Inventory playerInventory) {
		if(playerInventory.getCount(this.getClassName()) == 0) {
			playerInventory.addObject(this);
			engine.removeGameObject(this);
			return true;
		}
		return false;
	}
}