package GameEngine.utils;

import static org.junit.Assert.*;

import org.junit.Test;


public class PointTest {
    Point p1 = new Point(1, 2);
    Point p2 = new Point(3, 4);

    @Test
    public void getterAndSettertest() {
        assertEquals(p1.getX(), 1);
        assertEquals(p1.getY(), 2);
        p1.move(3, 4);
        assertEquals(p1.getX(), 3);
        assertEquals(p1.getY(), 4);
    }
    
    @Test
    public void equalAndCloneTest() {
        Point p3 = p1.clone();
        assertEquals(p1.equals(p2), true);
        assertEquals(p3.equals(p2), true);
    }
    
    @Test
    public void translateTest() {
        p1.translate(2, 2);
        assertEquals(p1.getX(), 5);
        assertEquals(p1.getY(), 6);
    }
}
