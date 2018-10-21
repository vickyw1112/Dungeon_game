package Controller;

import GameEngine.Map;
import Sample.SampleMaps;
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

	/**
	 * field values
	 */
    @FXML
    private ListView<File> mapListView;

    private HashMap<File, Map> maps;

	/**
	 * Constructor for MapSelectScreenController
	 *
	 * @param s
	 */
	public MapSelectScreenController(Stage s){
        super(s);
        maps = new HashMap<>();
    }

	/**
	 * Brings player to dungeonPlay screen on pressing Play Button
	 */
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

	/**
	 * Brings player to mode selection screen on pressing Mode Select button
	 */
	@FXML
    public void onModeSelectBtnClicked() {
        Screen screen = new Screen(stage, "Mode Select", "View/ModeScreen.fxml");
        screen.display(new ModeScreenController(stage));
    }

	/**
	 * initalises a list of maps
	 */
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
                    if(map == null)
                        return;
                    maps.put(item, map);
                    setGraphic(new MapBoxController(stage, map, item.getName().split("\\.")[0]).getBox());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mapListView.setOnMouseClicked(event -> {
            // load map if double clicked
            if(event.getClickCount() == 2){
                onPlayButtonClicked();
            }
        });

        // list all map
        File dir = new File("map");
        File[] files = dir.listFiles();
        // generate maps if there are no maps yet
        if(files == null || files.length == 0) {
            SampleMaps.generateMaps();
            files = dir.listFiles();
        }

        for (File file: files) {
            if(!file.getName().matches(".*\\.dungeon"))
                continue;
            mapListView.getItems().add(file);
        }
    }

	/**
	 * onModeSelectButtonClicked FXML method
	 */
	@FXML
	public void onModeSelectButtonClicked() {

	}

}

