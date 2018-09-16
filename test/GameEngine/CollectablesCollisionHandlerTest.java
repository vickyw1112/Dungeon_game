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

	@Test
    public void collectSwordTest() {
        Sword sword = new Sword(new Point(1, 3));
        Sword sword2 = new Sword(new Point(1, 3));

        assertEquals(player.getInventoryCount(Sword.class), 0);
        handler.handle(engine, player, sword);

        assertEquals(player.getInventoryCount(Sword.class), 5);

        Monster monster = new Hunter(new Point(1, 3));
        CollisionResult res = new PlayerMonsterCollisionHandler().handle(engine, player, monster);
        assertTrue(res.containFlag(CollisionResult.DELETE_SECOND));

        assertEquals(player.getInventoryCount(Sword.class), 4);

        handler.handle(engine, player, sword2);
        assertEquals(player.getInventoryCount(Sword.class), 5);

    }

    @Test
    public void collectTreasureTest() {
        Treasure t1 = new Treasure(new Point(1, 2));
        Treasure t2 = new Treasure(new Point(1, 4));

        handler.handle(engine, player, t1);
        assertTrue(player.containsInventory(t1));
        assertEquals(player.getInventoryCount(Treasure.class), 1);

        // stack the second treasure
        handler.handle(engine, player, t2);
        assertTrue(player.containsInventory(t2));
        assertEquals(player.getInventoryCount(Treasure.class), 2);
    }
}
