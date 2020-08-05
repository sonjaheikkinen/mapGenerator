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
    private int[][] biomes;
    private BiomeCreator bioc;

    /**
     * Constructor for this class, initializes class variables.
     *
     * @param mapSizeExponent The created map will have a side length of
     * (2^mapSizeExponent) + 1
     * @param mapSeed The corners of the height map will be assigned a random
     * value of mapSeed +/- 10
     * @param mapRandomizerRange The height values calculated for the height map
     * will be affected by a random value of +/- mapRandomizerRange
     * @param map A map object in which the generated map is saved
     */
    public MapConstructor(Random random, int mapSizeExponent, int mapSeed, int mapRandomizerRange, Map map, BiomeCreator bioc) {
        this.map = map;
        this.random = random;
        this.exponent = mapSizeExponent;
        this.seed = mapSeed;
        this.range = mapRandomizerRange;
        this.bioc = bioc;
    }

    /**
     * Creates the producers of map parts and calls for another method to
     * generate map objects, which are then given to a map object.
     */
    public void constructMap() {
        WaterGenerator waterGen = new WaterGenerator(exponent);
        generateMapObjects(waterGen);
        map.setHeightMap(this.heightMap);
        map.setWater(water);
        map.setMoisture(moisture);
        map.setBiomes(biomes);
    }

    /**
     * Calls for other methods to generate different parts of the map.
     */
    public void generateMapObjects(WaterGenerator waterGen) {
        heightMap = new NoiseMapGenerator(random, exponent, seed, range).createNoise();
        waterGen.addWaterByHeight(50, heightMap);
        moisture = new NoiseMapGenerator(random, exponent, seed, range).createNoise();
        water = waterGen.getWater();
        biomes = bioc.createBiomes(heightMap, water, moisture);
    }

    /**
     * Returns height map
     *
     * @return An array containing height values as doubles.
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }

    public boolean[][] getWater() {
        return water;
    }

}
