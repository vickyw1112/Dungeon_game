package GameEngine;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends GameObject implements Collectable {
    public static final int ALMOSTLIT = 1; // will find a better name
    public static final int LIT = 2;
    public static final int TIMER = 3000; // 3 seconds before it explodes and 3 second explosion time.

    /**
     * Constructor for bomb
     *
     * @param location
     */
    public Bomb(Point location){
        super(location);
        state = Collectable.COLLECTABLESTATE;
    }
    
    /**
     * Checks if any objects can be destroyed in radius and removes them
     * this class should only gets called if we set a bomb.
     * going to ignore the front end implementation of sending the message
     *
     * @param engine game engine
     * @param map game map
     * @return list of object that gets destroyed during explosion
     *         return empty list if no objects are destroyed
     *         return null if player is died during explosion
     */
	/* TODO: think about how front end should call bomb's explode method in a generic
     *       way, e.g. could have a interface for all object can be invoke after delaying for some time period
     *       then override them in individual sub class
     */
    public List<GameObject> explode(GameEngine engine, Map map) {
    	
    	int x = this.location.getX();
    	int y = this.location.getY();
    	
    	// list of positions maybe implement this function in point class (get surrounding points)
    	Point[] checkPositions = new Point[4];
    	checkPositions[0] = new Point(x + 1, y);
    	checkPositions[1] = new Point (x-1, y);
    	checkPositions[2] = new Point(x, y + 1);
    	checkPositions[3] = new Point(x, y - 1);

    	ArrayList<GameObject> adjacentObj = new ArrayList<>();

    	// cycle through each position
        for (Point currPos: checkPositions) {
            adjacentObj.addAll(map.getObjects(currPos));
    	}

    	List<GameObject> ret = new ArrayList<>();

        for (GameObject obj: adjacentObj) {
            if (obj instanceof Boulder ||
                    obj instanceof Monster) {
                engine.removeGameObject(obj);
                ret.add(obj);

            } else if (obj instanceof Player) {
                return null; // game over
            }
        }
        return ret;
    }

}
