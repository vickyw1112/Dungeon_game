package GameEngine;

public class Hunter extends Monster {
    public Hunter(Point location){
        super(location);
        this.pathGenerator = new ShortestPathGenerator();
    }
}
