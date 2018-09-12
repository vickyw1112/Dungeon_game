package GameEngine;

import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Test;

public class KeyUnitTest {

	/**
	 * Test getCollected method in Key
	 * @throws Exception
	 */
	@Test
	public void getCollectedTest() {
		Key key = new Key(new Point(1,1));
		GameEngine engine = new GameEngine(new Map());
		Inventory inv = new Inventory();
		key.getCollected(engine, inv);
		
		assertTrue(inv.contains(key));
		assertEquals(inv.getCount(key.getClassName()), 1);
	}

}
