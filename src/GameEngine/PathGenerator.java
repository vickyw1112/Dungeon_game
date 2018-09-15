package GameEngine;

import GameEngine.utils.Point;

import java.util.LinkedList;

public interface PathGenerator {
    LinkedList<Point> generatePath(Map map, Monster monster, Point target);

}
