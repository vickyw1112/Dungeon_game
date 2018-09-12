package GameEngineTest;

import org.junit.Test;
import static org.junit.Assert.*;
import GameEngine.*;

public class CollectablesCollisionHandlerTest {

	/**
	 * Checks the CollectablesCollisionHandler method
	 * @throws Exception
	 */
	@Test
	public void collectablesCollisionHandlerTest() throws Exception {
		GameEngine engine = new GameEngine("CHESTER");
		Player p = new Player(new Point(1,1));
		
		Arrow arrow = new Arrow(new Point(1,1));
		
		CollisionEntities cPH = new CollisionEntities(arrow.getClass(), p.getClass());
        arrow.registerCollisionHandler(engine);
        p.registerCollisionHandler(engine);
        
        CollisionHandler ch = new CollectablesCollisionHandler();
        CollisionResult res = ch.handle(engine, arrow, p);
        assertEquals(res.getFlags(), CollisionResult.REFRESH_INVENTORY | CollisionResult.DELETE_FIRST);
        res = ch.handle(engine, p, arrow);
        assertEquals(res.getFlags(), CollisionResult.REFRESH_INVENTORY | CollisionResult.DELETE_SECOND);
	}
}
