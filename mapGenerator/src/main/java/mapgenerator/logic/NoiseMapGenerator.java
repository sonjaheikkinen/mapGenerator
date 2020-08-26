package mapgenerator.logic;

import java.util.Random;
import mapgenerator.datastructures.MapCell;

/**
 * Generates height values to be used on a map
 */
public class NoiseMapGenerator {

    private Random random;
    private int seed;
    private int randomizerRange;
    private int mapSize;
    private double maxHeight;
    private double maxMoisture;

    /**
     * Constructor for NoiseMapGenerator, initializes class variables.
     *
     * @param random Random number generator
     * @param mapSize
     * @param seed Map corners will get the seed value +/- 10 before algorithm
     * runs
     * @param range A random value of +/- range will be added to values
     * calculated by algorithm
     */
    public NoiseMapGenerator(Random random, int mapSize, int seed, int range) {
        this.random = random;
        this.seed = seed;
        this.randomizerRange = range;
        this.mapSize = mapSize;
        this.maxHeight = 0;
        this.maxMoisture = 0;
    }

    /**
     * Uses the diamond-square algorithm to generate height values for a map.
     * Basic idea of the diamond-square-algorithm: 1. Assign random values
     * (possibly near some seed) to the corners of the map 2. The diamond step:
     * Take the four corners and put their average +/- a random number within a
     * range (randomizerRange) in the middle. 3. The square step: Now we have
     * five values. Looking from center, we can form a diamond to the top,
     * bottom, left and right of the center value (if we imagine a fourth corner
     * outside the array). Take the corners of every diamond and put their
     * average +/- a random value within range to the middle of the diamond (at
     * the this step it is on the edge of the map). Since at this step there are
     * only tree corners that are in the array, use only those to count the
     * average. 4. Now we have more values from which we can form four smaller
     * squares. For every square, repeat step two. Now we have more diamonds.
     * For every diamond, repeat step 3. If all the four corners are now inside
     * the array, use all four corners to count the average. 5. As the size of
     * the squares halves, divide the random number range by two. The algorithm
     * starts by generating big height differences for big areas, and as the
     * areas (diamonds and squares) get smaller, so do the height differences
     * (randomizer range). I have no idea why the step with squares is
     * illogically called the diamond step and vice versa, but several sources
     * claim so. Every time a new value is inserted, the current maximum value
     * is checked.
     *
     * @param map
     * @return An array containing height values as doubles
     */
    public MapCell[][] createNoise(MapCell[][] map) {
        map = assignCornerValues("height", map);
        map = assignCornerValues("moisture", map);
        for (int squareSize = mapSize - 1; squareSize >= 2; squareSize /= 2, this.randomizerRange /= 2.0) {
            int squareHalf = squareSize / 2;
            for (int x = 0; x < mapSize - 1; x += squareSize) {
                for (int y = 0; y < mapSize - 1; y += squareSize) {
                    map = diamondStep(x, y, squareSize, squareHalf, "height", map);
                    map = diamondStep(x, y, squareSize, squareHalf, "moisture", map);
                }
            }
            for (int x = 0; x < mapSize; x += squareHalf) {
                for (int y = (x + squareHalf) % squareSize; y < mapSize; y += squareSize) {
                    map = squareStep(x, y, squareHalf, "height", map);
                    map = squareStep(x, y, squareHalf, "moisture", map);
                }
            }
        }
        return map;
    }

