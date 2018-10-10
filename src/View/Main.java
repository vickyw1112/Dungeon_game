package View;

import Controller.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Screen startScreen = new StartScreen(primaryStage, "Dungeon", "View/StartScreen.fxml");
		Controller controller = new StartScreenController(primaryStage);
		startScreen.display(controller);
	}

	public static void main(String[] args) {
		launch(args);
	}
}