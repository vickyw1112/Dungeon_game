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
	public boolean getCollected(GameEngine engine, Inventory playerInventory){
    	return true;
	}

	/* TODO: think about how front end should call bomb's explode method in a generic
     *       way, e.g. could have a interface for all object can be invoke after delaying for some time period
     *       then override them in individual sub class
     */
}
