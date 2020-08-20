/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.math;

/**
 *
 * @author heisonja
 */
public class RandomNumberGenerator {
    
    /**
     * Returns a random integer between zero (inclusive) and bound (exclusive).
     * @param bound
     * @return 
     */
    public int nextInt(int bound) {
        Long nanotime = System.nanoTime();
        Long remainder = nanotime % bound;
        int number = remainder.intValue();
        return number;
    } 
    
    /**
     * Returns a random double between zero and one (both inclusive). 
     * @return 
     */
    public double nextDouble() {
        Long nanotime = System.nanoTime();
        Long remainder = nanotime % 100;
        double number = remainder / 100;
        return number;       
    }
    
}
