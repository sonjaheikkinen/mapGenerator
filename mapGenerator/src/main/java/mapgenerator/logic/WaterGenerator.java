package mapgenerator.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import mapgenerator.domain.Node;
import mapgenerator.domain.Map;

/**
 * Class generates water for the map
 */
public class WaterGenerator {

    private boolean[][] water;
    private boolean[][] rivers;
    private boolean[][] visited;
    //private HashMap<Node, Node> parentNodes;
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
        for (int startX = 0; startX < heightmap.length; startX++) {
            for (int startY = 0; startY < heightmap.length; startY++) {
                int randomNumber = random.nextInt(1000);
                if (!water[startX][startY] && randomNumber == 1) {
                    if (!water[startX][startY]) {
                        Node riverEnd = dijkstra(startX, startY, heightmap);
                        Node currentPlace = riverEnd;
                        while (true) {
                            //Node parent = parentNodes.get(currentPlace);
                            Node parent = currentPlace.getParent();
                            int x = currentPlace.getX();
                            int y = currentPlace.getY();
                            
                            for (int xCoord = x; xCoord <= x + 1; xCoord++) {
                                for (int yCoord = y; yCoord <= y + 1; yCoord++) {
                                    if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize) {
                                        rivers[xCoord][yCoord] = true;
                                    }
                                }
                            }
                            
                            currentPlace = parent;
                            if (parent.getX() == startX && parent.getY() == startY) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                if (rivers[x][y]) {
                    water[x][y] = true;
                }
            }
        }
    }

    public Node dijkstra(int x, int y, double[][] heightmap) {
        this.visited = new boolean[mapSize][mapSize];
        //this.parentNodes = new HashMap<>();
        this.distance = new double[mapSize][mapSize];
        this.priorityQ = new PriorityQueue<>();
        for (int row = 0; row < mapSize; row++) {
            for (int column = 0; column < mapSize; column++) {
                this.distance[row][column] = Integer.MAX_VALUE;
            }
        }
        Node start = new Node(x, y, 0);
        //parentNodes.put(start, start);
        start.setParent(start);
        priorityQ.add(start);
        distance[x][y] = 0;
        Node place = start;
        while (!priorityQ.isEmpty()) {
            place = priorityQ.poll();
            if (!visited[place.getX()][place.getY()]) {
                visited[place.getX()][place.getY()] = true;
                for (int xCoord = place.getX() - 1; xCoord <= place.getX() + 1; xCoord++) {
                    for (int yCoord = place.getY() - 1; yCoord <= place.getY() + 1; yCoord++) {
                        if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize) {
                            double currentNeighborDistance = distance[xCoord][yCoord];
                            double heightDifferenceToNeighbor = heightmap[xCoord][yCoord]
                                    - heightmap[place.getX()][place.getY()];
                            double distanceFromPlaceToNeighbor = heightDifferenceToNeighbor;
                            if (distanceFromPlaceToNeighbor < 0) {
                                distanceFromPlaceToNeighbor = 4 * Math.abs(distanceFromPlaceToNeighbor);
                            }
                            double newNeighborDistance = place.getDistance() + distanceFromPlaceToNeighbor;
                            if (newNeighborDistance < currentNeighborDistance) {
                                distance[xCoord][yCoord] = newNeighborDistance;
                                Node neighbor = new Node(xCoord, yCoord, newNeighborDistance);
                                //parentNodes.put(neighbor, place);
                                neighbor.setParent(place);
                                priorityQ.add(neighbor);
                            }
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
