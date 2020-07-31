package mapgenerator.logic;

import java.util.Random;

/**
 * Generates height values to be used on a map
 */
public class HeightmapGenerator {

    private double[][] heightMap;
    private Random random;
    private int sizeExponent;
    private int seed;
    private int randomizerRange;
    private int mapSize;

    /**
     * Constructor for HeightmapGenerator, initializes class variables.
     *
     * @param random Random number generator
     * @param exponent Generated map will be of size (2^exponent)-1
     * @param seed Map corners will get the seed value +/- 10 before algorithm
     * runs
     * @param range A random value of +/- range will be added to values
     * calculated by algorithm
     */
    public HeightmapGenerator(Random random, int exponent, int seed, int range) {
        this.random = random;
        this.sizeExponent = exponent;
        this.seed = seed;
        this.randomizerRange = range;
        mapSize = (int) Math.pow(2, this.sizeExponent) + 1;
        this.heightMap = new double[mapSize][mapSize];
    }

    /**
     * Uses the diamond-square algorithm to generate height values for a map.
     * The algorithm has a diamond step and a square step. The diamond step: For
     * each square in the array, take the average of the corners, alter by a
     * random value and put the new value in the middle of the rectangle. The
     * square step: For each diamond in the array, take the average of the
     * corners, alter by a random value and put the new value in the middle of
     * the rectangle. For edges, put the same value on opposite edges so that
     * the values "wrap around".
     *
     * @return An array containing height values as doubles
     */
    public double[][] calculateHeights() {

        assignCornerValues(this.mapSize, this.seed);

        for (int squareSize = mapSize - 1; squareSize >= 2; squareSize /= 2, this.randomizerRange /= 2.0) {
            int squareHalf = squareSize / 2;
            for (int x = 0; x < mapSize - 1; x += squareSize) {
                for (int y = 0; y < mapSize - 1; y += squareSize) {
                    diamondStep(x, y, squareSize, squareHalf, this.randomizerRange);
                }
            }
            for (int x = 0; x < mapSize - 1; x += squareHalf) {
                for (int y = (x + squareHalf) % squareSize; y < mapSize - 1; y += squareSize) {
                    squareStep(x, y, squareHalf, mapSize, this.randomizerRange);
                }
            }
        }

        roundHeightsToWholeNumbers();

        return this.heightMap;

    }

    /**
     * Rounds every height value to a whole number.
     */
    public void roundHeightsToWholeNumbers() {
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                this.heightMap[x][y] = (int) Math.round(this.heightMap[x][y]);
            }
        }
    }

    /**
     * Puts the seed value +/- 10 on the corners of the map.
     *
     * @param mapSize The length of one side of the map
     * @param seed The seed value for corner values
     */
    public void assignCornerValues(int mapSize, int seed) {
        this.heightMap[0][0] = seed + random.nextInt(20) - 10;
        this.heightMap[0][mapSize - 1] = seed + random.nextInt(20) - 10;
        this.heightMap[mapSize - 1][0] = seed + random.nextInt(20) - 10;
        this.heightMap[mapSize - 1][mapSize - 1] = seed + random.nextInt(20) - 10;
    }

    /**
     * The diamond step of the diamond-square algorithm. Counts the average
     * value of the corners of a square defined by the x and y coordinates of
     * upper left corner and the size of the square. This average values is then
     * altered by a random value and put in the middle of the square.
     *
     * @param x The x coordinate of the square's upper left corner
     * @param y The y coordinate of the square's upper left corner
     * @param squareSize The length of the side of the square
     * @param squareHalf The length of half the side of the square
     * @param randomizerRange The range in which random values added to initial
     * counted values can be
     */
    public void diamondStep(int x, int y, int squareSize, int squareHalf, int randomizerRange) {
        double cornerAverage = (this.heightMap[x][y]
                + this.heightMap[x + squareSize][y]
                + this.heightMap[x][y + squareSize]
                + this.heightMap[x + squareSize][y + squareSize]) / 4;
        double newValue = Math.max(1, cornerAverage
                + (random.nextDouble() * 2 * randomizerRange) - randomizerRange);
        this.heightMap[x + squareHalf][y + squareHalf] = newValue;
    }

    /**
     * The square step of the diamond-square algorithm. Counts the average
     * values of the corners of a diamond defined by centre coordinates of x, y
     * and distance to corners. This average value is then altered by a random
     * value and put in the middle of the diamond. If the middle of the diamond
     * is on the edge, the same value is put on the opposite side also.
     *
     * @param x The x coordinate of the centre of the diamond
     * @param y The y coordinate of the centre of the diamond
     * @param distanceToCorner Distance from centre to corner
     * @param mapSize The length of one side of the map
     * @param randomizerRange The range in which random values can be
     */
    public void squareStep(int x, int y, int distanceToCorner, int mapSize, int randomizerRange) {

        double cornerAverage = (this.heightMap[(x - distanceToCorner + mapSize - 1) % (mapSize - 1)][y]
                + this.heightMap[(x + distanceToCorner) % (mapSize - 1)][y]
                + this.heightMap[x][(y + distanceToCorner) % (mapSize - 1)]
                + this.heightMap[x][(y - distanceToCorner + mapSize - 1) % (mapSize - 1)]) / 4;
        double newValue = Math.max(1, cornerAverage + (random.nextDouble() * 2 * randomizerRange) - randomizerRange);
        this.heightMap[x][y] = newValue;
        wrapEdgeValues(x, y, mapSize, newValue);
    }

    /**
     * When coordinates x and y define a place at the edge of the map, puts the
     * given value to the other side of the map.
     *
     * @param x The given x coordinate
     * @param y The given y coordinate
     * @param mapSize The size of the map
     * @param newValue The given value to be inserted
     */
    public void wrapEdgeValues(int x, int y, int mapSize, double newValue) {
        if (x == 0) {
            this.heightMap[mapSize - 1][y] = newValue;
        }
        if (y == 0) {
            this.heightMap[x][mapSize - 1] = newValue;
        }
    }

    /**
     * Returns the generated height map
     *
     * @return An array containing height values as doubles
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }

}
