package mapgenerator.logic;

import java.util.Random;
import mapgenerator.domain.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapConstructorTest {

    private Map map;
    private MapConstructor mc;

    public MapConstructorTest() {
    }

    @Before
    public void setUp() {
        this.map = new Map();
        this.mc = new MapConstructor(new Random(), 6, 50, 50, this.map);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void heightMapHasNoZerosAfterRunningConstructMap() {
        this.mc.constructMap();
        assertTrue(countZeros(this.mc.getHeightMap()) == 0);
    }

    @Test
    public void mapHasAHeightMapWithNoZerosAfterRunningConstructMap() {
        this.mc.constructMap();
        assertTrue(countZeros(this.map.getHeightMap()) == 0);
    }

    public int countZeros(double[][] map) {
        int zeroCount = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y] == 0) {
                    zeroCount++;
                }
            }
        }
        return zeroCount;
    }

}
