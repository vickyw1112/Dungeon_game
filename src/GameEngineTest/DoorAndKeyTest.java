package GameEngineTest;

import static org.junit.Assert.*;

import org.junit.Test;

import GameEngine.Arrow;
import GameEngine.Boulder;
import GameEngine.CollisionEntities;
import GameEngine.CollisionHandler;
import GameEngine.CollisionHandlerNotImplement;
import GameEngine.CollisionResult;
import GameEngine.Door;
import GameEngine.GameEngine;
import GameEngine.Inventory;
import GameEngine.Key;
import GameEngine.Player;
import GameEngine.Point;
import GameEngine.Wall;

public class DoorAndKeyTest {
    
    // check when door and key are collision
    @Test
    public void doorAndKeyMatch() throws CollisionHandlerNotImplement {
        GameEngine ge = new GameEngine("yo");
        Point p1 = new Point(1, 2);
        Key k1 = new Key(p1);
        Player player = new Player(p1);
        Door door = new Door(p1);
        door.setKey(k1);
        player.getInventory().addObject(k1);
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
        GameEngine ge = new GameEngine("yo");
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
