package mapgenerator.domain;

import mapgenerator.datastructures.MapCell;

/**
 * Class contains all information regarding the created map.
 */
public class Map {

    private double maxHeight;
    private double maxMoisture;
    private MapCell[][] map;

    public void setMap(MapCell[][] map) {
        this.map = map;
    }

    public MapCell[][] getMap() {
        return this.map;
    }

    /**
     * Sets max height
     *
     * @param maxHeight Maximum value of the height map
     */
    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * Returns max height.
     *
     * @return max height
     */
    public double getMaxHeight() {
        return maxHeight;
    }

    /**
     * Sets max height
     *
     * @param maxHeight Maximum value of the height map
     */
    public void setMaxMoisture(double maxMoisture) {
        this.maxMoisture = maxMoisture;
    }

    /**
     * Returns max height.
     *
     * @return max height
     */
    public double getMaxMoisture() {
        return maxMoisture;
    }


}
