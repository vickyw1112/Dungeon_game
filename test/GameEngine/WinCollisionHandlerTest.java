package GameEngine;

import GameEngine.utils.Point;
import org.junit.Test;
import static org.junit.Assert.*;
import GameEngine.CollisionHandler.*;


public class WinCollisionHandlerTest {
    @Test
    public void WinCollisionHandlerTest () {
        GameEngine engine = new GameEngine(new Map());
        Player player = new Player(new Point(1, 1));
        Exit exit = new Exit(new Point(1, 1));
        CollisionEntities ce = new CollisionEntities(Player.class, Exit.class);

        player.initialize();
        exit.registerCollisionHandler(engine);

        CollisionHandler ch = null;
        try {
            ch = engine.getCollisionHandler(ce);
        } catch (CollisionHandlerNotImplement collisionHandlerNotImplement) {
            collisionHandlerNotImplement.printStackTrace();
        }
        CollisionResult res = ch.handle(engine, player, exit);
        assertEquals(res.getFlags(), CollisionResult.WIN);
    }
}