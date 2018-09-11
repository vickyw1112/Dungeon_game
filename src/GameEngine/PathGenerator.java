package GameEngine;

import java.util.LinkedList;

public interface PathGenerator {
    public LinkedList<Point> generatePath(Map map, Monster monster, Point target);

}
