package mapgenerator;

import java.util.Random;
import javafx.application.Application;
import javafx.stage.Stage;
import mapgenerator.domain.Map;
import mapgenerator.gui.GraphicUI;
import mapgenerator.logic.HeightmapGenerator;
import mapgenerator.logic.MapConstructor;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Random random = new Random();
        Map map = new Map();
        int mapSizeExponent = 6;
        int mapSeed = 50;
        int mapRandomizerRange = 50;
        HeightmapGenerator hmGenerator = new HeightmapGenerator(random, mapSizeExponent, mapSeed, mapRandomizerRange);
        MapConstructor constructor = new MapConstructor(map, hmGenerator);
        GraphicUI gui = new GraphicUI(stage, map);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(Main.class);
    }

}
