/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.domain;

import java.util.HashMap;

public class Biomes {
    int[][] biomeSelection;
    HashMap<String, Integer> biomeNumbers;

    public Biomes() {
        this.biomeSelection = new int[6][3];
        fillBiomeNumbers();
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
                int biome = biomeNumbers.get(biomeList[index]);
                biomeSelection[height][moisture] = biome;
                index++;
            }
        }
    }
    
    public void fillBiomeNumbers() {
        this.biomeNumbers = new HashMap<>();
        biomeNumbers.put("sand", 1);
        biomeNumbers.put("drygrass", 2);
        biomeNumbers.put("grass", 3);
        biomeNumbers.put("leaf", 4);
        biomeNumbers.put("taiga", 5);
        biomeNumbers.put("tundra", 6);
        biomeNumbers.put("bare", 7);
        biomeNumbers.put("snow", 8);
    }
    
    public int[][] getBiomeSelection() {
        return biomeSelection;
    }

}
