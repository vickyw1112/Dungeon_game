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
    public void testWallBlockMovement() {
        GameEngine ge = new GameEngine(new Map());
        
        Point p = new Point (1, 2);

        Wall wall = new Wall(p);
        Arrow arrow = new Arrow(p);
        Player player = new Player(p);

        // boulder wall collision tested inside BoulderTest
        CollisionHandler handler1 = new ArrowGameObjectCollisionHandler();
        CollisionHandler handler2 = new GameObjectMovableCollisionHandler();

        arrow.changeState(Arrow.MOVING);
        // test collision results on arrow and wall
        CollisionResult result = handler1.handle(ge, wall, arrow);
        assertEquals(result.getFlags(), CollisionResult.DELETE_SECOND);

        // test collision results on player and wall
        result = handler2.handle(ge, wall, player);
        assertEquals(result.getFlags(), CollisionResult.REJECT);
    }
}
