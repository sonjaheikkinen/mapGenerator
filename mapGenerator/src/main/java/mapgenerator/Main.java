package mapgenerator;

import javafx.application.Application;
import javafx.stage.Stage;
import mapgenerator.logic.ProgramHandler;

/**
 * Class creates a program handler and launches the program.
 */
public class Main extends Application {

    /**
     * Method creates a program handler which is in control of initializing and controlling program execution
     * @param stage Used in graphic user interface to show things on screen.
     */
    @Override
    public void start(Stage stage) {
        ProgramHandler handler = new ProgramHandler();
        handler.initialize(stage);
    }

    /**
     * Main method launches the whole program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(Main.class);
    }

}
