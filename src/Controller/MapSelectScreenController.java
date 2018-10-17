package Controller;

import GameEngine.Map;
import View.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class MapSelectScreenController extends Controller {
    @FXML
    private ListView<File> mapListView;

    @FXML
    public void onModeSelectButtonClicked() {

    }

    private HashMap<File, Map> maps;

    public MapSelectScreenController(Stage s){
        super(s);
        maps = new HashMap<>();
    }

    @FXML
    public void onPlayButtonClicked() {
        Map map = maps.get(mapListView.getSelectionModel().getSelectedItem());
        try {
            if (map != null) {
                Screen screen = new Screen(stage, "Dungeon", "View/DungeonPlayScreen.fxml");
                screen.display(new DungeonPlayController(stage, map));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        mapListView.setCellFactory(param -> new ListCell<File>(){
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if(empty)
                    return;
                Map map;
                try {
                    map = Map.loadFromFile(new FileInputStream(item));
                    maps.put(item, map);
                    setGraphic(new MapBoxController(map, item.getName().split("\\.")[0]).getBox());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // list all map
        File dir = new File("map");
        File[] files = dir.listFiles();
        if(files != null){
            for (File file: files) {
                if(!file.getName().matches(".*\\.dungeon"))
                    continue;
                mapListView.getItems().add(file);
            }
        }
    }

}

