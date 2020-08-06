/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import mapgenerator.domain.Biomes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author heisonja
 */
public class BiomeSelectorTest {
    
    private BiomeSelector selector;
    private double[][] testHeights;  
    private double[][] testMoisture;
    private boolean[][] testWater;

    public BiomeSelectorTest() {
    }

    @Before
    public void setUp() {
        Biomes biomes = new Biomes();
        this.selector = new BiomeSelector(biomes);
        this.testHeights = createTestMap();
        this.testMoisture = createTestMap();
        this.testWater = createWater(testHeights);
        double maxHeight = 9;
        double maxMoisture = 9;
        double waterLevel = 0.5;
        selector.createBiomes(testHeights, maxHeight, waterLevel, testWater, testMoisture, maxMoisture);       
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void biomeIsZeroWhereWaterIsTrueAndNotZeroElseWhere() {
        boolean biomesCorrect = true;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if ((testWater[x][y] && selector.getBiomes()[x][y] != 0)
                        || (!testWater[x][y] && selector.getBiomes()[x][y] == 0)) {
                    biomesCorrect = false;
                }
            }
        }
        assertTrue(biomesCorrect);
    }

    public double[][] createTestMap() {
        double[][] map = new double[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                map[x][y] = x;
            }
        }
        return map;
    }

    public boolean[][] createWater(double[][] heights) {
        boolean[][] water = new boolean[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (heights[x][y] < 5) {
                    water[x][y] = true;
                }
            }
        }
        return water;
    }

}
