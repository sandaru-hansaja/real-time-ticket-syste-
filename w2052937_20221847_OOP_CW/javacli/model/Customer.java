package javacli.model;

import javacli.cli.TicketSystemCLI;


public class Customer implements Runnable {
    private final TicketPool pool;
    private final int retrievalRate;

    public Customer(TicketPool pool, int retrievalRate) {
        this.pool = pool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        try {
            while (TicketSystemCLI.isRunning) {
                pool.retrieveTicket();
                Thread.sleep(retrievalRate); // Purchase one ticket at the configured rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            
            
        }
    }
}
