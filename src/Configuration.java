import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Configuration {
    private final int totalTickets;
    private final int releaseInterval;
    private final int retrievalInterval;
    private final int maxCapacity;

    private Configuration(int totalTickets, int releaseInterval, int retrievalInterval, int maxCapacity) {
        this.totalTickets = totalTickets;
        this.releaseInterval = releaseInterval;
        this.retrievalInterval = retrievalInterval;
        this.maxCapacity = maxCapacity;
    }

    public static Configuration getConfigurationFromUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total number of tickets: ");
        int totalTickets = scanner.nextInt();

        System.out.print("Enter release interval (ms): ");
        int releaseInterval = scanner.nextInt();

        System.out.print("Enter retrieval interval (ms): ");
        int retrievalInterval = scanner.nextInt();

        System.out.print("Enter maximum ticket pool capacity: ");
        int maxCapacity = scanner.nextInt();

        return new Configuration(totalTickets, releaseInterval, retrievalInterval, maxCapacity);
    }

    public static Configuration loadFromFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from file.", e);
        }
    }

    public void saveToFile(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration to file.", e);
        }
    }

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
