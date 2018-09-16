package GameEngine;

import GameEngine.utils.Point;

import java.util.LinkedList;

public class StrategistPathGenerator implements PathGenerator {
    @Override
    public LinkedList<Point> generatePath(Map map, Monster monster, Player player) {
         Point target = player.getFrontGrid(map);
         if(target != null && map.canMoveThrough(target)) {
             return map.getShortestPath(monster.location, target);
         }
         return map.getShortestPath(monster.location, player.location);
    }
}
