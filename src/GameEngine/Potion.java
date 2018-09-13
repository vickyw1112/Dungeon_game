package GameEngine;

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

}
