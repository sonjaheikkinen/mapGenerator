/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import javafx.stage.Stage;
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
public class ProgramHandlerTest {
    
    private ProgramHandler pg;
    
    public ProgramHandlerTest() {
    }
    
    @Before
    public void setUp() {
        pg = new ProgramHandler();
        pg.initialize(new Stage());
    }
    
    @After
    public void tearDown() {
    }
    
    /*
    @Test
    public void afterInitThereIsANewHeightMapWithNoZeroValues() {
        double[][] map = pg.getMapConstructor().getHeightMap();
        int zeroCount = 0;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y] == 0) {
                    zeroCount++;
                }
            }
        }
        assertTrue(zeroCount == 0);
    }
    */
    
}
