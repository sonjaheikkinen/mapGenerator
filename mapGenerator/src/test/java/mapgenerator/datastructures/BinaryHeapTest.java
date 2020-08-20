/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapgenerator.datastructures;

import java.util.PriorityQueue;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author heisonja
 */
public class BinaryHeapTest {

    private BinaryHeap heap;
    private Random random;

    public BinaryHeapTest() {
    }

    @Before
    public void setUp() {
        heap = new BinaryHeap();
        random = new Random();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void nodesInHeapIsInitiallyZero() {
        assertTrue(heap.getNodesInHeap() == 0);
    }

    @Test
    public void afterAddingOneNodeTheNodeCountIsOne() {
        heap.add(new Node(0, 0, 0));
        assertTrue(heap.getNodesInHeap() == 1);
    }

    @Test
    public void nodesInHeapCalculatorWorksCorrectly() {
        boolean correctAmountOfNodesInHeap = true;
        for (int i = 0; i < 100; i++) {
            int randomValue = random.nextInt(50);
            for (int j = 0; j < randomValue; j++) {
                heap.add(new Node(0, 0, j));
            }
            if (heap.getNodesInHeap() != randomValue) {
                correctAmountOfNodesInHeap = false;
            }
            heap.clear();
        }
        assertTrue(correctAmountOfNodesInHeap);
    }

    @Test
    public void addingNodesActuallyAddsAllNodesToHeap() {
        boolean correctAmountOfNodesInHeap = true;
        for (int i = 0; i < 100; i++) {
            int randomValue = random.nextInt(50);
            for (int j = 0; j < randomValue; j++) {
                heap.add(new Node(0, 0, 0));
            }
            if (calculateNodes(heap.getHeap()) != randomValue) {
                correctAmountOfNodesInHeap = false;
            }
            heap.clear();
        }
        assertTrue(correctAmountOfNodesInHeap);
    }

    @Test
    public void afterClearingHeapEveryEntryInTheHeapIsNull() {
        for (int i = 0; i < 5; i++) {
            heap.add(new Node(0, 0, 0));
        }
        heap.clear();
        assertTrue(calculateNodes(heap.getHeap()) == 0);
    }

    @Test
    public void afterClearingHeapNodeCountIsZero() {
        for (int i = 0; i < 5; i++) {
            heap.add(new Node(0, 0, 0));
        }
        heap.clear();
        assertTrue(heap.getNodesInHeap() == 0);
    }

    @Test
    public void peekAlwaysReturnsTheSmallestNode() {
        boolean smallestAlwaysOnTop = true;
        for (int i = 0; i < 100; i++) {
            PriorityQueue<Integer> randomNumbers = new PriorityQueue();
            for (int j = 0; j < 20; j++) {
                int randomNumber = random.nextInt(100);
                randomNumbers.add(randomNumber);
                heap.add(new Node(0, 0, randomNumber));
            }
            if (heap.peek().getCost() != randomNumbers.peek()) {
                smallestAlwaysOnTop = false;
            }
            heap.clear();
        }
        assertTrue(smallestAlwaysOnTop);
    }

    @Test
    public void pollAlwaysReturnsTheSmallestNode() {
        boolean smallestAlwaysOnTop = true;
        for (int i = 0; i < 100; i++) {
            PriorityQueue<Integer> randomNumbers = new PriorityQueue();
            for (int j = 0; j < 20; j++) {
                int randomNumber = random.nextInt(100);
                randomNumbers.add(randomNumber);
                heap.add(new Node(0, 0, randomNumber));
            }
            if (heap.poll().getCost() != randomNumbers.poll()) {
                smallestAlwaysOnTop = false;
            }
            heap.clear();
        }
        assertTrue(smallestAlwaysOnTop);
    }

    @Test
    public void afterPeekNodeCountStaysTheSame() {
        for (int i = 0; i < 10; i++) {
            heap.add(new Node(0, 0, 0));
        }
        heap.peek();
        assertTrue(calculateNodes(heap.getHeap()) == 10);
    }

    @Test
    public void afterPollNodeCountDecreases() {
        for (int i = 0; i < 10; i++) {
            heap.add(new Node(0, 0, 0));
        }
        heap.poll();
        assertTrue(calculateNodes(heap.getHeap()) == 9);
    }

    @Test
    public void nodesInHeapDecreasesCorrectlyWhenPollingNodes() {
        boolean correctNodeAmount = true;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 50; j++) {
                heap.add(new Node(0, 0, 0));
            }
            int randomNumber = random.nextInt(50);
            for (int k = 0; k < randomNumber; k++) {
                heap.poll();
            }
            if (heap.getNodesInHeap() != (50 - randomNumber)) {
                correctNodeAmount = false;
            }
            heap.clear();
        }
        assertTrue(correctNodeAmount);
    }

    @Test
    public void nodeCountDecreasesCorrectlyWhenPollingNodes() {
        boolean correctNodeAmount = true;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 50; j++) {
                heap.add(new Node(0, 0, 0));
            }
            int randomNumber = random.nextInt(50);
            for (int k = 0; k < randomNumber; k++) {
                heap.poll();
            }
            if (calculateNodes(heap.getHeap()) != (50 - randomNumber)) {
                correctNodeAmount = false;
            }
            heap.clear();
        }
        assertTrue(correctNodeAmount);
    }
    
    @Test
    public void whenPollingMultipleTimesTheSmallestNodeIsAlwaysOnTop() {
        boolean smallestOnTop = true;
        PriorityQueue<Integer> randomNumbers = new PriorityQueue<>();
        for (int i = 0; i < 50; i++) {
            int randomNumber = random.nextInt(100);
            randomNumbers.add(randomNumber);
            heap.add(new Node(0, 0, randomNumber));
        }
        for (int j = 0; j < 50; j++) {
            if (heap.poll().getCost() != randomNumbers.poll()) {
                smallestOnTop = false;
            }
        }
        assertTrue(smallestOnTop);
    }

    public int calculateNodes(Node[] heap) {
        int nodeCount = 0;
        for (int i = 0; i < heap.length; i++) {
            if (heap[i] != null) {
                nodeCount++;
            }
        }
        return nodeCount;
    }

}
