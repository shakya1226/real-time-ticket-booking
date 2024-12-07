public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerId;
    private final int retrievalInterval;

    public Customer(TicketPool ticketPool, int customerId, int retrievalInterval, int interval) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Simulate ticket retrieval interval
                Thread.sleep(retrievalInterval);

                // Simulate the customer purchasing 1 to 3 tickets (randomly)
                int ticketsToPurchase = (int) (Math.random() * 3) + 1; // 1, 2, or 3 tickets
                ticketPool.retrieveTickets(customerId, ticketsToPurchase);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt flag
                System.out.println("Customer-" + customerId + " interrupted.");
            }
        }
    }
}
