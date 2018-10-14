package Controller;

import GameEngine.Map;
import View.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;

public class MapSelectScreenController extends Controller {
    @FXML
    private ListView<File> mapListView;

    @FXML
    public void onModeSelectButtonClicked() {

    }

    public MapSelectScreenController(Stage s){
        super(s);
    }

    @FXML
    public void onPlayButtonClicked() {
        File currSelect = mapListView.getSelectionModel().getSelectedItem();
        try {
            if (currSelect != null) {
                Map map = new Map(new FileInputStream(currSelect));
                Screen screen = new Screen(stage, "Dungeon", "View/DungeonPlayScreen.fxml");
                screen.display(new DungeonPlayController(stage, map));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // list all map
        File dir = new File("map");
        File[] files = dir.listFiles();
        if(files != null){
            for (File file: files) {
                mapListView.getItems().add(file);
            }
        }
    }

}

