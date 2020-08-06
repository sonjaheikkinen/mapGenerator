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
public class Random {
    
    public int nextInt(int bound) {
        return (int) System.nanoTime() % bound;
    } 
    
    public double nextDouble() {
        return System.nanoTime() % 100 / 100;       
    }
    
}
