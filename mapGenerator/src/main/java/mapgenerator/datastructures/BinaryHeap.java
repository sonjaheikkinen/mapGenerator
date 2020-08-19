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
public class BinaryHeap {

    private Node[] heap;
    private int nodesInHeap;

    //Juuri eli pienin tai suurin alkio on aina kohdassa 1.
    //Jos solmu on kohdassa k, niin solmun vasen lapsi on kohdassa 2k, solmun oikea lapsi on kohdassa
    //2k + 1 ja solmun vanhempi on kohdassa k/2.
    public BinaryHeap() {
        this.heap = new Node[9];
        this.nodesInHeap = 0;
    }

    public void add(Node node) {
        if (nodesInHeap == heap.length - 1) {
            increaseHeapSize();
        }
        int nodeIndex = nodesInHeap + 1;
        heap[nodeIndex] = node;
        nodesInHeap++;
        while (true) {
            if (nodeIndex == 1) {
                break;
            }
            if (heap[nodeIndex / 2].compareTo(node) == 1) {
                switchPlaces(nodeIndex / 2, nodeIndex);
            } else {
                break;
            }
            nodeIndex = nodeIndex / 2;
        }
    }

    public Node peek() {
        return heap[1];
    }

    public Node poll() {
        Node nodeToReturn = heap[1];
        Node lastNode = heap[nodesInHeap];
        heap[1] = lastNode;
        int nodeIndex = 1;
        while (true) {
            moveNodeInHeap(nodeIndex);
            nodeIndex = nodeIndex * 2;
            if (nodeIndex >= nodesInHeap) {
                break;
            }
        }
        if (nodesInHeap <= heap.length / 4) {
            decreaseHeapSize();
        }
        nodesInHeap--;
        return nodeToReturn;
    }

    public void moveNodeInHeap(int nodeIndex) {
        Node child1 = heap[nodeIndex * 2];
        Node child2 = null;
        if (nodeIndex * 2 + 1 <= nodesInHeap) {
            child2 = heap[nodeIndex * 2 + 1];
        }
        if (child2 != null && child1.compareTo(child2) == 1) {
            switchPlaces(nodeIndex, nodeIndex * 2 + 1);
        } else {
            switchPlaces(nodeIndex, nodeIndex * 2);
        }
    }

    public void switchPlaces(int index1, int index2) {
        Node node1 = heap[index1];
        Node node2 = heap[index2];
        heap[index1] = node2;
        heap[index2] = node1;
    }

    public void increaseHeapSize() {
        Node[] newHeap = new Node[2 * heap.length];
        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    public void decreaseHeapSize() {
        Node[] newHeap = new Node[heap.length / 2];
        for (int i = 0; i < newHeap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    public boolean isEmpty() {
        return nodesInHeap == 0;
    }

}
