package Controller;

import javafx.stage.Stage;

public abstract class Controller {
    protected Stage stage;
    public Controller(Stage s){
        this.stage = s;
    }

    public Stage getStage(){ return this.stage; }

    public void afterInitialize() {

    }
}
