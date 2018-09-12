package GameEngine;

import GameEngine.utils.Point;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import GameEngine.CollisionHandler.*;

public class GetCollisionHandlerTest {
    static GameEngine engine;
    static Player player;
    static Key key;
    static Arrow arrow;
    static Sword sword;
    static Treasure treasure;
    static Bomb bomb;
    static Hunter hunter = new Hunter(new Point(1,1));

    @BeforeClass
    static public void beforeTest(){
        engine = new GameEngine(new Map());
        player = new Player(new Point(5,5));
        key = new Key(new Point(1,1));
        arrow = new Arrow(new Point(2,2));
        sword = new Sword(new Point(3,3));
        treasure = new Treasure(new Point(4,4));
        bomb = new Bomb(new Point(1,1));
        hunter = new Hunter(new Point(1,1));

        player.registerCollisionHandler(engine);
        key.registerCollisionHandler(engine);
        arrow.registerCollisionHandler(engine);
        sword.registerCollisionHandler(engine);
        treasure.registerCollisionHandler(engine);
        bomb.registerCollisionHandler(engine);
        hunter.registerCollisionHandler(engine);
    }

    /**
     * Checks the Output of getCollisionHandler
     * checking whether running different objects into the getCollisionHandler
     * would output the right fallback
     * @throws Exception
     */
    @Test
    public void CollectableHandlerTest() throws Exception {
        CollisionEntities KeyNPlayer = new CollisionEntities(key.getClass(), player.getClass());
        assertTrue(engine.getCollisionHandler(KeyNPlayer) instanceof CollectablesCollisionHandler);

        CollisionEntities ArrowNPlayer = new CollisionEntities(arrow.getClass(), player.getClass());
        // TODO: fix this - player/arrow have a specific collision handler
        // assertTrue(engine.getCollisionHandler(ArrowNPlayer) instanceof CollectablesCollisionHandler);
        
        CollisionEntities SwordNPlayer = new CollisionEntities(sword.getClass(), player.getClass());
        assertTrue(engine.getCollisionHandler(SwordNPlayer) instanceof CollectablesCollisionHandler);
        
        CollisionEntities TNPlayer = new CollisionEntities(treasure.getClass(), player.getClass());
        assertTrue(engine.getCollisionHandler(TNPlayer) instanceof CollectablesCollisionHandler);
        
        CollisionEntities BNPlayer = new CollisionEntities(bomb.getClass(), player.getClass());
        assertTrue(engine.getCollisionHandler(BNPlayer) instanceof CollectablesCollisionHandler);
    }

    /**
     * Check that the getCollisionHandler method gives the default handler correctly
     * @throws Exception
     */
    @Test
    public void DefaultHandlerTest() throws Exception {
        CollisionEntities ArrowNKey = new CollisionEntities(key.getClass(), arrow.getClass());
        assertTrue(engine.getCollisionHandler(ArrowNKey) instanceof DefaultHandler);

        CollisionEntities KeyNM = new CollisionEntities(key.getClass(), hunter.getClass());
        assertTrue(engine.getCollisionHandler(KeyNM) instanceof DefaultHandler);
        
        CollisionEntities MANKEY = new CollisionEntities(arrow.getClass(), key.getClass());
        assertTrue(engine.getCollisionHandler(MANKEY) instanceof DefaultHandler);
    }
    
    
}
