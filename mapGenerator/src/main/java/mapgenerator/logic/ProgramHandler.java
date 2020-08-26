package mapgenerator.logic;

import java.util.Random;
import javafx.stage.Stage;
import mapgenerator.domain.Biomes;
import mapgenerator.domain.Map;
import mapgenerator.gui.GraphicUI;
import mapgenerator.math.Calculator;

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
    private Calculator calc;

    /**
     * Constructor initializes all needed class variables.
     */
    public ProgramHandler() {
        this.random = new Random();
        this.mapStorage = new Map();
        this.biomes = new Biomes();
        this.selector = new BiomeSelector(biomes);
        this.calc = new Calculator();
    }

    /**
     * Method creates a map constructor and graphic
     * user interface of the program
     *
     * @param stage The graphic user interface and generated maps are shown on
     * stage
     */
    public void initialize(Stage stage) {
        int mapSizeExponent = 9;
        int canvasSize = calc.pow(2, mapSizeExponent) + 1;
        this.newMap(mapSizeExponent);
        GraphicUI gui = new GraphicUI(waterLevel, biomes, mapStorage, canvasSize);
        gui.start(stage, this, mapSizeExponent);
    }

    /**
     * Method creates a new map constructor that then creates a new map
     * @param mapSizeExponent The constructed map will be of size (2 ^ exponent)+ 1
     */
    public void newMap(int mapSizeExponent) {
        int mapSeed = 200;
        int mapRandomizerRange = 400;
        int mapSize = calc.pow(2, mapSizeExponent) + 1;
        this.waterLevel = 0.5;
        this.constructor = new MapConstructor(random, mapSize, mapSeed, mapRandomizerRange, mapStorage, selector, calc);
        Long creatingStarts = System.nanoTime();
        constructor.constructMap(waterLevel);
        Long creatingEnds = System.nanoTime();
        Long creatingTime = creatingEnds - creatingStarts;
        System.out.println("Map created in " + creatingTime + " nanoseconds (" + (creatingTime / 1000000) + " milliseconds)");
    }

    public MapConstructor getMapConstructor() {
        return constructor;
    }

}
