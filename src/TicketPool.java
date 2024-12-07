import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final int maxCapacity;
    private final Queue<Ticket> tickets = new LinkedList<>();
    private int totalTickets; // Keeps track of total ticket count to generate unique IDs

    public TicketPool(int maxCapacity, int initialTotalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = initialTotalTickets;

        // Initialize the pool with the user-defined total number of tickets
        for (int i = 0; i < initialTotalTickets; i++) {
            tickets.add(new Ticket(i + 1));  // Adding tickets with unique IDs starting from 1
        }
    }

    // Method to add tickets to the pool (called by vendors)
    public synchronized void addTickets(int vendorId, int ticketsToAdd) {
        // Check if there is enough capacity to add the tickets
        if (tickets.size() + ticketsToAdd <= maxCapacity) {
            for (int i = 0; i < ticketsToAdd; i++) {
                tickets.add(new Ticket(totalTickets + 1)); // Add a new ticket with a unique ID
                totalTickets++;
            }
            System.out.println("Vendor-" + vendorId + " added " + ticketsToAdd + " tickets. Total tickets: " + tickets.size());
        } else {
            System.out.println("Vendor-" + vendorId + " tried adding tickets, but capacity is full!");
        }
    }

    // Method to retrieve tickets from the pool (called by customers)
    public synchronized void retrieveTickets(int customerId, int ticketsToRetrieve) {
        int ticketsPurchased = 0;
        StringBuilder purchasedTicketIds = new StringBuilder();

        // Retrieve the tickets
        while (ticketsToRetrieve > 0 && !tickets.isEmpty()) {
            Ticket ticket = tickets.poll(); // Remove ticket from the pool
            purchasedTicketIds.append(ticket.getId()).append(" "); // Collect Ticket IDs
            ticketsToRetrieve--;
            ticketsPurchased++;
        }

        // Log the result of the purchase
        if (ticketsPurchased > 0) {
            System.out.println("Customer-" + customerId + " purchased " + ticketsPurchased + " tickets: " +
                    purchasedTicketIds.toString() + ". Remaining tickets: " + tickets.size());
        } else {
            System.out.println("Customer-" + customerId + " tried purchasing tickets, but none were available.");
        }
    }

    // Get the current number of tickets in the pool
    public synchronized int getTicketCount() {
        return tickets.size();
    }
}
