package GameEngine;

import GameEngine.CollisionHandler.CollisionEntities;
import GameEngine.CollisionHandler.CollisionHandler;
import GameEngine.CollisionHandler.CollisionHandlerNotImplement;
import GameEngine.CollisionHandler.CollisionResult;
import GameEngine.utils.PlayerEffect;
import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

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
        assertEquals(res.containFlag(CollisionResult.DELETE_BOTH), true);
        assertEquals(res.containFlag(CollisionResult.WIN), true);
    }

    @Test
    public void PlayerMonsterNoPotionTest() throws CollisionHandlerNotImplement {
        // no sword
        CollisionEntities ent = new CollisionEntities(Monster.class, Player.class);
        CollisionHandler handler = ge.getCollisionHandler(ent);
        CollisionResult res = handler.handle(ge, hunter, player);
        assertEquals(res.containFlag(CollisionResult.LOSE), true);

        // have swords
        player.getInventory().addObject(sword);
        ent = new CollisionEntities(Monster.class, Player.class);
        handler = ge.getCollisionHandler(ent);
        res = handler.handle(ge, hunter, player);
        assertEquals(res.containFlag(CollisionResult.DELETE_FIRST), true);
        assertEquals(res.containFlag(CollisionResult.WIN), true);
    }

    @Test
    public void PlayerPotionMonsterTest() throws CollisionHandlerNotImplement {
        player.addPlayerEffect(PlayerEffect.INVINCIBLE);
        CollisionEntities ent = new CollisionEntities(Monster.class, Player.class);
        CollisionHandler handler = ge.getCollisionHandler(ent);
        CollisionResult res = handler.handle(ge, hunter, player);
        assertEquals(res.containFlag(CollisionResult.DELETE_FIRST), true);
        assertEquals(res.containFlag(CollisionResult.WIN), true);
        assertEquals(res.containFlag(CollisionResult.LOSE), false);
    }
}
