package GameEngine;

import GameEngine.utils.PlayerEffect;
import GameEngine.utils.Point;

public class InvinciblePotion extends Potion {
    public InvinciblePotion(Point location) {
        super(location);
        this.playerEffect = PlayerEffect.INVINCIBLE;
        this.duration = 10;
    }
}
