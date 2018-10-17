package Controller;

import javafx.stage.Stage;

import java.util.List;

public abstract class Controller {
	private List<String> highscore;
    private Stage s;
    public Controller(Stage s){
        this.s = s;
    }

    public Stage getStage(){ return this.s; }
}
