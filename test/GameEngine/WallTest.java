package GameEngine;

import GameEngine.utils.Point;
import org.junit.Test;
import static org.junit.Assert.*;

import GameEngine.CollisionHandler.*;

public class WallTest {
    
    @Test
    public void testBitWise() {
        int a = CollisionResult.DELETE_FIRST;
        int b = CollisionResult.DELETE_SECOND;
        int c = a|b;
        assertEquals(c, CollisionResult.DELETE_BOTH);
    }
    
    @Test
    // check if the correct message is sent to the front end
    public void WallCollision() throws CollisionHandlerNotImplement {
        GameEngine ge = new GameEngine(new Map());
        
        Point p = new Point (1, 2);
        //testing these
        Wall wall = new Wall(p);
        Boulder boulder = new Boulder(p); 
        Arrow arrow = new Arrow(p);
        Player player = new Player(p);
        
        wall.registerCollisionHandler(ge);
        
        CollisionEntities ce1 = new CollisionEntities(Wall.class, Boulder.class);
        CollisionEntities ce2 = new CollisionEntities(Wall.class, Arrow.class);
        CollisionEntities ce3 = new CollisionEntities(Wall.class, Player.class);

        CollisionHandler ch1 = ge.getCollisionHandler(ce1);
        CollisionHandler ch2 = ge.getCollisionHandler(ce2);
        CollisionHandler ch3 = ge.getCollisionHandler(ce3);
   
        // test collision results on boulder and wall
        CollisionResult cr1 = ch1.handle(ge, wall, boulder);
        assertEquals(cr1.getFlags(), CollisionResult.REJECT);

        // test collision results on arrow and wall
        CollisionResult cr2 = ch2.handle(ge, wall, arrow);
        assertEquals(cr2.getFlags(), CollisionResult.DELETE_FIRST);

        // test collision results on player and wall
        CollisionResult cr3 = ch3.handle(ge, wall, player);
        assertEquals(cr3.getFlags(), CollisionResult.REJECT);

    }
}
