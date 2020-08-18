/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import mapgenerator.domain.Biomes;

/**
 * Class is responsible for picking out biomes based on height and moisture.
 */
public class BiomeSelector {

    private int[][] biomes;
    private int[][] biomeSelection;

    /**
     * Constructor gets biome options from biomes class.
     *
     * @param biomes A biomes object which holds a biome selection table
     */
    public BiomeSelector(Biomes biomes) {
        this.biomeSelection = biomes.getBiomeSelection();
    }

    /**
     * Fills biome table based on height, waterlevel and moisture. Calculates
     * the possible land height range by subtracting the water height from
     * maximum height of the map. Then selects a height level based on heightmap
     * and the calculated land height range and selects the moisture level based
     * on the moisture / maximum moisture -ratio of the moisture map. Biome is
     * selected from biome selection table using the height and moisture levels.
     *
     * @param heightMap An array containing height values as doubles
     * @param maxHeight Maximum height
     * @param waterLevel A value between zero and one. The absolute water level
     * is waterlevel * maxHeight
     * @param water A boolean array where true means water
     * @param moisture An array containing moisture values as doubles
     * @param maxMoisture Maximum moisture
     */
    public void createBiomes(double[][] heightMap, double maxHeight, double waterLevel, boolean[][] water,
            double[][] moisture, double maxMoisture) {
        this.biomes = new int[heightMap.length][heightMap.length];
        double waterHeight = waterLevel * maxHeight;
        double landHeightRange = maxHeight - waterHeight;
        for (int x = 0; x < heightMap.length; x++) {
            for (int y = 0; y < heightMap.length; y++) {
                if (!water[x][y]) {
                    int heightlevel = defineHeightLevel(heightMap, x, y, waterHeight, landHeightRange);
                    int moisturelevel = defineMoistureLevel(moisture, x, y, maxMoisture);
                    biomes[x][y] = biomeSelection[heightlevel][moisturelevel];
                } else {
                    biomes[x][y] = 0;
                }
            }
        }
    }

    /**
     * Selects correct height level based on waterlevel and the maximum height
     * of the map. Land height is calculated by subtracting waterlevel from
     * actual height. Height level is based on land height / land height range
     * -ratio.
     *
     * @param heightMap An array containing height values as doubles
     * @param x X coordinate
     * @param y Y coordinate
     * @param waterHeight Absolute water level
     * @param landHeightRange Maximum height - waterlevel
     * @return Selected height level as integer
     */
    public int defineHeightLevel(double[][] heightMap, int x, int y, double waterHeight, double landHeightRange) {
        int heightlevel;
        double landHeight = heightMap[x][y] - waterHeight;
        if (landHeight >= 0 && landHeight < 0.15 * landHeightRange) {
            heightlevel = 0;
        } else if (landHeight >= 0.15 * landHeightRange && landHeight < 0.2 * landHeightRange) {
            heightlevel = 1;
        } else if (landHeight >= 0.2 * landHeightRange && landHeight < 0.4 * landHeightRange) {
            heightlevel = 2;
        } else if (landHeight >= 0.4 * landHeightRange && landHeight < 0.6 * landHeightRange) {
            heightlevel = 3;
        } else if (landHeight >= 0.6 * landHeightRange && landHeight < 0.8 * landHeightRange) {
            heightlevel = 4;
        } else {
            heightlevel = 5;
        }
        return heightlevel;
    }

    /**
     * Selects moisture level based on maximum moisture and actual moisture.
     *
     * @param moisture An array containing moisture values as doubles
     * @param x X coordinate
     * @param y Y coordinate
     * @param maxMoisture Maximum moisture of the moisture map
     * @return Selected moisture level as integer
     */
    public int defineMoistureLevel(double[][] moisture, int x, int y, double maxMoisture) {
        int moisturelevel;
        if (moisture[x][y] < 0.4 * maxMoisture) {
            moisturelevel = 0;
        } else if (moisture[x][y] < 0.6 * maxMoisture) {
            moisturelevel = 1;
        } else {
            moisturelevel = 2;
        }
        return moisturelevel;
    }

    /**
     * Returns biome array.
     *
     * @return An array containing biome numbers as integers
     */
    public int[][] getBiomes() {
        return biomes;
    }

}
