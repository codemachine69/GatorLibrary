import java.util.*;

// Implementation of a Red-Black Tree for managing a library system
class RedBlackTree {

    // Maps to store the color information of nodes before and after an operation
    public static Map<Integer, NodeColor> hm1 = new HashMap<>();
    public static Map<Integer, NodeColor> hm2 = new HashMap<>();

    // Null node constant
    private final RedBlackNode nil = EmptyRBNode.nil;

    // Counter for color flip operations
    public int flipCount;

    // Root of the Red-Black Tree
    private RedBlackNode root;

    // StringBuilder to accumulate results during library operations
    public static StringBuilder resultString = new StringBuilder();

    // Constructor initializes an empty Red-Black Tree
    public RedBlackTree() {
        this.root = nil;
        this.flipCount = 0;
    }

    // Insert a book into the Red-Black Tree and update color information
    public void insertBook(int bookId, String bookName, String authorName, String isAvailable) {

        // Create a new Red-Black Node representing the book
        RedBlackNode book = new RedBlackNode(bookId, bookName, authorName, isAvailable.equals("Yes"));

        // Transfer color information from the previous map to the current map
        transferMap();

        // Update color information for the newly inserted book
        if (root == nil) {
            hm1.put(bookId, NodeColor.BLACK);
        } else {
            hm1.put(bookId, NodeColor.RED);
        }

        // Perform the book insertion operation
        insert(book);

        // Populate the latest color information map
        populateLatestMap();

        // Calculate and update the color flip count
        colorFlipCount();
    }

    // Count the number of color flips that occurred during library operations
    private void colorFlipCount() {
        for (Map.Entry<Integer, NodeColor> entry : hm1.entrySet()) {
            if (entry.getValue() != hm2.get(entry.getKey())) {
                this.flipCount++;
            }
        }
    }

    // Get and append the color flip count to the resultString
    public void getColorFlipCount() {
        resultString.append("Color Flip Count : " + this.flipCount + "\n");
    }

    // Populate the latest color information map through an inorder traversal of the
    // Red-Black Tree
    private void populateLatestMap() {
        inorderTraversal(root);
    }

    // Inorder traversal of the Red-Black Tree to update color information map
    private void inorderTraversal(RedBlackNode root) {
        if (root == nil) {
            return;
        }
        inorderTraversal(root.left);
        hm2.put(root.bookId, root.color);
        inorderTraversal(root.right);
    }

    // Transfer color information from the previous map to the current map
    private void transferMap() {
        hm1.clear();
        hm1 = new HashMap<>(hm2);
        hm2.clear();
    }

    // Insert a Red-Black Node into the Red-Black Tree and fix any violations
    private void insert(RedBlackNode book) {

        RedBlackNode tempRoot = root;
        if (root == nil) {
            root = book;
            book.color = NodeColor.BLACK;
            book.parent = nil;
        } else {
            book.color = NodeColor.RED;
            while (true) {
                if (book.bookId < tempRoot.bookId) {
                    if (tempRoot.left == nil) {
                        tempRoot.left = book;
                        book.parent = tempRoot;
                        break;
                    } else {
                        tempRoot = tempRoot.left;
                    }
                } else if (book.bookId == tempRoot.bookId) {
                    return;
                } else {
                    if (tempRoot.right == nil) {
                        tempRoot.right = book;
                        book.parent = tempRoot;
                        break;
                    } else {
                        tempRoot = tempRoot.right;
                    }
                }
            }
            fixInsertViolation(book);
        }
    }

    // Fix violations after inserting a Red-Black Node
    private void fixInsertViolation(RedBlackNode book) {
        while (book.parent.color == NodeColor.RED) {
            RedBlackNode uncle = nil;
            if (book.parent == book.parent.parent.left) {
                uncle = book.parent.parent.right;

                if (uncle != nil && uncle.color == NodeColor.RED) {
                    book.parent.color = NodeColor.BLACK;
                    if (book.parent.parent.color != NodeColor.RED && book.parent.parent != root) {
                        book.parent.parent.color = NodeColor.RED;
                    }
                    uncle.color = NodeColor.BLACK;
                    book = book.parent.parent;
                    continue;
                }
                if (book == book.parent.right) {
                    book = book.parent;
                    rotateLeft(book);
                }
                book.parent.color = NodeColor.BLACK;
                book.parent.parent.color = NodeColor.RED;
                rotateRight(book.parent.parent);
            } else {
                uncle = book.parent.parent.left;
                if (uncle != nil && uncle.color == NodeColor.RED) {
                    book.parent.color = NodeColor.BLACK;
                    book.parent.parent.color = NodeColor.RED;
                    uncle.color = NodeColor.BLACK;
                    book = book.parent.parent;
                    continue;
                }
                if (book == book.parent.left) {
                    book = book.parent;
                    rotateRight(book);
                }
                book.parent.color = NodeColor.BLACK;
                book.parent.parent.color = NodeColor.RED;
                rotateLeft(book.parent.parent);
            }
        }
        root.color = NodeColor.BLACK;
    }

