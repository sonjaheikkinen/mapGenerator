package mapgenerator.logic;

import java.util.Random;

/**
 * Class generates water for the map
 */
public class WaterGenerator {

    private boolean[][] water;
    private int[][] moisture;
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
        this.moisture = new int[mapSize][mapSize];
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
        calculateDistancesFromWater();
    }

    public void calculateDistancesFromWater() {
        int randomValue = 0;
        for (int x = 0; x < mapSize; x++) {
            int stepsFromWater = 0;
            for (int y = 0; y < mapSize; y++) {
                if (water[x][y]) {
                    distanceToWater[x][y] = 0;
                    stepsFromWater = 0;
                } else {
                    stepsFromWater++;
                    randomValue = random.nextInt(20) - 10;
                    distanceToWater[x][y] = stepsFromWater + randomValue;
                }
            }
            stepsFromWater = 0;
            for (int y = mapSize - 1; y >= 0; y--) {
                if (water[x][y]) {
                    stepsFromWater = 0;
                } else {
                    stepsFromWater++;
                    randomValue = random.nextInt(20) - 10;
                    distanceToWater[x][y] = Math.min(distanceToWater[x][y], stepsFromWater + randomValue);
                }
            }
        }
        for (int y = 0; y < mapSize; y++) {
            int stepsFromWater = 0;
            for (int x = 0; x < mapSize; x++) {
                if (water[x][y]) {
                    stepsFromWater = 0;
                } else {
                    stepsFromWater++;
                    randomValue = random.nextInt(20) - 10;
                    distanceToWater[x][y] = Math.min(distanceToWater[x][y], stepsFromWater + randomValue);
                }
            }
            stepsFromWater = 0;
            for (int x = mapSize - 1; x >= 0; x--) {
                if (water[x][y]) {
                    stepsFromWater = 0;
                } else {
                    stepsFromWater++;
                    randomValue = random.nextInt(20) - 10;
                    distanceToWater[x][y] = Math.min(distanceToWater[x][y], stepsFromWater + randomValue);
                }
            }
        }
    }

    public void addMoisture(double[][] heightmap) {
        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap.length; y++) {
                if (water[x][y]) {
                    moisture[x][y] = 6;
                } else {
                    int moistureLevel = random.nextInt(6);
                    int distance = (int) Math.round((double) distanceToWater[x][y] / (double) mapSize * 6);
                    int newMoisture = Math.max(moistureLevel - distance, 1);
                    moisture[x][y] = newMoisture;
                }
            }
        }
        addWind(heightmap);

    }

    public void addWind(double[][] heightmap) {
        for (int x = 1; x < mapSize - 1; x++) {
            for (int y = 1; y < mapSize - 1; y++) {
                /*
                if (heightmap[x - 1][y - 1] < heightmap[x][y]) {
                    if(!water[x - 1][y - 1]) {
                        moisture[x - 1][y - 1] = Math.max(1, moisture[x - 1][y - 1] - 1);
                    }
                    if(! water[x + 1][y + 1]) {
                        moisture[x + 1][y + 1] = Math.min(6, moisture[x + 1][y + 1] + 1);
                    }
                }
                 */
                if (!water[x][y]) {
                    int averageMoisture = (moisture[x - 1][y] + moisture[x + 1][y]
                            + moisture[x][y - 1] + moisture[x][y + 1]) / 4;
                    moisture[x][y] = Math.min(6, averageMoisture + 1);
                }
                if (heightmap[x - 1][y - 1] < heightmap[x][y] && !water[x][y]) {
                    int averageMoisture = (moisture[x - 1][y] + moisture[x][y - 1]) / 2;
                    moisture[x][y] = Math.min(6, averageMoisture + 1);
                }
                if (heightmap[x + 1][y + 1] > heightmap[x][y] && !water[x][y]) {
                    int averageMoisture = (moisture[x + 1][y] + moisture[x][y + 1]) / 2;
                    moisture[x][y] = Math.max(1, averageMoisture - 1);
                    //moisture[x][y] = Math.min(6, averageMoisture + 1);
                }
            }
        }
    }

    /**
     * Returns the water array
     *
     * @return A boolean array containing the information of water placement
     */
    public boolean[][] getWater() {
        return water;
    }

    public int[][] getMoisture() {
        return moisture;
    }

}
