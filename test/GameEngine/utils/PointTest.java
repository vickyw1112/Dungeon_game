package GameEngine.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PointTest {
    private Point p1;
    private Point p2;
    private Point p3;
    private Point p5;

    @Before
    public void setUp(){
        p1 = new Point(1, 2);
        p2 = new Point(3, 4);
    }

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
        p3 = p2.clone();
        p5 = new Point(3, 4);
        assertNotEquals(p1, p2);
        assertEquals(p3, p2);
        assertEquals(p5, p3);
    }
    
    @Test
    public void translateTest() {
        p1.translate(2, 2);
        assertEquals(p1.getX(), 3);
        assertEquals(p1.getY(), 4);
    }
}
