package Controller;

import GameEngine.*;
import GameEngine.Map;
import GameEngine.utils.*;
import GameEngine.CollisionHandler.*;
import View.Screen;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

import static GameEngine.utils.Direction.DOWN;
import static GameEngine.utils.Direction.RIGHT;

public class DungeonPlayController extends Controller{
	@FXML
	private Button startScreenButton;

	@FXML
	private Button modeScreenButton;

	@FXML
	private AnchorPane dungeonPane;

	private Stage currStage;
	public static final int GRID_SIZE = 32;

	private HashMap<String, Image> imgMap;
	private Player player;
	private Map map;
	private Stage stage;
	private AnimationTimer mainAnimation;
	private Set<KeyCode> keyCodes;

	public DungeonPlayController(Stage s){
		super(s);
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
		// TODO
		Group dungeon = new Group();
		imgMap = new HashMap<>();
		initMap();

		// load all images
		File dir = new File(getClass().getClassLoader().getResource("img").getPath());
		File[] directoryListing = dir.listFiles();
		for (File child : directoryListing) {
			try {
				imgMap.put(child.getName().split("[.]")[0], new Image(new FileInputStream(child)));
			} catch	(Exception e){
				e.printStackTrace();
			}
		}

		GameEngine engine = new GameEngine(map);
		List<Node> nodes = new LinkedList<>();

		for (GameObject obj : engine.getAllObjects()) {
			ImageView imageView = new ImageView(imgMap.get(obj.getClassName()));
			imageView.setId(Integer.toString(obj.getObjID()));
			imageView.setTranslateX(obj.getLocation().getX() * GRID_SIZE);
			imageView.setTranslateY(obj.getLocation().getY() * GRID_SIZE);
			nodes.add(imageView);
		}


		// draw grids
		drawGridLine(dungeon);

		// keep track of currently pressed keys
		keyCodes = new HashSet<>();


		final LongProperty lastUpdateTime = new SimpleLongProperty(0);
		mainAnimation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				double elapsedSeconds = (now - lastUpdateTime.get()) / 1000000000.0;
				// make sure at most go 40 ms
				elapsedSeconds = elapsedSeconds > 0.04 ? 0.04 : elapsedSeconds;

				// get player's moving status
				if (keyCodes.contains(KeyCode.LEFT)) {
					player.setFacing(Direction.LEFT);
					player.setIsMoving(true);
				} else if (keyCodes.contains(KeyCode.RIGHT)) {
					player.setFacing(RIGHT);
					player.setIsMoving(true);
				} else if (keyCodes.contains(KeyCode.UP)) {
					player.setFacing(Direction.UP);
					player.setIsMoving(true);
				} else if (keyCodes.contains(KeyCode.DOWN)) {
					player.setFacing(DOWN);
					player.setIsMoving(true);
				} else {
					player.setIsMoving(false);
				}

				if (keyCodes.contains(KeyCode.A)) {
					Arrow arrow = engine.playerShootArrow();
					if (arrow != null) {
						ImageView imageView = new ImageView(imgMap.get(arrow.getClassName()));
						imageView.setId(Integer.toString(arrow.getObjID()));
						imageView.setTranslateX(arrow.getLocation().getX() * GRID_SIZE);
						imageView.setTranslateY(arrow.getLocation().getY() * GRID_SIZE);
						dungeon.getChildren().add(imageView);
					}
				}

				for (Movable movingObj : engine.getMovingObjects()) {
					double dx = 0, dy = 0;
					switch (movingObj.getFacing()) {
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
					double oldX = movingNode.getTranslateX();
					double oldY = movingNode.getTranslateY();
					double newX = oldX + dx;
					double newY = oldY + dy;

					// translate objs
					movingNode.setTranslateX(newX);
					movingNode.setTranslateY(newY);

					// detect collisions
					for (GameObject anotherObj : engine.getAllObjects()) {
						// ignore self with self
						if (movingObj.equals(anotherObj)) continue;

						Node anotherNode =
								dungeon.lookup("#" + Integer.toString(anotherObj.getObjID()));

						if (isColliding(movingNode, anotherNode)) {
							CollisionResult result = engine.handleCollision(movingObj, anotherObj);
							System.out.format("%s and %s => %s\n", movingObj, anotherObj, result);
							if (result.containFlag(CollisionResult.REJECT)) {
								// set translate co-ord back to before
								newX = oldX;
								newY = oldY;
								movingNode.setTranslateX(newX);
								movingNode.setTranslateY(newY);
							}
							if (result.containFlag(CollisionResult.LOSE)) {
								System.out.println("You've LOST the game!");
								restart();
							}
//                            if(result.containFlag(CollisionResult.WIN)){
//                                System.out.println("You've WON the game!");
//                                restart();
//                            }
							if (result.containFlag(CollisionResult.DELETE_FIRST)) {
								dungeon.getChildren().remove(movingNode);
							}
							if (result.containFlag(CollisionResult.DELETE_SECOND)) {
								dungeon.getChildren().remove(anotherNode);
							}
							if (result.containFlag(CollisionResult.REFRESH_INVENTORY)) {
								System.out.print(player.getInventory());
							}
						}
					}

					if (movingObj instanceof Monster) {
						// exact scheme co-ord conversion
						Point newPoint = movingObj.getLocation().clone();
						int mapX, mapY;
						switch (movingObj.getFacing()) {
							case LEFT:
								if ((mapX = (int) (newX + GRID_SIZE) / GRID_SIZE) == movingObj.getLocation().getX())
									break;
								newPoint.setX(mapX);
								break;
							case UP:
								if ((mapY = (int) (newY + GRID_SIZE) / GRID_SIZE) == movingObj.getLocation().getY())
									break;
								newPoint.setY(mapY);
								break;
							case RIGHT:
								if ((mapX = (int) newX / GRID_SIZE) == movingObj.getLocation().getX())
									break;
								newPoint.setX(mapX);
								break;
							case DOWN:
								if ((mapY = (int) newY / GRID_SIZE) == movingObj.getLocation().getY())
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
		dungeonPane.getChildren().add(dungeon);
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


	private void initMap() {
		Monster hunter;
		Wall wall0;
		Wall wall1;
		Wall wall2;
		Wall wall4;
		Wall wall5;
		Wall wall6;
		Wall wall7;
		Boulder boulder1;
		Boulder boulder2;
		MapBuilder mb;

		mb = new MapBuilder();
		player = new Player(new Point(0, 0));
		wall0 = new Wall(new Point(0,1));
		wall1 = new Wall(new Point(1,1));
		wall2 = new Wall(new Point(2,1));
		wall4 = new Wall(new Point(4,1));
		wall5 = new Wall(new Point(5, 1));
		wall6 = new Wall(new Point(6, 1));
		wall7 = new Wall(new Point(7,1 ));
		boulder1 = new Boulder(new Point(3, 3));
		boulder2 = new Boulder(new Point(3, 4));
		hunter = new Hunter(new Point(0,9));

		Sword sword = new Sword(new Point(1, 0));
		Arrow arrow = new Arrow(new Point(2, 0));


		mb.addObject(player);
		mb.addObject(sword);
		mb.addObject(arrow);
		mb.addObject(wall0);
		mb.addObject(wall1);
		mb.addObject(wall2);
		mb.addObject(wall4);
		mb.addObject(wall5);
		mb.addObject(wall6);
		mb.addObject(wall7);
		mb.addObject(boulder1);
		mb.addObject(boulder2);
		mb.addObject(hunter);
		map = new Map(mb);
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
		mainAnimation.stop();
		try {
//			start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
