package mapgenerator.datastructures;

/**
 * Structure is used to store nodes and to easily return the node with smallest
 * weight. Binary heap has one root node, and every node has up to two child
 * nodes. Nodes are added, removed and moved so that a parent's weight is always
 * the same or less than that of its children's, so that the node at the root
 * always has the smallest weight. This structure is saved in an array so that
 * the root node is in index zero, and for every node in index k: the parent of
 * the node is in index k / 2, and the children are found in indexes 2k and 2k +
 * 1.
 */
public class BinaryHeap {

    private Node[] heap;
    private int nodesInHeap;

    /**
     * Constructor creates the initial heap array and sets amount of nodes in
     * heap to zero.
     */
    public BinaryHeap() {
        this.heap = new Node[9];
        this.nodesInHeap = 0;
    }

    /**
     * Method adds a new node to heap. If there is no space to add new nodes,
     * heap size is first increased. The new node is then added at the end of
     * the array, and continually compared to and switched with its parent until
     * a parent with a smaller weight is found.
     *
     * @param node Node to be added to the heap.
     */
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

    /**
     * Method returns, but does not remove the root node of the heap.
     *
     * @return The root node of the heap.
     */
    public Node peek() {
        return heap[1];
    }

    /**
     * Method returns and removes the root node of the heap. When the root of
     * the heap is stored in another variable, the node at the bottom of the
     * heap is put to the root. This new root node is then compared to and
     * switched with its children until a child with bigger weight is found. If
     * there is too much empty space in the heap array after removing, the heap
     * size is decreased.
     *
     * @return The root node of the heap.
     */
    public Node poll() {
        Node nodeToReturn = heap[1];
        Node lastNode = heap[nodesInHeap];
        heap[nodesInHeap] = null;
        heap[1] = lastNode;
        nodesInHeap--;
        int nodeIndex = 1;
        while (true) {
            if (nodeIndex * 2 > nodesInHeap || nodeIndex == 0) {
                break;
            }
            nodeIndex = moveNodeInHeap(nodeIndex);
        }
        if (nodesInHeap <= heap.length / 4) {
            decreaseHeapSize();
        }
        return nodeToReturn;
    }

    /**
     * Method compares the node in given index with its children and switches
     * their places if the child has smaller weight than the parent. The child
     * with smaller weight is always chosen for switching.
     *
     * @param nodeIndex Index of the node to be compared.
     */
    public int moveNodeInHeap(int nodeIndex) {
        Node child1 = heap[nodeIndex * 2];
        Node child2 = getChild2IfExists(nodeIndex);
        if (child2 != null) {
            if (child1.compareTo(child2) == 1 && heap[nodeIndex].compareTo(child2) == 1) {
                switchPlaces(nodeIndex, nodeIndex * 2 + 1);
                return nodeIndex * 2 + 1;
            } else if (heap[nodeIndex].compareTo(child1) == 1) {
                switchPlaces(nodeIndex, nodeIndex * 2);
                return nodeIndex * 2;
            } else {
                return 0;
            }
        } else if (heap[nodeIndex].compareTo(child1) == 1) {
            switchPlaces(nodeIndex, nodeIndex * 2);
            return nodeIndex * 2;
        } else {
            return 0;
        }
    }

    public Node getChild2IfExists(int nodeIndex) {
        Node child2 = null;
        if (nodeIndex * 2 + 1 <= nodesInHeap) {
            child2 = heap[nodeIndex * 2 + 1];
        }
        return child2;
    }

    /**
     * Switch places of two nodes in the heap. Node one is switched to the index
     * of node two and vice versa.
     *
     * @param index1 Index of node one.
     * @param index2 Index of node two.
     */
    public void switchPlaces(int index1, int index2) {
        Node node1 = heap[index1];
        Node node2 = heap[index2];
        heap[index1] = node2;
        heap[index2] = node1;
    }

    /**
     * Make a new heap that is twice as big as the current heap. Copy every
     * value from old heap to new heap.
     */
    public void increaseHeapSize() {
        Node[] newHeap = new Node[2 * heap.length];
        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    /**
     * Make a new heap that is half the size of the current heap. Copy every
     * value from old heap to new heap.
     */
    public void decreaseHeapSize() {
        Node[] newHeap = new Node[heap.length / 2];
        for (int i = 0; i < newHeap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    /**
     * Return true if there are no nodes in the heap.
     *
     * @return True or false.
     */
    public boolean isEmpty() {
        return nodesInHeap == 0;
    }

    public void clear() {
        Node[] newHeap = new Node[9];
        heap = newHeap;
        nodesInHeap = 0;
    }

    public Node[] getHeap() {
        return heap;
    }

    public int getNodesInHeap() {
        return nodesInHeap;
    }

}
