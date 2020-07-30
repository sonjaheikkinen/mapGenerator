/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

/**
 *
 * @author heisonja
 */
public class WaterGenerator {

    private boolean[][] water;

    public WaterGenerator(int mapSizeExponent) {
        int mapSize = (int) Math.pow(2, mapSizeExponent) + 1;
        this.water = new boolean[mapSize][mapSize];
    }

    public void addWaterByHeight(int waterlevel, double[][] heightmap) {
        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap.length; y++) {
                if (heightmap[x][y] < waterlevel) {
                    water[x][y] = true;
                }
            }
        }
    }
    
    public boolean[][] getWater() {
        return water;
    }

}
