import Controller.*;
import View.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

public class DesignMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        Screen startScreen = new Screen(primaryStage, "Dungeon", "View/DesignScreen.fxml");
        Controller controller = new DesignScreenController(primaryStage);
        startScreen.display(controller);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
