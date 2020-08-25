/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.domain;

import java.util.HashMap;
import javafx.scene.paint.Color;

/**
 * Class is responsible for storing biome names and their order in relation to
 * height and moisture.
 */
public class Biomes {

    //int[][] biomeSelection;
    private String[][] biomeSelection;
    private BiomeColor[] biomeColors;
    HashMap<String, Integer> biomeNumbers;

    /**
     * Constructor creates biome selection array and fills it with biomes.
     */
    public Biomes() {
        //this.biomeSelection = new int[7][3];
        this.biomeSelection = new String[7][3];
        this.biomeColors = new BiomeColor[21];
        //fillBiomeNumbers();
        fillBiomes();

    }

    /**
     * Creates an array of biomes from which they can be selected based on
     * height and moisture.
     */
    public void fillBiomes() {
        /*
        String biomeString = "sand;grass;leaf;"
                           + "sand;leaf;leaf;"
                           + "drygrass;taiga;leaf;"
                           + "drygrass;taiga;taiga;"
                           + "bare;tundra;tundra;"
                           + "bare;snow;snow";
         */
 /*
        String biomeString = "sand;sand;grass;"
                           + "sand;grass;leaf;"
                           + "drygrass;leaf;taiga;"
                           + "drygrass;leaf;taiga;"
                           + "bare;tundra;tundra;"
                           + "bare;snow;snow";
         */
        String biomeString = "Sand;Grassy beach;Forested beach;"
                + "Dry grass;Grass;Grass and leaf trees;"
                + "Dry grass with trees;Deciduous forest;Mixed forest;"
                + "Dry deciduous forest;Pine forest;Taiga;"
                + "Dry mountain forest; Mountain forest;Tundra;"
                + "Bare mountain;Bare mountain;Bare tundra;"
                + "Volcano;Snowy mountain;Snowy mountain";
        String[] biomeList = biomeString.split(";");
        int index = 0;
        for (int height = 0; height < 7; height++) {
            for (int moisture = 0; moisture < 3; moisture++) {
                //int biome = biomeNumbers.get(biomeList[index]);
                //biomeSelection[height][moisture] = biome;
                biomeSelection[height][moisture] = biomeList[index];
                if (index == 0) {
                    biomeColors[index] = new BiomeColor("water", null);
                } else {
                    biomeColors[index] = new BiomeColor(biomeList[index], null);
                }
                index++;
            }
        }
    }

    /**
     * Creates a hashmap where biome names are the keys and biome numbers are
     * values.
     */
    public void fillBiomeNumbers() {
        this.biomeNumbers = new HashMap<>();
        biomeNumbers.put("sand", 1);
        biomeNumbers.put("beachGrass", 2);
        biomeNumbers.put("beachForest", 3);
        biomeNumbers.put("dryGrass", 4);
        biomeNumbers.put("grass", 5);
        biomeNumbers.put("sparseLeaf", 6);
        biomeNumbers.put("dryGrassWithTrees", 7);
        biomeNumbers.put("leaf", 8);
        biomeNumbers.put("mixedForest", 9);
        biomeNumbers.put("dryLeaf", 10);
        biomeNumbers.put("taigaPine", 11);
        biomeNumbers.put("taigaSpruce", 12);
        biomeNumbers.put("dryMountainForest", 13);
        biomeNumbers.put("mountainForest", 14);
        biomeNumbers.put("tundra", 15);
        biomeNumbers.put("bare", 16);
        biomeNumbers.put("bareTundra", 17);
        biomeNumbers.put("volcano", 18);
        biomeNumbers.put("snow", 19);
    }

    /**
     * Returns biome selection table.
     *
     * @return An array containing biome numbers.
     */
    /*
    public int[][] getBiomeSelection() {
        return biomeSelection;
    }
     */
    public String[][] getBiomeSelection() {
        return biomeSelection;
    }
    
    public BiomeColor[] getBiomeColors() {
        return biomeColors;
    }
    
    public Color getBiomeColor(String biome) {
        for (int i = 0; i < biomeColors.length; i++) {
            if (biomeColors[i].getBiome().equals(biome)) {
                return biomeColors[i].getColor();
            }
        }
        return biomeColors[0].getColor();
    }
    
   

}
