import Controller.*;
import View.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		Screen startScreen = new Screen(primaryStage, "Dungeon", "View/StartScreen.fxml");
		Controller controller = new StartScreenController(primaryStage);
		startScreen.display(controller);
	}

	public static void main(String[] args) {
		launch(args);
	}
}