package GameEngine;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameEngine {
    private HashMap<Integer, GameObject> objects;
    private Map map;
    private Player player;
    private List<Movable> movingObjects;
    /**
     * Map for finding collision handler for two specific type of object
     * Key is CollisionEntities which contains two string of class name in order
     * Value is the CollisionHandler
     */
    private HashMap<CollisionEntities, CollisionHandler> collisionHandlerMap;
    private List<Monster> monsters;



    /**
     * Constructor for GameEngine
     * Load the map from saving
     * For each unique game object, call it's {@link GameObject#registerCollisionHandler} method
     * TODO: consider using factory pattern to instantiate all game objects?
     * TODO: also check Class.forName().getConstructor().newInstance()
     *
     * @param file map file
     */
    public GameEngine(String file){

    }

    /**
     * Define the behaviour of backend when the player
     * pressed the key for shooting arrow
     *
     * @return if the player have a arrow to shoot
     */
    public boolean playerShootArrow(){

        return false;
    }

    /**
     * Define the behaviour of backend when the player
     * pressed the key for setting bomb
     *
     * @return if the player have a bomb to set
     */
    public boolean playerSetBomb(){

        return false;
    }

    /**
     * Get the map
     *
     * @return map dungeon layout
     */
    public Map getMap(){
        return this.map;
    }

    /**
     * Get the list of all movable objects
     * except ones with zero speed
     *
     * @return list of all moving objects
     */
    public List<Movable> getMovingObjects(){
        // filter out all obj with speed 0
        return null;
    }

    /**
     * Find the handler for given two object and call
     * to handle the collision
     * @param obj1 first game obj
     * @param obj2 second game obj
     *
     * @return flags for instruction for front end
     *          TODO specify format here
     */
    public CollisionResult handleCollision(GameObject obj1, GameObject obj2){

        return null;
    }

    /**
     * Check if the player is wining the game now
     *
     * @return
     */
    public boolean checkWiningCondition(){
        return false;
    }

    /**
     * Wrapper of {@link Player#changeLocation}
     * Call {@link Monster#updatePath} for each {@link GameEngine#monsters}
     * if player moved to another grid
     *
     * @param point
     */
    public void playerChangeLocation(Point point){

    }


    /**
     * Get a game object by it's objID
     *
     * @param objId object ID
     * @return
     */
    public GameObject getObjectById(int objId){

        return null;
    }


    /**
     * Interface to register a collision handler for given entities
     */
    protected void registerCollisionHandler(CollisionEntities entities, CollisionHandler hander){

    }

    /**
     * Remove all reference to a given game object
     *
     * @see GameEngine#map
     * @see GameEngine#objects
     * @see GameEngine#movingObjects
     * @see GameEngine#monsters
     * @param obj object to be removed
     */
    public void removeGameObject(GameObject obj){

    }

    /**
     * Interface for front end to provide hook
     * for changing states of game objects
     *
     * @param stateChanger hook class
     */
    public void setStateChanger(StateChanger stateChanger){
        GameObject.stateChanger = stateChanger;
    }


    /**
     * Given a entities object, return the corresponding collision handler
     * from the map
     *
     * @param entities
     * @return collision handler
     */
    public CollisionHandler getCollisionHandler(CollisionEntities entities){

        return null;
    }

    public static void main(String[] args){
        GameEngine engine = new GameEngine("123");
        List<GameObject> testObjs = new LinkedList<GameObject>();
        engine.setStateChanger(new StateChanger() {
            @Override
            public void changeState(GameObject obj, int state) {
                // Test closure
                testObjs.add(obj);
            }
        });
        // TODO: check lambda?
        // engine.setStateChanger((obj, state) -> testObjs.add(((GameObject) obj)));
        Player p = new Player(new Point(2,3));
        p.changeState(1);
        p.changeState(2);
        try {
            Object obj = Class.forName("GameEngine.Player").getConstructor(Point.class).newInstance(new Point(1, 2));
            Player p2 = (Player)obj;
            p2.changeState(3);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(testObjs);
    }

}
