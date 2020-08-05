/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import mapgenerator.domain.Biomes;

public class BiomeSelector {
    
    int[] biomeSelection;

    public BiomeSelector(Biomes biomes) {
        this.biomeSelection = biomes.getBiomeSelection();
    }

    public int[][] createBiomes(double[][] heightMap, boolean[][] water, double[][] moisture) {
        int[][] biomes = new int[heightMap.length][heightMap.length];
        for (int x = 0; x < heightMap.length; x++) {
            for (int y = 0; y < heightMap.length; y++) {
                if (!water[x][y]) {
                    int heightlevel;
                    double height = heightMap[x][y];
                    if (height >= 50 && height < 52) {
                        heightlevel = 0;
                    } else if (height >= 52 && height < 55) {
                        heightlevel = 1;
                    } else if (height >= 55 && height < 65) {
                        heightlevel = 2;
                    } else if (height >= 65 && height < 80) {
                        heightlevel = 3;
                    } else if (height >= 80 && height < 90) {
                        heightlevel = 4;
                    } else {
                        heightlevel = 5;
                    }
                    biomes[x][y] = biomeSelection[heightlevel];
                } else {
                    biomes[x][y] = 0;
                }
            }
        }
        return biomes;
    }


}
