package GameEngine;

import GameEngine.utils.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameObjectObserverTest {
    @Test
    public void testObserver(){
        IntegerProperty state = new SimpleIntegerProperty(-1);
        Player player = new Player(new Point(1, 1 ));
        player.addObserver(obj -> state.set(obj.getState()));
        assertEquals(state.get(), -1);
        player.changeState(1);
        assertEquals(state.get(), 1);
        player.changeState(5);
        assertEquals(state.get(), 5);
    }
}
