package Controller;

import javafx.stage.Stage;

/**
 * Base class for all FXML controllers
 */
public abstract class Controller {
    protected Stage stage;

    public Controller(Stage s){
        this.stage = s;
    }

    public Stage getStage(){ return this.stage; }

    public void afterInitialize() {

    }
}
