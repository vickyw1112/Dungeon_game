package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.CollisionHandler.CollisionHandler;
import GameEngine.CollisionHandler.CollisionHandlerNotImplement;
import GameEngine.CollisionHandler.CollisionResult;
import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class MonsterCollisionTests {
    private GameEngine ge;
    private Player player;
    private Arrow arrow;
    private Sword sword;
    private MapBuilder mb;
    private Monster hunter;


    @Before
    public void setUp(){
        player = new Player(new Point(1, 1));
        arrow = new Arrow(new Point(2, 1));
        hunter = new Hunter(new Point(3, 3));
        sword = new Sword(new Point(3, 4));
        mb = new MapBuilder();
        mb.addObject(player);
        mb.addObject(hunter);

        ge = new GameEngine(new Map(mb));
        player.getInventory().addObject(arrow);

        player.registerCollisionHandler(ge);
        arrow.registerCollisionHandler(ge);
    }

    @Test
    public void ArrowMonsterTest() throws CollisionHandlerNotImplement {
        arrow.changeState(Arrow.MOVING);
        CollisionEntities ent = new CollisionEntities(Arrow.class, Monster.class);
        CollisionHandler handler = ge.getCollisionHandler(ent);
        CollisionResult res = handler.handle(ge, arrow, hunter);
        assertTrue(res.containFlag(CollisionResult.DELETE_BOTH));
        assertTrue(res.containFlag(CollisionResult.WIN));
    }

    @Test
    public void PlayerMonsterNoPotionTest() throws CollisionHandlerNotImplement {
        // no sword
        CollisionEntities ent = new CollisionEntities(Monster.class, Player.class);
        CollisionHandler handler = ge.getCollisionHandler(ent);
        CollisionResult res = handler.handle(ge, hunter, player);
        assertTrue(res.containFlag(CollisionResult.LOSE));

        // have swords
        player.getInventory().addObject(sword);
        ent = new CollisionEntities(Monster.class, Player.class);
        handler = ge.getCollisionHandler(ent);
        res = handler.handle(ge, hunter, player);
        assertTrue(res.containFlag(CollisionResult.DELETE_FIRST));
        assertTrue(res.containFlag(CollisionResult.WIN));
    }

    @Test
    public void PlayerPotionMonsterTest() throws CollisionHandlerNotImplement {
        Potion potion = new InvinciblePotion(new Point(1, 2));
        player.applyPotionEffect(ge, potion);

        CollisionEntities ent = new CollisionEntities(Monster.class, Player.class);
        CollisionHandler handler = ge.getCollisionHandler(ent);
        CollisionResult res = handler.handle(ge, hunter, player);
        assertTrue(res.containFlag(CollisionResult.DELETE_FIRST));
        assertTrue(res.containFlag(CollisionResult.WIN));
        assertFalse(res.containFlag(CollisionResult.LOSE));
    }
}
