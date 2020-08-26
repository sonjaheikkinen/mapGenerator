package mapgenerator.logic;

import java.util.Random;
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
        mapSize = (int) Math.pow(2, 2) + 1;
        wg = new WaterGenerator(new Random(), mapSize);
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
        int waterCount = countWater(map);
        assertTrue(waterCount == 10);
    }

    @Test
    public void createRiverWorksCorrectlyWhenThereIsClearPathForTheRiver() {
        MapCell[][] mapWithOneRiverPath = new MapCell[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                if (y == 2) {
                    mapWithOneRiverPath[x][y] = new MapCell();
                    mapWithOneRiverPath[x][y].setHeight(x);
                } else {
                    mapWithOneRiverPath[x][y] = new MapCell();
                    mapWithOneRiverPath[x][y].setHeight(4);
                }
            }
        }
        mapWithOneRiverPath = wg.createRiver(3, 2, mapWithOneRiverPath);
        assertTrue(countWater(mapWithOneRiverPath) == 4);
    }
    
    
    @Test
    public void riverAlwaysFlowsDownIfPossible() {
        map = wg.createRiver(3, 2, map);
        int waterCount = countWater(map);
        assertTrue(waterCount >= 3 && waterCount <= 4);
    }
    

    public int countWater(MapCell[][] countMap) {
        int waterCount = 0;
        for (int x = 0; x < countMap.length; x++) {
            for (int y = 0; y < countMap.length; y++) {
                if (countMap[x][y].isWater()) {
                    waterCount++;
                }
            }
        }
        return waterCount;
    }

}
