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

    private Random random;
    private Map mapStorage;
    private Biomes biomes;
    private BiomeSelector selector;
    private MapConstructor constructor;
    private double waterLevel;

    public ProgramHandler() {
        this.random = new Random();
        this.mapStorage = new Map();
        this.biomes = new Biomes();
        this.selector = new BiomeSelector(biomes);
    }

    /**
     * Method initializes variables and creates a map constructor and graphic
     * user interface of the program
     *
     * @param stage The graphic user interface and generated maps are shown on
     * stage
     */
    public void initialize(Stage stage) {
        int mapSizeExponent = 10;
        int multiplier = 1;
        int canvasSize = multiplier * (int) (Math.pow(2, mapSizeExponent) + 1);
        this.newMap(mapSizeExponent);
        GraphicUI gui = new GraphicUI(random, waterLevel, biomes);
        gui.start(stage, mapStorage, canvasSize, this, multiplier, mapSizeExponent);
    }

    /**
     * Method calls for map constructor to create a new map
     */
    public void newMap(int mapSizeExponent) {
        int mapSeed = 200;
        int mapRandomizerRange = 400;
        int mapSize = (int) Math.pow(2, mapSizeExponent) + 1;
        this.waterLevel = 0.5;
        this.constructor = new MapConstructor(random, mapSize, mapSeed, mapRandomizerRange, mapStorage, selector);
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
