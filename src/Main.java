import java.io.File;
import java.util.Scanner;

public class Main {
    private static boolean running = true;
    private static Thread[] vendorThreads;
    private static Thread[] customerThreads;

    public static void main(String[] args) {
        Configuration config;

        // Check if a previous configuration file exists
        File configFile = new File("configuration.json");
        if (configFile.exists()) {
            System.out.println("Previous configuration found.");
            System.out.println("1. Use previous configuration");
            System.out.println("2. Enter new configuration");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            if (choice == 1) {
                // Load previous configuration
                config = Configuration.loadFromFile(configFile);
                System.out.println("Using previous configuration.");
            } else if (choice == 2) {
                // Get new configuration and save it
                config = Configuration.getConfigurationFromUser();
                config.saveToFile(configFile);
                System.out.println("New configuration saved.");
            } else {
                System.out.println("Invalid choice. Exiting.");
                return;
            }
        } else {
            // No previous configuration; create a new one
            System.out.println("No previous configuration found. Entering new configuration...");
            config = Configuration.getConfigurationFromUser();
            config.saveToFile(configFile);
            System.out.println("Configuration saved.");
        }

        // Initialize the ticket pool with the maximum capacity and initial ticket count
        TicketPool ticketPool = new TicketPool(config.getMaxCapacity(), config.getTotalTickets());

        // Start vendor threads
        int vendorCount = 3;
        vendorThreads = new Thread[vendorCount];
        for (int i = 0; i < vendorCount; i++) {
            vendorThreads[i] = new Thread(new Vendor(ticketPool, i + 1, 1500, config.getReleaseInterval()));
            vendorThreads[i].start();
        }

        // Start customer threads
        int customerCount = 5;
        customerThreads = new Thread[customerCount];
        for (int i = 0; i < customerCount; i++) {
            customerThreads[i] = new Thread(new Customer(ticketPool, i + 1, 1000, config.getRetrievalInterval()));
            customerThreads[i].start();
        }

        // Display the menu options
        System.out.println("\n**** Ticketing System Menu ****");
        System.out.println("1. Stop");

        // Menu loop to handle user input
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    stopSystem();
                    System.out.println("System stopped.");
                    running = false;
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
            if (vendorThread != null) {
                vendorThread.interrupt();
            }
        }

        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            if (customerThread != null) {
                customerThread.interrupt();
            }
        }

        running = false;
    }
}
