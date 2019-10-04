package GameEngine;

import GameEngine.utils.PlayerEffect;
import GameEngine.utils.Point;

public class InvinciblePotion extends Potion {

    /**
     * Constructor for InvinciblePotion
     * takes in location parameter
     * @param location
     */
    public InvinciblePotion(Point location) {
        super(location);
        this.playerEffect = PlayerEffect.INVINCIBLE;
        this.duration = 5000;
    }

    /**
     * When player get invincible effect, all monsters run away
     * @param engine game engine to apply side effect on
     */
    @Override
    public void onPlayerGetPotion(GameEngine engine) {
        engine.updateMonstersMovementStrategy(new RunAwayPathGenerator());
    }

    /**
     * Restore all monsters' path generator to default one
     * @param engine game engine to apply side effect on
     */
    @Override
    public void onPotionExpires(GameEngine engine) {
        engine.updateMonstersMovementStrategy(Monster.DEFAULT_PATHGEN);
        engine.updateMonstersPath();
    }
}
