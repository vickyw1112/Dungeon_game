package GameEngine;

import GameEngine.CollisionHandler.*;
import GameEngine.utils.Point;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameEngine {
    private HashMap<Integer, GameObject> objects;
    private Map map;
    private Player player;
    private List<Movable> movingObjects;
    /**
     * Map for finding collision handler for two specific type of object Key is
     * CollisionEntities which contains two string of class name in order Value is
     * the CollisionHandler
     */
    private HashMap<CollisionEntities, CollisionHandler> collisionHandlerMap;
    private List<Monster> monsters;

    public GameEngine(InputStream input) throws IOException, ClassNotFoundException {
        this(new Map(input));
    }

    /**
     * Constructor for GameEngine Load the map from saving For each unique game
     * object, call it's {@link GameObject#registerCollisionHandler} method TODO:
     * consider using factory pattern to instantiate all game objects? TODO: also
     * check Class.forName().getConstructor().newInstance()
     *
     * @param file
     *            map file
     */
    public GameEngine(Map map) {
        // init
        // TODO init different thing in different places
        this.map = map;
        this.objects = new HashMap<>();
        this.movingObjects = new LinkedList<>();
        this.monsters = new LinkedList<>();

        for (GameObject obj : map.getAllObjects()) {
            obj.initialize();
            if (obj instanceof Player)
                this.player = (Player) obj;
            if (obj instanceof Movable)
                this.movingObjects.add((Movable) obj);
            if (obj instanceof Monster)
                this.monsters.add((Monster) obj);

            this.objects.put(obj.getObjID(), obj);
        }

        this.collisionHandlerMap = new HashMap<>();

        // register collisionHandler for (GameObject, GameObject) for default handler
        // here
        // fall back mechanism see GameEngine#getCollisionHandler
        registerCollisionHandler(new CollisionEntities(GameObject.class, GameObject.class), new DefaultHandler());
    }

    /**
     * Define the behaviour of backend when the player pressed the key for shooting
     * arrow
     *
     * @return if the player have a arrow to shoot
     */
    public Arrow playerShootArrow() {
        return player.shootArrow();
    }

    /**
     * Define the behaviour of backend when the player pressed the key for setting
     * bomb
     *
     * @return if the player have a bomb to set
     */
    public Bomb playerSetBomb() {

        return player.setBomb(map);
    }

    /**
     * Get the map
     *
     * @return map dungeon layout
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Get the list of all movable objects except ones with zero speed
     *
     * @return list of all moving objects
     */
    public List<Movable> getMovingObjects() {
        // filter out all obj with speed 0
        List<Movable> res = new LinkedList<>(movingObjects);
        res.removeIf(o -> o.getSpeed() == 0);
        return res;
    }

    /**
     * Find the handler for given two object and call to handle the collision and
     * return the collision result to front end
     * 
     * @param obj1
     *            first game obj
     * @param obj2
     *            second game obj
     *
     * @see CollisionResult
     * @return CollisionResult
     */
    public CollisionResult handleCollision(GameObject obj1, GameObject obj2) throws CollisionHandlerNotImplement {
        CollisionHandler handler = getCollisionHandler(new CollisionEntities(obj1.getClass(), obj2.getClass()));

        return handler.handle(this, obj1, obj2);
    }

    /**
     * Check if the player is wining the game now
     *
     * @return whether the player has won the game
     */
    public boolean checkWiningCondition() {
        // TODO
        return false;
    }

    /**
     * Wrapper of {@link GameObject#setLocation} Updates indexes for this object in
     * Map Call {@link Monster#updatePath} for each {@link GameEngine#monsters} if
     * player's location changed
     *
     * @param object
     *            game object
     * @param location
     *            new location
     */
    public void changeObjectLocation(GameObject object, Point location) {
        if (object.setLocation(location)) {
            map.updateObjectLocation(object, location);
            // TODO: think of better way to do this
            if (object instanceof Player) {
                for (Monster monster : monsters) {
                    monster.updatePath(map, player);
                }
            }
        }
    }

    /**
     * Get a game object by it's objID
     *
     * @param objId
     *            object ID
     * @return
     */
    public GameObject getObjectById(int objId) {

        return null;
    }

    /**
     * Remove all reference to a given game object in the backend
     *
     * @see GameEngine#map
     * @see GameEngine#objects
     * @see GameEngine#movingObjects
     * @see GameEngine#monsters
     * @param obj
     *            object to be removed
     */
    public void removeGameObject(GameObject obj) {
        map.removeObject(obj);
        objects.remove(obj.getObjID());
        movingObjects.remove(obj);
        monsters.remove(obj);
    }

    /**
     * Interface for front end to provide hook for changing states of game objects
     *
     * @deprecated use observer pattern now
     * @param stateChanger
     *            hook class
     */
    public void setStateChanger(StateChanger stateChanger) {
        // GameObject.stateChanger = stateChanger;
    }

    /**
     * Interface to register a collision handler for given entities
     */
    protected void registerCollisionHandler(CollisionEntities entities, CollisionHandler handler) {
        collisionHandlerMap.put(entities, handler);
    }

    /**
     * Given a entities object, return the corresponding collision handler from the
     * map If entities are not present in the key try to get the entities of parent
     * classes If no handler can be found throw exception
     *
     * @throws CollisionHandlerNotImplement
     * @param entities
     * @return collision handler
     */
    public CollisionHandler getCollisionHandler(CollisionEntities entities) throws CollisionHandlerNotImplement {
        // check if there's handler registered matching exactly given entities
        if (collisionHandlerMap.containsKey(entities)) {
            return collisionHandlerMap.get(entities);
        }

        // get entities' super types to fall back
        for (CollisionEntities ent : entities.getAllParentEntities()) {
            if (collisionHandlerMap.containsKey(ent)) {
                return collisionHandlerMap.get(ent);
            }
        }
        throw new CollisionHandlerNotImplement(entities.toString());
    }
}
