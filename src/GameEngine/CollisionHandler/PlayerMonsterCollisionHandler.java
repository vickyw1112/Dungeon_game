package GameEngine.CollisionHandler;


import GameEngine.*;
import GameEngine.utils.PlayerEffect;

public class PlayerMonsterCollisionHandler implements CollisionHandler {

    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2){
        Player player = (Player) (obj1 instanceof Player ? obj1 : obj2);
        Monster monster = (Monster)(obj1 instanceof Monster ? obj1 : obj2);
        CollisionResult res = new CollisionResult();

        int deleteFlag =
                obj1 instanceof Monster ? CollisionResult.DELETE_FIRST : CollisionResult.DELETE_SECOND;

        if (player.getPlayerEffects().contains(PlayerEffect.INVINCIBLE)) {
            res.addFlag(deleteFlag);
            engine.removeGameObject(monster);
        } else if (player.getInventoryCount(Sword.class) > 0) {
            int n = player.getInventoryCount(Sword.class);
            player.getInventory().setCount(Sword.class, n - 1);
            res.addFlag(deleteFlag);
            engine.removeGameObject(monster);
        } else {
            res.addFlag(CollisionResult.LOSE);
        }

        if(engine.checkWiningCondition())
            res.addFlag(CollisionResult.WIN);
        return res;
    }

}