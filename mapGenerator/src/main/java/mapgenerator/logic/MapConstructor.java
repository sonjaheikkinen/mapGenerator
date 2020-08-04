
package mapgenerator.logic;

import java.util.Random;
import mapgenerator.domain.Map;

/*
 * Calls for other classes to generate different parts of the map and puts all the parts together.
 */
public final class MapConstructor {

    private Map map;
    private Random random;
    private int exponent;
    private int seed;
    private int range;
    private double[][] heightMap;
    private boolean[][] water;
    private double[][] moisture;

    /**
     * Constructor for this class, initializes class variables.
     * @param mapSizeExponent The created map will have a side length of (2^mapSizeExponent) + 1
     * @param mapSeed The corners of the height map will be assigned a random value of mapSeed +/- 10
     * @param mapRandomizerRange The height values calculated for the height map will be affected by a random value of +/- mapRandomizerRange
     * @param map A map object in which the generated map is saved
     */
    public MapConstructor(Random random, int mapSizeExponent, int mapSeed, int mapRandomizerRange, Map map) {
        this.map = map;
        this.random = random;
        this.exponent = mapSizeExponent;
        this.seed = mapSeed;
        this.range = mapRandomizerRange;
    }

    /**
     * Creates the producers of map parts and calls for another method to generate map objects, which
     * are then given to a map object.
     */
    public void constructMap() {
        HeightmapGenerator heightmapGenerator = new HeightmapGenerator(random, exponent, seed, range);
        WaterGenerator waterGenerator = new WaterGenerator(exponent);
        generateMapObjects(heightmapGenerator, waterGenerator);
        map.setHeightMap(this.heightMap);
        map.setWater(water);
        map.setMoisture(moisture);
    }

    /**
     * Calls for other methods to generate different parts of the map.
     */
    public void generateMapObjects(HeightmapGenerator heightmapGenerator, WaterGenerator waterGenerator) {
        heightMap = heightmapGenerator.calculateHeights();
        waterGenerator.addWaterByHeight(50, heightMap);
        waterGenerator.addMoisture();
        moisture = waterGenerator.getMoisture();
        water = waterGenerator.getWater();
    }
    
    /**
     * Returns height map
     * @return An array containing height values as doubles.
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }
    
    public boolean[][] getWater() {
        return water;
    }

}
