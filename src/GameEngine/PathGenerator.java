package GameEngine;

import GameEngine.utils.Point;

import java.util.LinkedList;

/**
 * Interface for path generator for monsters
 * @see Monster#updatePath(Map, Player)
 */
public interface PathGenerator {
    LinkedList<Point> generatePath(Map map, Monster monster, Player player);
}
