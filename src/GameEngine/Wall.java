package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.CollisionHandler.CollisionResult;
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
