// Class representing a node in a Red-Black Tree for a library management system
public class RedBlackNode {
    int bookId; // Unique identifier for the book
    String bookName; // Title of the book
    String authorName; // Author of the book
    boolean isAvailable; // Flag indicating whether the book is available for borrowing
    int borrowedBy; // ID of the patron who borrowed the book (-1 if not borrowed)
    RedBlackNode left, right, parent; // References to left child, right child, and parent nodes
    NodeColor color; // Color of the node in the Red-Black Tree (RED or BLACK)
    MinHeap minHeap; // MinHeap to manage reservations for the book

    // Default constructor
    public RedBlackNode() {
    }

    // Parameterized constructor to initialize a Red-Black Node with book details
    public RedBlackNode(int bookId, String bookName, String authorName, boolean isAvailable) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.left = EmptyRBNode.nil; // Initialize left child as nil
        this.right = EmptyRBNode.nil; // Initialize right child as nil
        this.parent = EmptyRBNode.nil; // Initialize parent as nil
        this.color = NodeColor.BLACK; // New nodes are initially colored black
        this.isAvailable = isAvailable;
        this.borrowedBy = -1; // Initialize borrowedBy as -1 (indicating not borrowed)
        minHeap = new MinHeap(20); // Initialize MinHeap for reservations with a capacity of 20
    }

    // Constructor to create a special node with only a bookId and default color
    public RedBlackNode(int bookId) {
        this.bookId = bookId;
        this.color = NodeColor.BLACK; // New nodes are initially colored black
    }

    // Override toString method to provide a formatted string representation of the node
    @Override
    public String toString() {
        return "BookID = " + bookId +
                "\nTitle = \"" + bookName +
                "\"\nAuthor = \"" + authorName +
                "\"\nAvailability = \"" + (isAvailable ? "Yes" : "No") +
                "\"\nBorrowedBy = " + (borrowedBy == -1 ? "None" : borrowedBy) +
                "\nReservations = [" + minHeap + "]\n";
    }
}
