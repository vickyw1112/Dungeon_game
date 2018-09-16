package GameEngine;

import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;
import GameEngine.CollisionHandler.*;

public class ArrowTest {
    private Player player;
    private GameEngine engine;
    private Arrow arrow;
    private Map map;
    private CollisionResult res;
    private CollisionHandler handler;

    @Before
    public void setUp(){
        MapBuilder mb = new MapBuilder();
        player = new Player(new Point(2, 2));
        arrow = new Arrow(new Point(1, 1));

        mb.addObject(player);
        mb.addObject(arrow);
        map = new Map(mb);
        engine = new GameEngine(map);
        player.initialize();
    }

	/**
	 * Construction unit test, test the created arrow location is set to the right location
	 */
	@Test
	public void constructorTest() {
		Point p = new Point(1,1);
		assertEquals(arrow.getLocation(), p);
        assertEquals(arrow.getState(), Arrow.COLLECTABLESTATE);
	}
	
	/**
	 * Test for getCollected method
	 */
	@Test
	public void getCollectedTest() {
		assertEquals(arrow.getState(), Arrow.COLLECTABLESTATE);
		GameEngine engine = new GameEngine(new Map());

		handler = new CollectablesCollisionHandler();

        handler.handle(engine, player, arrow);

		assertTrue(player.containsInventory(arrow));
		assertEquals(player.getInventoryCount(Arrow.class), 1);

        Arrow arrow2 = new Arrow(new Point(1,1));

        handler.handle(engine, player, arrow2);
        assertTrue(player.containsInventory(arrow2));
        assertEquals(player.getInventoryCount(Arrow.class), 2);

	}

	/**
	 * Collision Handler for arrow 
	 */
	@Test
	public void arrowHitBlockingObjectTest() {
		Boulder boulder = new Boulder(new Point(5,5));
        Door door = new Door(new Point(6,6));

        handler = new ArrowGameObjectCollisionHandler();

        arrow.changeState(Arrow.MOVING);
        res = handler.handle(engine, boulder, arrow);
        assertEquals(res.getFlags(), CollisionResult.DELETE_SECOND);

        assertEquals(door.getState(), Door.CLOSED);
        res = handler.handle(engine, arrow, door);
        assertEquals(res.getFlags(), CollisionResult.DELETE_FIRST);
	}

	@Test
    public void arrowHitMonsterTest() {
        handler = new ArrowMonsterCollisionHandler();
        arrow.changeState(Arrow.MOVING);

        Monster monster = new Hunter(new Point(1, 1));
        res = handler.handle(engine, arrow, monster);
        assertTrue(res.containFlag(CollisionResult.DELETE_BOTH));
    }


	@Test
    public void playerShootArrowTest() {
	    // if player does not have an arrow
	    assertNull(player.shootArrow(map));

	    // collect arrow
        handler = new CollectablesCollisionHandler();
        handler.handle(engine, player, arrow);

        assertEquals(player.shootArrow(map), arrow);
        assertTrue(arrow.getSpeed() > 0);
        assertEquals(player.getFacing(), arrow.getFacing());
        assertEquals(player.getFrontGrid(map), arrow.getLocation());
    }
}
