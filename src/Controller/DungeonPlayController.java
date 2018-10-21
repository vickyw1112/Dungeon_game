package Controller;

import GameEngine.*;
import GameEngine.Map;
import GameEngine.utils.*;
import GameEngine.CollisionHandler.*;
import View.Screen;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static Controller.Config.GRID_SIZE;


public class DungeonPlayController extends Controller {

	@FXML
	private AnchorPane dungeonPane;

    private AnimationTimer mainAnimation;

	@FXML
	private Label timerLabel;

	@FXML
	private ListView<String> inventoryItems;

	@FXML
    private HBox timerBox;

	@FXML
    private StackPane pausePane;

	private GameEngine engine;
    private TimerCollection timers;
	private ResourceManager resources;
    private Set<KeyCode> keyPressed;
    private ObservableList<String> inventoryList;

	private Player player;
	private Map originalMap;
	private Map map;
	private boolean paused;

	public DungeonPlayController(Stage s, Map map){
		super(s);
        // load all resources
        resources = new ResourceManager();

        keyPressed = new HashSet<>();
        this.map = map;
        originalMap = map.cloneMap();
		inventoryList = FXCollections.observableArrayList();
		paused = false;
	}

	@FXML
	public void handleStartScreenButton(){
	    mainAnimation.stop();
		Screen cs = new Screen(this.getStage(), "Dungeon Start", "View/StartScreen.fxml");
		Controller controller = new StartScreenController(this.getStage());
		cs.display(controller);
	}

	@FXML
	public void handleModeScreenButton(){
	    mainAnimation.stop();
		Screen cs = new Screen(this.getStage(), "Select Mode to Play", "View/ModeScreen.fxml");
		Controller controller = new ModeScreenController(this.getStage());
		cs.display(controller);
	}

    /**
     * Refresh what's in Inventory
     */
	private void updateInventory() {
		// remove all entry
		inventoryList.removeIf(o -> true);

		inventoryList.addAll(engine.getInventoryAllClasses());
	}

