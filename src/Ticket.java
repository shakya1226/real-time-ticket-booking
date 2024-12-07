public class Ticket {
    private final int id; // Unique ID for each ticket

    // Constructor to initialize the ticket with a unique ID
    public Ticket(int id) {
        this.id = id;
    }

    // Getter method to retrieve the ticket ID
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + id;
    }
}
