package mapgenerator.logic;

import java.util.Random;
import mapgenerator.datastructures.BinaryHeap;
import mapgenerator.datastructures.Node;

/**
 * Class generates water for the map
 */
public class WaterGenerator {

    private boolean[][] water;
    private int mapSize;
    private Random random;

    /**
     * The constructor initializes the boolean array for water placement
     *
     * @param mapSizeExponent The map is of size (2^mapSizeExponent) + 1
     */
    public WaterGenerator(int mapSizeExponent) {
        mapSize = (int) Math.pow(2, mapSizeExponent) + 1;
        this.water = new boolean[mapSize][mapSize];
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
    
    /**
     * Method adds rivers and lakes. The whole map is looped trough, and in every cell, a river might randomly be 
     * started. The probability of the river starting in given location is based on height and moisture.
     * @param heightmap Height values of the map as doubles
     * @param moisture Moisture values of the map as doubles
     */
    public void addRivers(double[][] heightmap, double[][] moisture) {
        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap.length; y++) {
                int randomValue = random.nextInt(1000000);
                int riverX = x;
                int riverY = y;
                if (randomValue < heightmap[x][y] * 4 || randomValue < moisture[x][y] * 4) {
                    createRiver(riverX, riverY, heightmap);
                }
            }
        }
    }
    
    /**
     * Method generates river starting from a point given as parameter and stopping at the edge of the map or 
     * when ending up in water. The river always goes to the direction of smallest height where there is still no water.
     * If such neighbor cannot be found, the river continues to random direction. 
     * @param riverX Current x coordinate of the river generation
     * @param riverY Current y coordinate of the river generation
     * @param heightmap Height values of the map as doubles
     */
    public void createRiver(int riverX, int riverY, double[][] heightmap) {
        while (!water[riverX][riverY]) {
            water[riverX][riverY] = true;
            if (riverX <= 0 || riverX >= mapSize - 1 || riverY <= 0 || riverY >= mapSize - 1) {
                break;
            }
            BinaryHeap neighbors = getNeighbors(riverX, riverY, heightmap);
            if (neighbors.isEmpty()) {
                riverX = random.nextInt(3) - 1;
                riverX = moveInsideMap(riverX);
                riverY = random.nextInt(3) - 1;      
                riverY = moveInsideMap(riverY);
            } else {
                Node next = neighbors.poll();
                riverX = next.getX();
                riverY = next.getY();
            }
        }
    }
    
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
     * Returns all neighbors of a coordinate as minimum binary heap. The current coordinates and coordinates that already 
     * have water are not added to heap. 
     * @param x
     * @param y
     * @param heightmap
     * @return Neighbors as binary heap. 
     */
    public BinaryHeap getNeighbors(int x, int y, double[][] heightmap) {
        BinaryHeap neighbors = new BinaryHeap();
        for (int xCoord = x - 1; xCoord <= x + 1; xCoord++) {
            for (int yCoord = y - 1; yCoord <= y + 1; yCoord++) {
                if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize) {
                    if (xCoord == x && yCoord == y || water[xCoord][yCoord]) {
                        continue;
                    }
                    Node neighbor = new Node(xCoord, yCoord, heightmap[xCoord][yCoord]);
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
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
