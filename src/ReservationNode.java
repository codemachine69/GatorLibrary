// Class representing a node in the MinHeap used for book reservations
public class ReservationNode implements Comparable<ReservationNode> {
    private int patronId; // Identifier of the patron making the reservation
    private int priorityNumber; // Priority number assigned to the reservation
    private long timeOfReservation; // Timestamp indicating when the reservation was made

    // Parameterized constructor to initialize a ReservationNode with reservation details
    public ReservationNode(int patronId, int priorityNumber, long timeOfReservation) {
        this.patronId = patronId;
        this.priorityNumber = priorityNumber;
        this.timeOfReservation = timeOfReservation;
    }

    // Default constructor (empty constructor)
    public ReservationNode() {
    }

    // Getter method to retrieve the patron ID
    public int getPatronId() {
        return patronId;
    }

    // Getter method to retrieve the priority number
    public int getPriorityNumber() {
        return priorityNumber;
    }

    // Getter method to retrieve the timestamp of the reservation
    public long getTimeOfReservation() {
        return timeOfReservation;
    }

    // Override toString method to provide a formatted string representation of the
    // node
    @Override
    public String toString() {
        return "(" + patronId + ", " + priorityNumber + ", " + timeOfReservation + ")";
    }

    // Override compareTo method to define the natural ordering of ReservationNodes
    @Override
    public int compareTo(ReservationNode o) {
        // Compare based on priority number
        if (this.priorityNumber != o.priorityNumber) {
            return this.priorityNumber - o.priorityNumber;
        } else {
            // If priority numbers are equal, compare based on reservation timestamp
            if (this.timeOfReservation > o.timeOfReservation) {
                return 1;
            } else if (this.timeOfReservation < o.timeOfReservation) {
                return -1;
            } else {
                return 0; // Timestamps are equal
            }
        }
    }
}
