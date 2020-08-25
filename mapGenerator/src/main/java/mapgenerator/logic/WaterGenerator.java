package mapgenerator.logic;

import java.util.Random;
import mapgenerator.datastructures.BinaryHeap;
import mapgenerator.datastructures.MapCell;
import mapgenerator.datastructures.Node;

/**
 * Class generates water for the map
 */
public class WaterGenerator {

    private int mapSize;
    private Random random;

    /**
     * The constructor initializes the boolean array for water placement
     *
     * @param mapSize
     */
    public WaterGenerator(int mapSize) {
        this.mapSize = mapSize;
        this.random = new Random();
    }

    /**
     * Marks all parts of the map with a height below water level as water
     *
     * @param waterlevel All heights under this are water
     * @param map All height values of the map
     * @return map
     */
    public MapCell[][] addWaterByHeight(double waterlevel, MapCell[][] map) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y].getHeight() < waterlevel) {
                    map[x][y].setWater(true);
                }
            }
        }
        return map;
    }

    /**
     * Method adds rivers and lakes. The whole map is looped trough, and in
     * every cell, a river might randomly be started. The probability of the
     * river starting in given location is based on height and moisture.
     *
     * @param map
     * @return map
     */
    public MapCell[][] addRivers(MapCell[][] map) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                int randomValue = random.nextInt(10000000);
                int riverX = x;
                int riverY = y;
                if (randomValue < map[x][y].getMoisture() * 30) {
                    map = createRiver(riverX, riverY, map);
                }
            }
        }
        return map;
    }

    /**
     * Method generates river starting from a point given as parameter and
     * stopping at the edge of the map or when ending up in water. The river
     * always goes to the direction of smallest height where there is still no
     * water. If such neighbour cannot be found, the river continues to random
     * direction.
     *
     * @param riverX Current x coordinate of the river generation
     * @param riverY Current y coordinate of the river generation
     * @param map
     * @return map
     */
    public MapCell[][] createRiver(int riverX, int riverY, MapCell[][] map) {
        while (!map[riverX][riverY].isWater()) {
            map[riverX][riverY].setWater(true);
            if (riverX <= 0 || riverX >= mapSize - 1 || riverY <= 0 || riverY >= mapSize - 1) {
                break;
            }
            BinaryHeap neighbours = getNeighbours(riverX, riverY, map);
            if (neighbours.isEmpty()) {
                riverX = random.nextInt(3) - 1;
                riverX = moveInsideMap(riverX);
                riverY = random.nextInt(3) - 1;
                riverY = moveInsideMap(riverY);
            } else {
                double height = map[riverX][riverY].getHeight();
                Node next = neighbours.poll();
                
                while(!neighbours.isEmpty() && neighbours.peek().getCost() < height) {
                    int randomValue = random.nextInt(10);
                    if (randomValue == 0) {
                        next = neighbours.poll();
                    } else {
                        break;
                    }
                }
                
                riverX = next.getX();
                riverY = next.getY();
            }
        }
        return map;
    }

    /**
     * If coordinate is outside map borders, return the coordinate inside map
     *
     * @param coordinate
     * @return coordinate
     */
    public int moveInsideMap(int coordinate) {
        if (coordinate < 0) {
            return 0;
        }
        if (coordinate > mapSize - 1) {
            return mapSize - 1;
        }
        return coordinate;

    }

    /**
     * Returns all neighbours of a coordinate as minimum binary heap. The
     * current coordinates and coordinates that already have water are not added
     * to heap.
     *
     * @param x
     * @param y
     * @param map
     * @return Neighbours as binary heap.
     */
    public BinaryHeap getNeighbours(int x, int y, MapCell[][] map) {
        BinaryHeap neighbours = new BinaryHeap();
        for (int xCoord = x - 1; xCoord <= x + 1; xCoord++) {
            for (int yCoord = y - 1; yCoord <= y + 1; yCoord++) {
                if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize) {
                    if (xCoord == x && yCoord == y || map[xCoord][yCoord].isWater()) {
                        continue;
                    }
                    Node neighbour = new Node(xCoord, yCoord, map[xCoord][yCoord].getHeight());
                    neighbours.add(neighbour);
                }
            }
        }
        return neighbours;
    }

}
