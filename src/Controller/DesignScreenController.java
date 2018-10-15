package Controller;

import GameEngine.GameObject;
import GameEngine.Map;
import GameEngine.MapBuilder;
import GameEngine.Wall;
import GameEngine.utils.Point;
import View.Screen;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static Controller.Config.GRID_SIZE;

public class DesignScreenController extends Controller {
    @FXML
    private ListView<ImageView> objectsListView;

    @FXML
    private AnchorPane dungeonPane;

    @FXML
    private TextField mapNameTextField;

    private ResourceManager resources;

    private Set<KeyCode> keyPressed;
    /**
     * Classname of the current dragging object
     */
    private StringProperty draggingClass;
    private int maxRow;
    private int maxCol;

    private MapBuilder mapBuilder;

    public DesignScreenController(Stage s) {
        super(s);
        resources = new ResourceManager();
        draggingClass = new SimpleStringProperty();
        keyPressed = new HashSet<>();
        mapBuilder = new MapBuilder();
        maxCol = 11;
        maxRow = 11;
    }

    @Override
    public void afterInitialize() {
        stage.getScene().setOnKeyPressed(event -> keyPressed.add(event.getCode()));
        stage.getScene().setOnKeyReleased(event -> keyPressed.remove(event.getCode()));
    }

    @FXML
    public void initialize(){
        // set the dungeon gird pane to 11 x 11 by default
        dungeonPane.setMinWidth(maxCol * GRID_SIZE);
        dungeonPane.setMaxWidth(maxCol * GRID_SIZE);
        dungeonPane.setMinHeight(maxRow * GRID_SIZE);
        dungeonPane.setMaxHeight(maxRow * GRID_SIZE);

        resources.drawGridLine(dungeonPane.getChildren());

        initWallBoundary();

        for (String cls : resources.getAllClassNames()) {
            ImageView imageView = resources.createImageViewByClassName(cls);
            imageView.setId(cls);
            objectsListView.getItems().add(imageView);
        }

        objectsListView.setCellFactory(param -> {
            ListCell<ImageView> cell = new ListCell<ImageView>() {
                @Override
                public void updateItem(ImageView imgView, boolean empty) {
                    super.updateItem(imgView, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(imgView.getId());
                        setGraphic(imgView);
                    }
                }
            };
            // event when dragging cell in this ListView
            cell.setOnDragDetected(event -> handleDragStart(event, (ImageView) cell.getGraphic(), cell.getText(), false));
            return cell;
        });

        // event when dragging is over dungeonPane
        dungeonPane.setOnDragOver(event -> {
            // remove all previous indicator first
            dungeonPane.getChildren().removeIf(node -> node instanceof Rectangle);
            Dragboard db = event.getDragboard();

            Point point = getEventIndex(event.getSceneX(), event.getSceneY());

            if(db.hasImage() && !draggingClass.get().isEmpty() &&
                    point.getY() > 0 && point.getY() < maxRow - 1 &&
                    point.getX() > 0 && point.getX() < maxCol - 1) {
                // continuous placing feature
                if(keyPressed.contains(KeyCode.CONTROL)) {
                    if(mapBuilder.getObject(point) != null){
                        // delete object from map builder
                        GameObject deletedObj = mapBuilder.deleteObject(point);
                        // delete ImageView displayed in GridPane
                        dungeonPane.getChildren().remove(getNodeById(deletedObj.getObjID()));
                    }
                    newGameObject(draggingClass.get(), point, true);
                } else {
                    // visualise the current drop location
                    Rectangle indicator = new Rectangle(GRID_SIZE, GRID_SIZE);
                    indicator.setFill(new Color(0.5, 0.5, 1, 0.8));
                    indicator.setTranslateX(point.getX() * GRID_SIZE);
                    indicator.setTranslateY(point.getY() * GRID_SIZE);
                    dungeonPane.getChildren().add(indicator);

                    event.acceptTransferModes(TransferMode.COPY);
                }
            }
            event.consume();
        });

        dungeonPane.setOnDragDropped(event -> {
            // remove all previous indicator first
            dungeonPane.getChildren().removeIf(node -> node instanceof Rectangle);

            Point point = getEventIndex(event.getSceneX(), event.getSceneY());
            GameObject deleted = mapBuilder.deleteObject(point);
            if(deleted != null){
                dungeonPane.getChildren().remove(getNodeById(deleted.getObjID()));
            }

            newGameObject(draggingClass.get(), point, true);
            event.consume();
        });
    }

    @FXML
    public void onSaveButtonClicked(MouseEvent event){
        new File("map").mkdirs();

        Map map = new Map(mapBuilder);
        try {
            map.serialize(new FileOutputStream("map/" + mapNameTextField.getText()));
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Map serialized");
        Screen cs = new Screen(this.getStage(), "Select Mode", "View/ModeScreen.fxml");
        Controller controller = new ModeScreenController(this.getStage());
        cs.display(controller);
    }

    /**
     * Create a new game object in map builder and display it in dungeonGridPane
     *
     * @param enableInteraction whether to add handler to allow drag & delete
     */
    private void newGameObject(String className, Point point, boolean enableInteraction){
        GameObject obj = GameObject.build(className, point);
        if(obj == null) return;
        mapBuilder.addObject(obj);
        ImageView imageView = resources.createImageViewByGameObject(obj, dungeonPane.getChildren());

        if(enableInteraction) {
            imageView.setOnMouseClicked(this::handleRightClick);
            imageView.setOnDragDetected(e -> handleDragStart(e, imageView, obj.getClassName(), true));
        }
    }

    /**
     * Get the index of the cell in dungeonGridPane
     * by given absolute sceneX and sceneY
     * @return point index in grid pane
     */
    private Point getEventIndex(double sceneX, double sceneY){
        double paneX = sceneX - dungeonPane.getLayoutX();
        double paneY = sceneY - dungeonPane.getLayoutY();
        int row = (int)(paneY / GRID_SIZE);
        int col = (int)(paneX / GRID_SIZE);
        return new Point(col, row);
    }

    /**
     * Event handler for removing the object when right click it
     */
    private void handleRightClick(MouseEvent event){
        Point point = getEventIndex(event.getSceneX(), event.getSceneY());
        // right click to delete the image
        if(event.getButton() == MouseButton.SECONDARY){
            System.out.println("Right clicked");
            GameObject deleted = mapBuilder.deleteObject(point);
            if(deleted != null){
                dungeonPane.getChildren().remove(getNodeById(deleted.getObjID()));
            }
        }
    }

    private void handleDragStart(MouseEvent event, ImageView draggingNode, String classname, boolean duplicateAllowed) {
        Point point = getEventIndex(event.getSceneX(), event.getSceneY());

        Dragboard db = draggingNode.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        content.putImage(draggingNode.getImage());
        db.setContent(content);
        draggingClass.set(classname);
        if (duplicateAllowed && !(keyPressed.contains(KeyCode.SHIFT) || keyPressed.contains(KeyCode.CONTROL))) {
            draggingNode.setOnDragDone(e2 -> {
                mapBuilder.deleteObject(point);
            });
            dungeonPane.getChildren().remove(draggingNode);
        }
    }

    private void initWallBoundary(){
        String wallClassName = Wall.class.getSimpleName();
        for(int x = 0; x < maxCol; x++) {
            newGameObject(wallClassName, new Point(x, 0), false);
            newGameObject(wallClassName, new Point(x, maxRow - 1), false);
        }

        for(int y = 1; y < maxRow - 1; y++) {
            newGameObject(wallClassName, new Point(0, y), false);
            newGameObject(wallClassName, new Point(maxCol - 1, y), false);
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
}

