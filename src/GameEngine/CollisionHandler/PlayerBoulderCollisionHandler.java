package GameEngine.CollisionHandler;

import GameEngine.*;

public class PlayerBoulderCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        CollisionResult res = new CollisionResult(0);
        Player player = (Player) (obj1 instanceof Player ? obj1 : obj2);
        Boulder boulder = (Boulder) (obj1 instanceof Boulder ? obj1 : obj2);
        res.addFlag(CollisionResult.REJECT);
        player.setPushBoulder(true);
        boulder.setFacing(player.getFacing());
        boulder.setSpeed(Boulder.SPEED);

        if(engine.isWinning())
            res.addFlag(CollisionResult.WIN);
        return res;
    }
}
