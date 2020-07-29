
package mapgenerator.domain;

/**
 * Class contains all information regarding the created map.
 */
public class Map {
    
    private double[][] heightMap;
    
    /**
     * Sets a height map.
     * @param heightMap An array containing the height values of the map as doubles.
     */
    public void setHeightMap(double[][] heightMap) {
        this.heightMap = heightMap;
    }
    
    /**
     * Returns the height map.
     * @retun An array containing the height values of the map as doubles.
     */
    public double[][] getHeightMap() {
        return this.heightMap;
    }
}
