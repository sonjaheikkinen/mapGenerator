/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import java.util.Random;
import mapgenerator.datastructures.MapCell;
import mapgenerator.domain.Biomes;

/**
 * Class is responsible for picking out biomes based on height and moisture.
 */
public class BiomeSelector {

    private String[][] biomeSelection;
    private Random random;

    /**
     * Constructor gets biome options from biomes class.
     *
     * @param biomes A biomes object which holds a biome selection table
     */
    public BiomeSelector(Biomes biomes) {
        this.random = new Random();
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
     * @param map
     * @param maxHeight Maximum height
     * @param waterLevel A value between zero and one. The absolute water level
     * is water level * maxHeight
     * @param maxMoisture Maximum moisture
     */
    public MapCell[][] createBiomes(MapCell[][] map, double maxHeight, double waterLevel, double maxMoisture) {
        double waterHeight = waterLevel * maxHeight;
        double landHeightRange = maxHeight - waterHeight;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (!map[x][y].isWater()) {
                    int heightlevel = defineHeightLevel(map, x, y, waterHeight, landHeightRange);
                    int moisturelevel = defineMoistureLevel(map, x, y, maxMoisture);
                    map[x][y].setBiome(biomeSelection[heightlevel][moisturelevel]);
                } else {
                    map[x][y].setBiome("water");
                }
            }
        }
        return map;
    }

    /**
     * Selects correct height level based on waterlevel and the maximum height
     * of the map. Land height is calculated by subtracting waterlevel from
     * actual height. Height level is based on land height / land height range
     * -ratio.
     *
     * @param map
     * @param x X coordinate
     * @param y Y coordinate
     * @param waterHeight Absolute water level
     * @param landHeightRange Maximum height - waterlevel
     * @return Selected height level as integer
     */
    public int defineHeightLevel(MapCell[][] map, int x, int y, double waterHeight, double landHeightRange) {
        int heightlevel;
        double landHeight = map[x][y].getHeight() - waterHeight;
        if (landHeight < 0.05 * landHeightRange) {
            heightlevel = 0;
        } else if (landHeight < 0.1 * landHeightRange) {
            heightlevel = 1;
        } else if (landHeight < 0.2 * landHeightRange) {
            heightlevel = 2;
        } else if (landHeight < 0.5 * landHeightRange) {
            heightlevel = 3;
        } else if (landHeight < 0.6 * landHeightRange) {
            heightlevel = 4;
        } else if (landHeight < 0.8 * landHeightRange) {
            heightlevel = 5;
        } else {
            heightlevel = 6;
        }
        return heightlevel;
    }

    /**
     * Selects moisture level based on maximum moisture and actual moisture.
     *
     * @param map
     * @param x X coordinate
     * @param y Y coordinate
     * @param maxMoisture Maximum moisture of the moisture map
     * @return Selected moisture level as integer
     */
    public int defineMoistureLevel(MapCell[][] map, int x, int y, double maxMoisture) {
        int moisturelevel;
        if (map[x][y].getMoisture() < 0.1 * maxMoisture) {
            moisturelevel = 0;
        } else if (map[x][y].getMoisture() < 0.4 * maxMoisture) {
            moisturelevel = 1;
        } else {
            moisturelevel = 2;
        }
        return moisturelevel;
    }

}
