package GameEngine.CollisionHandler;

import GameEngine.*;

public class PlayerPitCollisionHandler implements CollisionHandler{

    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2){
        // Have to check instance type here
        Player player = (Player) (obj1 instanceof Player ? obj1 : obj2);
        CollisionResult res = new CollisionResult();
        if (player.getPlayerEffects().contains(PlayerEffect.HOVER)) {
            res.addFlag(CollisionResult.HANDLED);
            return res;
        } else {
            res.addFlag(CollisionResult.LOSE);
            return res;
        }
    }
}
