package GameEngine;

import GameEngine.utils.Point;

public class Key extends StandardObject implements Collectable {

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
