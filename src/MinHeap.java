import java.util.ArrayList;
import java.util.List;

// MinHeap class representing a binary min-heap for managing reservations
public class MinHeap {
    private final int capacity; // Maximum capacity of the heap
    private int size; // Current number of elements in the heap
    public ReservationNode[] heap; // Array to store the heap elements

    // Constructor to initialize a MinHeap with a specified capacity
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new ReservationNode[capacity];
        this.size = 0;
    }

    // Check if the heap is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the current size of the heap
    public int size() {
        return size;
    }

    // Get the index of the left child of a parent at a given index
    public int getLeftChildIdx(int parentIdx) {
        return 2 * parentIdx + 1;
    }

    // Get the index of the right child of a parent at a given index
    public int getRightChildIdx(int parentIdx) {
        return 2 * parentIdx + 2;
    }

    // Get the index of the parent of a node at a given index
    public int getParentIdx(int childIdx) {
        return (childIdx - 1) / 2;
    }

    // Get the left child of a parent at a given index
    public ReservationNode leftChild(int parentIdx) {
        return heap[getLeftChildIdx(parentIdx)];
    }

    // Get the right child of a parent at a given index
    public ReservationNode rightChild(int parentIdx) {
        return heap[getRightChildIdx(parentIdx)];
    }

    // Get the parent of a node at a given index
    public ReservationNode parent(int childIdx) {
        return heap[getParentIdx(childIdx)];
    }

    // Peek at the minimum (root) element of the heap without removing it
    public ReservationNode peek() {
        if (size == 0) {
            System.out.println("MinHeap empty, invalid peek()");
            return null;
        }
        return heap[0];
    }

    // Remove and return the minimum (root) element of the heap
    public ReservationNode poll() {
        if (size == 0) {
            System.out.println("MinHeap empty, invalid poll()");
            return null;
        }
        ReservationNode minNode = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        return minNode;
    }

    // Insert a new reservation node into the heap
    public void insertNode(ReservationNode reservation) {
        if (size == capacity) {
            System.out.println("The Reservation Heap is full and cannot accept any more reservations");
        } else {
            heap[size] = reservation;
            size++;
            heapifyUp();
        }
    }

    // Restore the heap property by moving a newly added node up to its correct position
    public void heapifyUp() {
        int idx = size - 1;
        while (getParentIdx(idx) >= 0 && heap[idx].compareTo(parent(idx)) < 0) {
            swap(getParentIdx(idx), idx);
            idx = getParentIdx(idx);
        }
    }

    // Restore the heap property by moving a node down to its correct position
    public void heapifyDown() {
        int idx = 0;
        while (getLeftChildIdx(idx) < size) {
            int smallestChild = getLeftChildIdx(idx);

            if (getRightChildIdx(idx) < size && rightChild(idx).compareTo(leftChild(idx)) < 0) {
                smallestChild = getRightChildIdx(idx);
            }

            if (heap[idx].compareTo(heap[smallestChild]) < 0) {
                break;
            } else {
                swap(idx, smallestChild);
            }
            idx = smallestChild;
        }
    }

    // Swap two elements in the heap
    public void swap(int x, int y) {
        ReservationNode temp = heap[x];
        heap[x] = heap[y];
        heap[y] = temp;
    }

    // Print the elements of the heap
    public void printHeap() {
        if (size == 0) {
            System.out.println("MinHeap is empty, nothing to print");
            return;
        }
        System.out.print("{ ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println(" }");
    }

    // Override toString method to provide a formatted string representation of the heap
    @Override
    public String toString() {
        String res = "";
        List<ReservationNode> tempList = new ArrayList<>();
        while (this.size > 1) {
            ReservationNode heapNode = poll();
            tempList.add(heapNode);
            res += heapNode.getPatronId() + ",";
        }
        if (!isEmpty()) {
            ReservationNode heapNode = poll();
            tempList.add(heapNode);
            res += heapNode.getPatronId();
        }
        for (ReservationNode heapNode : tempList)
            insertNode(heapNode);
        return res;
    }
}
