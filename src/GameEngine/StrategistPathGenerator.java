package GameEngine;

import GameEngine.utils.Direction;
import GameEngine.utils.Point;

import java.util.LinkedList;
import java.util.List;

public class StrategistPathGenerator implements PathGenerator {
    @Override
    public LinkedList<Point> generatePath(Map map, Monster monster, Point target) {
         Point tar = null;
         List<GameObject> list = map.getObjects(target);
         for(GameObject obj : list){
             if(obj instanceof Player) {
                 Point front = ((Player) obj).getFrontGrid(map);
                if(front != null && map.isBlockingObjHere(front)){
                    tar = ((Player) obj).getFrontGrid(map);
                }
                else
                    tar = ((Player) obj).location;
             }
         }
         return map.getShortestPath(monster.getLocation(), tar);
    }
}
