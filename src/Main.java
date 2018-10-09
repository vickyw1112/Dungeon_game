import GameEngine.*;
import GameEngine.Map;
import GameEngine.utils.Direction;
import GameEngine.utils.Point;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sun.awt.image.ImageWatched;

public class Main extends Application {
    public static final int GRID_SIZE = 32;

    private HashMap<String, Image> imgMap;
    private Player player;
    private Map map;

    public static void main(String[] args) {
        launch(args);
    }

    private void initMap(){
        Monster strategist;
        Wall wall0;
        Wall wall2;
        Wall wall4;
        Wall wall5;
        Wall wall6;
        Wall wall7;
        MapBuilder mb;
        PathGenerator pg;

        mb = new MapBuilder();
        player = new Player(new Point(0, 0));
        wall0 = new Wall(new Point(0,1));
        wall2 = new Wall(new Point(2,1));
        wall4 = new Wall(new Point(4,1));
        wall5 = new Wall(new Point(5, 1));
        wall6 = new Wall(new Point(6, 1));
        wall7 = new Wall(new Point(7,1 ));
        strategist = new Strategist(new Point(0,2));


        mb.addObject(player);
        mb.addObject(wall0);
        mb.addObject(wall2);
        mb.addObject(wall4);
        mb.addObject(wall5);
        mb.addObject(wall6);
        mb.addObject(wall7);
        mb.addObject(strategist);
        map = new Map(mb);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Group dungeon = new Group();
        imgMap = new HashMap<>();
        initMap();

        // load all images
        File dir = new File(getClass().getClassLoader().getResource("img").getPath());
        File[] directoryListing = dir.listFiles();
        for (File child : directoryListing) {
            System.out.println(child.getName().split("[.]")[0]);
            imgMap.put(child.getName().split("[.]")[0], new Image(new FileInputStream(child)));
        }

        GameEngine engine = new GameEngine(map);
        List<Node> nodes = new LinkedList<>();

        for(GameObject obj: engine.getAllObjects()){
            ImageView imageView = new ImageView(imgMap.get(obj.getClassName()));
            imageView.setId(Integer.toString(obj.getObjID()));
            imageView.setTranslateX(obj.getLocation().getX() * GRID_SIZE);
            imageView.setTranslateY(obj.getLocation().getY() * GRID_SIZE);
            nodes.add(imageView);
        }

        Scene scene = new Scene(dungeon,
                Map.DUNGEON_SIZE_X * GRID_SIZE, Map.DUNGEON_SIZE_Y * GRID_SIZE);

        // keep track of currently pressed keys
        Set<KeyCode> keyCodes = new HashSet<>();
        scene.setOnKeyPressed(event -> keyCodes.add(event.getCode()));
        scene.setOnKeyReleased(event -> keyCodes.remove(event.getCode()));

        final LongProperty lastUpdateTime = new SimpleLongProperty(0);
        final AnimationTimer mainAnimation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                final double elapsedSeconds = (now - lastUpdateTime.get()) / 1000000000.0 ;

                // get player's moving status
                if(keyCodes.contains(KeyCode.LEFT)){
                    player.setFacing(Direction.LEFT);
                    player.setIsMoving(true);
                } else if(keyCodes.contains(KeyCode.RIGHT)){
                    player.setFacing(Direction.RIGHT);
                    player.setIsMoving(true);
                } else if(keyCodes.contains(KeyCode.UP)){
                    player.setFacing(Direction.UP);
                    player.setIsMoving(true);
                } else if(keyCodes.contains(KeyCode.DOWN)){
                    player.setFacing(Direction.DOWN);
                    player.setIsMoving(true);
                } else {
                    player.setIsMoving(false);
                }

                for(Movable movingObj : engine.getMovingObjects()){
                    double dx = 0, dy = 0;
                    switch (movingObj.getFacing()){
                        case UP:
                            dy -= movingObj.getSpeed() * GRID_SIZE * elapsedSeconds;
                            break;
                        case DOWN:
                            dy += movingObj.getSpeed() * GRID_SIZE * elapsedSeconds;
                            break;
                        case LEFT:
                            dx -= movingObj.getSpeed() * GRID_SIZE * elapsedSeconds;
                            break;
                        case RIGHT:
                            dx += movingObj.getSpeed() * GRID_SIZE * elapsedSeconds;
                            break;
                    }
                    Node thisNode = dungeon.lookup("#" + Integer.toString(movingObj.getObjID()));
                    double oldX = thisNode.getTranslateX();
                    double oldY = thisNode.getTranslateY();
                    thisNode.setTranslateX(oldX + dx);
                    thisNode.setTranslateY(oldY + dy);
                    engine.changeObjectLocation(movingObj, new Point((int)((oldX + dx) / GRID_SIZE), (int)((oldY + dy) / GRID_SIZE)));
                }
                lastUpdateTime.set(now);
            }
        };

        mainAnimation.start();

        dungeon.getChildren().addAll(nodes);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
