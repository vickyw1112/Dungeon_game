package GameEngine;

public class FloorSwitch extends GameObject {

   public static int OPEN = 0;
   public static int CLOSED = 1;
    
    
	public FloorSwitch(Point location) {
		super(location);
		this.state = OPEN;
	}
	
	
	
}
