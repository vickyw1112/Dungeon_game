package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.CollisionHandler.CollisionResult;
import GameEngine.CollisionHandler.SwitchBoulderCollisionHandler;
import GameEngine.utils.Point;

public class FloorSwitch extends StandardObject {
    public static final int TRIGGERED = 1;
    public static final int UNTRIGGERED = 0;

    public FloorSwitch(Point location) {
        super(location);
        this.state = UNTRIGGERED;
    }

    /*
     * (non-Javadoc)
     * 
     * @see GameEngine.GameObject#registerCollisionHandler(GameEngine.GameEngine)
     */
    @Override
    public void registerCollisionHandler(GameEngine gameEngine) {
        // TODO delete this since it will not 'untrigger' the switch if boulder is no
        // long
        // colliding with the switch, rely on GameEngine#checkWinning instead
        gameEngine.registerCollisionHandler(new CollisionEntities(this.getClass(), Boulder.class),
                new SwitchBoulderCollisionHandler());
    }
}
