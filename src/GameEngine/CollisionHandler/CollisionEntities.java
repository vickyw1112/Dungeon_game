package GameEngine.CollisionHandler;

import GameEngine.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollisionEntities {
    private Class<?> cls1;
    private Class<?> cls2;

    /**
     * Constructor for CollisionEntities Sort two given type so that order does not
     * matter
     *
     * @param cls1
     *            class for first obj
     * @param cls2
     *            class for second obj
     */

    public CollisionEntities(Class<?> cls1, Class<?> cls2) {
        assert (GameObject.class.isAssignableFrom(cls1));
        assert (GameObject.class.isAssignableFrom(cls2));

        String type1 = cls1.getSimpleName();
        String type2 = cls2.getSimpleName();
        if (type1.compareTo(type2) < 0) {
            this.cls1 = cls1;
            this.cls2 = cls2;
        } else {
            this.cls1 = cls2;
            this.cls2 = cls1;
        }
    }

    /**
     * Override equals and hashCode to make sure different instance
     * CollisionEntities can be effective the same key for HashMap
     *
     * @see GameEngine#collisionHandlerMap
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CollisionEntities))
            return false;

        return cls1.equals(((CollisionEntities) obj).cls1) && cls2.equals(((CollisionEntities) obj).cls2);
    }

    @Override
    public int hashCode() {
        return cls1.hashCode() ^ cls2.hashCode();
    }

    @Override
    public String toString() {
        String type1 = cls1.getSimpleName();
        String type2 = cls2.getSimpleName();
        return String.format("<CollisionEnt|(%s, %s)>", type1, type2);
    }

    /**
     * Return a list of entities with two type being super type of this entities
     * This is used when this entities is not found in the key of
     * collisionHandlerMap in GameObject TODO: test this thoroughly
     *
     * @see GameEngine#getCollisionHandler(CollisionEntities)
     * @return list of CollisionEntities in the order that subclasses go first
     */
    public Collection<CollisionEntities> getAllParentEntities() {
        Set<CollisionEntities> ret = new LinkedHashSet<>();

        Set<Class<?>> currALvl = new LinkedHashSet<>(), currBLvl = new LinkedHashSet<>();
        Set<Class<?>> nextALvl = new LinkedHashSet<>(), nextBLvl = new LinkedHashSet<>();
        currALvl.add(cls1);
        currBLvl.add(cls2);

        while (!(currALvl.contains(GameObject.class) && currBLvl.contains(GameObject.class))) {

            // mix classes in curr A Level and classes in nextB level
            for (Class<?> currA : currALvl) {
                nextBLvl = currBLvl.stream().flatMap(CollisionEntities::getParentTypes).collect(Collectors.toSet());
                nextBLvl.forEach(currB -> ret.add(new CollisionEntities(currA, currB)));
            }

            // mix classes in curr B Level and classes in nextA level
            for (Class<?> currB : currBLvl) {
                nextALvl = currALvl.stream().flatMap(CollisionEntities::getParentTypes).collect(Collectors.toSet());
                nextALvl.forEach(currA -> ret.add(new CollisionEntities(currA, currB)));
            }

            // increment curr level to next level if it has not reached end
            if (!currALvl.contains(GameObject.class)) {
                currALvl = nextALvl;
            }

            if (!currBLvl.contains(GameObject.class)) {
                currBLvl = nextBLvl;
            }
        }

        ret.add(new CollisionEntities(GameObject.class, GameObject.class));
        return ret;
    }

    /**
     * Get parent types (classes/interfaces) of given class filter out all non
     * GameObject types
     *
     * @return stream of parent types
     */
    private static Stream<Class<?>> getParentTypes(Class<?> cls) {
        List<Class<?>> parents = new ArrayList<>(Arrays.asList(cls.getInterfaces()));
        if (cls.getSuperclass() != null)
            parents.add(cls.getSuperclass());

        return parents.stream().filter(GameObject.class::isAssignableFrom);
    }

}
