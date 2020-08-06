/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.domain;

public class Biomes {
    int[][] biomeSelection;

    public Biomes() {
        this.biomeSelection = new int[6][3];
        fillBiomes();
    }
    
    public void fillBiomes() {
        String biomeString = "sand;grass;leaf;"
                           + "sand;leaf;leaf;"
                           + "drygrass;taiga;leaf;"
                           + "drygrass;taiga;taiga;"
                           + "bare;tundra;tundra;"
                           + "bare;snow;snow";
        String[] biomeList = biomeString.split(";");
        int index = 0;
        for (int height = 0; height < 6; height++) {
            for (int moisture = 0; moisture < 3; moisture++) {
                int biome = getBiomeNumber(biomeList[index]);
                biomeSelection[height][moisture] = biome;
                index++;
            }
        }
    }
    
    public int getBiomeNumber(String biomeName) {
         switch (biomeName) {
            case "sand":
                return 1;
            case "drygrass":
                return 2;
            case "grass":
                return 3;
            case "leaf":
                return 4;
            case "taiga":
                return 5;
            case "tundra":
                return 6;
            case "bare":
                return 7;
            case "snow":
                return 8;
        }
        return 0;
    }
    
    public int[][] getBiomeSelection() {
        return biomeSelection;
    }

}
