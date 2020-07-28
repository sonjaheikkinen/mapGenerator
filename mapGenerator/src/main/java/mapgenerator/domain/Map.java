/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.domain;

/**
 *
 * @author heisonja
 */
public class Map {
    
    int[][] heightMap;
    
    public void setHeightMap(int[][] heightMap) {
        this.heightMap = heightMap;
    }
    
    public int[][] getHeightMap() {
        return this.heightMap;
    }
}
