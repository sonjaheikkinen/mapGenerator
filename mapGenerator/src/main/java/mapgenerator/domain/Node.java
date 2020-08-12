/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.domain;

/**
 *
 * @author heisonja
 */
public class Node implements Comparable<Node> {
    
    private int x;
    private int y;
    private Node parent;
    private double distance;
   
    public Node(int x, int y, double distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public Node getParent() {
        return parent;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public String toString() {
        return "(x=" + x + ", y=" + y + ")";
    }
    
    @Override
    public int compareTo(Node node) {
        if (this.distance > node.getDistance()) {
            return 1;
        } else if (this.distance < node.getDistance()) {
            return -1;
        } else {
            return 0;
        }
    }
    
}
