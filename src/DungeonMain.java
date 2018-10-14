import Controller.*;
import GameEngine.Map;
import View.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class DungeonMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        Screen startScreen = new Screen(primaryStage, "Dungeon", "View/DungeonPlayScreen.fxml");
        try {
            Controller controller = new DungeonPlayController(primaryStage, new Map(new FileInputStream("testMap")));
            startScreen.display(controller);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
