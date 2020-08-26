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

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxMoisture(double maxMoisture) {
        this.maxMoisture = maxMoisture;
    }

    public double getMaxMoisture() {
        return maxMoisture;
    }


}
