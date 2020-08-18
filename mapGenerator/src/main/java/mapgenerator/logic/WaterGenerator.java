package mapgenerator.logic;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import mapgenerator.domain.Node;

/**
 * Class generates water for the map
 */
public class WaterGenerator {

    private boolean[][] water;
    private boolean[][] rivers;
    private boolean[][] visited;
    private int mapSize;
    private Random random;
    private Queue<Node> queue;
    private PriorityQueue<Node> priorityQ;
    private double[][] distance;

    /**
     * The constructor initializes the boolean array for water placement
     *
     * @param mapSizeExponent The map is of size (2^mapSizeExponent) + 1
     */
    public WaterGenerator(int mapSizeExponent) {
        mapSize = (int) Math.pow(2, mapSizeExponent) + 1;
        this.water = new boolean[mapSize][mapSize];
        this.rivers = new boolean[mapSize][mapSize];
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
    
    public void addRivers(double[][] heightmap, double[][] moisture) {
        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap.length; y++) {
                int randomValue = random.nextInt(1000000);
                int riverX = x;
                int riverY = y;
                int earlierX = x;
                int earlierY = y;
                if (randomValue < heightmap[x][y] * 4 || randomValue < moisture[x][y] * 4) {
                    while (!water[riverX][riverY]) {
                        water[riverX][riverY] = true;
                        if (riverX == 0 || riverX == mapSize - 1 || riverY == 0 || riverY == mapSize - 1) {
                            break;
                        }
                        PriorityQueue<Node> neighbors = getNeighbors(riverX, riverY, heightmap);
                        if (neighbors.isEmpty()) {
                            int randomValueX = random.nextInt(100);
                            int randomValueY = random.nextInt(100);
                            if (earlierX < riverX) {
                                earlierX = riverX;
                                riverX++;
                            } else {
                                earlierX = riverX;
                                riverX--;
                            }
                            if (earlierY < riverY) {
                                earlierY = riverY;
                                riverY++;
                            } else {
                                earlierY = riverY;
                                riverY--;
                            }

                        } else {
                            Node next = neighbors.poll();
                            riverX = next.getX();
                            riverY = next.getY();
                        }
                    }
                }
            }
        }
        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap.length; y++) {
                if (rivers[x][y]) {
                    water[x][y] = true;
                }
            }
        }
    }

    public PriorityQueue<Node> getNeighbors(int x, int y, double[][] heightmap) {
        PriorityQueue<Node> neighbors = new PriorityQueue<>();
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
