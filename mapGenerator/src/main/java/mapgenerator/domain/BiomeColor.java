/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.domain;

import javafx.scene.paint.Color;

/**
 *
 * @author heisonja
 */
public class BiomeColor {
    
    String biome;
    Color color;
    
    public BiomeColor(String biome, Color color) {
        this.biome = biome;
        this.color = color;
    }
    
    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
    
}
