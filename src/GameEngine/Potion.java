package GameEngine;

import GameEngine.utils.PlayerEffect;
import GameEngine.utils.Point;

public abstract class Potion extends StandardObject {
    public static final int LAST_FOREVER = -1;

    protected PlayerEffect playerEffect;

    /**
     * Duration of the effect of the potion in seconds
     */
    protected int duration;

    public Potion(Point location) {
        super(location);
    }

    public PlayerEffect getEffect(){
        return playerEffect;
    }

    public int getDuration(){
        return duration;
    }

    /**
     * Side effect apply on other game object
     * or game state upon player collide them.
     * By default do nothing
     *
     * @see GameEngine.CollisionHandler.PlayerPotionCollisionHandler#handle
     * @param engine game engine to apply side effect on
     */
    public void onPlayerGetPotion(GameEngine engine){

    }

    /**
     * Side effect apply on other game object or game state
     * when this potion expires
     * By default do nothing
     *
     * @see GameEngine.CollisionHandler.PlayerPotionCollisionHandler#handle
     * @param engine game engine to apply side effect on
     */
    public void onPotionExpires(GameEngine engine){

    }
}
