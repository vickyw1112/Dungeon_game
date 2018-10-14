package View;
import Controller.DungeonPlayController;
import GameEngine.*;
import GameEngine.Map;
import GameEngine.utils.Point;
import javafx.animation.AnimationTimer;
import java.io.IOException;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class DungeonPlayScreen {

	public static final int GRID_SIZE = 32;

	private HashMap<String, Image> imgMap;
	private Player player;
	private Map map;
	private AnimationTimer mainAnimation;

	private Stage s;
	private String title;
	private FXMLLoader fxmlLoader;

	public DungeonPlayScreen(Stage s) {
		this.s = s;
		this.title = "Dungeon Play";
		this.fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/DungeonPlayScreen.fxml"));
	}

	public void start() {
		s.setTitle(title);
		fxmlLoader.setController(new DungeonPlayController(s));
		try {
			// load into a Parent node called root
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root); // optionally specify dimensions too

			s.setScene(scene);
			s.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
			start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
