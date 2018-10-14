package Controller;

import GameEngine.*;
import GameEngine.Map;
import GameEngine.utils.*;
import GameEngine.CollisionHandler.*;
import GameEngine.utils.Observable;
import Sample.SampleMaps;
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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;


public class DungeonPlayController extends Controller{

	@FXML
	private AnchorPane dungeonPane;

	@FXML
    private Label timerLabel;

	@FXML
	private ListView<String> inventoryList;


	public static final int GRID_SIZE = 32;

	private HashMap<String, Image> imgMap;
	private Player player;
	private Map map;
	private AnimationTimer mainAnimation;
	private Set<KeyCode> keyCodes; // currently pressed keys
    private TimerCollection timers;
    private ObservableList<String> inventoryItems;

	public DungeonPlayController(Stage s, Map map){
		super(s);
        keyCodes = new HashSet<>();
        imgMap = new HashMap<>();
        timers = new TimerCollection();
        inventoryItems = FXCollections.observableArrayList();
        this.map = map;
	}

    /**
     * Debug only, use sample hardcoded map
     * @param s the stage
     */
	public DungeonPlayController(Stage s){
	    this(s, SampleMaps.getMap2());
    }

	@FXML
	void onKeyPressed(KeyEvent event) {
		keyCodes.add(event.getCode());
		event.consume();
	}

	@FXML
	void onKeyReleased(KeyEvent event) {
		keyCodes.remove(event.getCode());
		event.consume();
	}

	@FXML
	public void initialize() {
		inventoryList.setItems(inventoryItems);
		inventoryList.setCellFactory(param -> new ListCell<String>() {
			private ImageView imageView = new ImageView();
            @Override
			public void updateItem(String name, boolean empty) {
				super.updateItem(name,empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					for(String string: imgMap.keySet())
						if(name.equals(string)) {
							imageView.setImage(imgMap.get(string));
                            setText(name);
                            setGraphic(imageView);
						}
				}
			}
		});

		loadResources();
	    Group dungeon = initDungeon();
		dungeonPane.getChildren().add(dungeon);
	}