    /**
     * Puts the seed value +/- 10 on the corners of the map.
     *
     * @param attribute Attribute is used to select if we are updating heights
     * or moistures.
     * @param map
     */
    public MapCell[][] assignCornerValues(String attribute, MapCell[][] map) {
        double value1 = seed + random.nextInt(20) - 10;
        double value2 = seed + random.nextInt(20) - 10;
        double value3 = seed + random.nextInt(20) - 10;
        double value4 = seed + random.nextInt(20) - 10;
        checkMaxValue(attribute, value1);
        checkMaxValue(attribute, value2);
        checkMaxValue(attribute, value3);
        checkMaxValue(attribute, value4);
        map[0][0].setNoiseValue(attribute, value1);
        map[0][mapSize - 1].setNoiseValue(attribute, value2);
        map[mapSize - 1][0].setNoiseValue(attribute, value3);
        map[mapSize - 1][mapSize - 1].setNoiseValue(attribute, value4);
        return map;
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
     * @param attribute Attribute is used to select if we are updating heights
     * or moistures.
     * @param map
     *
     */
    public MapCell[][] diamondStep(int x, int y, int squareSize, int squareHalf, String attribute, MapCell[][] map) {
        double cornerAverage = (map[x][y].getNoiseValue(attribute)
                + map[x + squareSize][y].getNoiseValue(attribute)
                + map[x][y + squareSize].getNoiseValue(attribute)
                + map[x + squareSize][y + squareSize].getNoiseValue(attribute)) / 4;
        double newValue = Math.max(1, cornerAverage
                + (random.nextDouble() * 2 * randomizerRange) - randomizerRange);
        checkMaxValue(attribute, newValue);
        map[x + squareHalf][y + squareHalf].setNoiseValue(attribute, newValue);
        return map;
    }

    /**
     * The square step of the diamond-square algorithm. Counts the average
     * values of the corners of a diamond defined by centre coordinates of x, y
     * and distance to corners. This average value is then altered by a
     * randomvalue and put in the middle of the diamond.
     *
     * @param x The x coordinate of the centre of the diamond
     * @param y The y coordinate of the centre of the diamond
     * @param distanceToCorner Distance from centre to corner
     * @param attribute Attribute is used to select if we are updating heights
     * or moistures.
     * @param map
     */
    public MapCell[][] squareStep(int x, int y, int distanceToCorner, String attribute, MapCell[][] map) {
        double cornerAverage = countCornerAverage(x, y, distanceToCorner, attribute, map);
        double newValue = Math.max(1, cornerAverage + (random.nextDouble() * 2 * randomizerRange) - randomizerRange);
        checkMaxValue(attribute, newValue);
        map[x][y].setNoiseValue(attribute, newValue);
        return map;
    }

    /**
     * Counts the average values of the corners of the diamond defined by centre
     * coordinates of x, y, and distance to corner. Only corners falling inside
     * the map count towards average.
     *
     * @param x X coordinate of the centre of the diamond
     * @param y Y coordinate of the centre of the diamond
     * @param distanceToCorner Distance from centre to corner
     * @param attribute Attribute is used to select if we are updating heights or moistures.
     * @param map
     * @return average value of the corners
     */
    public double countCornerAverage(int x, int y, int distanceToCorner, String attribute, MapCell[][] map) {
        double cornerSum = 0;
        double cornerCount = 0;
        if (x - distanceToCorner >= 0) {
            cornerSum = cornerSum + map[x - distanceToCorner][y].getNoiseValue(attribute);
            cornerCount++;
        }
        if (x + distanceToCorner < mapSize) {
            cornerSum = cornerSum + map[Math.min(x + distanceToCorner, mapSize - 1)][y].getNoiseValue(attribute);
            cornerCount++;
        }
        if (y + distanceToCorner < mapSize) {
            cornerSum = cornerSum + map[x][y + distanceToCorner].getNoiseValue(attribute);
            cornerCount++;
        }
        if (y - distanceToCorner >= 0) {
            cornerSum = cornerSum + map[x][y - distanceToCorner].getNoiseValue(attribute);
            cornerCount++;
        }
        return cornerSum / cornerCount;
    }

    /**
     * Checks if given value is greater than current max value
     * @param attribute Attribute is used to select if we are updating heights or moistures. 
     * @param value A value to be checked
     */
    public void checkMaxValue(String attribute, double value) {
        if (attribute.equals("height") && value > maxHeight) {
            maxHeight = value;
        } else if (attribute.equals("moisture") && value > maxMoisture) {
            maxMoisture = value;
        }
    }

    /**
     * Returns the maximum value of the height map
     * @param attribute Attribute is used to select if we are updating heights or moistures.
     * @return The maximum value as double
     */
    public double getMaxValue(String attribute) {
        if (attribute.equals("height")) {
            return maxHeight;
        } else if (attribute.equals("moisture")) {
            return maxMoisture;
        }
        return 0;
    }

}
