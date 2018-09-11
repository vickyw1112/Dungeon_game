package GameEngine;

public class Strategist extends Monster {

    public Strategist(Point location){
        super(location);
        pathGenerator = new StrategistPathGenerator();
    }

}
