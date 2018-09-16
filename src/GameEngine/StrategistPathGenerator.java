package GameEngine;

import GameEngine.utils.Point;

import java.util.LinkedList;

public class StrategistPathGenerator implements PathGenerator {

    /**
     * generatePath method for Strategist
     * takes in map, monster and player for parameters
     * returns a linked list of points
     * @param map
     * @param monster
     * @param player
     * @return
     */
    @Override
    public LinkedList<Point> generatePath(Map map, Monster monster, Player player) {
         Point target = player.getFrontGrid(map);
         if(target != null && map.canMoveThrough(target)) {
             return map.getShortestPath(monster.location, target);
         }
         return map.getShortestPath(monster.location, player.location);
    }
}
