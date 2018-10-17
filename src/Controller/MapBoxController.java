package Controller;

import GameEngine.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
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

    public MapBoxController(Map map, String mapName){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("View/mapBox.fxml"));
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
            mapNameTestField.setText(mapName);
            mapSizeTextField.setText(map.getSizeX() + " x " + map.getSizeY());
            Image mapPreview = new Image(new FileInputStream("map/" + mapName + ".png"));
            mapPreviewImageView.setImage(mapPreview);
            mapPreviewImageView.setPreserveRatio(true);
            mapPreviewImageView.setFitHeight(200);
            mapPreviewImageView.setFitWidth(200);
            mapAuthorTextField.setText("By " + map.getAuthor());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public GridPane getBox() {
        return box;
    }


}

