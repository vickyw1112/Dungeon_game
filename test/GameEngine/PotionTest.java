package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.PlayerEffect;
import org.junit.Before;
import org.junit.Test;
import GameEngine.utils.Point;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PotionTest {
    private GameEngine ge;
    private Player player;
    private Map map;
    private CollisionHandler handler;
    private Potion potion;

    @Before
    public void setup(){
        map = new Map();
        ge = new GameEngine(map);
        player = new Player(new Point(1, 1));
        handler = new PlayerPotionCollisionHandler();
        player.initialize();
    }

    @Test
    public void testPlayerHoverPotion() {
        potion = new HoverPotion(new Point(0, 1));

        handler.handle(ge, player, potion);
        assertTrue(player.getPlayerEffects().contains(PlayerEffect.HOVER));
    }

    @Test
    public void testPlayerInvinciblePotion() {
        potion = new InvinciblePotion(new Point(2, 1));

        handler.handle(ge, player, potion);
        assertTrue(player.getPlayerEffects().contains(PlayerEffect.INVINCIBLE));
    }

    @Test
    public void testRemovePotionEffect() {
        Potion potion1 = new InvinciblePotion(new Point(1, 1));
        Potion potion2 = new HoverPotion(new Point(2, 1));
        player.applyPotionEffect(ge, potion1);
        player.applyPotionEffect(ge, potion2);

        assertEquals(player.getPlayerEffects().size(), 2);

        player.removePotionEffect(ge, potion1);
        assertEquals(player.getPlayerEffects().size(), 1);
        assertTrue(player.getPlayerEffects().contains(PlayerEffect.HOVER));

        player.removePotionEffect(ge, potion2);
        assertEquals(player.getPlayerEffects().size(), 0);
    }

    // add test for player got invincible potion and monster all went away
    @Test
    public void testMonsterRunAwayUponPlayerGotInvinciblePotion() {
        MapBuilder mb = new MapBuilder();
        Monster m1 = new Hunter(new Point(0, 1));
        Monster m2 = new Strategist(new Point(1, 1));
        potion = new InvinciblePotion(new Point(2, 2));
        player.setLocation(new Point(0,0));
        mb.addObject(m1);
        mb.addObject(m2);
        mb.addObject(player);
        ge = new GameEngine(new Map(mb));

        // player collide with invincible potion
        handler.handle(ge, player, potion);
        //ge.updateMonstersPath();
        assertTrue(m1.pathGenerator instanceof RunAwayPathGenerator);
        assertTrue(m2.pathGenerator instanceof RunAwayPathGenerator);

        player.removePotionEffect(ge, potion);
        assertEquals(m1.pathGenerator.getClass(), m1.getDefaultPathGenerator().getClass());
        assertEquals(m2.pathGenerator.getClass(), m2.getDefaultPathGenerator().getClass());
    }
}
