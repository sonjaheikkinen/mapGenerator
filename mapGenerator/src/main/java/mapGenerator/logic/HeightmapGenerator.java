/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapGenerator.logic;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author heisonja
 */
public class HeightmapGenerator {

    double[][] heights;
    int[][] heightMap;
    Random random;
    int maxValue;

    public HeightmapGenerator(Random random) {
        this.random = random;
    }

    public int[][] calculateHeights() {

        int n = 6;
        int mapSize = (int) Math.pow(2, n) + 1;
        int seed = 50;
        this.heights = new double[mapSize][mapSize];
        this.heightMap = new int[mapSize][mapSize];

        assignCornerValues(mapSize, seed);

        int randomizerRange = 50;
        for (int rectSize = mapSize - 1; rectSize >= 2; rectSize /= 2, randomizerRange /= 2.0) {
            int rectHalf = rectSize / 2;
            for (int x = 0; x < mapSize - 1; x += rectSize) {
                for (int y = 0; y < mapSize - 1; y += rectSize) {
                    diamondStep(x, y, rectSize, rectHalf, randomizerRange);
                }
            }
            for (int x = 0; x < mapSize - 1; x += rectHalf) {
                for (int y = (x + rectHalf) % rectSize; y < mapSize - 1; y += rectSize) {
                    squareStep(x, y, rectHalf, mapSize, randomizerRange);
                }
            }
        }

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                this.heightMap[x][y] = (int) Math.round(this.heights[x][y]);
                if (this.heightMap[x][y] < 0) {
                    this.heightMap[x][y] = 0;
                }
            }
        }

        return this.heightMap;
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
        double cornerAverage = (this.heights[(x - rectHalf + mapSize) % mapSize][y]
                + this.heights[(x + rectHalf) % mapSize][y]
                + this.heights[x][(y + rectHalf) % mapSize]
                + this.heights[x][(y - rectHalf + mapSize) % mapSize]) / 4;
        cornerAverage = cornerAverage + (random.nextDouble() * 2 * randomizerRange) - randomizerRange;
        this.heights[x][y] = cornerAverage;
        wrapEdgeValues(x, mapSize, y, cornerAverage);
    }

    public void wrapEdgeValues(int x, int mapSize, int y, double cornerAverage) {
        if (x == 0) {
            heights[mapSize - 1][y] = cornerAverage;
        }
        if (y == 0) {
            heights[x][mapSize - 1] = cornerAverage;
        }
    }

}
