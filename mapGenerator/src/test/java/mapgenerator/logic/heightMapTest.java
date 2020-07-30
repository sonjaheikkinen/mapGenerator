/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author heisonja
 */
public class heightMapTest {

    Random random;
    int exponent;
    int seed;
    int range;
    int mapSize;
    HeightmapGenerator hmg;

    public heightMapTest() {
    }

    @Before
    public void setUp() {
        this.random = new Random();
        this.exponent = 6;
        this.seed = 50;
        this.range = 50;
        this.mapSize = (int) Math.pow(2, this.exponent) + 1;
        this.hmg = new HeightmapGenerator(this.random, this.exponent, this.seed, this.range);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void allHeightsAreZeroOnInitialization() {
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 0);
    }

    @Test
    public void cornerValuesAreAssignedCorrectly() {
        this.hmg.assignCornerValues(this.mapSize, this.seed);
        double[][] map = this.hmg.getHeightMap();
        assertTrue(map[0][0] >= seed - 10 && map[0][0] <= seed + 10);
        assertTrue(map[0][this.mapSize - 1] >= seed - 10 && map[0][this.mapSize - 1] <= seed + 10);
        assertTrue(map[this.mapSize - 1][0] >= seed - 10 && map[this.mapSize - 1][0] <= seed + 10);
        assertTrue(map[this.mapSize - 1][this.mapSize - 1] >= seed - 10 
                && map[this.mapSize - 1][this.mapSize - 1] <= seed + 10);
    }

    @Test
    public void afterCornerStepThereAreRightAmountOfZerosRemaining() {
        this.hmg.assignCornerValues(this.mapSize, this.seed);
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 4);
    }

    @Test
    public void afterOneDiamondStepThereAreRightAmountOfZerosRemaining() {
        this.hmg.assignCornerValues(this.mapSize, this.seed);
        this.hmg.diamondStep(0, 0, mapSize - 1, (mapSize - 1) / 2, range);
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 5);
    }
    
    @Test
    public void afterOneSquareStepThereAreRightAmountOfZerosRemaining() {
        this.hmg.assignCornerValues(this.mapSize, this.seed);
        this.hmg.diamondStep(0, 0, mapSize - 1, (mapSize - 1) / 2, range);
        this.hmg.squareStep(0, 32, (mapSize - 1) / 2, mapSize, range);
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 7);
    }
    
    @Test
    public void edgeWrappingPutsSameValueToEdgesHorizontal() {
        this.hmg.wrapEdgeValues(0, 10, mapSize, 7);
        double[][] map = this.hmg.getHeightMap();
        assertTrue(map[mapSize - 1][10] == 7);
    }
    
    @Test
    public void edgeWrappingPutsSameValueToEdgesVertical() {
        this.hmg.wrapEdgeValues(10, 0, mapSize, 7);
        double[][] map = this.hmg.getHeightMap();
        assertTrue(map[10][mapSize - 1] == 7);
    }
    
    @Test
    public void edgeWrappingDoesNothingIfCoordinatesNotOnEdge() {
        this.hmg.wrapEdgeValues(5, 5, mapSize, 7);
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 0);
    }
    
    @Test
    public void noZeroValuesLeftAfterRunningCalculateHeights() {
        this.hmg.calculateHeights();
        int valuesOtherThanZero = notZeroValueCount();
        assertTrue(valuesOtherThanZero == 65 * 65);
    }

    public int notZeroValueCount() {
        int valuesOtherThanZero = 0;
        for (int x = 0; x < this.mapSize; x++) {
            for (int y = 0; y < this.mapSize; y++) {
                double[][] map = this.hmg.getHeightMap();
                if (map[x][y] != 0) {
                    valuesOtherThanZero++;
                }
            }
        }
        return valuesOtherThanZero;
    }

}
