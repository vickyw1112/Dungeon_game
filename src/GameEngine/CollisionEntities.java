package GameEngine;

public class CollisionEntities {
    private String type1;
    private String type2;

    /**
     * Constructor for CollisionEntities
     * Sort two given type so that order does not matter
     *
     * @param type1 classId for first obj
     * @param type2 classId for second obj
     */
    public CollisionEntities(Class type1, Class type2) {

    }

    /**
     * Override equals and hashCode to make sure
     * different instance CollisionEntities can be effective the
     * same key for HashMap
     *
     * @see GameEngine#collisionHandlerMap
     */
    @Override
    public boolean equals(Object obj){

        return false;
    }

    @Override
    public int hashCode(){

        return 0;
    }
}
