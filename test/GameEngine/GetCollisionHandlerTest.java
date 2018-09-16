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
    static Hunter hunter;
    static Boulder boulder;
    static Wall wall;
    static Pit pit;
    static Door door;
    static Exit exit;

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
        boulder = new Boulder(new Point(1, 1));
        wall = new Wall(new Point(2,1));
        pit = new Pit(new Point(3,1));
        door = new Door(new Point(5,6));
        exit = new Exit(new Point(1,2));
        door.setKey(key);

        player.registerCollisionHandler(engine);
        key.registerCollisionHandler(engine);
        arrow.registerCollisionHandler(engine);
        sword.registerCollisionHandler(engine);
        treasure.registerCollisionHandler(engine);
        bomb.registerCollisionHandler(engine);
        hunter.registerCollisionHandler(engine);
        boulder.registerCollisionHandler(engine);
        wall.registerCollisionHandler(engine);
        pit.registerCollisionHandler(engine);
        door.registerCollisionHandler(engine);
        exit.registerCollisionHandler(engine);
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
        assertTrue(engine.getCollisionHandler(ArrowNPlayer) instanceof CollectablesCollisionHandler);
        
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
        assertTrue(engine.getCollisionHandler(ArrowNKey) instanceof ArrowGameObjectCollisionHandler);

        CollisionEntities KeyNM = new CollisionEntities(key.getClass(), hunter.getClass());
        assertTrue(engine.getCollisionHandler(KeyNM) instanceof GameObjectMovableCollisionHandler);
        
        CollisionEntities MANKEY = new CollisionEntities(arrow.getClass(), key.getClass());
        assertTrue(engine.getCollisionHandler(MANKEY) instanceof ArrowGameObjectCollisionHandler);
    }

    @Test
    public void playerBoulderHandlerTest() throws Exception {
        CollisionEntities ent = new CollisionEntities(player.getClass(), boulder.getClass());
        assertTrue(engine.getCollisionHandler(ent) instanceof PlayerBoulderCollisionHandler);
    }

    @Test
    public void ArrowGameObjectHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities AWent = new CollisionEntities(Arrow.class, Wall.class);
        assertTrue(engine.getCollisionHandler(AWent) instanceof ArrowGameObjectCollisionHandler);

        CollisionEntities ABent = new CollisionEntities(Arrow.class, Boulder.class);
        assertTrue(engine.getCollisionHandler(ABent) instanceof ArrowGameObjectCollisionHandler);

        CollisionEntities DAent = new CollisionEntities(Door.class, Arrow.class);
        assertTrue(engine.getCollisionHandler(DAent) instanceof DoorMovableCollisionHandler);
    }

    @Test
    public void ArrowMonsterHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities AMent = new CollisionEntities(Arrow.class, Monster.class);
        assertTrue(engine.getCollisionHandler(AMent) instanceof ArrowMonsterCollisionHandler);
    }

    @Test
    public void BoulderMonsterHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities ent = new CollisionEntities(Boulder.class, Monster.class);
        assertTrue(engine.getCollisionHandler(ent) instanceof BoulderMonsterCollisionHandler);
    }

    @Test
    public void BoulderPitHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities ent = new CollisionEntities(Boulder.class, Pit.class);
        assertTrue(engine.getCollisionHandler(ent) instanceof BoulderPitCollisionHandler);
    }

    @Test
    public void GameObjectMovableHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities PAent = new CollisionEntities(Player.class, Arrow.class);
        assertTrue(engine.getCollisionHandler(PAent) instanceof CollectablesCollisionHandler);

        CollisionEntities PWent = new CollisionEntities(Player.class, Wall.class);
        assertTrue(engine.getCollisionHandler(PWent) instanceof GameObjectMovableCollisionHandler);

        CollisionEntities DBent = new CollisionEntities(Door.class, Boulder.class);
        assertTrue(engine.getCollisionHandler(DBent) instanceof DoorMovableCollisionHandler);

    }

    @Test
    public void PlayerBoulderHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities ent = new CollisionEntities(Player.class, Boulder.class);
        assertTrue(engine.getCollisionHandler(ent) instanceof PlayerBoulderCollisionHandler);
    }

    @Test
    public void DoorPlayerHandlerTest() throws CollisionHandlerNotImplement {
        player.initialize();
        player.addObjectToInventory(key);
        CollisionEntities ent = new CollisionEntities(Door.class, Player.class);
        assertTrue(engine.getCollisionHandler(ent) instanceof DoorPlayerCollisionHandler);
    }

    @Test
    public void PlayerMonsterHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities ent = new CollisionEntities(Player.class, Monster.class);
        assertTrue(engine.getCollisionHandler(ent) instanceof PlayerMonsterCollisionHandler);
    }

    @Test
    public void PlayerPitHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities ent = new CollisionEntities(Player.class, Pit.class);
        assertTrue(engine.getCollisionHandler(ent) instanceof PlayerPitCollisionHandler);
    }

    @Test
    public void PlayerPotionHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities ent = new CollisionEntities(Player.class, Potion.class);
        assertTrue(engine.getCollisionHandler(ent) instanceof PlayerPotionCollisionHandler);
    }

    @Test
     public void  WinCollisionHandlerTest() throws CollisionHandlerNotImplement {
        CollisionEntities playerExit = new CollisionEntities(player.getClass(), exit.getClass());
        assertTrue(engine.getCollisionHandler(playerExit) instanceof WinCollisionHandler);
    }

}
