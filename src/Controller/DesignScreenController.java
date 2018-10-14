package Controller;

import GameEngine.GameObject;
import GameEngine.Map;
import GameEngine.MapBuilder;
import GameEngine.Wall;
import GameEngine.utils.Point;
import View.Screen;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static Controller.Config.GRID_SIZE;

public class DesignScreenController extends Controller {
    @FXML
    private ListView<ImageView> objectsListView;

    @FXML
    private GridPane dungeonGridPane;

    @FXML
    private TextField mapNameTextField;

    private HashMap<String, Image> imgMap;
    private ObservableList<ImageView> imgViews;
    private Set<KeyCode> keyPressed;
    /**
     * Key is GameObject in mapBuilder
     * Value is the ImageView displayed in dungeonGridPane
     */
    private HashMap<GameObject, ImageView> objectImageViewMap;
    private StringProperty draggingClass;
    private int maxRow;
    private int maxCol;

    private MapBuilder mapBuilder;

    public DesignScreenController(Stage s) {
        super(s);
        imgMap = new HashMap<>();
        draggingClass = new SimpleStringProperty();
        objectImageViewMap = new HashMap<>();
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
        for(int i = 0; i < maxCol; i++){
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / maxCol);
            dungeonGridPane.getColumnConstraints().add(colConst);
        }
        for(int i = 0; i < maxRow; i++){
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / maxRow);
            dungeonGridPane.getRowConstraints().add(rowConst);
        }
        dungeonGridPane.setMinWidth(maxCol * GRID_SIZE);
        dungeonGridPane.setMaxWidth(maxCol * GRID_SIZE);
        dungeonGridPane.setMinHeight(maxRow * GRID_SIZE);
        dungeonGridPane.setMaxHeight(maxRow * GRID_SIZE);

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

        initWallBoundary();

        for (java.util.Map.Entry<String, Image> entry : imgMap.entrySet()) {
            ImageView imageView = new ImageView(entry.getValue());
            imageView.setId(entry.getKey());
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
            // event when dragging a GameObject
            cell.setOnDragDetected(event -> handleDragStart(event, (ImageView) cell.getGraphic(), cell.getText(), false));
            return cell;
        });

        dungeonGridPane.setOnDragOver(event -> {
            // remove all previous indicator first
            dungeonGridPane.getChildren().removeIf(node -> node instanceof Rectangle);
            Dragboard db = event.getDragboard();

//            System.out.format("(%f, %f)\n", event.getSceneX(), event.getSceneX());
            double paneX = event.getSceneX() + dungeonGridPane.getLayoutX();
            double paneY = event.getSceneY() + dungeonGridPane.getLayoutY();
            int row = (int)(paneY / GRID_SIZE);
            int col = (int)(paneX / GRID_SIZE);

            if(db.hasImage() && !draggingClass.get().isEmpty() &&
                    row > 0 && row < maxRow - 1 && col > 0 && col < maxCol - 1) {
                Point point = new Point(col, row);
                // continuous placing feature
                if(keyPressed.contains(KeyCode.ALT)) {
                    if(mapBuilder.getObject(point) != null){
                        // delete object from map builder
                        GameObject deletedObj = mapBuilder.deleteObject(point);
                        // delete ImageView displayed in GridPane
                        dungeonGridPane.getChildren().remove(objectImageViewMap.get(deletedObj));
                        objectImageViewMap.remove(deletedObj);
                    }
                    newGameObject(draggingClass.get(), point, true);
                } else {
                    // visualise the current drop location
                    Rectangle indicator = new Rectangle(GRID_SIZE, GRID_SIZE);
                    indicator.setFill(new Color(0.5, 0.5, 1, 0.8));
                    dungeonGridPane.add(indicator, col, row);

                    event.acceptTransferModes(TransferMode.COPY);
                }
            }
            event.consume();
        });

        dungeonGridPane.setOnDragDropped(event -> {
            // remove all previous indicator first
            dungeonGridPane.getChildren().removeIf(node -> node instanceof Rectangle);

            Point point = getEventIndex(event.getSceneX(), event.getSceneY());
            GameObject deleted = mapBuilder.deleteObject(point);
            if(deleted != null){
                dungeonGridPane.getChildren().remove(objectImageViewMap.get(deleted));
                objectImageViewMap.remove(deleted);
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
        ImageView imageView = new ImageView(imgMap.get(className));
        if(enableInteraction) {
            imageView.setOnMouseClicked(this::handleRightClick);
            imageView.setOnDragDetected(e -> handleDragStart(e, imageView, obj.getClassName(), true));
        }
        objectImageViewMap.put(obj, imageView);
        dungeonGridPane.add(imageView, point.getX(), point.getY());
    }

    /**
     * Get the index of the cell in dungeonGridPane
     * by given absolute sceneX and sceneY
     * @return point index in grid pane
     */
    private Point getEventIndex(double sceneX, double sceneY){
        double paneX = sceneX + dungeonGridPane.getLayoutX();
        double paneY = sceneY + dungeonGridPane.getLayoutY();
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
                dungeonGridPane.getChildren().remove(objectImageViewMap.get(deleted));
                objectImageViewMap.remove(deleted);
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
        if (duplicateAllowed && !(keyPressed.contains(KeyCode.SHIFT) || keyPressed.contains(KeyCode.ALT))) {
            draggingNode.setOnDragDone(e2 -> {
                dungeonGridPane.getChildren().remove(draggingNode);
                GameObject deleted = mapBuilder.deleteObject(point);
                objectImageViewMap.remove(deleted);
            });
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
}