    // Perform a left rotation operation on the Red-Black Tree
    private void rotateLeft(RedBlackNode book) {
        if (book.parent != nil) {
            if (book == book.parent.left) {
                book.parent.left = book.right;
            } else {
                book.parent.right = book.right;
            }
            book.right.parent = book.parent;
            book.parent = book.right;
            if (book.right.left != nil) {
                book.right.left.parent = book;
            }
            book.right = book.right.left;
            book.parent.left = book;
        } else {
            RedBlackNode right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    // Perform a right rotation operation on the Red-Black Tree
    private void rotateRight(RedBlackNode book) {
        if (book.parent != nil) {
            if (book == book.parent.left) {
                book.parent.left = book.left;
            } else {
                book.parent.right = book.left;
            }

            book.left.parent = book.parent;
            book.parent = book.left;
            if (book.left.right != nil) {
                book.left.right.parent = book;
            }
            book.left = book.left.right;
            book.parent.right = book;
        } else {
            RedBlackNode left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }

    // Print information about a specific book in the Red-Black Tree
    public RedBlackNode printBook(int bookId) {
        RedBlackNode temp = root;
        if (root.bookId == -1)
            return null;
        while (true) {
            if (bookId < temp.bookId) {
                if (temp.left == nil) {
                    return null;
                } else {
                    temp = temp.left;
                }
            } else if (bookId == temp.bookId) {
                return temp;
            } else {
                if (temp.right == nil) {
                    return null;
                } else {
                    temp = temp.right;
                }
            }
        }
    }

    // Helper for Deleting a book from the Red-Black Tree and update color
    // information
    public void deleteBook(int bookId) {
        RedBlackNode book = printBook(bookId);
        if (book == null) {
            resultString.append("Book " + bookId + " is no longer available.\n");
            return;
        }
        transferMap();
        hm1.remove(bookId);
        delete(book);
        populateLatestMap();
        colorFlipCount();
        if (book.minHeap.isEmpty()) {
            resultString.append("Book " + bookId + " is no longer available.\n");
        } else {
            resultString.append("Book " + bookId + " is no longer available. Reservations made by Patrons "
                    + book.minHeap + " have been cancelled!\n");
        }
    }

    // Delete Book from Red-Black Tree
    private boolean delete(RedBlackNode z) {
        RedBlackNode y = z;
        NodeColor y_original_color = y.color;
        RedBlackNode x;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = treeMaximum(z.left);
            y_original_color = y.color;
            x = y.left;
            if (y.parent == z)
                x.parent = y;
            else {
                transplant(y, y.left);
                y.left = z.left;
                y.left.parent = y;
            }
            transplant(z, y);
            y.right = z.right;
            y.right.parent = y;
            y.color = z.color;
        }
        if (y_original_color == NodeColor.BLACK) {
            fixDeleteViolation(x);
        }
        return true;
    }

    // Perform transplant operation in the Red-Black Tree
    private void transplant(RedBlackNode x, RedBlackNode y) {
        if (x.parent == nil) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else
            x.parent.right = y;
        y.parent = x.parent;
    }

    // Find the node with the maximum value in the heap
    private RedBlackNode treeMaximum(RedBlackNode z) {
        while (z.right != nil) {
            z = z.right;
        }
        return z;
    }

    // Fix violations after deleting a Red-Black Node
    private void fixDeleteViolation(RedBlackNode x) {
        while (x != root && x.color == NodeColor.BLACK) {
            if (x == x.parent.left) {
                RedBlackNode w = x.parent.right;
                if (w.color == NodeColor.RED) {
                    w.color = NodeColor.BLACK;
                    x.parent.color = NodeColor.RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == NodeColor.BLACK && w.right.color == NodeColor.BLACK) {
                    w.color = NodeColor.RED;
                    x = x.parent;
                    continue;
                } else if (w.right.color == NodeColor.BLACK) {
                    w.left.color = NodeColor.BLACK;
                    w.color = NodeColor.RED;
                    rotateRight(w);
                    w = x.parent.right;
                }
                if (w.right.color == NodeColor.RED) {
                    w.color = x.parent.color;
                    x.parent.color = NodeColor.BLACK;
                    w.right.color = NodeColor.BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                RedBlackNode w = x.parent.left;
                if (w.color == NodeColor.RED) {
                    w.color = NodeColor.BLACK;
                    x.parent.color = NodeColor.RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == NodeColor.BLACK && w.left.color == NodeColor.BLACK) {
                    w.color = NodeColor.RED;
                    x = x.parent;
                    continue;
                } else if (w.left.color == NodeColor.BLACK) {
                    w.right.color = NodeColor.BLACK;
                    w.color = NodeColor.RED;
                    rotateLeft(w);
                    w = x.parent.left;
                }
                if (w.left.color == NodeColor.RED) {
                    w.color = x.parent.color;
                    x.parent.color = NodeColor.BLACK;
                    w.left.color = NodeColor.BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = NodeColor.BLACK;
    }

    // Print information about books within a specified range of book IDs
    public void printBooks(int bookId1, int bookId2) {
        List<RedBlackNode> listOfBooks = new ArrayList<>();
        inorder(this.root, bookId1, bookId2, listOfBooks, true);
        for (RedBlackNode book : listOfBooks)
            resultString.append(book + "\n");
    }

    // Find and print the closest book to a target book ID
    public void findClosestBook(int targetId) {
        int minDiff = Integer.MAX_VALUE;
        List<RedBlackNode> listOfBooks = new ArrayList<>();
        inorder(this.root, -1, -1, listOfBooks, false);
        List<RedBlackNode> res = new ArrayList<>();
        for (RedBlackNode book : listOfBooks) {
            int diff = Math.abs(targetId - book.bookId);
            if (minDiff > diff) {
                minDiff = diff;
                res = new ArrayList<>();
                res.add(book);
            } else if (minDiff == diff)
                res.add(book);
        }
        Collections.sort(res, (x, y) -> {
            return x.bookId - y.bookId;
        });
        for (RedBlackNode book : res)
            resultString.append(book + "\n");
    }

    // Inorder traversal of the Red-Black Tree to collect books within a range or
    // all books
    private void inorder(RedBlackNode book, int lower, int upper, List<RedBlackNode> listOfBooks, boolean flag) {
        if (book == nil)
            return;
        inorder(book.left, lower, upper, listOfBooks, flag);
        if (flag) {
            if (book.bookId >= lower && book.bookId <= upper)
                listOfBooks.add(book);
        } else {
            listOfBooks.add(book);
        }
        inorder(book.right, lower, upper, listOfBooks, flag);
    }

    // Borrow a book from the library or reserve it for a patron
    public void borrowBook(int patronId, int bookId, int patronPriority) {
        RedBlackNode book = printBook(bookId);
        if (book == null)
            return;
        if (book.isAvailable) {
            book.borrowedBy = patronId;
            book.isAvailable = false;
            resultString.append("Book " + bookId + " Borrowed by Patron " + patronId + "\n");
        } else {
            resultString.append("Book " + bookId + " Reserved by Patron " + patronId + "\n");
            book.minHeap.insertNode(new ReservationNode(patronId, patronPriority, System.nanoTime()));
        }
    }

    // Return a borrowed book to the library and handle reservations
    public void returnBook(int patronId, int bookId) {
        RedBlackNode book = printBook(bookId);
        if (book == null)
            return;
        if (book.borrowedBy != patronId)
            return;
        if (book.isAvailable)
            return;
        book.borrowedBy = -1;
        book.isAvailable = true;
        resultString.append("Book " + bookId + " Returned by Patron " + patronId + "\n");
        if (!book.minHeap.isEmpty()) {
            ReservationNode latestReservation = book.minHeap.poll();
            if (latestReservation.getPatronId() == -1)
                return;
            book.borrowedBy = latestReservation.getPatronId();
            book.isAvailable = false;
            resultString.append("Book " + bookId + " Allotted to Patron " + latestReservation.getPatronId() + "\n");
        }
    }

    // Quit the program and append termination message to resultString
    public void quit() {
        resultString.append("Program Terminated!!\n");
        this.root = null;
    }

}