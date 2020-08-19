/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.datastructures;

/**
 *
 * @author heisonja
 */
public class Node implements Comparable<Node> {
    
    private int x;
    private int y;
    private double weight;
   
    public Node(int x, int y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public double getWeight() {
        return weight;
    }
    
    @Override
    public int compareTo(Node node) {
        if (this.weight > node.getWeight()) {
            return 1;
        } else if (this.weight < node.getWeight()) {
            return -1;
        } else {
            return 0;
        }
    }
    
}
