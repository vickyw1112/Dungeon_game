package GameEngine;

import GameEngine.utils.PlayerEffect;
import GameEngine.utils.Point;

public class HoverPotion extends Potion {

    public HoverPotion(Point location) {
        super(location);
        this.playerEffect = PlayerEffect.HOVER;
        this.duration = 10;
    }

}
