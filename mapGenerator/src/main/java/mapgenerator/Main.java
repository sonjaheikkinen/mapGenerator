package mapgenerator;

import java.util.Random;
import javafx.application.Application;
import javafx.stage.Stage;
import mapgenerator.domain.Map;
import mapgenerator.gui.GraphicUI;
import mapgenerator.logic.HeightmapGenerator;
import mapgenerator.logic.MapConstructor;

/**
 * Class creates instances of other classes and launches the program.
 */
public class Main extends Application {

    /**
     * Method creates instances of the map, generating logic and graphic user interface
     * @param stage Used in graphic user interface to show things on screen.
     */
    @Override
    public void start(Stage stage) {
        Random random = new Random();
        Map map = new Map();
        int mapSizeExponent = 6;
        int canvasSize =  (int) (Math.pow(2, mapSizeExponent) + 1) * 10;
        int mapSeed = 50;
        int mapRandomizerRange = 50;
        HeightmapGenerator hmGenerator = new HeightmapGenerator(random, mapSizeExponent, mapSeed, mapRandomizerRange);
        MapConstructor constructor = new MapConstructor(map, hmGenerator);
        GraphicUI gui = new GraphicUI(stage, map, canvasSize);
    }

    /**
     * Main method launches the whole program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(Main.class);
    }

}
