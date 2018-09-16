package GameEngine;

import GameEngine.utils.Point;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends StandardObject implements Collectable {
    public static final long TIMER = 3000; // 3 seconds before it explodes and 3 second explosion time.
    private static final long EXPLOSION_TIMER = 100; // change to EXPLODE state at last 100 ms

    public static final int LIT = 1;
    public static final int EXPLODE = 2; // will find a better name


    /**
     * Constructor for bomb
     *
     * @param location init location
     */
    public Bomb(Point location) {
        super(location);
        state = COLLECTABLESTATE;
    }

    /**
     * Interface to front end to update timer value
     * Call to change state for different style of bomb
     */
    public void updateTimer(double timer) {
        // display the explosion pic at very last 100 ms
        if(timer <= 100){
            this.changeState(EXPLODE);
        }
    }

    /**
     * Checks if any objects can be destroyed in radius and removes them this class
     * should only gets called if we set a bomb. going to ignore the front end
     * implementation of sending the message
     *
     * @param engine
     *            game engine
     * @return list of object that gets destroyed during explosion return empty list
     *         if no objects are destroyed or null if player is died during
     *         explosion
     */
    public List<GameObject> explode(GameEngine engine) {

        int x = this.location.getX();
        int y = this.location.getY();
        // list of positions maybe implement this function in point class
        // (get surrounding points)
        Point[] checkPositions = new Point[5];
        checkPositions[0] = new Point(x + 1, y);
        checkPositions[1] = new Point(x - 1, y);
        checkPositions[2] = new Point(x, y + 1);
        checkPositions[3] = new Point(x, y - 1);
        // include the location of bomb as boulder/monster can go over it
        checkPositions[4] = new Point(x, y);

        ArrayList<GameObject> adjacentObj = new ArrayList<>();

        // cycle through each position
        for (Point currPos : checkPositions) {
            adjacentObj.addAll(engine.getObjectsAtLocation(currPos));
        }

        List<GameObject> ret = new ArrayList<>();

        // remove all adjacent Boulder and Monster
        for (GameObject obj : adjacentObj) {
            if (obj instanceof Boulder || obj instanceof Monster) {
                engine.removeGameObject(obj);
                ret.add(obj);
            } else if (obj instanceof Player) {
                return null; // game over
            }
        }

        engine.removeGameObject(this); // remove reference of this Lit Bomb
        return ret;
    }
}
