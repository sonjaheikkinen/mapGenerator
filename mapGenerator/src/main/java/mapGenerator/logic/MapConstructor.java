/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapGenerator.logic;

import mapGenerator.domain.Map;

/**
 *
 * @author heisonja
 */
public final class MapConstructor {

    Map map;
    HeightmapGenerator heightmapGenerator;
    int[][] heightMap;

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
