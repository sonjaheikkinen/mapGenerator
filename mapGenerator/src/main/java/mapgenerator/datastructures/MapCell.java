
package mapgenerator.datastructures;

/**
 * Class is used to store information about the height, moisture and biome of one cell in map array. 
 */
public class MapCell {

    private double height;
    private double moisture;
    private boolean water;
    private String biome;

    /**
     * Returns the value of height or moisture based on given attribute.
     * @param attribute
     * @return 
     */
    public double getNoiseValue(String attribute) {
        if (attribute.equals("height")) {
            return height;
        } else if (attribute.equals("moisture")) {
            return moisture;
        }
        return 0;
    }

    /**
     * Sets the value of height or moisture based on given attribute. 
     * @param attribute
     * @param value 
     */
    public void setNoiseValue(String attribute, double value) {
        if (attribute.equals("height")) {
            this.height = value;
        } else if (attribute.equals("moisture")) {
            this.moisture = value;
        } 
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

}
