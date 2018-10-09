import View.DungeonPlayScreen;
import javafx.application.Application;
import java.io.IOException;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        DungeonPlayScreen dungeonPlayScreen = new DungeonPlayScreen(primaryStage);
        dungeonPlayScreen.start();
    }

}
