package mapgenerator.domain;

/**
 * Class contains all information regarding the created map.
 */
public class Map {

    private double[][] heightMap;
    private boolean[][] water;
    private double[][] moisture;
    private int[][] biomes;
    private double maxHeight;

    /**
     * Sets a height map.
     *
     * @param heightMap An array containing the height values of the map as
     * doubles.
     */
    public void setHeightMap(double[][] heightMap) {
        this.heightMap = heightMap;
    }

    /**
     * Returns the height map.
     *
     * @return An array containing the height values of the map as doubles.
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }

    /**
     * Sets max height
     * @param maxHeight Maximum value of the height map
     */
    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * Returns max height.
     * @return max height
     */
    public double getMaxHeight() {
        return maxHeight;
    }

    /**
     * Sets a water map.
     *
     * @param water A boolean array in which true means water and false means
     * land
     */
    public void setWater(boolean[][] water) {
        this.water = water;
    }

    /**
     * Returns the water map.
     *
     * @return A boolean array in which true means water and false means land
     */
    public boolean[][] getWater() {
        return water;
    }

    /**
     * Sets a moisture map.
     *
     * @param moisture An array containing moisture values as doubles
     */
    public void setMoisture(double[][] moisture) {
        this.moisture = moisture;
    }

    /**
     * Returns the moisture map.
     *
     * @return An array containing moisture values as doubles
     */
    public double[][] getMoisture() {
        return moisture;
    }

    /**
     * Sets a biome map.
     *
     * @param biomes An array containing biome numbers as integers
     */
    public void setBiomes(int[][] biomes) {
        this.biomes = biomes;
    }

    /**
     * Returns the biome map.
     *
     * @return An array containing biome numbers as integers
     */
    public int[][] getBiomes() {
        return biomes;
    }

}
