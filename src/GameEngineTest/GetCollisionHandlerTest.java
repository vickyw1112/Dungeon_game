package GameEngineTest;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import GameEngine.*;
import GameEngine.CollisionHandler.*;

public class GetCollisionHandlerTest {
    static GameEngine engine;
    static Player player;

    @BeforeClass
    static public void beforeTest(){
        engine = new GameEngine(new Map());
        player = new Player(new Point(5,5));
    }

    /**
     * Checks the Output of getCollisionHandler, checking whether running different objects into the getCollisionHandler would output the right fallback
     * @throws Exception
     */
    @Test
    public void OutputCollectableHandler() throws Exception {
        Key key = new Key(new Point(1,1));
        CollisionEntities KeyNPlayer = new CollisionEntities(key.getClass(), player.getClass());
        System.out.println(engine.getCollisionHandler(KeyNPlayer));
        assertTrue(engine.getCollisionHandler(KeyNPlayer) instanceof CollectablesCollisionHandler);
        
        Arrow arrow = new Arrow(new Point(2,2));
        CollisionEntities ArrowNPlayer = new CollisionEntities(arrow.getClass(), player.getClass());
        assertTrue(engine.getCollisionHandler(ArrowNPlayer) instanceof CollectablesCollisionHandler);
        
        Sword sword = new Sword(new Point(3,3));
        CollisionEntities SwordNPlayer = new CollisionEntities(sword.getClass(), player.getClass());
        assertTrue(engine.getCollisionHandler(SwordNPlayer) instanceof CollectablesCollisionHandler);
        
        Treasure t = new Treasure(new Point(4,4));
        CollisionEntities TNPlayer = new CollisionEntities(t.getClass(), player.getClass());
        assertTrue(engine.getCollisionHandler(TNPlayer) instanceof CollectablesCollisionHandler);
        
        Bomb b = new Bomb(new Point(1,1));
        CollisionEntities BNPlayer = new CollisionEntities(b.getClass(), player.getClass());
        assertTrue(engine.getCollisionHandler(BNPlayer) instanceof CollectablesCollisionHandler);
    }
    /**
     * Check that the getCollisionHandler method gives the default handler correctly
     * @throws Exception
     */
    @Test
    public void OutputDefaultHandler() throws Exception {
        Key key = new Key(new Point(1,1));
        Arrow arrow = new Arrow(new Point(2,2));
        CollisionEntities ArrowNKey = new CollisionEntities(key.getClass(), arrow.getClass());
        assertTrue(engine.getCollisionHandler(ArrowNKey) instanceof DefaultHandler);
        
        Player player2 = new Player(new Point(5,5));
        CollisionEntities PNP = new CollisionEntities(player.getClass(), player2.getClass());
        assertTrue(engine.getCollisionHandler(PNP) instanceof DefaultHandler);
        
        Hunter hunter = new Hunter(new Point(1,1));
        CollisionEntities KeyNM = new CollisionEntities(key.getClass(), hunter.getClass());
        assertTrue(engine.getCollisionHandler(KeyNM) instanceof DefaultHandler);
        
        arrow.changeState(Arrow.MOVING);
        CollisionEntities MANKEY = new CollisionEntities(arrow.getClass(), key.getClass());
        assertTrue(engine.getCollisionHandler(MANKEY) instanceof DefaultHandler);
    }
    
    
}
