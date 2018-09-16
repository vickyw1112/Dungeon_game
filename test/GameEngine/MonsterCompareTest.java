package GameEngine;

import static org.junit.Assert.*;

import GameEngine.utils.Point;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class MonsterCompareTest {
    @Test
    public void testMonsterCompare() {
        List<Monster> monsters = new LinkedList<>();
        monsters.add(new Hound(new Point(1, 3)));
        monsters.add(new Hunter(new Point(2, 3)));
        monsters.add(new Hound(new Point(1, 4)));
        monsters.add(new Hunter(new Point(2, 4)));
        // test this will sort so that hound is after hunter
        monsters.sort(Monster::compare);
        assertEquals(monsters.get(0).getClass(), Hunter.class);
        assertEquals(monsters.get(1).getClass(), Hunter.class);
        assertEquals(monsters.get(2).getClass(), Hound.class);
        assertEquals(monsters.get(3).getClass(), Hound.class);
    }
}
