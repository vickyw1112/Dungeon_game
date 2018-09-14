package GameEngine;

import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Test;

public class KeyUnitTest {

	/**
	 * Test getCollected method in Key
     * player can only carrie one key at a time
	 */
	@Test
	public void getCollectedTest() {
		Key key = new Key(new Point(1,1));
		GameEngine engine = new GameEngine(new Map());
		Inventory inv = new Inventory();
		assertTrue(key.getCollected(engine, inv));
		
		assertTrue(inv.contains(key));
		assertEquals(inv.getCount(key.getClass()), 1);

		// try to pick up second key
		assertFalse(key.getCollected(engine, inv));
        assertEquals(inv.getCount(key.getClass()), 1);
	}

}
