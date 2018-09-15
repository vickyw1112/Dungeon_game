package GameEngine;

import GameEngine.utils.*;

public class HoverPotion extends Potion {
    public HoverPotion(Point location) {
        super(location);
        this.playerEffect = PlayerEffect.HOVER;
        this.duration = Potion.LAST_FOREVER;
    }
}
