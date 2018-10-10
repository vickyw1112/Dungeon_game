package View;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		StartScreen startScreen = new StartScreen(primaryStage);
		startScreen.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}