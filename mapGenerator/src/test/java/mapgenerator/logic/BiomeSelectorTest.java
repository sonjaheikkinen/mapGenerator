/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import mapgenerator.datastructures.MapCell;
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
    private MapCell[][] map;

    public BiomeSelectorTest() {
    }

    @Before
    public void setUp() {
        Biomes biomes = new Biomes();
        this.selector = new BiomeSelector(biomes);
        this.map = createTestMap();
        createWater();
        double maxHeight = 9;
        double maxMoisture = 9;
        double waterLevel = 0.5;
        this.map = selector.createBiomes(map, maxHeight, waterLevel, maxMoisture);       
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void biomeIsZeroWhereWaterIsTrueAndNotZeroElseWhere() {
        boolean biomesCorrect = true;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if ((map[x][y].isWater() && map[x][y].getBiome() != 0)
                        || (!map[x][y].isWater() && map[x][y].getBiome() == 0)) {
                    biomesCorrect = false;
                }
            }
        }
        assertTrue(biomesCorrect);
    }

    public MapCell[][] createTestMap() {
        MapCell[][] map = new MapCell[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                map[x][y] = new MapCell();
                map[x][y].setHeight(x);
                map[x][y].setMoisture(x);
            }
        }
        return map;
    }

    public void createWater() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (map[x][y].getHeight() < 5) {
                    map[x][y].setWater(true);
                }
            }
        }
    }

}
