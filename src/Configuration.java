import java.util.Scanner;

public class Configuration {
    private final int totalTickets;
    private final int releaseInterval;
    private final int retrievalInterval;
    private final int maxCapacity;


    // Private constructor to prevent external instantiation without using the static method
    private Configuration(int totalTickets, int releaseInterval, int retrievalInterval, int maxCapacity) {
        this.totalTickets = totalTickets;
        this.releaseInterval = releaseInterval;
        this.retrievalInterval = retrievalInterval;
        this.maxCapacity = maxCapacity;
    }

    // Static method to collect user input and return a Configuration object
    public static Configuration getConfigurationFromUser() {
        Scanner scanner = new Scanner(System.in);

        // Collect configuration parameters from the user
        System.out.print("Enter total number of tickets: ");
        int totalTickets = scanner.nextInt();

        System.out.print("Enter release interval (ms): ");
        int releaseInterval = scanner.nextInt();

        System.out.print("Enter retrieval interval (ms): ");
        int retrievalInterval = scanner.nextInt();

        System.out.print("Enter maximum ticket pool capacity: ");
        int maxCapacity = scanner.nextInt();

        // Return a new Configuration object initialized with user input
        return new Configuration(totalTickets, releaseInterval, retrievalInterval, maxCapacity);
    }

    // Getters for the configuration parameters
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getReleaseInterval() {
        return releaseInterval;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

}
