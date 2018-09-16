package GameEngine;

import GameEngine.utils.*;

/**
 * Flow of hover potion
 * 1. UI collision handler detects a hover potion and player interaction
 * 2. Handler returned (updates the status of the player to hover potion active)
 * 3. Now the player will maintain this state for a duration
 * 4. After a certain amount of time the front end will tell the back end to cancel potion effect
 */
public class HoverPotion extends Potion {
    public HoverPotion(Point location) {
        super(location);
        this.playerEffect = PlayerEffect.HOVER;
        this.duration = Potion.LAST_FOREVER;
    }
}
