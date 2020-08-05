/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.domain;

public class Biomes {

    int[] biomeSelection;

    public Biomes() {
        this.biomeSelection = new int[6];
        biomeSelection = fillBiomes(biomeSelection);
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
    
    public int[] getBiomeSelection() {
        return biomeSelection;
    }

}
