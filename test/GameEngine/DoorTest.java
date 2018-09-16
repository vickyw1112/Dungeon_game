package GameEngine;

import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;

import GameEngine.CollisionHandler.*;

public class DoorTest {
    private Point p1 = new Point(1, 2);
    private Point p2 = new Point(3, 4);
    Key key1;
    Key key2;
    Player player;
    Door door;
    GameEngine engine;

    @Before
    public void setup(){
        key1 = new Key(p1);
        player = new Player(new Point(1, 1));
        door = new Door(new Point(2, 2));
        key2 = new Key(p2);
        player.initialize();
        engine = new GameEngine(new Map());
    }

    
    // check when door and key are collision
    @Test
    public void doorAndKeyMatch() {

        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.addObject(player);
        mapBuilder.addObject(key1);
        mapBuilder.addObject(door);

        GameEngine engine = new GameEngine(new Map(mapBuilder));
        door.setKey(key1);
        player.getInventory().addObject(key1);
        player.registerCollisionHandler(engine);
        door.registerCollisionHandler(engine);
        assertEquals(door.getState(), Door.CLOSED);
        
        CollisionHandler ch1 = new DoorPlayerCollisionHandler();
        // test collision results on player and door
        CollisionResult cr1 = ch1.handle(engine, player, door);
        assertEquals(cr1.getFlags(), CollisionResult.REJECT);
        assertEquals(door.getState(), Door.OPEN);
    }
    
    @Test
    public void doorAndKeyNotMatch() {
        door.setKey(key2);

        player.getInventory().addObject(key1);
        player.registerCollisionHandler(engine);
        door.registerCollisionHandler(engine);
        
        CollisionHandler ch1 = new DoorPlayerCollisionHandler();
        // test collision results on player and door
        CollisionResult cr1 = ch1.handle(engine, player, door);
        assertEquals(cr1.getFlags(), CollisionResult.REJECT);
        assertEquals(door.getState(), Door.CLOSED);
    }

    @Test
    public void closedDoorBlockBoulderTest(){
        CollisionHandler handler = new DoorMovableCollisionHandler();
        Boulder boulder = new Boulder(new Point(1, 1));

        assertEquals(door.getState(), Door.CLOSED);
        assertEquals(handler.handle(engine, boulder, door).getFlags(), CollisionResult.REJECT);

        door.changeState(Door.OPEN);

        assertEquals(door.getState(), Door.OPEN);
        assertEquals(handler.handle(engine, boulder, door).getFlags(), CollisionResult.HANDLED);
    }
}
