package mapgenerator.logic;

import java.util.Random;
import mapgenerator.datastructures.MapCell;
import mapgenerator.domain.Biomes;
import mapgenerator.domain.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapConstructorTest {

    private Map mapStorage;
    private Biomes biomes;
    private BiomeSelector bioS;
    private MapConstructor mc;

    public MapConstructorTest() {
    }

    @Before
    public void setUp() {
        this.mapStorage = new Map();
        this.biomes = new Biomes();
        this.bioS = new BiomeSelector(biomes);
        int mapSize = (int) Math.pow(2, 6) + 1;
        this.mc = new MapConstructor(new Random(), mapSize, 50, 50, this.mapStorage, this.bioS);
    }

    @Test
    public void mapHasAHeightMapWithNoZerosAfterRunningConstructMap() {
        this.mc.constructMap();
        assertTrue(countZeros(this.mapStorage.getMap(), "height") == 0);
    }

    @Test
    public void mapHasAMoistureMapWithNoZerosAfterRunningConstructMap() {
        this.mc.constructMap();
        assertTrue(countZeros(this.mapStorage.getMap(), "moisture") == 0);
    }

    public int countZeros(MapCell[][] map, String attribute) {
        int zeroCount = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y].getNoiseValue(attribute) == 0) {
                    zeroCount++;
                }
            }
        }
        return zeroCount;
    }

}
