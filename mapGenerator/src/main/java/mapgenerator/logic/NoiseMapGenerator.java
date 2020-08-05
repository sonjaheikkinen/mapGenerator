package mapgenerator.logic;

import java.util.Random;

/**
 * Generates height values to be used on a map
 */
public class NoiseMapGenerator {

    private double[][] noiseMap;
    private Random random;
    private int sizeExponent;
    private int seed;
    private int randomizerRange;
    private int mapSize;
    private double maxValue;

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
    public NoiseMapGenerator(Random random, int exponent, int seed, int range) {
        this.random = random;
        this.sizeExponent = exponent;
        this.seed = seed;
        this.randomizerRange = range;
        mapSize = (int) Math.pow(2, this.sizeExponent) + 1;
        this.noiseMap = new double[mapSize][mapSize];
        this.maxValue = 0;
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
    public double[][] createNoise() {

        assignCornerValues(this.mapSize, this.seed);

        for (int squareSize = mapSize - 1; squareSize >= 2; squareSize /= 2, this.randomizerRange /= 2.0) {
            int squareHalf = squareSize / 2;
            for (int x = 0; x < mapSize - 1; x += squareSize) {
                for (int y = 0; y < mapSize - 1; y += squareSize) {
                    diamondStep(x, y, squareSize, squareHalf, this.randomizerRange);
                }
            }
            for (int x = 0; x < mapSize; x += squareHalf) {
                for (int y = (x + squareHalf) % squareSize; y < mapSize; y += squareSize) {
                    squareStep(x, y, squareHalf, mapSize, this.randomizerRange);
                }
            }
        }

        return this.noiseMap;

    }

    /**
     * Puts the seed value +/- 10 on the corners of the map.
     *
     * @param mapSize The length of one side of the map
     * @param seed The seed value for corner values
     */
    public void assignCornerValues(int mapSize, int seed) {
        double value1 = seed + random.nextInt(20) - 10;
        double value2 = seed + random.nextInt(20) - 10;
        double value3 = seed + random.nextInt(20) - 10;
        double value4 = seed + random.nextInt(20) - 10;
        checkMaxValue(value1);
        checkMaxValue(value2);
        checkMaxValue(value3);
        checkMaxValue(value4);
        this.noiseMap[0][0] = value1;
        this.noiseMap[0][mapSize - 1] = value2;
        this.noiseMap[mapSize - 1][0] = value3;
        this.noiseMap[mapSize - 1][mapSize - 1] = value4;
        
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
        double cornerAverage = (this.noiseMap[x][y]
                + this.noiseMap[x + squareSize][y]
                + this.noiseMap[x][y + squareSize]
                + this.noiseMap[x + squareSize][y + squareSize]) / 4;
        double newValue = Math.max(1, cornerAverage
                + (random.nextDouble() * 2 * randomizerRange) - randomizerRange);
        checkMaxValue(newValue);
        this.noiseMap[x + squareHalf][y + squareHalf] = newValue;
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
        double cornerSum = 0;
        double cornerCount = 0;
        if (x - distanceToCorner >= 0) {
            cornerSum = cornerSum + this.noiseMap[x - distanceToCorner][y];
            cornerCount++;
        }
        if (x + distanceToCorner < mapSize) {
            cornerSum = cornerSum + this.noiseMap[Math.min(x + distanceToCorner, mapSize - 1)][y];
            cornerCount++;
        }
        if (y + distanceToCorner < mapSize) {
            cornerSum = cornerSum + this.noiseMap[x][y + distanceToCorner];
            cornerCount++;
        }
        if (y - distanceToCorner >= 0) {
            cornerSum = cornerSum + this.noiseMap[x][y - distanceToCorner];
            cornerCount++;
        }
        double cornerAverage = cornerSum / cornerCount;
        double newValue = Math.max(1, cornerAverage + (random.nextDouble() * 2 * randomizerRange) - randomizerRange);
        checkMaxValue(newValue);
        this.noiseMap[x][y] = newValue;
    }
    
    public void checkMaxValue(double value) {
        if (value > maxValue) {
            maxValue = value;
        }
    }

    /**
     * Returns the generated height map
     *
     * @return An array containing height values as doubles
     */
    public double[][] getNoiseMap() {
        return this.noiseMap;
    }
    
    public double getMaxValue() {
        return maxValue;
    }

}
