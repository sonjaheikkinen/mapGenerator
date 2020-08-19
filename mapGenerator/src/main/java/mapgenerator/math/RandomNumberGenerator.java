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
        return (int) System.nanoTime() % bound;
    } 
    
    /**
     * Returns a random double between zero and one (both inclusive). 
     * @return 
     */
    public double nextDouble() {
        return (System.nanoTime() % 100) / (double) 100;       
    }
    
}