    @Override
    public void afterInitialize() {
        stage.getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                if(paused)
                    mainAnimation.start();
                else
                    mainAnimation.stop();
                paused = !paused;
                pausePane.setVisible(paused);
            }
            keyPressed.add(event.getCode());
            event.consume();
        });
        stage.getScene().setOnKeyReleased(event -> {
            keyPressed.remove(event.getCode());
            event.consume();
        });
    }

    @FXML
	public void initialize() {
	    pausePane.setVisible(false);
	    timerBox.getChildren().clear();
        timers = new TimerCollection(timerBox.getChildren(), resources);

        initDungeon();

		inventoryItems.setItems(inventoryList);
		inventoryItems.setCellFactory(this::inventoryListViewCellFactory);

	}

    /**
     * Observer callback for state change of a object
     * @param obj
     */
    private void handleObjectStateChange(GameObject obj){
        ImageView imageView = (ImageView) getNodeById(obj.getObjID());
        if(imageView != null) {
            imageView.setImage(resources.getImage(obj.getClassName(), obj.getState()));
            System.out.println(obj + " changed state to: " + obj.getState());
        }
    }

    /**
     * Cell factory for inventory ListView
     * Given a class string added, figure out the count for that
     * class of object in user's inventory
     */
	private ListCell<String> inventoryListViewCellFactory(ListView<String> param){
	    return new ListCell<String>() {
			@Override
			public void updateItem(String name, boolean empty) {
				super.updateItem(name, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					ImageView imageView = resources.createImageViewByClassName(name);
					imageView.setPreserveRatio(true);
					imageView.setFitWidth(25);
					int count = engine.getInventoryCounts(name);
					setGraphic(imageView);
					setText(Integer.toString(count));
					if(count == 0){
					    imageView.setImage(null);
					    setText(null);
                    }
				}
			}
		};
    }

	private void initDungeon() {
		engine = new GameEngine(map, this::handleObjectStateChange);

		player = engine.getPlayer();
		ObservableList<Node> nodes = dungeonPane.getChildren();

        // draw grids
        resources.drawGridLine(nodes, map.getSizeX(), map.getSizeY());

		for(GameObject obj: engine.getAllObjects()) {
            resources.createImageViewByGameObject(obj, nodes);
        }


		final LongProperty lastUpdateTime = new SimpleLongProperty(0);
		mainAnimation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				double elapsedSeconds = (now - lastUpdateTime.get()) / 1000000000.0 ;
				// make sure at most go 40 ms for each frame
				elapsedSeconds = elapsedSeconds > 0.04 ? 0.04 : elapsedSeconds;


				updatePlayerMovingStatus();

				if(keyPressed.contains(KeyCode.A))
				    handlePlayerShootArrow();

				if(keyPressed.contains(KeyCode.B))
				    handlePlayerSetBomb();

				if(updateMovingObjects(elapsedSeconds))
				    return;

				// update timer last in case bomb went off and player is dead
                timers.updateAll((int)(elapsedSeconds * 1000));
				lastUpdateTime.set(now);
			}
		};
		mainAnimation.start();
	}

    /**
     * Update player's moving status by checking whether
     * the user is pressing keys for player movement
     */
	private void updatePlayerMovingStatus(){
        // get player's moving status
        if(keyPressed.contains(KeyCode.LEFT)){
            player.setFacing(Direction.LEFT);
            player.setIsMoving(true);
        } else if(keyPressed.contains(KeyCode.RIGHT)){
            player.setFacing(Direction.RIGHT);
            player.setIsMoving(true);
        } else if(keyPressed.contains(KeyCode.UP)){
            player.setFacing(Direction.UP);
            player.setIsMoving(true);
        } else if(keyPressed.contains(KeyCode.DOWN)){
            player.setFacing(Direction.DOWN);
            player.setIsMoving(true);
        } else {
            player.setIsMoving(false);
        }
    }

    /**
     * Player shoot arrow
     */
	private void handlePlayerShootArrow(){
        Arrow arrow = engine.playerShootArrow();
        if(arrow != null) {
            resources.createImageViewByGameObject(arrow, dungeonPane.getChildren());
            updateInventory();
        }
        keyPressed.remove(KeyCode.A);
    }

    /**
     * Player set bomb
     */
	private void handlePlayerSetBomb(){
        Bomb bomb = engine.playerSetBomb();
        if(bomb != null){
            updateInventory();
            ImageView imageView =
                    resources.createImageViewByGameObject(bomb, dungeonPane.getChildren());

            Timer timer = new Timer(bomb, arg -> {
                System.out.format("%s exploded\n", bomb);
                List<GameObject> destroyedObjects = bomb.explode(engine);

                if (destroyedObjects == null) {
                    System.out.println("You've got bombed!");
                    Platform.runLater(this::handleLose);
                    mainAnimation.stop();

                } else {
                    // remove all destroyed nodes
                    dungeonPane.getChildren().removeAll(
                            destroyedObjects.stream()
                                    .map(o -> getNodeById(o.getObjID()))
                                    .collect(Collectors.toList()));
                    // remove the bomb itself
                    dungeonPane.getChildren().remove(imageView);
                }
            });
            timers.add(timer);
        }
        keyPressed.remove(KeyCode.B);
    }

    /**
     * Calculate the backend coordinate by given front end coordinate
     * This takes into account the moving scheme of the given movable object
     *
     * @see Movable#getMovingScheme()
     * @param obj the moving object
     * @param newX new front end X coordinate
     * @param newY new front end Y coordinate
     * @return the calculated back end coordinate
     */
	private Point getUpdatedPoint(Movable obj, double newX, double newY){
	    if(obj.getMovingScheme() == Movable.APPROX){
	        return new Point((int) ((newX + GRID_SIZE / 2) / GRID_SIZE),
                             (int) ((newY + GRID_SIZE / 2) / GRID_SIZE));
        } else if(obj.getMovingScheme() == Movable.EXACT){
            Point newPoint = obj.getLocation().clone();
            int mapX, mapY;
            switch (obj.getFacing()){
                case LEFT:
                    if((mapX = (int)(newX + GRID_SIZE) / GRID_SIZE) == obj.getLocation().getX())
                        break;
                    newPoint.setX(mapX);
                    break;
                case UP:
                    if((mapY = (int)(newY + GRID_SIZE) / GRID_SIZE) == obj.getLocation().getY())
                        break;
                    newPoint.setY(mapY);
                    break;
                case RIGHT:
                    if((mapX = (int)newX / GRID_SIZE) == obj.getLocation().getX())
                        break;
                    newPoint.setX(mapX);
                    break;
                case DOWN:
                    if((mapY = (int)newY / GRID_SIZE) == obj.getLocation().getY())
                        break;
                    newPoint.setY(mapY);
                    break;
            }
            return newPoint;
        }
        return null;
    }



	/**
	 * Detect collision between two JavaFX node
	 * @param node1
	 * @param node2
	 * @return
	 */
	private boolean isColliding(Node node1, Node node2){
		final int offset = 2;
		Bounds bounds1 = node1.getBoundsInParent();
		Bounds bounds2 = node2.getBoundsInParent();
		bounds1 = new BoundingBox(bounds1.getMinX() + offset, bounds1.getMinY() + offset,
				bounds1.getWidth() - offset * 2, bounds1.getHeight() - offset * 2);
		bounds2 = new BoundingBox(bounds2.getMinX() + offset, bounds2.getMinY() + offset,
				bounds2.getWidth() - offset * 2, bounds2.getHeight() - offset * 2);
		return bounds1.intersects(bounds2);
	}


    /**
     * When the user wins, move to the win screen
     */
	private void handleWin(){
		Screen cs = new Screen(this.getStage(), "Highscore", "View/WinScreen.fxml");
		Controller controller = new HighscoreScreenController(this.getStage());
		cs.display(controller);
	}

	private void handleLose(){
	    ButtonType goBackBtn = new ButtonType("Go back to menu");
	    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "You've lost the game, retry?", ButtonType.YES, goBackBtn);
	    alert.setTitle("Lost");
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent())
            return;
        if(result.get() == ButtonType.YES){
            Screen screen = new Screen(stage, "Dungeon", "View/DungeonPlayScreen.fxml");
            try {
                Map reloadedMap = Map.loadFromFile(new FileInputStream("map/" + map.getMapName() + ".dungeon"));
                screen.display(new DungeonPlayController(stage, reloadedMap));
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        if(result.get() == goBackBtn){
            handleModeScreenButton();
        }
    }

    /**
     * Get a JavaFX Node by it's id which is the same
     * objId in the backend;
     * @param objId object id
     * @return node
     */
	private Node getNodeById(int objId){
        return dungeonPane.lookup("#" + Integer.toString(objId));
    }

    /**
     * Update all moving objects in game engine
     * by elapsedSeconds
     *
     * @return whether to break out current frame
     *          this is true when game finished
     */
    private boolean updateMovingObjects(double elapsedSeconds) {
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
            Node movingNode = getNodeById(movingObj.getObjID());
            if(movingNode == null) break; // if the node is deleted
            double oldX = movingNode.getTranslateX();
            double oldY = movingNode.getTranslateY();
            double newX = oldX + dx;
            double newY = oldY + dy;

            // translate objs
            movingNode.setTranslateX(newX);
            movingNode.setTranslateY(newY);

            // detect collisions
            for(GameObject anotherObj : engine.getAllObjects()){
                // ignore self with self
                if(movingObj.equals(anotherObj)) continue;

                Node anotherNode =
                        getNodeById(anotherObj.getObjID());

                if(isColliding(movingNode, anotherNode)){
                    CollisionResult result = engine.handleCollision(movingObj, anotherObj);
                    if(handleCollision(result, movingNode, anotherNode, oldX, oldY))
                        return true;
                }
            }

            engine.changeObjectLocation(movingObj, getUpdatedPoint(movingObj, newX, newY));
        }
        return false;
    }

    /**
     * Given collision result, handle changes to front end
     *
     * @param result collision result
     * @param movingNode the moving node
     * @param anotherNode the node being collided
     * @param oldX old X value for moving node
     * @param oldY old Y value for moving node
     * @return whether to break out current frame
     *          this is true when game finished
     */
    private boolean handleCollision(CollisionResult result, Node movingNode, Node anotherNode,
                                 double oldX, double oldY){
        if(result.containFlag(CollisionResult.REJECT)){
            // set translate co-ord back to before
            movingNode.setTranslateX(oldX);
            movingNode.setTranslateY(oldY);
        }
        if(result.containFlag(CollisionResult.LOSE)){
            System.out.println("You've LOST the game!");
            Platform.runLater(this::handleLose);
            mainAnimation.stop();
            return true;
        }
        if(result.containFlag(CollisionResult.WIN)){
            System.out.println("You've WON the game!");
            handleWin();
            mainAnimation.stop();
            return true;
        }
        if(result.containFlag(CollisionResult.DELETE_FIRST)){
            dungeonPane.getChildren().remove(movingNode);
        }
        if(result.containFlag(CollisionResult.DELETE_SECOND)){
            dungeonPane.getChildren().remove(anotherNode);
        }
        if(result.containFlag(CollisionResult.REFRESH_INVENTORY)){
            updateInventory();
        }
        if(result.containFlag(CollisionResult.REFRESH_EFFECT_TIMER)){
            // second one is the one being collided
            Potion potion = (Potion) result.getCollidingObjects()[1];
            Timer timer = new Timer(potion, arg -> player.removePotionEffect(engine, potion));

            timers.add(timer);
        }
        return false;
    }

}
