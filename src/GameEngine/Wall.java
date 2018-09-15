package GameEngine;

import GameEngine.utils.Point;


public class Wall extends StandardObject {
    public Wall(Point location) {
        super(location);
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

}
