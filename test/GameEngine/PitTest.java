package GameEngine;

import static org.junit.Assert.*;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.PlayerEffect;
import GameEngine.utils.Point;
import org.junit.Before;
import org.junit.Test;

public class PitTest {
    private Pit pit;
    private Player player;
    private GameEngine engine;
    private CollisionHandler handler;

    @Before
    public void setup(){
        pit = new Pit(new Point(1, 1));
        player = new Player(new Point(2, 2));
        engine = new GameEngine();
        handler = new PlayerPitCollisionHandler();
        player.initialize();
    }

    @Test
    public void playFallInPitTest(){
        assertFalse(player.getPlayerEffects().contains(PlayerEffect.HOVER));
        assertEquals(handler.handle(engine, player, pit).getFlags(), CollisionResult.LOSE);
    }

    @Test
    public void playerGoOverPitTest() {
        Potion potion = new HoverPotion(new Point(1, 1));
        player.applyPotionEffect(engine, potion);

        assertTrue(player.getPlayerEffects().contains(PlayerEffect.HOVER));
        assertEquals(handler.handle(engine, player, pit).getFlags(), CollisionResult.HANDLED);
    }
}
