import java.util.Scanner;

public class Main {
    private static boolean running = true;
    private static Thread[] vendorThreads;
    private static Thread[] customerThreads;

    public static void main(String[] args) {
        // Get configuration object from the Configuration class
        Configuration config = Configuration.getConfigurationFromUser();

        // Initialize the ticket pool with the maximum capacity and initial ticket count
        TicketPool ticketPool = new TicketPool(config.getMaxCapacity(), config.getTotalTickets());

        // Start vendor threads
        int vendorCount = 3;
        vendorThreads = new Thread[vendorCount];
        for (int i = 0; i < vendorCount; i++) {
            vendorThreads[i] = new Thread(new Vendor(ticketPool,i+1, 1500, config.getReleaseInterval()));
            vendorThreads[i].start();
        }

        // Start customer threads
        int customerCount = 5; // Example: 5 customers
        customerThreads = new Thread[customerCount];
        for (int i = 0; i < customerCount; i++) {
            // Pass retrieval rate as part of the input
            customerThreads[i] = new Thread(new Customer(ticketPool, i + 1,1000, config.getRetrievalInterval()));
            customerThreads[i].start();
        }


        // Display the menu options
        System.out.println("\n****Ticketing System Menu****");
        System.out.println("1. Stop");

        // Menu loop to handle user input
        Scanner scanner = new Scanner(System.in);
        while (running) {

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    stopSystem();
                    System.out.println("System stopped.");
                    running = false; // Break the loop to ensure the system fully stops
                    break;

                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }

        scanner.close();
    }

    private static void stopSystem() {
        // Interrupt all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }

        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        // Mark the system as no longer running
        running = false;
    }
}
