package GameEngine;
import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Test;
import GameEngine.CollisionHandler.*;



    public class BoulderTest {
            /**
             * Test that the boulder collides with a wall and is rejected when attempted to
             */
            @Test
            public void testBoulderCollisionWall() throws CollisionHandlerNotImplement {
                GameEngine ge = new GameEngine(new Map());
                Wall wall = new Wall(new Point(1, 2));
                Boulder boulder = new Boulder(new Point(1, 1));
                wall.registerCollisionHandler(ge);

                CollisionEntities ce1 = new CollisionEntities(Boulder.class, Wall.class);
                CollisionHandler ch = ge.getCollisionHandler(ce1);
                CollisionResult res = ch.handle(ge, wall, boulder);

                // tests thats a boulder rejects a wall
                assertEquals(res.getFlags(), CollisionResult.REJECT);
            }


            /**
             * Test that boulder collides with any collectable object and it should result in handle
             */

            @Test
            public void testBoulderCollisionCollectable() throws CollisionHandlerNotImplement{
                GameEngine ge = new GameEngine(new Map());
                Key key = new Key(new Point(1,1));
                Boulder boulder = new Boulder(new Point(1,1));
                boulder.registerCollisionHandler(ge);

                CollisionEntities ce1 = new CollisionEntities(Boulder.class, Key.class);
                CollisionHandler ch = ge.getCollisionHandler(ce1);
                CollisionResult res = ch.handle(ge, key, boulder);

                // Boulders should just go over a collectable item (keys and such)
                assertEquals(res.getFlags(), CollisionResult.HANDLED);
            }

            /**
             * Testing that the boulder disappears when entering a pit
             * @throws CollisionHandlerNotImplement
             */

            @Test
            public void testBoulderCollisionPit() throws CollisionHandlerNotImplement{
                GameEngine ge = new GameEngine(new Map());
                Pit pit = new Pit(new Point(1,1));
                Boulder boulder = new Boulder(new Point(1,1));
                boulder.registerCollisionHandler(ge);

                CollisionEntities ce1 = new CollisionEntities(Boulder.class, Pit.class);
                CollisionHandler ch = ge.getCollisionHandler(ce1);
                CollisionResult res = ch.handle(ge, boulder, pit);

                // tests that a boulder disappears when collided with pit
                assertEquals(res.getFlags(), CollisionResult.DELETE_FIRST);
                }


            /**
             * When a boulder collides with a player, the SPEED should be updated (not zero anymore)
              */

            @Test
            public void testBoulderSpeed() throws CollisionHandlerNotImplement{
                GameEngine ge = new GameEngine(new Map());
                Player player = new Player(new Point(1,1));
                Boulder boulder = new Boulder(new Point(1,1));
                player.registerCollisionHandler(ge);
                boulder.registerCollisionHandler(ge);

                assertTrue(boulder.getSpeed() == 0);
                CollisionEntities ce1 = new CollisionEntities(Boulder.class, Player.class);
                CollisionHandler ch = ge.getCollisionHandler(ce1);
                CollisionResult res = ch.handle(ge, boulder, player);

                assertTrue(boulder.getSpeed() > 0);

            }

            /*
            TODO: Test that direction changes when player pushes (front end implementation)
            @Test
            public void testDirection() throws CollisionHandlerNotImplement{
                fail("Not yet Implemented");
            }
            */

        }
