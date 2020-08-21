package mapgenerator.logic;

import mapgenerator.datastructures.MapCell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaterGeneratorTest {

    private WaterGenerator wg;
    private int mapSize;
    private MapCell[][] map;

    public WaterGeneratorTest() {
    }

    @Before
    public void setUp() {
        wg = new WaterGenerator(2);
        mapSize = (int) Math.pow(2, 2) + 1;
        map = new MapCell[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                map[x][y] = new MapCell();
                map[x][y].setHeight(x);
            }
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addWaterByHeightAssignsWaterCorrectly() {
        wg.addWaterByHeight(2, map);
        int waterCount = countWater();
        assertTrue(waterCount == 10);
    }

    @Test
    public void createRiverWorksCorrectlyWhenThereIsClearPathForTheRiver() {
        MapCell[][] mapWithOneRiverPath = new MapCell[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                if (y == 2) {
                    map[x][y] = new MapCell();
                    mapWithOneRiverPath[x][y].setHeight(x);
                } else {
                    map[x][y] = new MapCell();
                    mapWithOneRiverPath[x][y].setHeight(4);
                }
            }
        }
        wg.createRiver(3, 2, mapWithOneRiverPath);
        assertTrue(countWater() == 4);
    }
    
    
    @Test
    public void riverAlwaysFlowsDownIfPossible() {
        wg.createRiver(3, 2, map);
        int waterCount = countWater();
        assertTrue(waterCount >= 3 && waterCount <= 4);
    }
    

    public int countWater() {
        int waterCount = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y].isWater()) {
                    waterCount++;
                }
            }
        }
        return waterCount;
    }

}
