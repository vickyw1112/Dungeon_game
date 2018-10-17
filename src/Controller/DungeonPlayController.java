package Controller;

import GameEngine.*;
import GameEngine.Map;
import GameEngine.utils.*;
import GameEngine.CollisionHandler.*;
import Sample.SampleMaps;
import View.Screen;
import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

import static Controller.Config.GRID_SIZE;


public class DungeonPlayController extends Controller{

	@FXML
	private AnchorPane dungeonPane;

    private AnimationTimer mainAnimation;

	@FXML
	private ProgressBar timerInvincibilityPotion;

    private TimerCollection timers;
	private ResourceManager resources;
    private Set<KeyCode> keyPressed;

	private Player player;
	private Map map;

	public DungeonPlayController(Stage s, Map map){
		super(s);
        keyPressed = new HashSet<>();
        timers = new TimerCollection();
        this.map = map;
	}

    /**
     * Debug only, use sample hardcoded map
     * @param s the stage
     */
	public DungeonPlayController(Stage s){
	    this(s, SampleMaps.getMap2());
    }

    @Override
    public void afterInitialize() {
        stage.getScene().setOnKeyPressed(event -> keyPressed.add(event.getCode()));
        stage.getScene().setOnKeyReleased(event -> keyPressed.remove(event.getCode()));
    }

    @FXML
	public void initialize() {
		timerInvincibilityPotion.setOpacity(0.0);

	    resources = new ResourceManager();
	    initDungeon();
	}

	private void handleObjectStateChange(GameObject obj){
        ImageView imageView = (ImageView) getNodeById(obj.getObjID());
        if(imageView != null) {
            imageView.setImage(resources.getImage(obj.getClassName(), obj.getState()));
            System.out.println(obj + " changed state to: " + obj.getState());
        }
    }

	private void initDungeon() {
		GameEngine engine = new GameEngine(map, this::handleObjectStateChange);

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

				timers.updateAll((int)(elapsedSeconds * 1000));

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

				if(keyPressed.contains(KeyCode.A)){
					Arrow arrow = engine.playerShootArrow();
					if(arrow != null)
						resources.createImageViewByGameObject(arrow, nodes);
				}

				if(keyPressed.contains(KeyCode.B)){
					Bomb bomb = engine.playerSetBomb();
					if(bomb != null){
						ImageView imageView =
                                resources.createImageViewByGameObject(bomb, nodes);

						Timer timer = new Timer(bomb, arg -> {
                            System.out.format("%s exploded\n", bomb);
                            List<GameObject> destroyedObjects = bomb.explode(engine);

                            if (destroyedObjects == null) {
                                // LOST
                                System.out.println("You've got bombed!");
                                restart();
                            } else {
                                // remove all destroyed nodes
                                dungeonPane.getChildren().removeAll(
                                        destroyedObjects.stream()
                                                .map(o -> getNodeById(o.getObjID()))
                                                .collect(Collectors.toList()));
                                // remove the bomb itself
                                nodes.remove(imageView);
                            }
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
							if(result.containFlag(CollisionResult.REJECT)){
								// set translate co-ord back to before
								movingNode.setTranslateX(oldX);
								movingNode.setTranslateY(oldY);
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
                                won();
							}
							if(result.containFlag(CollisionResult.DELETE_FIRST)){
								nodes.remove(movingNode);
							}
							if(result.containFlag(CollisionResult.DELETE_SECOND)){
								nodes.remove(anotherNode);
							}
							if(result.containFlag(CollisionResult.REFRESH_INVENTORY)){
								System.out.print(player.getInventory());
							}
							if(result.containFlag(CollisionResult.REFRESH_EFFECT_TIMER)){
                                Potion potion = (Potion) anotherObj;
                                Timer timer = new Timer(potion, arg -> {
                                    player.removePotionEffect(engine, potion);
                                    timerInvincibilityPotion.setOpacity(0.0);
                                });

                                timer.setOnUpdateTimer(t -> {
                                	timerInvincibilityPotion.setOpacity(1.0);
									timerInvincibilityPotion.setProgress(t.getRemain() * 1.0 / t.getTotalDuration());
								});

                                timers.add(timer);
                            }

						}
					}

                    engine.changeObjectLocation(movingObj, getUpdatedPoint(movingObj, newX, newY));
				}
				lastUpdateTime.set(now);
			}
		};

		mainAnimation.start();
	}

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


	private void restart(){
        Screen cs = new Screen(this.getStage(), "Dungeon", "View/DungeonPlayScreen.fxml");
        Controller controller = new DungeonPlayController(this.getStage());
        cs.display(controller);
	}

	private void won(){
		Screen cs = new Screen(this.getStage(), "Highscore", "View/HighscoreScreen.fxml");
		Controller controller = new HighscoreScreenController(this.getStage());
		cs.display(controller);
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

}
