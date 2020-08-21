package mapgenerator.logic;

import java.util.Random;
import mapgenerator.datastructures.MapCell;
import mapgenerator.domain.Map;

/*
 * Calls for other classes to generate different parts of the map and puts all the parts together.
 */
public final class MapConstructor {

    private Map mapStorage;
    private MapCell[][] map;
    private Random random;
    private int mapSize;
    private int seed;
    private int range;
    private BiomeSelector bioS;
    private double maxHeight;

    /**
     * Constructor for this class, initializes class variables.
     *
     * @param random A random number generator
     * @param mapSize
     * @param mapSeed The corners of the height map will be assigned a random
     * value of mapSeed +/- 10
     * @param mapRandomizerRange The height values calculated for the height map
     * will be affected by a random value of +/- mapRandomizerRange
     * @param map A map object in which the generated map is saved
     * @param bioS A biome selector objects for adding biomes to map
     */
    public MapConstructor(Random random, int mapSize, int mapSeed, int mapRandomizerRange, Map map, BiomeSelector bioS) {
        this.mapStorage = map;
        this.random = random;
        this.mapSize = mapSize;
        this.seed = mapSeed;
        this.range = mapRandomizerRange;
        this.bioS = bioS;
    }

    /**
     * Creates the producers of map parts and calls for another method to
     * generate map objects, which are then given to a map.
     */
    public void constructMap() {
        this.map = new MapCell[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                this.map[x][y] = new MapCell();
            }
        }
        WaterGenerator waterGen = new WaterGenerator(mapSize);
        NoiseMapGenerator heightGen = new NoiseMapGenerator(random, mapSize, seed, range);
        NoiseMapGenerator moistureGen = new NoiseMapGenerator(random, mapSize, seed, range);
        generateMapObjects(waterGen, heightGen, moistureGen);
        setMapToStorage();
    }

    /**
     * Gives all generated map objects to map.
     */
    public void setMapToStorage() {
        mapStorage.setMaxHeight(maxHeight);
        mapStorage.setMap(this.map);
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
        map = heightGen.createNoise(map, "height");
        this.maxHeight = heightGen.getMaxValue();
        double waterLevel = 0.5;
        map = waterGen.addWaterByHeight(waterLevel * maxHeight, map);
        map = moistureGen.createNoise(map, "moisture");
        map = waterGen.addRivers(map);
        double maxMoisture = moistureGen.getMaxValue();
        //roughen(heightGen, moistureGen);
        map = bioS.createBiomes(map, maxHeight, waterLevel, maxMoisture);
    }

    /**
     * Adds jitter to given map
     *
     * @param noiseMap An array containing doubles (a noise map)
     * @param generator A NoiseMapGenerator which has generated the given map
     * @return Given map with more noise
     */
    //TODO: this version of the method does not look good with current map algorithm, and is therefore not in use. Method is 
    //supposed to make the terrain and moisture maps more rough, and make the biome borders less "clean". 
    public void roughen(NoiseMapGenerator heightGen, NoiseMapGenerator moistureGen) {
        for (int x = 1; x < map.length - 1; x++) {
            for (int y = 1; y < map.length - 1; y++) {
                if (!map[x][y].isWater()) {
                    if (map[x][y].getHeight() > map[x - 1][y - 1].getHeight()
                            || map[x][y].getHeight() > map[x - 1][y].getHeight()
                            || map[x][y].getHeight() > map[x][y - 1].getHeight()) {
                        double heightAverage = (map[x - 1][y - 1].getHeight()
                                + map[x - 1][y].getHeight() + map[x][y - 1].getHeight()) / 3 + 5;
                        map[x][y].setHeight(Math.max(1, heightAverage));
                        double moistureAverage = (map[x - 1][y - 1].getMoisture()
                                + map[x - 1][y].getMoisture() + map[x][y - 1].getMoisture()) / 3 + 5;
                        map[x][y].setMoisture(Math.max(1, moistureAverage));
                        //generator.checkMaxValue(average);
                    } else if (map[x][y].getHeight() > map[x + 1][y + 1].getHeight()
                            || map[x][y].getHeight() > map[x + 1][y].getHeight()
                            || map[x][y].getHeight() > map[x + 1][y - 1].getHeight()) {
                        double heightAverage = (map[x + 1][y + 1].getHeight()
                                + map[x + 1][y].getHeight() + map[x + 1][y - 1].getHeight()) / 3 + 5;
                        map[x][y].setHeight(Math.max(1, heightAverage));
                        double moistureAverage = (map[x + 1][y + 1].getMoisture()
                                + map[x + 1][y].getMoisture() + map[x + 1][y - 1].getMoisture()) / 3 + 5;
                        map[x][y].setMoisture(Math.max(1, moistureAverage));
                        //generator.checkMaxValue(average);
                    }
                }
            }
        }
    }

}
