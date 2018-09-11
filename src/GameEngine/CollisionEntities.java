package GameEngine;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CollisionEntities {
    private Class<?> cls1;
    private Class<?> cls2;

    /**
     * Constructor for CollisionEntities
     * Sort two given type so that order does not matter
     *
     * @param cls1 class for first obj
     * @param cls2 class for second obj
     */

    public CollisionEntities(Class<?> cls1, Class<?> cls2) {
        String type1 = cls1.getSimpleName();
        String type2 = cls2.getSimpleName();
        if(type1.compareTo(type2) < 0){
            this.cls1 = cls1;
            this.cls2 = cls2;
        } else {
            this.cls1 = cls2;
            this.cls2 = cls1;
        }
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
        if(obj == null || !(obj instanceof CollisionEntities)) return false;

        return cls1.equals(((CollisionEntities) obj).cls1) &&
                cls2.equals(((CollisionEntities) obj).cls2);
    }

    @Override
    public int hashCode(){
        return cls1.hashCode() ^ cls2.hashCode();
    }

    @Override
    public String toString() {
        String type1 = cls1.getSimpleName();
        String type2 = cls2.getSimpleName();
        return String.format("<CollisionEnt|(%s, %s)>", type1, type2);
    }

    /**
     * Return a list of entities with two type being super type
     * of this entities
     * This is used when this entities is not found in the key of
     * collisionHandlerMap in GameObject
     * TODO: test this
     *
     * @see GameEngine#getCollisionHandler(CollisionEntities)
     * @return list of CollisionEntities in the order that subclasses go first
     */
    public List<CollisionEntities> getParentEntities() {

        List<CollisionEntities> res = new LinkedList<>();

        List<Class<?>> currClsA = new LinkedList<>(); // current level of clsA
        List<Class<?>> currClsB = new LinkedList<>(); // current level of clsB
        currClsA.add(cls1);
        currClsB.add(cls2);

        // TODO: fix this by recurse one class first and then another
        while (!currClsA.contains(GameObject.class)){
            while(!currClsB.contains(GameObject.class)) {
                for(Class<?> newClsA : currClsA){
                    for(Class<?> newClsB: currClsB){
                        res.add(new CollisionEntities(newClsA, newClsB));
                    }
                }

                List<Class<?>> nextLvl = new LinkedList<>();
                for(Class<?> cls : currClsB){
                    nextLvl.addAll(Arrays.asList(cls.getInterfaces()));
                    nextLvl.add(cls.getSuperclass());
                }
                currClsB = nextLvl;
            }
            List<Class<?>> nextLvl = new LinkedList<>();
            for(Class<?> cls : currClsA){
                nextLvl.addAll(Arrays.asList(cls.getInterfaces()));
                nextLvl.add(cls.getSuperclass());
            }
            for(Class<?> newClsA : nextLvl){
                for(Class<?> newClsB: currClsB){
                    res.add(new CollisionEntities(newClsA, newClsB));
                }

            }
            currClsA = nextLvl;
        }
        return res;
    }
}
