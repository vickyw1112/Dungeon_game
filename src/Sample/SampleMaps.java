package Sample;

import GameEngine.*;
import GameEngine.utils.*;

public class SampleMaps {
    public static Map getMap1(){
        Monster hunter;
        Wall wall0;
        Wall wall1;
        Wall wall2;
        Wall wall4;
        Wall wall5;
        Wall wall6;
        Wall wall7;
        Wall wall8;
        Boulder boulder1;
        Boulder boulder2;
        MapBuilder mb;
        Player player;

        mb = new MapBuilder();
        player = new Player(new Point(0, 0));
        wall0 = new Wall(new Point(0,1));
        wall1 = new Wall(new Point(1,1));
        wall2 = new Wall(new Point(2,1));
        wall4 = new Wall(new Point(4,1));
        wall5 = new Wall(new Point(5, 1));
        wall6 = new Wall(new Point(6, 1));
        wall7 = new Wall(new Point(7,1 ));
        wall8 = new Wall(new Point(8,1 ));
        boulder1 = new Boulder(new Point(3, 1));
        boulder2 = new Boulder(new Point(9, 1));
        hunter = new Hunter(new Point(0,9));

        Hound hound = new Hound(new Point(4, 9));
        hound.setPair(hunter);
        Coward coward = new Coward(new Point(1, 9));
        Strategist strategist = new Strategist(new Point(3, 9));
        Sword sword = new Sword(new Point(1, 0));
        Arrow arrow = new Arrow(new Point(2, 0));
        Bomb bomb = new Bomb(new Point(3, 0));
        Exit exit = new Exit(new Point(9, 9));


        mb.addObject(player);
        mb.addObject(exit);
        mb.addObject(bomb);
        mb.addObject(hound);
        mb.addObject(sword);
        mb.addObject(arrow);
        mb.addObject(coward);
        mb.addObject(strategist);
        mb.addObject(wall0);
        mb.addObject(wall1);
        mb.addObject(wall2);
        mb.addObject(wall4);
        mb.addObject(wall5);
        mb.addObject(wall6);
        mb.addObject(wall7);
        mb.addObject(wall8);
        mb.addObject(boulder1);
        mb.addObject(boulder2);
        mb.addObject(hunter);
        return new Map(mb);
    }
}
