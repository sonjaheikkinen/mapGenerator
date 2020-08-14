package mapgenerator.logic;

import java.util.Random;
import mapgenerator.domain.Biomes;
import mapgenerator.domain.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapConstructorTest {

    private Map map;
    private Biomes biomes;
    private BiomeSelector bioS;
    private MapConstructor mc;

    public MapConstructorTest() {
    }

    @Before
    public void setUp() {
        this.map = new Map();
        this.biomes = new Biomes();
        this.bioS = new BiomeSelector(biomes);
        this.mc = new MapConstructor(new Random(), 6, 50, 50, this.map, this.bioS);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void mapHasAHeightMapWithNoZerosAfterRunningConstructMap() {
        this.mc.constructMap();
        assertTrue(countZeros(this.map.getHeightMap()) == 0);
    }

    @Test
    public void mapHasAMoistureMapWithNoZerosAfterRunningConstructMap() {
        this.mc.constructMap();
        assertTrue(countZeros(this.map.getMoisture()) == 0);
    }
    
    @Test
    public void mapHasAWaterMapAfterRunningConstructMap() {
        this.mc.constructMap();
        assertTrue(this.map.getWater() != null);
    }
    
    @Test
    public void mapHasABiomeMapAfterRunningConstructMap() {
        this.mc.constructMap();
        assertTrue(this.map.getBiomes() != null);
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
    
    public double checkMaxValue(double[][] map) {
        double maxValue = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y] > maxValue) {
                    maxValue = map[x][y];
                }
            }
        }
        return maxValue;
    }

}
