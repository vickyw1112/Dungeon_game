package GameEngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import GameEngine.*;
import GameEngine.CollisionHandler.*;

public class OpenDoorTest {
    
    // check when door and key are collision
    @Test
    public void doorAndKeyMatch() throws CollisionHandlerNotImplement {
        Point p1 = new Point(1, 2);
        Key key = new Key(p1);
        Player player = new Player(new Point(1, 1));
        Door door = new Door(new Point(2, 2));

        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.addObject(player);
        mapBuilder.addObject(key);
        mapBuilder.addObject(door);

        GameEngine ge = new GameEngine(new Map(mapBuilder));
        door.setKey(key);
        player.getInventory().addObject(key);
        player.registerCollisionHandler(ge);
        assertEquals(door.getState(), Door.CLOSED);
        
        CollisionEntities ce1 = new CollisionEntities(Player.class, Door.class);  
        CollisionHandler ch1 = ge.getCollisionHandler(ce1);
        // test collision results on player and door
        CollisionResult cr1 = ch1.handle(ge, player, door);
        assertEquals(cr1.getFlags(), CollisionResult.HANDLED);
        assertEquals(door.getState(), Door.OPEN);
    }
    
    @Test
    public void doorAndKeyNotMatch() throws CollisionHandlerNotImplement {
        GameEngine ge = new GameEngine(new Map());
        Point p2 = new Point(3, 4);
        Key k2 = new Key(p2);
        Player player = new Player(p2);
        Door door = new Door(p2);
        door.setKey(k2);
        player.getInventory().addObject(k2);
        player.registerCollisionHandler(ge);
        
        CollisionEntities ce1 = new CollisionEntities(Player.class, Door.class);  
        CollisionHandler ch1 = ge.getCollisionHandler(ce1);
        // test collision results on player and door
        CollisionResult cr1 = ch1.handle(ge, player, door);
        assertEquals(cr1.getFlags(), CollisionResult.HANDLED);
        assertEquals(door.getState(), Door.CLOSED);
    }
}
