package GameEngine;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Map implements Serializable {
    // TODO: we can probs do multi-sized dungeon by changing these const to variable
    public static final int DUNGEON_SIZE_X = 10;
    public static final int DUNGEON_SIZE_Y = 10;

    public List<GameObject>[][] map;

    /**
     * Empty arg constructor
     */
    public Map(){
        this.map = new List[DUNGEON_SIZE_X][DUNGEON_SIZE_Y];
        for(int i = 0; i < DUNGEON_SIZE_X; i++)
            for(int j = 0; j < DUNGEON_SIZE_Y; j++)
                map[i][j] = new LinkedList<>();
    }

    /**
     * Constructor from a map builder
     *
     * @param mapBuilder
     */
    public Map(MapBuilder mapBuilder){
        this();
        GameObject[][] builderMap = mapBuilder.getMap();
        for(int i = 0; i < DUNGEON_SIZE_X; i++)
            for(int j = 0; j < DUNGEON_SIZE_Y; j++)
                map[i][j].add(builderMap[i][j]);
    }

    /**
     * Construct a Map from a saved file
     *
     * @param
     */
    public Map(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        this.map = (List<GameObject>[][]) in.readObject();
    }

    /**
     * Get list of object in specific grid
     */
    public List<GameObject> getObjects(Point location){
        return map[location.getX()][location.getY()];
    }

    /**
     * Remove a specific object from the map
     */
    public void removeObject(GameObject obj){
        map[obj.location.getX()][obj.location.getY()].remove(obj);
    }

    /**
     * Update an object's location
     *
     * @param obj game object
     * @param location new location
     */
    public void updateObjectLocation(GameObject obj, Point location){
        removeObject(obj);
        map[location.getX()][location.getY()].add(obj);
        obj.setLocation(location);
    }

    /**
     * Interface for monster to find path
     * Return a list of point adjacent to given point that does
     * not contains an object that blocks monsters' movement
     *
     * @param point current point
     * @return adjacent movable points
     */
    public List<Point> getNonBlockAdjacentPoints(Point point){
        List<Point> ret = new LinkedList<>();
        // enumerate all adjacent point
        Point[] points = new Point[4];
        points[0] = point.clone().translate(1, 0);
        points[1] = point.clone().translate(-1, 0);
        points[2] = point.clone().translate(0, 1);
        points[3] = point.clone().translate(0, -1);
        for(Point curr : points) {
            if(curr.getX() < 0 || curr.getX() >= DUNGEON_SIZE_X ||
                    curr.getY() < 0 || curr.getY() >= DUNGEON_SIZE_Y)
                continue;

            // check if there's blockable obj in that point
            if (getObjects(curr).stream()
                    .filter(o -> (o instanceof Blockable)) // TODO: change this later
                    .collect(Collectors.toList()).size() == 0)
                ret.add(curr);
        }
        return ret;
    }

    public void serialize(OutputStream outputStream) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(this.map);
    }


}
