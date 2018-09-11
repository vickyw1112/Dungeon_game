package GameEngine;

public class Hound extends Monster {
    public Hound(Point location){
        super(location);
        this.pathGenerator = new HoundPathGenerator();
    }
}
