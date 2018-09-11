package GameEngine;

public class Sword extends GameObject implements Collectable {
	public static final int ATTACKNUM = 5;

	public Sword(Point location) {
		super(location);
	}
	
	@Override
	public boolean getCollected(GameEngine engine, Inventory playerInventory) {
		playerInventory.setCount(Sword.class.getSimpleName(), ATTACKNUM);
		engine.removeGameObject(this);
		return true;
	}

}
