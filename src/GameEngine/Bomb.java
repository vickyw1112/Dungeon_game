package GameEngine;

import java.awt.*;

public class Bomb extends GameObject implements Collectable {
    public static final int UNLIT = 0;
    public static final int LIT = 1;

    /**
     * Constructor for bomb
     *
     * @param location
     */
    public Bomb(Point location){
        super(location);
        state = UNLIT;
    }


    @Override
	public void getCollected(GameEngine engine, Inventory playerInventory) {
			
    		if (this.state == UNLIT) {
    			playerInventory.addObject(this); // bombs can be added indefinitely
    			engine.removeGameObject(this);
    		}
	}
}
