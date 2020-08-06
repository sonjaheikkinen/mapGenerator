/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.math;

import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MathTest {

    private Calculator calc;
    private RandomNumberGenerator random;
    private Random trueRandom;

    public MathTest() {
    }

    @Before
    public void setUp() {
        this.calc = new Calculator();
        this.random = new RandomNumberGenerator();
        this.trueRandom = new Random();
        
    }

    @After
    public void tearDown() {
    }

    @Test
    public void intMinWorks() {
        int a = 5;
        int b = 3;
        int c = 0;
        int d = -3;
        int e = -5;
        assertTrue(calc.min(a, b) == b);
        assertTrue(calc.min(b, a) == b);
        assertTrue(calc.min(b, c) == c);
        assertTrue(calc.min(b, b) == b);
        assertTrue(calc.min(d, c) == d);
        assertTrue(calc.min(e, b) == e);
        assertTrue(calc.min(e, d) == e);
    }

    @Test
    public void doubleMinWorks() {
        double a = 0.5;
        double b = 0.2;
        double c = 0;
        double d = -0.2;
        double e = -0.5;
        assertTrue(calc.min(a, b) == b);
        assertTrue(calc.min(b, a) == b);
        assertTrue(calc.min(b, c) == c);
        assertTrue(calc.min(b, b) == b);
        assertTrue(calc.min(d, c) == d);
        assertTrue(calc.min(e, b) == e);
        assertTrue(calc.min(e, d) == e);
    }

    @Test
    public void intMaxWorks() {
        int a = 5;
        int b = 3;
        int c = 0;
        int d = -3;
        int e = -5;
        assertTrue(calc.max(a, b) == a);
        assertTrue(calc.max(b, a) == a);
        assertTrue(calc.max(b, c) == b);
        assertTrue(calc.max(b, b) == b);
        assertTrue(calc.max(d, c) == c);
        assertTrue(calc.max(e, b) == b);
        assertTrue(calc.max(e, d) == d);
    }

    @Test
    public void doubleMaxWorks() {
        double a = 0.5;
        double b = 0.2;
        double c = 0;
        double d = -0.2;
        double e = -0.5;
        assertTrue(calc.max(a, b) == a);
        assertTrue(calc.max(b, a) == a);
        assertTrue(calc.max(b, c) == b);
        assertTrue(calc.max(b, b) == b);
        assertTrue(calc.max(d, c) == c);
        assertTrue(calc.max(e, b) == b);
        assertTrue(calc.max(e, d) == d);
    }

    @Test
    public void intPowWorks() {
        assertTrue(calc.pow(2, 6) == 64);
        assertTrue(calc.pow(5, -2) == 0);
        assertTrue(calc.pow(7, 0) == 1);
    }

    @Test
    public void doublePowWorks() {
        assertTrue(calc.pow(2.0, 6.0) == 64);
        assertTrue(calc.pow(5.0, -2.0) == 0.04);
        assertTrue(calc.pow(7.0, 0.0) == 1.0);
    }
    
    @Test
    public void nextIntReturnsANumberInCorrectRange() {
        boolean allNumbersInCorrectRange = true;
        for (int i = 0; i < 100; i++) {
            int randomBound = trueRandom.nextInt(1000);
            if (randomBound == 0) {
                randomBound = 1;
            }
            int generatedRandomNumber = random.nextInt(randomBound);
            if (generatedRandomNumber >= randomBound || generatedRandomNumber < 0) {
                allNumbersInCorrectRange = false;
            }
        }
        assertTrue(allNumbersInCorrectRange);
    }
    
    @Test
    public void nextDoubleReturnsANumberInCorrectRange() {
        boolean allNumbersInCorrectRange = true;
        for (int i = 0; i < 100; i++) {
            double generatedRandomNumber = random.nextDouble();
            if (generatedRandomNumber > 1 || generatedRandomNumber < 0) {
                allNumbersInCorrectRange = false;
            }
        }
        assertTrue(allNumbersInCorrectRange);
    }
    
    

}
