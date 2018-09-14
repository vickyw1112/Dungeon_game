package GameEngine.CollisionHandler;

import GameEngine.*;
import GameEngine.utils.PlayerEffect;

public class PlayerPotionCollisionHandler implements CollisionHandler {
    @Override
    public CollisionResult handle(GameEngine engine, GameObject obj1, GameObject obj2) {
        CollisionResult res = new CollisionResult(0);
        Player player = (Player)(obj1 instanceof Player ? obj1 : obj2);
        if (obj1 instanceof Potion)
            res.addFlag(CollisionResult.DELETE_FIRST);
        else
            res.addFlag(CollisionResult.DELETE_SECOND);
        if (obj1 instanceof HoverPotion || obj2 instanceof HoverPotion)
            player.getPlayerEffects().add(PlayerEffect.HOVER);
        else
            player.getPlayerEffects().add(PlayerEffect.INVINCIBLE);
        return res;
    }
}
