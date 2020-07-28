
package mapgenerator.logic;

import java.util.Random;

public class HeightmapGenerator {

    double[][] heightMap;
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
        this.heightMap = new double[mapSize][mapSize];

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
                this.heightMap[x][y] = (int) Math.round(this.heightMap[x][y]);
                if (this.heightMap[x][y] < 0) {
                    this.heightMap[x][y] = 0;
                }
            }
        }

        return this.heightMap;
    }

    public void assignCornerValues(int mapSize, int seed) {
        this.heightMap[0][0] = seed;
        this.heightMap[0][mapSize - 1] = seed;
        this.heightMap[mapSize - 1][0] = seed;
        this.heightMap[mapSize - 1][mapSize - 1] = seed;
    }

    public void diamondStep(int x, int y, int rectSize, int rectHalf, int randomizerRange) {
        double cornerAverage = (this.heightMap[x][y]
                + this.heightMap[x + rectSize][y]
                + this.heightMap[x][y + rectSize]
                + this.heightMap[x + rectSize][y + rectSize]) / 4;
        this.heightMap[x + rectHalf][y + rectHalf] = cornerAverage
                + (random.nextDouble() * 2 * randomizerRange) - randomizerRange;
    }

    public void squareStep(int x, int y, int rectHalf, int mapSize, int randomizerRange) {
        double cornerAverage = (this.heightMap[(x - rectHalf + mapSize - 1) % (mapSize - 1)][y]
                + this.heightMap[(x + rectHalf) % (mapSize - 1)][y]
                + this.heightMap[x][(y + rectHalf) % (mapSize - 1)]
                + this.heightMap[x][(y - rectHalf + mapSize - 1) % (mapSize - 1)]) / 4;
        cornerAverage = cornerAverage + (random.nextDouble() * 2 * randomizerRange) - randomizerRange;
        this.heightMap[x][y] = cornerAverage;
        wrapEdgeValues(x, mapSize, y, cornerAverage);
    }

    public void wrapEdgeValues(int x, int mapSize, int y, double cornerAverage) {
        if (x == 0) {
            this.heightMap[mapSize - 1][y] = cornerAverage;
        }
        if (y == 0) {
            this.heightMap[x][mapSize - 1] = cornerAverage;
        }
    }
    
    public double[][] getHeightMap() {
        return this.heightMap;
    }

}
