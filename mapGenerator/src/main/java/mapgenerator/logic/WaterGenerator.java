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
        int riverCount = 0;
        startNewRivers(heightmap, "wide");
        setRiversToWater();
        startNewRivers(heightmap, "narrow");
        setRiversToWater();
    }

    public void startNewRivers(double[][] heightmap, String width) {
        for (int startX = 0; startX < heightmap.length; startX++) {
            for (int startY = 0; startY < heightmap.length; startY++) {
                
                int randomNumber = random.nextInt(1000);
                makeRivers(startX, startY, randomNumber, heightmap, width);
            }
        }
    }

    public void makeRivers(int startX, int startY, int randomNumber, double[][] heightmap, String width) {
        int riverProbability = 1;
        if (width.equals("Narrow")) {
            riverProbability = 5;
        }
        if (!water[startX][startY] && randomNumber <= riverProbability) {
            Node riverEnd = dijkstra(startX, startY, heightmap);
            Node currentPlace = riverEnd;
            double riverLength = Math.sqrt(Math.pow(startX - riverEnd.getX(), 2) + Math.pow(startY - riverEnd.getY(), 2));
            while (true) {
                Node parent = currentPlace.getParent();
                int x = currentPlace.getX();
                int y = currentPlace.getY();
                double distanceToStart = Math.sqrt(Math.pow(startX - x, 2) + Math.pow(startY - y, 2));
                if (x >= 0 && x < mapSize && y >= 0 && y < mapSize) {
                    rivers[x][y] = true;
                    if (distanceToStart > (riverLength / 2) && x < mapSize - 1 && y > 0 && width.equals("wide")) {
                        rivers[x][y - 1] = true;
                        rivers[x + 1][y - 1] = true;
                        rivers[x + 1][y] = true;
                    }
                }
                currentPlace = parent;
                if (parent.getX() == startX && parent.getY() == startY) {
                    break;
                }
            }
        }
    }

    public void setRiversToWater() {
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                if (rivers[x][y]) {
                    water[x][y] = true;
                }
            }
        }
    }

    public void fillLake(int x, int y, double waterlevel, double[][] heights) {
        if (heights[x][y] < waterlevel) {

        }
    }

    public Node dijkstra(int x, int y, double[][] heightmap) {
        initArrays();
        String direction = nearestWaterDirection(x, y);
        Node place = setStartingPlace(x, y);
        while (!priorityQ.isEmpty()) {
            place = priorityQ.poll();
            if (!visited[place.getX()][place.getY()]) {
                visited[place.getX()][place.getY()] = true;
                for (int xCoord = place.getX() - 1; xCoord <= place.getX() + 1; xCoord = xCoord + 1) {
                    for (int yCoord = place.getY() - 1; yCoord <= place.getY() + 1; yCoord = yCoord + 1) {
                        if (direction.equals("right") && xCoord < place.getX()
                                || direction.equals("left") && xCoord > place.getX()
                                || direction.equals("up") && yCoord > place.getY()
                                || direction.equals("down") && yCoord < place.getY()) {
                            continue;
                        } else if (xCoord >= 0 && xCoord < mapSize && yCoord >= 0 && yCoord < mapSize) {
                            updateDistances(xCoord, yCoord, heightmap, place);
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

    public Node setStartingPlace(int x, int y) {
        //Set start
        Node start = new Node(x, y, 0);
        start.setParent(start);
        priorityQ.add(start);
        distance[x][y] = 0;
        Node place = start;
        return place;
    }

    public void updateDistances(int xCoord, int yCoord, double[][] heightmap, Node place) {
        //get current distance of the neightbor and the distance if going trough current place
        double currentNeighborDistance = distance[xCoord][yCoord];
        double newNeighborDistance = calculateNewNeighborDistance(heightmap, xCoord, yCoord, place);

        //if distance smaller, set current place as parent and add neighbor to priorityqueue
        if (newNeighborDistance < currentNeighborDistance) {
            distance[xCoord][yCoord] = newNeighborDistance;
            Node neighbor = new Node(xCoord, yCoord, newNeighborDistance);
            neighbor.setParent(place);
            priorityQ.add(neighbor);
        }
    }

    public double calculateNewNeighborDistance(double[][] heightmap, int xCoord, int yCoord, Node place) {
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
        return newNeighborDistance;
    }

    public void initArrays() {
        //Initialize
        this.visited = new boolean[mapSize][mapSize];
        this.distance = new double[mapSize][mapSize];
        this.priorityQ = new PriorityQueue<>();
        for (int row = 0; row < mapSize; row++) {
            for (int column = 0; column < mapSize; column++) {
                this.distance[row][column] = Integer.MAX_VALUE;
            }
        }
    }

    public String nearestWaterDirection(int x, int y) {
        int shortestDistance = Integer.MAX_VALUE;
        int distanceToWaterRight = searchWater(x, y, "Right");
        int distanceToWaterLeft = searchWater(x, y, "Left");
        int distanceToWaterUp = searchWater(x, y, "Up");
        int distanceToWaterDown = searchWater(x, y, "Down");
        String direction = selectSearchingDirection(shortestDistance,
                distanceToWaterRight, distanceToWaterLeft, distanceToWaterUp, distanceToWaterDown);
        return direction;
    }

    public int searchWater(int x, int y, String direction) {
        int searchDistance = 0;
        boolean waterFound = false;
        while (!water[x][y] && x < mapSize - 1 && x > 0 && y < mapSize - 1 && y > 0) {
            searchDistance++;
            if (direction.equals("Right")) {
                x++;
            } else if (direction.equals("Left")) {
                x--;
            } else if (direction.equals("Down")) {
                y++;
            } else if (direction.equals("Up")) {
                y--;
            }
            if (water[x][y]) {
                waterFound = true;
            }
        }
        if (waterFound) {
            return searchDistance;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public String selectSearchingDirection(int shortestDistance,
            int distanceToWaterRight, int distanceToWaterLeft, int distanceToWaterUp, int distanceToWaterDown) {
        String direction = "";
        if (distanceToWaterRight < shortestDistance) {
            direction = "Right";
        }
        if (distanceToWaterLeft < shortestDistance) {
            direction = "Left";
        }
        if (distanceToWaterUp < shortestDistance) {
            direction = "Up";
        }
        if (distanceToWaterUp < shortestDistance) {
            direction = "Up";
        }
        if (distanceToWaterDown < shortestDistance) {
            direction = "Down";
        }
        return direction;
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
