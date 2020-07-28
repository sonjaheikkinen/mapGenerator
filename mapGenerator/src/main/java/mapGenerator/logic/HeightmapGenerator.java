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

    int[][] heightMap;
    Random random;

    public HeightmapGenerator(Random random) {
        this.random = random;
    }

    public int[][] calculateHeights() {

        int n = 6;
        int mapSize = (int) Math.pow(2, n) + 1;
        this.heightMap = new int[mapSize][mapSize];

        assignCornerValues(mapSize);

        int rectSize = mapSize - 1;
        int randomizerRange = 70;

        while (rectSize > 1) {
            for (int x = 0; x <= (mapSize - rectSize); x = x + rectSize) {
                for (int y = 0; y <= (mapSize - rectSize); y = y + rectSize) {
                    diamondStep(x, y, rectSize, randomizerRange);
                }
            }
            int row = 1;
            for (int x = 0; x < mapSize; x = x + rectSize / 2) {
                if (row == 1) {
                    for (int y = rectSize / 2; y < mapSize; y = y + rectSize) {
                        squareStep(x, y, rectSize, randomizerRange);
                    }
                    row = 0;
                } else {
                    for (int y = 0; y <= mapSize; y = y + rectSize) {
                        squareStep(x, y, rectSize, randomizerRange);
                    }
                    row = 1;
                }
            }
            if (randomizerRange > 1) {
                randomizerRange--;
            }
            rectSize = rectSize / 2;
        }

        /*
        //while (rectSize >= 2) {
        squareStep(rectSize, mapSize);
        rectSize = rectSize / 2 + 1;
        //}
         */
        return this.heightMap;
    }

    public void assignCornerValues(int mapSize) {
        heightMap[0][0] = random.nextInt(255);
        heightMap[0][mapSize - 1] = random.nextInt(255);
        heightMap[mapSize - 1][0] = random.nextInt(255);
        heightMap[mapSize - 1][mapSize - 1] = random.nextInt(255);
    }

    public void diamondStep(int x, int y, int rectSize, int randomizerRange) {
        int cornerAverage = (heightMap[x][y] + heightMap[x + rectSize][y] + heightMap[x][y + rectSize]
                + heightMap[x + rectSize][y + rectSize]) / 4;
        heightMap[x + rectSize / 2][y + rectSize / 2] = Math.min(cornerAverage + random.nextInt(randomizerRange), 255);
    }

    public void squareStep(int x, int y, int rectSize, int randomizerRange) {
        int cornerCount = 0;
        int cornerSum = 0;
        int distanceToCorner = rectSize / 2;
        if (x - distanceToCorner >= 0) {
            cornerCount++;
            cornerSum = heightMap[x - distanceToCorner][y];
        }
        if (x + distanceToCorner <= rectSize) {
            cornerCount++;
            cornerSum = heightMap[x + distanceToCorner][y];
        }
        if (y - distanceToCorner >= 0) {
            cornerCount++;
            cornerSum = heightMap[x][y - distanceToCorner];
        }
        if (y + distanceToCorner <= rectSize) {
            cornerCount++;
            cornerSum = heightMap[x][y + distanceToCorner];
        }
        this.heightMap[x][y] = Math.min(cornerSum / cornerCount + random.nextInt(randomizerRange), 255);
    }

    /*
    public void squareStep(int rectSize, int mapSize) {
        int rectMapRatio = (int) Math.floor(mapSize / rectSize);
        for (int leftUpperCornerX = 0; leftUpperCornerX < rectMapRatio; leftUpperCornerX++) {
            for (int leftUpperCornerY = 0; leftUpperCornerY < rectMapRatio; leftUpperCornerY++) {
                int rectCenterValue = rectSize / 2;
                int cornerAverage = (heightMap[leftUpperCornerX][leftUpperCornerY]
                        + heightMap[leftUpperCornerX + rectSize - 1][leftUpperCornerY]
                        + heightMap[leftUpperCornerX][leftUpperCornerY + rectSize - 1]
                        + heightMap[leftUpperCornerX + rectSize - 1][leftUpperCornerY + rectSize - 1]) / 4;
                heightMap[leftUpperCornerX + (rectCenterValue)][leftUpperCornerY + (rectCenterValue)] = cornerAverage;
                diamondStep(rectCenterValue, leftUpperCornerX + rectCenterValue, leftUpperCornerY + rectCenterValue);
            }
        }
    }
    
    public void diamondStep(int rectHalf, int centerX, int centerY) {
        calculateDiamondValue(rectHalf, centerX, centerY, "left");
        calculateDiamondValue(rectHalf, centerX, centerY, "upper");
        calculateDiamondValue(rectHalf, centerX, centerY, "right");
        calculateDiamondValue(rectHalf, centerX, centerY, "lower");
    }
     */
}
