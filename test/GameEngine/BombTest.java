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

    @Before
    public void setUp(){
        bomb = new Bomb(new Point(1, 1));
        engine = new GameEngine(new Map());
        player = new Player(new Point(2, 2));
        player.initialize();
    }

    @Test
    public void getCollectedTest() {
        assertEquals(bomb.getState(), Bomb.COLLECTABLESTATE);
        bomb.getCollected(engine, player.getInventory());

        assertTrue(player.getInventory().contains(bomb));
        assertEquals(player.getInventory().getCount(bomb.getClass()), 1);
    }

    @Test
    public void testBombRetrieval() {
        // when a bomb is retrieved check inventory goes up
        // check the states of the bomb as well

        // checking a new bomb state starts of UNLIT
        assertEquals(bomb.getState(), Bomb.COLLECTABLESTATE);

        // instance of when a new bomb collides with a player
        CollisionHandler ch1 = new CollectablesCollisionHandler();
        CollisionResult result = ch1.handle(engine, bomb, player);
        assertEquals(result.getFlags(), CollisionResult.DELETE_FIRST | CollisionResult.REFRESH_INVENTORY);
        assertTrue(player.getInventory().contains(bomb));
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

        mapBuilder.addObject(boulder);
        mapBuilder.addObject(boulder2);
        mapBuilder.addObject(bomb);

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

