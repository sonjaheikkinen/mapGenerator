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
    private BiomeSelector bioS;

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
        NoiseMapGenerator heightGen = new NoiseMapGenerator(random, exponent, seed, range);
        heightMap = heightGen.createNoise();
        double maxHeight = heightGen.getMaxValue();
        double waterLevel = 0.5;
        waterGen.addWaterByHeight(waterLevel * maxHeight, heightMap);
        water = waterGen.getWater();
        NoiseMapGenerator moistureGen = new NoiseMapGenerator(random, exponent, seed, range);
        moisture = moistureGen.createNoise();
        moisture = roughen(moisture);
        heightMap = roughen(heightMap);
        double maxMoisture = moistureGen.getMaxValue();
        biomes = bioS.createBiomes(heightMap, maxHeight, waterLevel, water, moisture, maxMoisture);
    }

    public double[][] roughen(double[][] map) {
        for (int x = 1; x < map.length - 1; x++) {
            for (int y = 1; y < map.length - 1; y++) {
                if (!water[x][y]) {
                    if (heightMap[x][y] > heightMap[x - 1][y - 1]
                            || heightMap[x][y] > heightMap[x - 1][y]
                            || heightMap[x][y] > heightMap[x][y - 1]) {
                        double average = (map[x - 1][y - 1] + map[x - 1][y] + map[x][y - 1]) / 3 + 5;
                        map[x][y] = Math.max(1, average);
                    } else if (heightMap[x][y] > heightMap[x + 1][y + 1]
                            || heightMap[x][y] > heightMap[x + 1][y]
                            || heightMap[x][y] > heightMap[x + 1][y - 1]) {
                        double average = (map[x + 1][y + 1] + map[x + 1][y] + map[x + 1][y - 1]) / 3 + 5;
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
     * @return An array containing height values as doubles.
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }

    public boolean[][] getWater() {
        return water;
    }

}
