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
    
    /**
     * Checks if any objects can be destroyed in radius and removes them
     * this class should only gets called if we set a bomb.
     * going to ignore the front end implementation of sending the message
     */
    public void destroy(GameEngine ge, Map m, Point p) {
    	
    	// get the location of the bomb
    	Point p = this.getLocation();
    	int x = p.getX();
    	int y = p.getY();
    	
    	// list of positions maybe implement this function in point class (get surrounding points)
    	ArrayList<Point> checkPositions = new ArrayList<Point>
    	checkPositions.add(new Point(x + 1, y));
    	checkPositions.add(new Point (x-1, y));
    	checkPositions.add(new Point(x, y + 1));
    	checkPositions.add(new Point(x, y - 1));
    	
    	// cycle through each position
    	for (Point p: checkPositions) {
    		for (GameObject go: m.getObjects(p)) {
    			if (go.getSampleName().isEquals("Boulder")) {
    				m.updateObjectLocation(go, p);
    			} else if (go.getSampleName().isEquals("Monster")) {
    				m.updateObjectLocation(go, p);
    			} else if (go.getSimpleName().isEquals("Player")) {
    				// send an instant game over message?
    			}
    		}
    	}
    }
    
    @Override
	public void getCollected(GameEngine engine, Inventory playerInventory) {		
    		if (this.state == UNLIT) {
    			playerInventory.addObject(this); // bombs can be added indefinitely
    			engine.removeGameObject(this);
    		}
	}
 
}
