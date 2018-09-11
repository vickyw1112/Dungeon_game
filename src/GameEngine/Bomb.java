package GameEngine;

import java.awt.*;

public class Bomb extends GameObject implements Collectable {
    public static final int UNLIT = 0;
    public static final int ALMOSTLIT = 1; // will find a better name
    public static final int LIT = 2;
    public static final int TIMER = 3000; // 3 seconds before it explodes and 3 second explosion time.

    
    private int state;
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
