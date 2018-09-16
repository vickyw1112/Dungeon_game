package GameEngine.CollisionHandler;

import GameEngine.*;

public class PlayerPotionCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        CollisionResult res = new CollisionResult(0);
        Player player = (Player)(obj1 instanceof Player ? obj1 : obj2);
        Potion potion = (Potion)(obj1 instanceof Potion ? obj1 : obj2);


        if (obj1 instanceof Potion)
            res.addFlag(CollisionResult.DELETE_FIRST);
        else
            res.addFlag(CollisionResult.DELETE_SECOND);
      
        player.applyPotionEffect(engine, potion);
        res.addFlag(CollisionResult.REFRESH_EFFECT_TIMER);

        return res;
    }
}
