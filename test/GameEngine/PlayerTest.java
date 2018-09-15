package GameEngine;

import static org.junit.Assert.*;

import GameEngine.utils.Direction;
import org.junit.Before;
import org.junit.Test;
import GameEngine.utils.Point;

public class PlayerTest {
    private GameEngine ge;
    private Player player;
    private Map map;

    @Before
    public void setup(){
        map = new Map();
        ge = new GameEngine(map);
        player = new Player(new Point(1, 1));
    }

    @Test
    public void testGetFrontGrid() {
        player.setFacing(Direction.RIGHT);
        assertEquals(player.getFrontGrid(map), new Point(2, 1));

        player.setFacing(Direction.UP);
        assertEquals(player.getFrontGrid(map), new Point(1, 0));

        player.setFacing(Direction.LEFT);
        assertEquals(player.getFrontGrid(map), new Point(0, 1));

        player.setFacing(Direction.RIGHT);
        assertEquals(player.getFrontGrid(map), new Point(2, 1));

        player.setLocation(new Point(0, 0));
        player.setFacing(Direction.UP);
        assertNull(player.getFrontGrid(map));

        player.setFacing(Direction.LEFT);
        assertNull(player.getFrontGrid(map));
    }
    // Wall block player movement tested in WallTest
}
