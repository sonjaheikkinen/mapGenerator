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

    private String[][] biomeSelection;
    private BiomeColor[] biomeColors;

    /**
     * Constructor creates biome selection array and fills it with biomes.
     */
    public Biomes() {
        this.biomeSelection = new String[7][3];
        this.biomeColors = new BiomeColor[21];
        fillBiomes();

    }

    /**
     * Creates an array of biomes from which they can be selected based on
     * height and moisture.
     */
    public void fillBiomes() {
        String[] biomeList = constructBiomeList();
        int index = 0;
        for (int height = 0; height < 7; height++) {
            for (int moisture = 0; moisture < 3; moisture++) {
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

    public String[] constructBiomeList() {
        String biomeString = "Sand;Grassy beach;Forested beach;"
                + "Dry grass;Grass;Grass and leaf trees;"
                + "Dry grass with trees;Deciduous forest;Mixed forest;"
                + "Dry deciduous forest;Pine forest;Taiga;"
                + "Dry mountain forest; Mountain forest;Tundra;"
                + "Bare mountain;Bare mountain;Bare tundra;"
                + "Volcano;Snowy mountain;Snowy mountain";
        String[] biomeList = biomeString.split(";");
        return biomeList;
    }

    /**
     * Returns biome selection table.
     *
     * @return An array containing biomes.
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
