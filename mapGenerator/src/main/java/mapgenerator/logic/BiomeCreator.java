/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.logic;

import mapgenerator.domain.Map;

/**
 *
 * @author heisonja
 */
public class BiomeCreator {

    int[] biomeSelection;

    public BiomeCreator() {
        this.biomeSelection = new int[6];
        biomeSelection = fillBiomes(biomeSelection);
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

    public int[] fillBiomes(int[] biomes) {
        String biomeString = "sand;grass;leaf;taiga;tundra;snow";
        String[] biomeList = biomeString.split(";");

        int index = 0;
        for (int height = 0; height < 6; height++) {
            int biome = getBiomeNumber(biomeList[index]);
            biomes[height] = biome;
            index++;
        }
        return biomes;
    }

    public int getBiomeNumber(String biomeName) {
        switch (biomeName) {
            case "sand":
                return 1;
            case "grass":
                return 2;
            case "leaf":
                return 3;
            case "taiga":
                return 4;
            case "tundra":
                return 5;
            case "snow":
                return 6;
        }
        return 0;
    }

}
