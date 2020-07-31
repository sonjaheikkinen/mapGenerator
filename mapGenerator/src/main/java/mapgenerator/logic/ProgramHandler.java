
package mapgenerator.logic;

import java.util.Random;
import javafx.stage.Stage;
import mapgenerator.domain.Map;
import mapgenerator.gui.GraphicUI;

/**
 * Class controls the execution of the program
 */
public class ProgramHandler {
    
    private MapConstructor constructor;

    /**
     * Method initializes variables and creates a map constructor and graphic user interface of the program
     * @param stage The graphic user interface and generated maps are shown on stage
     */
    public void initialize(Stage stage) {
        Random random = new Random();
        Map map = new Map();
        int mapSizeExponent = 8;
        int canvasSize = (int) (Math.pow(2, mapSizeExponent) + 1) * 3;
        int mapSeed = 50;
        int mapRandomizerRange = 50;
        this.constructor = new MapConstructor(random, mapSizeExponent, mapSeed, mapRandomizerRange, map);
        this.newMap();
        GraphicUI gui = new GraphicUI(stage, map, canvasSize, this);
    }
    
    /**
     * Method calls for map constructor to create a new map
     */
    public void newMap() {
        constructor.constructMap();
    }

}
