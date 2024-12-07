public class Vendor implements Runnable {
    private final TicketPool ticketPool; // TicketPool that the vendor will interact with
    private final int vendorId; // Unique ID for the vendor
    private final int releaseInterval; // Interval (in milliseconds) between each ticket release

    // Constructor that takes the TicketPool, vendorId, and releaseInterval
    public Vendor(TicketPool ticketPool, int vendorId, int releaseInterval, int interval) {
        this.ticketPool = ticketPool;
        this.vendorId = vendorId;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Wait for the specified release interval before adding tickets
                Thread.sleep(releaseInterval);

                // Add 2 tickets per release (this can be adjusted as needed)
                ticketPool.addTickets(vendorId, 2);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle thread interruption
            System.out.println("Vendor-" + vendorId + " stopped.");
        }
    }
}
