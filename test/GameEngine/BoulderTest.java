package GameEngine;
import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;
import GameEngine.CollisionHandler.*;


public class BoulderTest {
    private GameEngine ge;
    private Boulder boulder;
    private Player player;

    @Before
    public void setup(){
        ge = new GameEngine();
        boulder = new Boulder(new Point(0, 1));
        player = new Player(new Point(1, 1));
    }

    /**
     * Test that the boulder collides with a wall and is rejected when attempted to
     */
    @Test
    public void testBoulderCollisionWall() {
        Wall wall = new Wall(new Point(1, 2));
        wall.registerCollisionHandler(ge);

        CollisionHandler ch = new GameObjectMovableCollisionHandler();
        CollisionResult res = ch.handle(ge, wall, boulder);

        // tests that Wall reject Boulder
        assertEquals(res.getFlags(), CollisionResult.REJECT);
    }


    /**
     * Test that boulder collides with any collectable object and it should result in handle
     */
    @Test
    public void testBoulderGoOverCollectable() throws CollisionHandlerNotImplement{
        GameEngine ge = new GameEngine(new Map());
        Key key = new Key(new Point(1,1));
        Boulder boulder = new Boulder(new Point(1,1));
        boulder.registerCollisionHandler(ge);

        CollisionEntities ce1 = new CollisionEntities(Boulder.class, Key.class);
        CollisionHandler ch = ge.getCollisionHandler(ce1);
        CollisionResult res = ch.handle(ge, key, boulder);

        // Boulders should just go over a collectable item (keys and such)
        assertEquals(res.getFlags(), CollisionResult.HANDLED);
    }

    /**
     * Testing that the boulder disappears when entering a pit
     * @throws CollisionHandlerNotImplement
     */

    @Test
    public void testBoulderCollisionPit() {
        GameEngine ge = new GameEngine(new Map());
        Pit pit = new Pit(new Point(1,1));
        Boulder boulder = new Boulder(new Point(1,1));
        boulder.registerCollisionHandler(ge);

        CollisionHandler ch = new BoulderPitCollisionHandler();
        CollisionResult res = ch.handle(ge, boulder, pit);

        // tests that a boulder disappears when collided with pit
        assertEquals(res.getFlags(), CollisionResult.DELETE_FIRST);
    }

    /**
     * When Player collide with boulder,
     * it should have a speed and a facing same as player
     */
    @Test
    public void testPlayerPushBoulder() {
        Boulder boulder = new Boulder(new Point(1,1));
        player.registerCollisionHandler(ge);
        boulder.registerCollisionHandler(ge);

        assertTrue(boulder.getSpeed() == 0);
        CollisionHandler ch = new PlayerBoulderCollisionHandler();
        CollisionResult res = ch.handle(ge, boulder, player);

        assertEquals(res.getFlags(), CollisionResult.REJECT);
        assertTrue(boulder.getSpeed() > 0);
        assertEquals(player.getFacing(), boulder.getFacing());
    }

    @Test
    public void testBoulderCanGoOverSwitch() throws CollisionHandlerNotImplement {
        FloorSwitch _switch = new FloorSwitch(new Point(1, 9));
        CollisionEntities ent = new CollisionEntities(_switch.getClass(), boulder.getClass());
        CollisionHandler handler = ge.getCollisionHandler(ent);
        assertEquals(handler.handle(ge, _switch, boulder).getFlags(), CollisionResult.HANDLED);
    }

    @Test
    public void testBoulderCollideMonster(){
        Monster monster = new Hunter(new Point(4, 1));
        CollisionHandler handler = new BoulderMonsterCollisionHandler();
        assertEquals(handler.handle(ge, monster, boulder).getFlags(), CollisionResult.REJECT);
    }

    // Boulder Door covered in DoorTest#closedDoorBlockingTest
}