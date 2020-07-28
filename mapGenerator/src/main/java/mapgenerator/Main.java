package mapGenerator;

import java.util.Random;
import javafx.application.Application;
import javafx.stage.Stage;
import mapGenerator.domain.Map;
import mapGenerator.gui.GraphicUI;
import mapGenerator.logic.HeightmapGenerator;
import mapGenerator.logic.MapConstructor;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Random random = new Random();
        Map map = new Map();
        HeightmapGenerator hmGenerator = new HeightmapGenerator(random);
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