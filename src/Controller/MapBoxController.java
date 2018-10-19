package Controller;

import GameEngine.Map;
import View.Screen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MapBoxController {
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

    public MapBoxController(Stage stage, Map map, String mapName){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/MapBox.fxml"));
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        mapNameTestField.setText(mapName);
        mapSizeTextField.setText(map.getSizeX() + " x " + map.getSizeY());
        Image mapPreview = null;
        try {
            mapPreview = new Image(new FileInputStream(getClass().getClassLoader().getResource("img/question-mark.png").getPath()));
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
        try {
            mapPreview = new Image(new FileInputStream("map/" + mapName + ".png"));
        } catch (FileNotFoundException e){
            System.err.println("Map preview image not found for " + mapName);
        }
        mapPreviewImageView.setImage(mapPreview);
        mapPreviewImageView.setPreserveRatio(true);
        mapPreviewImageView.setFitHeight(200);
        mapPreviewImageView.setFitWidth(200);
        mapAuthorTextField.setText("By " + map.getAuthor());

        modifyMapBtn.setOnMouseClicked(event -> {
            Screen screen = new Screen(stage, "Design Dungeon", "View/DesignScreen.fxml");
            DesignScreenController controller = new DesignScreenController(stage);
            screen.display(controller);
            controller.loadExistingMapBuilder(map, mapName);
        });

    }

    public GridPane getBox() {
        return box;
    }


}

