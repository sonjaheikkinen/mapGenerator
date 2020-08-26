
package mapgenerator.domain;

import javafx.scene.paint.Color;

/**
 * Class is used to store a biome-color-pair so that choosing biome colors when drawing is easier. Every biome is paired with one color, 
 * but this color may change during the execution of the program. 
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
