
package mapgenerator.logic;

import mapgenerator.domain.Map;

public final class MapConstructor {

    Map map;
    HeightmapGenerator heightmapGenerator;
    double[][] heightMap;

    public MapConstructor(Map map, HeightmapGenerator hmGenerator) {
        this.map = map;
        this.heightmapGenerator = hmGenerator;
        constructMap();
    }

    public void constructMap() {
        generateMapObjects();
        map.setHeightMap(this.heightMap);
    }

    public void generateMapObjects() {
        generateHeightmap();
    }

    public void generateHeightmap() {
        this.heightMap = heightmapGenerator.calculateHeights();
    }

}
