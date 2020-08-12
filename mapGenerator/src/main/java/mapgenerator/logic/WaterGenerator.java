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

                    Node riverEnd = dijkstra(startX, startY, heightmap);
                    
                    Node currentPlace = riverEnd;
                    int riverLength = 30;
                    
                    while (true) {
                        riverLength--;
                        Node parent = currentPlace.getParent();
                        int x = currentPlace.getX();
                        int y = currentPlace.getY();
                        while (true) {
                            if (x >= 0 && x < mapSize && y >= 0 && y < mapSize) {
                                rivers[x][y] = true;
                                if (riverLength > 10 && x < mapSize - 1 && y > 0) {
                                    rivers[x][y - 1] = true;
                                    rivers[x + 1][y - 1] = true;
                                    rivers[x + 1][y] = true;
                                }
                                if (riverLength > 20 && x < mapSize - 1 && x > 0 && y < mapSize - 1 && y > 0) {
                                    rivers[x + 1][y + 1] = true;
                                    rivers[x][y + 1] = true;
                                    rivers[x - 1][y + 1] = true;
                                    rivers[x - 1][y] = true;
                                    rivers[x - 1][y - 1] = true;
                                }
                            }
                            if (parent.getX() < x) {
                                x--;
                            } else if (parent.getX() > x) {
                                x++;
                            }
                            if (parent.getY() < y) {
                                y--;
                            } else if (parent.getY() > y) {
                                y++;
                            }
                            if (x == parent.getX() && y == parent.getY()) {
                                break;
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
        
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                if (rivers[x][y]) {
                    water[x][y] = true;
                }
            }
        }
    }

    public Node dijkstra(int x, int y, double[][] heightmap) {

        //Initialize
        this.visited = new boolean[mapSize][mapSize];
        this.distance = new double[mapSize][mapSize];
        this.priorityQ = new PriorityQueue<>();
        for (int row = 0; row < mapSize; row++) {
            for (int column = 0; column < mapSize; column++) {
                this.distance[row][column] = Integer.MAX_VALUE;
            }
        }

        //Set start
        Node start = new Node(x, y, 0);
        start.setParent(start);
        priorityQ.add(start);
        distance[x][y] = 0;
        Node place = start;

        //Find route
        while (!priorityQ.isEmpty()) {

            //Get place
            place = priorityQ.poll();

            //If place not yet visited, update distances
            if (!visited[place.getX()][place.getY()]) {

                //set place to visited
                visited[place.getX()][place.getY()] = true;

                //Go trough neighbors
                for (int xCoord = place.getX() - 2; xCoord <= place.getX() + 2; xCoord = xCoord + 2) {
                    for (int yCoord = place.getY() - 2; yCoord <= place.getY() + 2; yCoord = yCoord + 2) {

                        //Only update neighbors that are in map
                        if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize) {

                            //get current distance of the neighbor
                            double currentNeighborDistance = distance[xCoord][yCoord];

                            //calculate distance of the neighbor if going trough current place
                            double heightDifferenceToNeighbor = heightmap[xCoord][yCoord]
                                    - heightmap[place.getX()][place.getY()];
                            double costFromPlaceToNeighbor;
                            if (heightDifferenceToNeighbor < 0) {
                                costFromPlaceToNeighbor = Math.max(1, -1 * heightDifferenceToNeighbor);
                            } else if (heightDifferenceToNeighbor > 0) {
                                costFromPlaceToNeighbor = 0;
                            } else {
                                costFromPlaceToNeighbor = 1;
                            }
                            double newNeighborDistance = place.getDistance() + costFromPlaceToNeighbor;

                            //if distance smaller, set current place as parent and add neighbor to priorityqueue
                            if (newNeighborDistance < currentNeighborDistance) {
                                distance[xCoord][yCoord] = newNeighborDistance;
                                Node neighbor = new Node(xCoord, yCoord, newNeighborDistance);
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