	private void loadResources(){
        // load all images
        File dir = new File(getClass().getClassLoader().getResource("img").getPath());
        File[] directoryListing = dir.listFiles();
        try {
            for (File child : directoryListing) {
                imgMap.put(child.getName().split("[.]")[0], new Image(new FileInputStream(child)));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


	private Group initDungeon() {
		Group dungeon = new Group();

		GameEngine engine = new GameEngine(map);
		player = engine.getPlayer();
		List<Node> nodes = new LinkedList<>();

		for(GameObject obj: engine.getAllObjects()){
			ImageView imageView = new ImageView(imgMap.get(obj.getClassName()));
			imageView.setId(Integer.toString(obj.getObjID()));
			imageView.setTranslateX(obj.getLocation().getX() * GRID_SIZE);
			imageView.setTranslateY(obj.getLocation().getY() * GRID_SIZE);
			nodes.add(imageView);
		}

		// draw grids
		drawGridLine(dungeon);

		final LongProperty lastUpdateTime = new SimpleLongProperty(0);
		mainAnimation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				double elapsedSeconds = (now - lastUpdateTime.get()) / 1000000000.0 ;
				// make sure at most go 40 ms
				elapsedSeconds = elapsedSeconds > 0.04 ? 0.04 : elapsedSeconds;

				timers.updateAll((int)(elapsedSeconds * 1000));

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

				if(keyCodes.contains(KeyCode.A)){
					Arrow arrow = engine.playerShootArrow();
					if(arrow != null){
						ImageView imageView = new ImageView(imgMap.get(arrow.getClassName()));
						imageView.setId(Integer.toString(arrow.getObjID()));
						imageView.setTranslateX(arrow.getLocation().getX() * GRID_SIZE);
						imageView.setTranslateY(arrow.getLocation().getY() * GRID_SIZE);
						dungeon.getChildren().add(imageView);
					}
				}

				if(keyCodes.contains(KeyCode.B)){
					Bomb bomb = engine.playerSetBomb();
					if(bomb != null){
						ImageView imageView = new ImageView(imgMap.get(bomb.getClassName()));
						imageView.setId(Integer.toString(bomb.getObjID()));
						imageView.setTranslateX(bomb.getLocation().getX() * GRID_SIZE);
						imageView.setTranslateY(bomb.getLocation().getY() * GRID_SIZE);
						dungeon.getChildren().add(imageView);

						Timer timer = new Timer(bomb, arg -> {
                            System.out.format("%s exploded\n", bomb);
                            List<GameObject> destroyedObjects = bomb.explode(engine);

                            if (destroyedObjects == null) {
                                System.out.println("You've got bombed!");
                                restart();
                            } else {
                                // remove all destroyed nodes
                                dungeon.getChildren().removeAll(
                                        destroyedObjects.stream()
                                                .map(o -> dungeon.lookup("#" + o.getObjID()))
                                                .collect(Collectors.toList()));
                                // remove the bomb itself
                                dungeon.getChildren().remove(imageView);
                            }
                        });
						timer.setOnUpdateTimer(remain -> {
						    timerLabel.setText("Bomb will explode in: " + (remain/1000 + 1) + " s.");
                        });
						timers.add(timer);
					}
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
					Node movingNode = dungeon.lookup("#" + Integer.toString(movingObj.getObjID()));
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
								dungeon.lookup("#" + Integer.toString(anotherObj.getObjID()));

						if(isColliding(movingNode, anotherNode)){
							CollisionResult result = engine.handleCollision(movingObj, anotherObj);
							if(result.containFlag(CollisionResult.REJECT)){
								// set translate co-ord back to before
								newX = oldX;
								newY = oldY;
								movingNode.setTranslateX(newX);
								movingNode.setTranslateY(newY);
							}
							if(result.containFlag(CollisionResult.LOSE)){
								System.out.println("You've LOST the game!");
                                mainAnimation.stop();
//                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You've LOST the game");
//                                Platform.runLater(alert::showAndWait);
								restart();
							}
							if(result.containFlag(CollisionResult.WIN)){
								System.out.println("You've WON the game!");
                                mainAnimation.stop();
//                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You've WON the game");
//                                Platform.runLater(alert::showAndWait);
								restart();
							}
							if(result.containFlag(CollisionResult.DELETE_FIRST)){
								dungeon.getChildren().remove(movingNode);
							}
							if(result.containFlag(CollisionResult.DELETE_SECOND)){
								dungeon.getChildren().remove(anotherNode);
							}
							if(result.containFlag(CollisionResult.REFRESH_INVENTORY)){
							    inventoryItems.add(anotherObj.getClassName());
								System.out.print(player.getInventory());
							}
							if(result.containFlag(CollisionResult.REFRESH_EFFECT_TIMER)){
                                Potion potion = (Potion) anotherObj;
                                Timer timer = new Timer(potion, arg -> {
                                    player.removePotionEffect(engine, potion);
                                });
                                timer.setOnUpdateTimer(remain -> {
                                    timerLabel.setText(String.format("%s: %.2f", potion, remain/1000.0));
                                });
                                timers.add(timer);
                            }
						}
					}

					if(movingObj instanceof Monster){
						// exact scheme co-ord conversion
						Point newPoint = movingObj.getLocation().clone();
						int mapX, mapY;
						switch (movingObj.getFacing()){
							case LEFT:
								if((mapX = (int)(newX + GRID_SIZE) / GRID_SIZE) == movingObj.getLocation().getX())
									break;
								newPoint.setX(mapX);
								break;
							case UP:
								if((mapY = (int)(newY + GRID_SIZE) / GRID_SIZE) == movingObj.getLocation().getY())
									break;
								newPoint.setY(mapY);
								break;
							case RIGHT:
								if((mapX = (int)newX / GRID_SIZE) == movingObj.getLocation().getX())
									break;
								newPoint.setX(mapX);
								break;
							case DOWN:
								if((mapY = (int)newY / GRID_SIZE) == movingObj.getLocation().getY())
									break;
								newPoint.setY(mapY);
								break;
						}
//                        Point newPoint = new Point((int) ((oldX + dx + ((movingObj.getFacing() == Direction.LEFT) ? GRID_SIZE : GRID_SIZE / 2)) / GRID_SIZE),
//                                          (int) ((oldY + dy + ((movingObj.getFacing() == Direction.UP) ? GRID_SIZE : GRID_SIZE / 2)) / GRID_SIZE));
						engine.changeObjectLocation(movingObj, newPoint);
					} else {
						engine.changeObjectLocation(movingObj, new Point((int) ((newX + GRID_SIZE / 2) / GRID_SIZE), (int) ((newY + GRID_SIZE / 2) / GRID_SIZE)));
					}
				}
				lastUpdateTime.set(now);
			}
		};

		mainAnimation.start();

		dungeon.getChildren().addAll(nodes);
		return dungeon;
	}

	@FXML
	public void handleStartScreenButton(){
		Screen cs = new Screen(this.getStage(), "Dungeon Start", "View/StartScreen.fxml");
		Controller controller = new StartScreenController(this.getStage());
		cs.display(controller);
	}

	@FXML
	public void handleModeScreenButton(){
		Screen cs = new Screen(this.getStage(), "Selection", "View/ModeScreen.fxml");
		Controller controller = new ModeScreenController(this.getStage());
		cs.display(controller);
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

	private void drawGridLine(Group parent){
		// vertical lines
		for(int i = 0; i < 11; i++){
			Line line = new Line();
			line.setStartX(i * GRID_SIZE);
			line.setEndX(i * GRID_SIZE);
			line.setStartY(0);
			line.setStartY(GRID_SIZE * Map.DUNGEON_SIZE_Y);
			line.setStrokeWidth(1);
			line.setStroke(Color.GRAY);
			parent.getChildren().add(line);
		}

		// horizontal lines
		for(int i = 0; i < 11; i++){
			Line line = new Line();
			line.setStartY(i * GRID_SIZE);
			line.setEndY(i * GRID_SIZE);
			line.setStartX(0);
			line.setStartX(GRID_SIZE * Map.DUNGEON_SIZE_X);
			line.setStrokeWidth(1);
			line.setStroke(Color.GRAY);
			parent.getChildren().add(line);
		}
	}

	private void restart(){
        Screen cs = new Screen(this.getStage(), "Dungeon", "View/DungeonPlayScreen.fxml");
        Controller controller = new DungeonPlayController(this.getStage());
        cs.display(controller);
	}

}
