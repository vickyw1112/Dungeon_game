package GameEngine;

import GameEngine.utils.Point;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends StandardObject implements Collectable {
    private static final int TIMER = 3000; // 3 seconds before it explodes and 3 second explosion time.

    public static final int ALMOSTLIT = 1; // will find a better name
    public static final int LIT = 2;

    private double timer;


    /**
     * Constructor for bomb
     *
     * @param location init location
     */
    public Bomb(Point location) {
        super(location);
        state = COLLECTABLESTATE;
        timer = TIMER;
    }

    /**
     * Interface to front end to update timer value
     * Call to change state for different style of bomb
     */
    public void updateTimer(double timer) {
        this.timer = timer;
        // TODO: update state for diff remain time
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
    /*
     * TODO: think about how front end should call bomb's explode method in a
     * generic way, e.g. could have a interface for all object can be invoke after
     * delaying for some time period then override them in individual sub class
     */
    public List<GameObject> explode(GameEngine engine) {

        int x = this.location.getX();
        int y = this.location.getY();
        Map map = engine.getMap();
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
            adjacentObj.addAll(map.getObjects(currPos));
        }

        List<GameObject> ret = new ArrayList<>();

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
