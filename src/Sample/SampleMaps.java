package Sample;

import GameEngine.*;
import GameEngine.utils.*;

public class SampleMaps {
    public static Map getMap1(){

        MapBuilder mb = new MapBuilder();
        Player player = new Player(new Point(0, 0));
        Wall wall0 = new Wall(new Point(0,1));
        Wall wall1 = new Wall(new Point(1,1));
        Wall wall2 = new Wall(new Point(2,1));
        Wall wall4 = new Wall(new Point(4,1));
        Wall wall5 = new Wall(new Point(5, 1));
        Wall wall6 = new Wall(new Point(6, 1));
        Wall wall7 = new Wall(new Point(7,1 ));
        Wall wall8 = new Wall(new Point(8,1 ));
        Boulder boulder1 = new Boulder(new Point(3, 1));
        Boulder boulder2 = new Boulder(new Point(9, 1));
        Hunter hunter = new Hunter(new Point(0,9));

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

    public static Map getMap2(){
        MapBuilder mb = new MapBuilder();
        for(int x = 2; x <= 6; x++){
            mb.addObject(new Wall(new Point(x, 1)));
            mb.addObject(new Wall(new Point(x, 3)));
        }
        Pit pit = new Pit(new Point(3, 2));
        mb.addObject(pit);
        Potion potion = new HoverPotion(new Point(4, 2));
        mb.addObject(potion);
        Potion potion2 = new InvinciblePotion(new Point(1, 2));
        mb.addObject(potion2);
        Player player = new Player(new Point(2, 2));
        mb.addObject(player);
        Hunter hunter = new Hunter(new Point(1, 5));
        mb.addObject(hunter);
        Hound hound = new Hound(new Point(1, 6));
        mb.addObject(hound);
        hound.setPair(hunter);
        return new Map(mb);
    }
}
