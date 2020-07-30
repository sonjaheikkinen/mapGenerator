
package mapgenerator.logic;

import mapgenerator.domain.Map;

/*
 * Calls for other classes to generate different parts of the map and puts all the parts together.
 */
public final class MapConstructor {

    private Map map;
    private HeightmapGenerator heightmapGenerator;
    private WaterGenerator waterGenerator;
    private double[][] heightMap;
    private boolean[][] water;

    /**
     * Constructor for this class, initializes class variables.
     * @param map A map object in which the generated map is saved
     * @param hmGenerator Generates height values of the map
     */
    public MapConstructor(Map map, HeightmapGenerator hmGenerator, WaterGenerator waterGenerator) {
        this.map = map;
        this.heightmapGenerator = hmGenerator;
        this.waterGenerator = waterGenerator;
        constructMap();
    }

    /**
     * Calls for generateMapObjects to generate different map parts and gives these to a map object.
     */
    public void constructMap() {
        generateMapObjects();
        map.setHeightMap(this.heightMap);
        map.setWater(water);
    }

    /**
     * Calls for other methods to generate different parts of the map.
     */
    public void generateMapObjects() {
        heightMap = heightmapGenerator.calculateHeights();
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
