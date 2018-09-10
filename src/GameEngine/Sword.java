package GameEngine;

public class Sword extends GameObject implements Collectable {
	public static final int attackNum = 5;

	public Sword(Point location) {
		super(location);
	}
	
	@Override
	public void getCollected(GameEngine engine, Inventory playerInventory) {
		playerInventory.setCount(Sword.class.getSimpleName(), attackNum);
		engine.removeGameObject(this);
	}

}
