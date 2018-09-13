package GameEngine;

import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Test;
import java.util.List;

    public class BombTest {

        @Test
        public void getCollectedTest() throws Exception {

            Bomb bomb = new Bomb(new Point(1, 1));
            assertEquals(bomb.getState(), Bomb.COLLECTABLESTATE);
            GameEngine engine = new GameEngine(new Map());
            Player p = new Player(new Point(2, 2));
            Inventory inv = new Inventory();
            bomb.getCollected(engine, inv);

            assertTrue(inv.contains(bomb));
            assertEquals(inv.getCount(bomb.getClassName()), 1);
        }

        @Test
        public void testBombRetrieval() {
            // when a bomb is retrieved check inventory goes up
            // check the states of the bomb as well

            GameEngine ge = new GameEngine(new Map());
            Player p = new Player(new Point(1, 2));
            Bomb b = new Bomb(new Point(1, 2));
            p.registerCollisionHandler(ge);

            // checking a new bomb state starts of UNLIT
            assertEquals(b.getState(), 0);

            // instance of when a new bomb collides with a player
            //CollisionEntities ce1 = new CollisionEntities(Player.class, Bomb.class);
            //CollisionHandler ch1 = ge.getCollisionHandler(ce1);
            //CollisionResult cr1 = ch1.handle(ge, b, p);

        }


        /**
         * The front end requests the bomb destroy
         * Then the backend returns the new elements for now
         */
        @Test
        public void testBombDestroy() {

            Map m = new Map();
            GameEngine ge = new GameEngine(m);

            Point p1 = new Point(1,1);
            Point p2 = new Point(1,2);
           // Point p3 = new Point(2, 1);

            Bomb bomb = new Bomb(p1);
            Boulder boulder = new Boulder(p2);
          //  Player player = new Player (p3);

            m.updateObjectLocation(bomb, p1);
            m.updateObjectLocation(boulder, p2);
           // m.updateObjectLocation(player, p3);


            // this should return 2 things to destroy.
            List<GameObject> list = bomb.explode(ge, m);
            System.out.println(list);

            // Should have added boulder to the list of things to delete
            assertEquals(list.size(), 1);
            // need to consider edge cases (wall next to bomb)



        }


    }

