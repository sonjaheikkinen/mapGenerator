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
    private double[][] heightMapOrig;
    private boolean[][] water;
    private double[][] moisture;
    private int[][] biomes;
    private BiomeSelector bioS;
    private double maxHeight;

    /**
     * Constructor for this class, initializes class variables.
     *
     * @param random A random number generator
     * @param mapSizeExponent The created map will have a side length of
     * (2^mapSizeExponent) + 1
     * @param mapSeed The corners of the height map will be assigned a random
     * value of mapSeed +/- 10
     * @param mapRandomizerRange The height values calculated for the height map
     * will be affected by a random value of +/- mapRandomizerRange
     * @param map A map object in which the generated map is saved
     * @param bioS A biome selector objects for adding biomes to map
     */
    public MapConstructor(Random random, int mapSizeExponent, int mapSeed, int mapRandomizerRange, Map map, BiomeSelector bioS) {
        this.map = map;
        this.random = random;
        this.exponent = mapSizeExponent;
        this.seed = mapSeed;
        this.range = mapRandomizerRange;
        this.bioS = bioS;
    }

    /**
     * Creates the producers of map parts and calls for another method to
     * generate map objects, which are then given to a map.
     */
    public void constructMap() {
        WaterGenerator waterGen = new WaterGenerator(exponent);
        NoiseMapGenerator heightGen = new NoiseMapGenerator(random, exponent, seed, range);
        NoiseMapGenerator moistureGen = new NoiseMapGenerator(random, exponent, seed, range);
        generateMapObjects(waterGen, heightGen, moistureGen);
        setObjectsToMap();
    }

    /**
     * Gives all generated map objects to map.
     */
    public void setObjectsToMap() {
        map.setHeightMap(this.heightMap);
        map.setHeightMapOrig(this.heightMapOrig);
        map.setMaxHeight(maxHeight);
        map.setWater(water);
        map.setMoisture(moisture);
        map.setBiomes(biomes);
    }

    /**
     * Calls for other methods to generate different parts of the map and calls
     * for roughen method to add jitter to heightmap.
     *
     * @param waterGen A WaterGenerator
     * @param heightGen A NoiseMapGenerator for generating height map
     * @param moistureGen A NoiseMapGenerator for generating moisture map
     */
    public void generateMapObjects(WaterGenerator waterGen, NoiseMapGenerator heightGen, NoiseMapGenerator moistureGen) {
        generateHeightMap(heightGen);
        double maxHeight = heightGen.getMaxValue();
        double waterLevel = 0.5;
        generateWater(waterGen, waterLevel, maxHeight);
        generateMoisture(moistureGen);
        waterGen.addRivers(heightMap, moisture);
        heightMap = roughen(heightMap, heightGen);
        double maxMoisture = moistureGen.getMaxValue();
        generateBiomes(maxHeight, waterLevel, maxMoisture);
    }

    /**
     * Calls for BiomeSelector to add biomes to map
     *
     * @param maxHeight Maximum value of the height map
     * @param waterLevel A number between 0 and 1 where waterlevel * maxheight
     * is the actual water level
     * @param maxMoisture Maximum value of the moisture map
     */
    public void generateBiomes(double maxHeight, double waterLevel, double maxMoisture) {
        bioS.createBiomes(heightMap, maxHeight, waterLevel, water, moisture, maxMoisture);
        biomes = bioS.getBiomes();
    }

    /**
     * Calls for NoiseMapGenerator to generate height map
     *
     * @param heightGen A NoiseMapGenerator for generating height map
     */
    public void generateHeightMap(NoiseMapGenerator heightGen) {
        heightMap = heightGen.createNoise();
        this.heightMapOrig = new double[heightMap.length][heightMap.length];
        for (int x = 0; x < heightMap.length; x++) {
            for (int y = 0; y < heightMap.length; y++) {
                this.heightMapOrig[x][y] = heightMap[x][y];
            }
        }
        this.maxHeight = heightGen.getMaxValue();
    }

    /**
     * Calls for NoiseMapGenerator to generate moisture map, and calls for
     * roughen method to add jitter to moisture map.
     *
     * @param moistureGen A NoiseMapGenerator for generating moisture map
     */
    public void generateMoisture(NoiseMapGenerator moistureGen) {
        moisture = moistureGen.createNoise();
        moisture = roughen(moisture, moistureGen);
    }

    /**
     * Calls for WaterGenerator to add water based on waterlevel
     *
     * @param waterGen A WaterGenerator
     * @param waterLevel A number between 0 and 1 where waterlevel * maxHeight
     * is the actual water level
     * @param maxHeight The maximum value of the height map
     */
    public void generateWater(WaterGenerator waterGen, double waterLevel, double maxHeight) {
        waterGen.addWaterByHeight(waterLevel * maxHeight, heightMap);
        water = waterGen.getWater();
    }

    /**
     * Adds jitter to given map
     *
     * @param map An array containing doubles (a noise map)
     * @param generator A NoiseMapGenerator which has generated the given map
     * @return Given map with more noise
     */
    public double[][] roughen(double[][] map, NoiseMapGenerator generator) {
        for (int x = 1; x < map.length - 1; x++) {
            for (int y = 1; y < map.length - 1; y++) {
                if (!water[x][y]) {
                    if (heightMap[x][y] > heightMap[x - 1][y - 1]
                            || heightMap[x][y] > heightMap[x - 1][y]
                            || heightMap[x][y] > heightMap[x][y - 1]) {
                        double average = (map[x - 1][y - 1] + map[x - 1][y] + map[x][y - 1]) / 3 + 5;
                        //generator.checkMaxValue(average);
                        map[x][y] = Math.max(1, average);
                    } else if (heightMap[x][y] > heightMap[x + 1][y + 1]
                            || heightMap[x][y] > heightMap[x + 1][y]
                            || heightMap[x][y] > heightMap[x + 1][y - 1]) {
                        double average = (map[x + 1][y + 1] + map[x + 1][y] + map[x + 1][y - 1]) / 3 + 5;
                        //generator.checkMaxValue(average);
                        map[x][y] = Math.max(1, average);
                    }
                }
            }
        }
        return map;
    }

    /**
     * Returns height map
     *
     * @return An array containing height values as doubles
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }

    /**
     * Returns water map
     *
     * @return A boolean array where true means water
     */
    public boolean[][] getWater() {
        return water;
    }

    /**
     * Returns moisture map
     *
     * @return An array containing moisture values as doubles
     */
    public double[][] getMoisture() {
        return moisture;
    }

}
