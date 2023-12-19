import java.io.*;

public class GatorLibrary {

    // Constants for delimiters
    private static final String COMMA = ",";
    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSED_PARENTHESIS = ")";

    public static void main(String[] args) {
        try {
            // Retrieve file name from command line arguments
            String fileName = args[0];

            // Open the file for reading
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));

            // Initialize a RedBlackTree to manage library operations
            RedBlackTree rbTree = new RedBlackTree();
            String row;

            // Read each line from the input file and process it
            while ((row = bufferedReader.readLine()) != null) {
                parse(rbTree, row, fileName.substring(0, fileName.indexOf(".")));
            }

            // Create and write the output file
            createOutput(fileName);

        } catch (Exception e) {
            // Print any exceptions that occur during execution
            e.printStackTrace();
        }
    }

    // Parse the input row and perform corresponding operation on the RedBlackTree
    private static void parse(RedBlackTree rbTree, String row, String fileName) throws IOException {
        // Remove double quotes from the row
        row = row.replaceAll("\"", "");

        // Extract operation and arguments from the row
        int start = row.indexOf(OPEN_PARENTHESIS);
        int end = row.indexOf(CLOSED_PARENTHESIS);
        String[] argArray = row.substring(start + 1, end).split(COMMA);
        String operation = row.substring(0, start);

        // Perform the specified operation based on the parsed input
        if (operation.equals("InsertBook")) {
            // Insert a book into the RedBlackTree
            rbTree.insertBook(Integer.parseInt(argArray[0].trim()), argArray[1].trim(), argArray[2].trim(),
                    argArray[3].trim());
        } else if (operation.equals("PrintBook")) {
            // Print information about a specific book
            RedBlackNode book = rbTree.printBook(Integer.parseInt(argArray[0].trim()));
            if (book == null) {
                RedBlackTree.resultString
                        .append("Book " + Integer.parseInt(argArray[0].trim()) + " not found in the library\n");
            } else {
                RedBlackTree.resultString.append(book + "\n");
            }
        } else if (operation.equals("PrintBooks")) {
            // Print information about a range of books
            rbTree.printBooks(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()));
        } else if (operation.equals("BorrowBook")) {
            // Borrow a book from the library
            rbTree.borrowBook(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()),
                    Integer.parseInt(argArray[2].trim()));
        } else if (operation.equals("ReturnBook")) {
            // Return a borrowed book to the library
            rbTree.returnBook(Integer.parseInt(argArray[0].trim()), Integer.parseInt(argArray[1].trim()));
        } else if (operation.equals("DeleteBook")) {
            // Delete a book from the library
            rbTree.deleteBook(Integer.parseInt(argArray[0].trim()));
        } else if (operation.equals("FindClosestBook")) {
            // Find the closest book in the library
            rbTree.findClosestBook(Integer.parseInt(argArray[0].trim()));
        } else if (operation.equals("ColorFlipCount")) {
            // Get the count of color flips in the RedBlackTree
            rbTree.getColorFlipCount();
        } else if (operation.equals("Quit")) {
            // Quit the program, create output file, and exit
            rbTree.quit();
            createOutput(fileName);
            System.exit(0);
        } else {
            // Handle invalid GatorLibrary operation
            RedBlackTree.resultString.append("Invalid GatorLibrary operation\n");
        }
    }

    // Create an output file and write the resultString to it
    private static void createOutput(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName + "_output_file.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(RedBlackTree.resultString));
        bufferedWriter.close();
        fileWriter.close();
    }
}
