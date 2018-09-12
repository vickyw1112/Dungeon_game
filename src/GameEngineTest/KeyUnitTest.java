package GameEngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import GameEngine.*;

public class KeyUnitTest {

	/**
	 * Test getCollected method in Key
	 * @throws Exception
	 */
	@Test
	public void getCollectedTest() throws Exception {
		Key key = new Key(new Point(1,1));
		Player p = new Player(new Point(2,2));
		GameEngine engine = new GameEngine("CHESTER");
		Inventory inv = new Inventory();
		key.getCollected(engine, inv);
		
		assertTrue(inv.contains(key));
		assertEquals(inv.getCount(key.getClassName()), 1);
	}

}
