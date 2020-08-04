package mapgenerator.logic;

import java.util.Random;

/**
 * Class generates water for the map
 */
public class WaterGenerator {

    private boolean[][] water;
    private double[][] moisture;
    private int[][] distanceToWater;
    private int mapSize;
    private Random random;

    /**
     * The constructor initializes the boolean array for water placement
     *
     * @param mapSizeExponent The map is of size (2^mapSizeExponent) + 1
     */
    public WaterGenerator(int mapSizeExponent) {
        mapSize = (int) Math.pow(2, mapSizeExponent) + 1;
        this.water = new boolean[mapSize][mapSize];
        this.moisture = new double[mapSize][mapSize];
        this.distanceToWater = new int[mapSize][mapSize];
        this.random = new Random();
    }

    /**
     * Marks all parts of the map with a height below water level as water
     *
     * @param waterlevel All heights under this are water
     * @param heightmap All height values of the map
     */
    public void addWaterByHeight(int waterlevel, double[][] heightmap) {
        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap.length; y++) {
                if (heightmap[x][y] < waterlevel) {
                    water[x][y] = true;
                }
            }
        }
    }
    
    //TODO = refactor diamond square into its own class so that both moisture and heightmap can use it
    public void addMoisture() {
        HeightmapGenerator hmg = new HeightmapGenerator(random, 8, 50, 50);
        moisture = hmg.calculateHeights();
    }

    /**
     * Returns the water array
     *
     * @return A boolean array containing the information of water placement
     */
    public boolean[][] getWater() {
        return water;
    }

    public double[][] getMoisture() {
        return moisture;
    }

}
