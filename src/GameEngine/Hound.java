package GameEngine;

public class Hound extends Monster implements Pairable {
    /**
     * Paired hunter
     */
    private Hunter hunter;

    public Hound(Point location){
        super(location);
    }

    @Override
    public void initialize() {
        this.pathGenerator = new HoundPathGenerator();
    }

    public GameObject getPair(){
        return hunter;
    }

    public void setPair(GameObject pair){
        hunter = (Hunter) pair;
    }
}
