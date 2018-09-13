package GameEngine;

import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Test;
import GameEngine.CollisionHandler.*;

public class ArrowTest {

	/**
	 * Construction unit test, test the created arrow location is set to the right location
	 * @throws Exception
	 */
	@Test
	public void constructorTest() throws Exception {
		Arrow arrow = new Arrow(new Point(1,1));
		Point p = new Point(1,1);
		assertEquals(arrow.getLocation(), p);
	}
	
	/**
	 * Checks arrow state after it get created
	 * @throws Exception
	 */
	@Test
	public void checkStateTest() throws Exception {
		Arrow arrow = new Arrow(new Point(1,1));
		assertEquals(arrow.getState(), Arrow.COLLECTABLESTATE);
	}
	
	/**
	 * Test for getCollected method
	 * @throws Exception
	 */
	@Test
	public void getCollectedTest() throws Exception {
		
		Arrow arrow = new Arrow(new Point(1,1));
		assertEquals(arrow.getState(), Arrow.COLLECTABLESTATE);
		GameEngine engine = new GameEngine(new Map());
		Player p = new Player(new Point(2,2));
		Inventory inv = new Inventory();
		arrow.getCollected(engine, inv);
		
		assertTrue(inv.contains(arrow));
		assertEquals(inv.getCount(arrow.getClassName()), 1);
	}
	
	/**
	 * Collision Handler for arrow 
	 * @throws Exception
	 */
	@Test
	public void arrowCollisionHandlerTest() throws Exception {
		
		GameEngine engine = new GameEngine(new Map());
		Player p = new Player(new Point(2,2));
		Inventory inv = new Inventory();
		
		Boulder boulder = new Boulder(new Point(5,5));
		Arrow arrow1 = new Arrow(new Point(1,1));
		
		CollisionEntities bMV = new CollisionEntities(Arrow.class, Boulder.class);
        CollisionEntities bMV1 = new CollisionEntities(Arrow.class, Monster.class);
        CollisionEntities bMV2 = new CollisionEntities(Arrow.class, Door.class);
        arrow1.registerCollisionHandler(engine);
        
        CollisionHandler ch = engine.getCollisionHandler(bMV);
        CollisionResult res = ch.handle(engine, arrow1, boulder);
        assertEquals(res.getFlags(), CollisionResult.HANDLED);
        res = ch.handle(engine,arrow1,boulder);
        
        arrow1.changeState(Arrow.MOVING);
        res = ch.handle(engine, arrow1, boulder);
        assertEquals(res.getFlags(), CollisionResult.DELETE_FIRST);
        
        Door door = new Door(new Point(6,6));
        res = ch.handle(engine, arrow1, door);
        assertEquals(res.getFlags(), CollisionResult.DELETE_FIRST);
        
	}

}
