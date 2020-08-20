package mapgenerator.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaterGeneratorTest {

    private WaterGenerator wg;
    private int mapSize;
    private double[][] heightmap;

    public WaterGeneratorTest() {
    }

    @Before
    public void setUp() {
        wg = new WaterGenerator(2);
        mapSize = (int) Math.pow(2, 2) + 1;
        heightmap = new double[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                heightmap[x][y] = x;
            }
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addWaterByHeightAssignsWaterCorrectly() {
        wg.addWaterByHeight(2, heightmap);
        boolean[][] water = wg.getWater();
        int waterCount = countWater(water);
        assertTrue(waterCount == 10);
    }

    @Test
    public void createRiverWorksCorrectlyWhenThereIsClearPathForTheRiver() {
        double[][] heightmapWithOneRiverPath = new double[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                if (y == 2) {
                    heightmapWithOneRiverPath[x][y] = x;
                } else {
                    heightmapWithOneRiverPath[x][y] = 4;
                }
            }
        }
        wg.createRiver(3, 2, heightmapWithOneRiverPath);
        assertTrue(countWater(wg.getWater()) == 4);
    }
    
    
    @Test
    public void riverAlwaysFlowsDownIfPossible() {
        wg.createRiver(3, 2, heightmap);
        int waterCount = countWater(wg.getWater());
        assertTrue(waterCount >= 3 && waterCount <= 4);
    }
    

    public int countWater(boolean[][] water) {
        int waterCount = 0;
        for (int x = 0; x < water.length; x++) {
            for (int y = 0; y < water.length; y++) {
                if (water[x][y]) {
                    waterCount++;
                }
            }
        }
        return waterCount;
    }

}
