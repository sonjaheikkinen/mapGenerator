
package mapgenerator.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaterGeneratorTest {

    private WaterGenerator wg;

    public WaterGeneratorTest() {
    }

    @Before
    public void setUp() {
        wg = new WaterGenerator(2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addWaterByHeightAssignsWaterCorrectly() {
        int mapSize = (int) Math.pow(2, 2) + 1;
        double[][] heightmap = new double[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                heightmap[x][y] = x;
            }
        }
        wg.addWaterByHeight(2, heightmap);
        int waterCount = 0;
        boolean[][] water = wg.getWater();
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                if (water[x][y]) {
                    waterCount++;
                }
            }
        }
        assertTrue(waterCount == 10);
    }

}
