package GameEngine.CollisionHandler;

import GameEngine.*;

import static GameEngine.Arrow.MOVING;

public class ArrowClosedDoorCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        // Leaving the boulder conditional incase of future change to methodology
        Door door = (Door) (obj1 instanceof Door ? obj1 : obj2);
        Arrow arrow = (Arrow) (obj1 instanceof Arrow ? obj1 : obj2);

        CollisionResult res = new CollisionResult(0);

        // Check if arrow is in the moving state
        if ((arrow.getState() == MOVING) && (door.getState() == 0)) {
            if (obj1 instanceof Arrow) {
                res.addFlag(CollisionResult.DELETE_FIRST);
            } else if (obj2 instanceof Arrow) {
                res.addFlag(CollisionResult.DELETE_SECOND);
            }
        }
        return res;
    }
}
