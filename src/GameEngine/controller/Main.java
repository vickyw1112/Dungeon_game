package GameEngine.controller;


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage s) throws Exception{
        Screen screen = new Screen(s, "Whatever", "GameEngine/controller/fixedMap.fxml");
        Controller cc = new fixedMapControllor(s);
        screen.display(cc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}