package GameEngine;

import GameEngine.utils.Point;


public class Wall extends StandardObject {
    public Wall(Point location) {
        super(location);
    }

    /**
     * isBlocking method returns always true for Walls
     * @return
     *          boolean
     */
    @Override
    public boolean isBlocking() {
        return true;
    }

}
