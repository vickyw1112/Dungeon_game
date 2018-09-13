package GameEngine;

import GameEngine.utils.Point;

public abstract class Potion extends StandardObject {
    
    private PlayerEffect playerEffect;
    private int duration;
    public Potion(Point location) {
        super(location);
    }
    
    
}
