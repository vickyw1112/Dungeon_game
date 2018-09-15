package GameEngine;

import static org.junit.Assert.*;
import GameEngine.utils.Point;
import org.junit.Test;
import GameEngine.CollisionHandler.*;

public class ExitTest {

    // testing if the right handler is given?
    // (in the actual collision test we will see if it returns the right resutl
    @Test
    public void exitCollisionHandlerTest() {

        GameEngine ge = new GameEngine(new Map());
        Exit exit = new Exit(new Point(1,1));
        Player player = new Player(new Point (1,1));

        CollisionEntities ce1 = new CollisionEntities(Player.class, Exit.class);

        exit.registerCollisionHandler(ge);

        CollisionHandler ch = null;
        try {
            ch = ge.getCollisionHandler(ce1);
        } catch (CollisionHandlerNotImplement collisionHandlerNotImplement) {
            collisionHandlerNotImplement.printStackTrace();
        }
        CollisionResult res = ch.handle(ge, player , exit);

        assertEquals(res.getFlags(), CollisionResult.WIN);
    }
}