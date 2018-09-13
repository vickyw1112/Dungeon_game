package GameEngine.CollisionHandler;

import GameEngine.*;

public class DoorMovableCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        CollisionResult res = new CollisionResult(0);
        Door door = (Door) (obj1 instanceof Door ? obj1 : obj2);
        if (door.getState() == Door.CLOSED)
            res.addFlag(CollisionResult.REJECT);
        return res;
    }
}
