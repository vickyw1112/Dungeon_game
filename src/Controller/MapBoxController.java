package Controller;

import GameEngine.Map;
import GameEngine.WinningCondition.WinningCondition;
import View.Screen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MapBoxController {

	/**
	 * field values
	 */
    @FXML
    private GridPane box;

    @FXML
    private Label mapNameTestField;

    @FXML
    private Label mapSizeTextField;

    @FXML
    private ImageView mapPreviewImageView;

    @FXML
    private Label mapAuthorTextField;

    @FXML
    private Button modifyMapBtn;

    @FXML
    private VBox winningConditions;

    public MapBoxController(Stage stage, Map map){
        // TODO load resources once only in first screen and pass it around
        ResourceManager resources = new ResourceManager();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/MapBox.fxml"));
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        // set map info
        mapNameTestField.setText(map.getMapName());
        mapSizeTextField.setText(map.getSizeX() + " x " + map.getSizeY());

        // set map preview img
        Image mapPreview = null;
        try {
            mapPreview = new Image(new FileInputStream(getClass().getClassLoader().getResource("img/question-mark.png").getPath()));
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
        try {
            mapPreview = new Image(new FileInputStream("map/" + map.getMapName() + ".png"));
        } catch (FileNotFoundException e){
            System.err.println("Map preview image not found for " + map.getMapName());
        }
        mapPreviewImageView.setImage(mapPreview);
        mapPreviewImageView.setPreserveRatio(true);
        mapPreviewImageView.setFitHeight(200);
        mapPreviewImageView.setFitWidth(200);


        // set winning conditions
        for(WinningCondition winningCondition: map.getWinningConditions()){
            Label label = new Label();
            label.setGraphic(resources.createImageViewByWinningCondition(winningCondition));
            label.setText(winningCondition.displayString());
            winningConditions.getChildren().add(label);
        }

        // set author
        mapAuthorTextField.setText("By " + map.getAuthor());

        // handle modify map button
        modifyMapBtn.setOnMouseClicked(event -> {
            Screen screen = new Screen(stage, "Design Dungeon", "View/DesignScreen.fxml");
            DesignScreenController controller = new DesignScreenController(stage);
            screen.display(controller);
            controller.loadExistingMapBuilder(map, map.getMapName());
        });

    }

	public GridPane getBox() {
        return box;
    }


}

