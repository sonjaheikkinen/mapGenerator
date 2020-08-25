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
    private double waterLevel;

    /**
     * Method initializes variables and creates a map constructor and graphic
     * user interface of the program
     *
     * @param stage The graphic user interface and generated maps are shown on
     * stage
     */
    public void initialize(Stage stage) {
        Random random = new Random();
        Map map = new Map();
        Biomes biomes = new Biomes();
        BiomeSelector selector = new BiomeSelector(biomes);
        int mapSizeExponent = 9;
        int multiplier = 1;
        int canvasSize = multiplier * (int) (Math.pow(2, mapSizeExponent) + 1);
        int mapSeed = 200;
        int mapRandomizerRange = 400;
        int mapSize = (int) Math.pow(2, mapSizeExponent) + 1;
        this.waterLevel = 0.5;
        this.constructor = new MapConstructor(random, mapSize, mapSeed, mapRandomizerRange, map, selector);
        this.newMap();
        GraphicUI gui = new GraphicUI(stage, map, canvasSize, this, random, multiplier, waterLevel);
    }

    /**
     * Method calls for map constructor to create a new map
     */
    public void newMap() {
        Long creatingStarts = System.nanoTime();
        constructor.constructMap(waterLevel);
        Long creatingEnds = System.nanoTime();
        Long creatingTime = creatingEnds - creatingStarts;
        System.out.println("Map created in " + creatingTime + " nanoseconds (" + (creatingTime / 1000000) + " milliseconds)");
    }

    /**
     * Method returns the map constructor
     *
     * @return MapConstructor
     */
    public MapConstructor getMapConstructor() {
        return constructor;
    }

}
