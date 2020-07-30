
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

    /**
     * Constructor for this class, initializes class variables.
     * @param map A map object in which the generated map is saved
     * @param hmGenerator Generates height values of the map
     */
    public MapConstructor(Random random, int mapSizeExponent, int mapSeed, int mapRandomizerRange, Map map) {
        this.map = map;
        this.random = random;
        this.exponent = mapSizeExponent;
        this.seed = mapSeed;
        this.range = mapRandomizerRange;
    }

    /**
     * Calls for generateMapObjects to generate different map parts and gives these to a map object.
     */
    public void constructMap() {
        HeightmapGenerator heightmapGenerator = new HeightmapGenerator(random, exponent, seed, range);
        WaterGenerator waterGenerator = new WaterGenerator(exponent);
        generateMapObjects(heightmapGenerator, waterGenerator);
        map.setHeightMap(this.heightMap);
        map.setWater(water);
    }

    /**
     * Calls for other methods to generate different parts of the map.
     */
    public void generateMapObjects(HeightmapGenerator heigthmapGenerator, WaterGenerator waterGenerator) {
        heightMap = heigthmapGenerator.calculateHeights();
        waterGenerator.addWaterByHeight(50, heightMap);
        water = waterGenerator.getWater();
    }
    
    /**
     * Returns height map
     * @return An array containing height values as doubles.
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }

}
