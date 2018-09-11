package GameEngine;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrowUnitTest {

	@Test
	public void test() throws CollisionHandlerNotImplement {
		/**
		 * Creating a new arrow should be in a collectablestate
		 */
		Arrow arrow = new Arrow(new Point(1,1));
		assert(arrow.getState() == Arrow.COLLECTABLESTATE);
		
		/**
		 * Testing the getCollected function in Arrow.java
		 * Arrow should be gone?
		 */
		GameEngine engine = new GameEngine("CHESTER");
		Player p = new Player(new Point(2,2));
		Inventory inv = new Inventory();
		arrow.getCollected(engine, inv);
		GameObject.stateChanger = (GameObject obj, int state) -> {};
		
		/**
		 * Testing Collision Handler for Arrow/Moving arrow
		 */
		Boulder boulder = new Boulder(new Point(5,5));
		Arrow arrow1 = new Arrow(new Point(1,1));
		CollisionEntities bMV = new CollisionEntities(Arrow.class, Boulder.class);
        CollisionEntities bMV1 = new CollisionEntities(Arrow.class, Monster.class);
        CollisionEntities bMV2 = new CollisionEntities(Arrow.class, Door.class);
        arrow1.registerCollisionHandler(engine);
        
        CollisionHandler ch = engine.getCollisionHandler(bMV);
        CollisionResult res = ch.handle(engine, arrow1, boulder);
        assertEquals(res.getFlags(), CollisionResult.HANDLED);
        
        arrow1.changeState(Arrow.MOVING);
        res = ch.handle(engine, arrow1, boulder);
        assertEquals(res.getFlags(), CollisionResult.DELETE_FIRST);
        
        Door door = new Door(new Point(6,6));
        res = ch.handle(engine, arrow1, door);
        assertEquals(res.getFlags(), CollisionResult.DELETE_FIRST);
        
	}

}
