package mapgenerator.logic;

import java.util.Random;
import mapgenerator.datastructures.MapCell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class NoiseMapGeneratorTest {

    Random random;
    int exponent;
    int seed;
    int range;
    int mapSize;
    NoiseMapGenerator noiseGen;
    private MapCell[][] map;

    public NoiseMapGeneratorTest() {
    }

    @Before
    public void setUp() {
        this.random = new Random();
        this.exponent = 6;
        this.seed = 50;
        this.range = 50;
        this.mapSize = (int) Math.pow(2, this.exponent) + 1;
        this.noiseGen = new NoiseMapGenerator(this.random, this.mapSize, this.seed, this.range, new Calculator());
        this.map = new MapCell[this.mapSize][this.mapSize];
        for (int x = 0; x < this.mapSize; x++) {
            for (int y = 0; y < this.mapSize; y++) {
                this.map[x][y] = new MapCell();
            }
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void allValuesAreZeroOnInitialization() {
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 0);
    }

    @Test
    public void cornerValuesAreAssignedCorrectly() {
        map = this.noiseGen.assignCornerValues("height", map);
        assertTrue(map[0][0].getHeight() >= seed - 10 && map[0][0].getHeight() <= seed + 10);
        assertTrue(map[0][this.mapSize - 1].getHeight() >= seed - 10 && map[0][this.mapSize - 1].getHeight() <= seed + 10);
        assertTrue(map[this.mapSize - 1][0].getHeight() >= seed - 10 && map[this.mapSize - 1][0].getHeight() <= seed + 10);
        assertTrue(map[this.mapSize - 1][this.mapSize - 1].getHeight() >= seed - 10
                && map[this.mapSize - 1][this.mapSize - 1].getHeight() <= seed + 10);
    }

    @Test
    public void afterCornerStepThereAreRightAmountOfZerosRemaining() {
        map = this.noiseGen.assignCornerValues("height", map);
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 4);
    }

    @Test
    public void afterOneDiamondStepThereAreRightAmountOfZerosRemaining() {
        map = this.noiseGen.assignCornerValues("height", map);
        map = this.noiseGen.diamondStep(0, 0, mapSize - 1, (mapSize - 1) / 2, "height", map);
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 5);
    }

    @Test
    public void afterOneSquareStepThereAreRightAmountOfZerosRemaining() {
        map = this.noiseGen.assignCornerValues("height", map);
        map = this.noiseGen.diamondStep(0, 0, mapSize - 1, (mapSize - 1) / 2, "height", map);
        map = this.noiseGen.squareStep(0, 32, (mapSize - 1) / 2, "height", map);
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 6);
    }

    @Test
    public void noZeroValuesLeftAfterCreatingNoise() {
        map = this.noiseGen.createNoise(map);
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 65 * 65);
    }
    
    @Test
    public void maxValueHasRightValueAfterCreatingNoise() {
        map = this.noiseGen.createNoise(map);
        double maxValue = checkMaxValue(map);
        assertTrue(maxValue == this.noiseGen.getMaxValue("height"));
    }

    public int notZeroValueCount() {
        int valuesOtherThanZero = 0;
        for (int x = 0; x < this.mapSize; x++) {
            for (int y = 0; y < this.mapSize; y++) {
                if (map[x][y].getHeight() != 0) {
                    valuesOtherThanZero++;
                }
            }
        }
        return valuesOtherThanZero;
    }

    public double checkMaxValue(MapCell[][] map) {
        double maxValue = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y].getHeight() > maxValue) {
                    maxValue = map[x][y].getHeight();
                }
            }
        }
        return maxValue;
    }

}
