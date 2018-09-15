package GameEngine;

import static org.junit.Assert.*;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class BombTest {
    private Bomb bomb;
    private Player player;
    private GameEngine engine;
    private CollisionHandler handler;
    private Map map;

    @Before
    public void setUp(){
        MapBuilder mb = new MapBuilder();
        bomb = new Bomb(new Point(1, 1));
        player = new Player(new Point(2, 2));
        mb.addObject(bomb);
        mb.addObject(player);
        map = new Map(mb);
        engine = new GameEngine(map);
        handler = new CollectablesCollisionHandler();
        player.initialize();
    }

    @Test
    public void getCollectedTest() {
        assertEquals(bomb.getState(), Bomb.COLLECTABLESTATE);
        handler.handle(engine, player, bomb);

        assertTrue(player.getInventory().contains(bomb));
        assertEquals(player.getInventory().getCount(bomb.getClass()), 1);

        Bomb bomb2 = new Bomb(new Point(1, 1));
        handler.handle(engine, player, bomb2);

        assertTrue(player.getInventory().contains(bomb2));
        assertEquals(player.getInventory().getCount(Bomb.class), 2);

    }

    @Test
    public void playerSetUpBombTest(){
        assertNull(player.setBomb(map));

        // collect bomb
        handler.handle(engine, player, bomb);

        // player will set the bomb just collected
        assertEquals(player.setBomb(map), bomb);

        assertTrue(map.getObjects(player.getFrontGrid(map)).contains(bomb));
        assertNotEquals(bomb.getState(), Bomb.COLLECTABLESTATE);
    }


    /**
     * The front end requests the bomb to explode
     * Then the backend returns the new elements get destroyed
     */
    @Test
    public void testBombDestroy() {
        MapBuilder mapBuilder = new MapBuilder();
        Boulder boulder = new Boulder(new Point(5, 5));
        Boulder boulder2 = new Boulder(new Point(5, 3));
        Wall wall = new Wall(new Point(4, 4));

        mapBuilder.addObject(boulder);
        mapBuilder.addObject(boulder2);
        mapBuilder.addObject(bomb);
        mapBuilder.addObject(wall);

        engine = new GameEngine(new Map(mapBuilder));

        // player set bomb to (5, 4)
        engine.changeObjectLocation(bomb, new Point(5, 4));


        // this should return 2 things to destroy.
        List<GameObject> list = bomb.explode(engine);


        // Should have added boulders to the list of things to delete
        assertEquals(list.size(), 2);
        assertTrue(list.contains(boulder));
        assertTrue(list.contains(boulder2));

        // Boulder should be removed from map
        assertFalse(engine.getMap().getObjects(new Point(5, 5)).contains(boulder));

    }
}

