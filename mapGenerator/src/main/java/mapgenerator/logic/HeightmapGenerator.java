
package mapgenerator.logic;

import java.util.Random;

public class HeightmapGenerator {

    double[][] heights;
    int[][] heightMap;
    Random random;
    int sizeExponent;
    int seed;
    int randomizerRange;

    public HeightmapGenerator(Random random, int exponent, int seed, int range) {
        this.random = random;
        this.sizeExponent = exponent;
        this.seed = seed;
        this.randomizerRange = range;
    }

    public double[][] calculateHeights() {

        int mapSize = (int) Math.pow(2, this.sizeExponent) + 1;
        this.heights = new double[mapSize][mapSize];
        this.heightMap = new int[mapSize][mapSize];

        assignCornerValues(mapSize, this.seed);

        for (int rectSize = mapSize - 1; rectSize >= 2; rectSize /= 2, this.randomizerRange /= 2.0) {
            int rectHalf = rectSize / 2;
            for (int x = 0; x < mapSize - 1; x += rectSize) {
                for (int y = 0; y < mapSize - 1; y += rectSize) {
                    diamondStep(x, y, rectSize, rectHalf, this.randomizerRange);
                }
            }
            for (int x = 0; x < mapSize - 1; x += rectHalf) {
                for (int y = (x + rectHalf) % rectSize; y < mapSize - 1; y += rectSize) {
                    squareStep(x, y, rectHalf, mapSize, this.randomizerRange);
                }
            }
        }

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                this.heights[x][y] = (int) Math.round(this.heights[x][y]);
                if (this.heights[x][y] < 0) {
                    this.heights[x][y] = 0;
                }
            }
        }

        return this.heights;
    }

    public void assignCornerValues(int mapSize, int seed) {
        this.heights[0][0] = seed;
        this.heights[0][mapSize - 1] = seed;
        this.heights[mapSize - 1][0] = seed;
        this.heights[mapSize - 1][mapSize - 1] = seed;
    }

    public void diamondStep(int x, int y, int rectSize, int rectHalf, int randomizerRange) {
        double cornerAverage = (this.heights[x][y]
                + this.heights[x + rectSize][y]
                + this.heights[x][y + rectSize]
                + this.heights[x + rectSize][y + rectSize]) / 4;
        this.heights[x + rectHalf][y + rectHalf] = cornerAverage
                + (random.nextDouble() * 2 * randomizerRange) - randomizerRange;
    }

    public void squareStep(int x, int y, int rectHalf, int mapSize, int randomizerRange) {
        double cornerAverage = (this.heights[(x - rectHalf + mapSize - 1) % (mapSize - 1)][y]
                + this.heights[(x + rectHalf) % (mapSize - 1)][y]
                + this.heights[x][(y + rectHalf) % (mapSize - 1)]
                + this.heights[x][(y - rectHalf + mapSize - 1) % (mapSize - 1)]) / 4;
        cornerAverage = cornerAverage + (random.nextDouble() * 2 * randomizerRange) - randomizerRange;
        this.heights[x][y] = cornerAverage;
        wrapEdgeValues(x, mapSize, y, cornerAverage);
    }

    public void wrapEdgeValues(int x, int mapSize, int y, double cornerAverage) {
        if (x == 0) {
            this.heights[mapSize - 1][y] = cornerAverage;
        }
        if (y == 0) {
            this.heights[x][mapSize - 1] = cornerAverage;
        }
    }
    
    public double[][] getHeights() {
        return this.heights;
    }

}
