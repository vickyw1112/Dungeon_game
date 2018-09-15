package GameEngine;

import GameEngine.CollisionHandler.CollisionHandler;
import GameEngine.CollisionHandler.PlayerPotionCollisionHandler;
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
    private CollisionHandler hanlder;

    @Before
    public void setup(){
        map = new Map();
        ge = new GameEngine(map);
        player = new Player(new Point(1, 1));
        hanlder = new PlayerPotionCollisionHandler();
        player.initialize();
    }

    @Test
    public void testPlayerGetHoverPotion() {
        Potion potion = new HoverPotion(new Point(0, 1));

        hanlder.handle(ge, player, potion);
        assertTrue(player.getPlayerEffects().contains(PlayerEffect.HOVER));
    }

    @Test
    public void testPlayerGetInvinciblePotion() {
        Potion potion = new InvinciblePotion(new Point(2, 1));

        hanlder.handle(ge, player, potion);
        assertTrue(player.getPlayerEffects().contains(PlayerEffect.INVINCIBLE));
    }

    @Test
    public void testRemovePlayerEffect() {
        testPlayerGetHoverPotion();
        testPlayerGetInvinciblePotion();

        assertEquals(player.getPlayerEffects().size(), 2);

        player.removePlyaerEffect(PlayerEffect.HOVER);
        assertEquals(player.getPlayerEffects().size(), 1);
        assertTrue(player.getPlayerEffects().contains(PlayerEffect.INVINCIBLE));

        player.removePlyaerEffect(PlayerEffect.INVINCIBLE);
        assertEquals(player.getPlayerEffects().size(), 0);
    }
}
