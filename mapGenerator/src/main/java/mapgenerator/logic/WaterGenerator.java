package mapgenerator.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import mapgenerator.domain.CoordinatePair;
import mapgenerator.domain.Map;

/**
 * Class generates water for the map
 */
public class WaterGenerator {

    private boolean[][] water;
    private boolean[][] visited;
    private HashMap<CoordinatePair, CoordinatePair> parentNodes;
    private int mapSize;
    private Random random;
    private Queue<CoordinatePair> queue;
    private int[][] distance;

    /**
     * The constructor initializes the boolean array for water placement
     *
     * @param mapSizeExponent The map is of size (2^mapSizeExponent) + 1
     */
    public WaterGenerator(int mapSizeExponent) {
        mapSize = (int) Math.pow(2, mapSizeExponent) + 1;
        this.water = new boolean[mapSize][mapSize];
        this.visited = new boolean[mapSize][mapSize];
        this.parentNodes = new HashMap<>();
        this.distance = new int[mapSize][mapSize];
        this.queue = new LinkedList<>();
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                this.distance[x][y] = Integer.MAX_VALUE;
            }
        }
        this.random = new Random();
    }

    /**
     * Marks all parts of the map with a height below water level as water
     *
     * @param waterlevel All heights under this are water
     * @param heightmap All height values of the map
     */
    public void addWaterByHeight(double waterlevel, double[][] heightmap) {
        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap.length; y++) {
                if (heightmap[x][y] < waterlevel) {
                    water[x][y] = true;
                }
            }
        }
    }

    public void removeSmallWaters() {
        for (int x = 0; x < water.length; x++) {
            for (int y = 0; y < water.length; y++) {
                int wateryNeighbors = 0;
                for (int xCoord = x - 1; xCoord <= x + 1; xCoord++) {
                    for (int yCoord = y - 1; yCoord <= y + 1; yCoord++) {
                        if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize && water[xCoord][yCoord]) {
                            wateryNeighbors++;
                        }
                    }
                }
                if (wateryNeighbors <= 4) {
                    water[x][y] = false;
                }
            }
        }
    }

    public void addRivers(double[][] heightmap) {
        int startX = heightmap.length / 2;
        int startY = heightmap.length / 2;
        CoordinatePair riverEnd = bfs(startX, startY);
        CoordinatePair currentPlace = riverEnd;
        while (true) {
            CoordinatePair parent = parentNodes.get(currentPlace);
            int x = currentPlace.getX();
            int y = currentPlace.getY();
            for (int xCoord = x - 1; xCoord <= x + 1; xCoord++) {
                for (int yCoord = y - 1; yCoord <= y + 1; yCoord++) {
                    if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize) {
                        water[xCoord][yCoord] = true;
                    }
                }
            }
            currentPlace = parent;
            if (parent.getX() == startX && parent.getY() == startY) {
                break;
            }
        }

        System.out.println("");

    }

    public CoordinatePair bfs(int x, int y) {
        CoordinatePair start = new CoordinatePair(x, y);
        parentNodes.put(start, start);
        queue.add(start);
        visited[x][y] = true;
        distance[x][y] = 0;
        int size = mapSize * mapSize;
        int index = 1;
        CoordinatePair place = start;
        while (!queue.isEmpty()) {
            place = queue.poll();
            for (int xCoord = place.getX() - 1; xCoord <= place.getX() + 1; xCoord++) {
                for (int yCoord = place.getY() - 1; yCoord <= place.getY() + 1; yCoord++) {
                    if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize) {
                        if (!visited[xCoord][yCoord]) {
                            CoordinatePair neighbor = new CoordinatePair(xCoord, yCoord);
                            queue.add(neighbor);
                            visited[xCoord][yCoord] = true;
                            index++;
                            distance[neighbor.getX()][neighbor.getY()] = distance[x][y] + 1;
                            parentNodes.put(neighbor, place);
                        }
                    }
                }
            }
            if (water[place.getX()][place.getY()]) {
                return place;
            }
        }
        return place;
    }

    /**
     * Returns the water array
     *
     * @return A boolean array containing the information of water placement
     */
    public boolean[][] getWater() {
        return water;
    }

}
