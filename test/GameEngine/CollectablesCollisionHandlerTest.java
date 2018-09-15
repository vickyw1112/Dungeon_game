package GameEngine;

import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import GameEngine.CollisionHandler.*;

public class CollectablesCollisionHandlerTest {
    private Player player;
    private GameEngine engine;
    private CollisionHandler handler;

    @Before
    public void setup(){
        player = new Player(new Point(2, 2));
        engine = new GameEngine();
        handler = new CollectablesCollisionHandler();
        player.initialize();
    }


	@Test
	public void collectArrowTest() {
		Arrow arrow = new Arrow(new Point(1,1));

        CollisionResult res = handler.handle(engine, arrow, player);
        assertTrue(res.containFlag(CollisionResult.REFRESH_INVENTORY | CollisionResult.DELETE_FIRST));

        res = handler.handle(engine, player, arrow);
        assertTrue(res.containFlag(CollisionResult.REFRESH_INVENTORY | CollisionResult.DELETE_SECOND));
	}
}
