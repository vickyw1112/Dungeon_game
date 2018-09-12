package GameEngine;

public abstract class Potion extends GameObject {
    
    private PlayerEffect playerEffect;
    private int duration;
    public Potion(Point location) {
        super(location);
    }
    
    
}
