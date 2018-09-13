package GameEngine.CollisionHandler;

import GameEngine.*;

public class SwitchBoulderCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        FloorSwitch floorSwitch = (FloorSwitch) (obj1 instanceof FloorSwitch ? obj1 : obj2);
        floorSwitch.changeState(FloorSwitch.TRIGGERED);
        CollisionResult res = new CollisionResult(0);
        res.addFlag(CollisionResult.HANDLED);
        return res;
    }
}
