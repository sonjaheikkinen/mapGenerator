
package mapgenerator.logic;

import java.util.Random;
import javafx.stage.Stage;
import mapgenerator.domain.Biomes;
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
        Biomes biomes = new Biomes();
        BiomeSelector selector = new BiomeSelector(biomes);
        int mapSizeExponent = 9;
        int canvasSize = (int) (Math.pow(2, mapSizeExponent) + 1);
        int mapSeed = 100;
        int mapRandomizerRange = 200;
        this.constructor = new MapConstructor(random, mapSizeExponent, mapSeed, mapRandomizerRange, map, selector);
        this.newMap();
        GraphicUI gui = new GraphicUI(stage, map, canvasSize, this, random);
    }
    
    /**
     * Method calls for map constructor to create a new map
     */
    public void newMap() {
        constructor.constructMap();
    }

    /**
     * Method returns the map constructor
     * @return MapConstructor
     */
    public MapConstructor getMapConstructor() {
        return constructor;
    }

}
