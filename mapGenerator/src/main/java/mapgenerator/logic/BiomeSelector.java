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

    public int[][] createBiomes(double[][] heightMap, double maxHeight, double waterLevel, boolean[][] water, double[][] moisture) {
        int[][] biomes = new int[heightMap.length][heightMap.length];
        double waterHeight = waterLevel * maxHeight;
        double landHeightRange = maxHeight - waterHeight;
        for (int x = 0; x < heightMap.length; x++) {
            for (int y = 0; y < heightMap.length; y++) {
                if (!water[x][y]) {
                    int heightlevel;
                    double landHeight = heightMap[x][y] - waterHeight;
                    if (landHeight >= 0 && landHeight < 0.1 * landHeightRange) {
                        heightlevel = 0;
                    } else if (landHeight >= 0.1 * landHeightRange && landHeight < 0.2 * landHeightRange) {
                        heightlevel = 1;
                    } else if (landHeight >= 0.2 * landHeightRange && landHeight < 0.4 * landHeightRange) {
                        heightlevel = 2;
                    } else if (landHeight >= 0.4 * landHeightRange && landHeight < 0.6 * landHeightRange) {
                        heightlevel = 3;
                    } else if (landHeight >= 0.6 * landHeightRange && landHeight < 0.8 * landHeightRange) {
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
