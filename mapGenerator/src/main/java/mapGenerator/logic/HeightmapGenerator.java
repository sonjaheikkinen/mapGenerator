/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapGenerator.logic;

/**
 *
 * @author heisonja
 */
public class HeightmapGenerator {
    
    
    public int[][] calculateHeights() {
        int[][] heightMap = new int[100][100];
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                heightMap[x][y] = x;
            }
        }
        return heightMap;
    }
    
}
 