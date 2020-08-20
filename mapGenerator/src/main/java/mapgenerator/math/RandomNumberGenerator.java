
package mapgenerator.math;

/**
 * Class is used to generate random values.
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
