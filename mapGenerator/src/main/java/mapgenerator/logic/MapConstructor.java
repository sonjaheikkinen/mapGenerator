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
    private double maxMoisture;

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
    public void constructMap(double waterlevel) {
        this.map = new MapCell[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                this.map[x][y] = new MapCell();
            }
        }
        WaterGenerator waterGen = new WaterGenerator(mapSize);
        NoiseMapGenerator noiseGen = new NoiseMapGenerator(random, mapSize, seed, range);
        generateMapObjects(waterGen, noiseGen, waterlevel);
        setMapToStorage();
    }

    /**
     * Gives all generated map objects to map.
     */
    public void setMapToStorage() {
        mapStorage.setMaxHeight(maxHeight);
        mapStorage.setMaxMoisture(maxMoisture);
        mapStorage.setMap(this.map);
    }

    /**
     * Calls for other methods to generate different parts of the map.
     *
     * @param waterGen A WaterGenerator
     * @param noiseGen A NoiseMapGenerator
     */
    public void generateMapObjects(WaterGenerator waterGen, NoiseMapGenerator noiseGen, double waterlevel) {
        map = noiseGen.createNoise(map);
        maxHeight = noiseGen.getMaxValue("height");
        map = waterGen.addWaterByHeight(waterlevel * maxHeight, map);
        map = waterGen.addRivers(map);
        maxMoisture = noiseGen.getMaxValue("moisture");
        map = bioS.createBiomes(map, maxHeight, waterlevel, maxMoisture);
    }      

}
