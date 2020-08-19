
package mapgenerator.datastructures;

/**
 * Class is used for river generation to store new directions and their costs. 
 */
public class Node implements Comparable<Node> {
    
    private int x;
    private int y;
    private double cost;
   
    /**
     * Constructor creates a new node object.
     * @param x X coordinate of the direction.
     * @param y Y coordinate of the direction.
     * @param cost Cost of the direction. 
     */
    public Node(int x, int y, double cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }
    
    /**
     * Returns X.
     * @return x
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns Y.
     * @return y
     */
    public int getY() {
        return y;
    }
    
    /**
     * Returns cost.
     * @return cost
     */
    public double getCost() {
        return cost;
    }
    
    /**
     * Method compares directions by their cost. 
     * @param node A node for comparison
     * @return 1 if this node has bigger cost, -1 if this node has smaller cost and 0 if node costs are equal. 
     */
    @Override
    public int compareTo(Node node) {
        if (this.cost > node.getCost()) {
            return 1;
        } else if (this.cost < node.getCost()) {
            return -1;
        } else {
            return 0;
        }
    }
    
}
