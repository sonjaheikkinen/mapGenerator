package mapgenerator;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;
import javafx.application.Application;
import javafx.stage.Stage;
import mapgenerator.datastructures.BinaryHeap;
import mapgenerator.datastructures.Node;
import mapgenerator.logic.ProgramHandler;
import mapgenerator.logic.WaterGenerator;
import mapgenerator.math.RandomNumberGenerator;

/**
 * Class creates a program handler and launches the program.
 */
public class Main extends Application {

    /**
     * Method creates a program handler which is in control of initializing and
     * controlling program execution
     *
     * @param stage Used in graphic user interface to show things on screen.
     */
    @Override
    public void start(Stage stage) {
        ProgramHandler handler = new ProgramHandler();
        handler.initialize(stage);
    }

    /**
     * Main method launches the whole program
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        launch(Main.class);
    }

}
