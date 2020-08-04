
package mapgenerator.domain;

/**
 * Class contains all information regarding the created map.
 */
public class Map {
    
    private double[][] heightMap;
    private boolean[][] water;
    private double[][] moisture;
    
    /**
     * Sets a height map.
     * @param heightMap An array containing the height values of the map as doubles.
     */
    public void setHeightMap(double[][] heightMap) {
        this.heightMap = heightMap;
    }
    
    /**
     * Returns the height map.
     * @return An array containing the height values of the map as doubles.
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }
    
    /**
     * Sets a water map
     * @param water A boolean array in which true means water and false means land
     */
    public void setWater(boolean[][] water) {
        this.water = water;
    } 
    
    /**
     * Returns the water map
     * @return A boolean array in which true means water and false means land
     */
    public boolean[][] getWater() {
        return water;
    }
    
    public void setMoisture(double[][] moisture) {
        this.moisture = moisture;
    }
    
    public double[][] getMoisture() {
        return moisture;
    }

}
