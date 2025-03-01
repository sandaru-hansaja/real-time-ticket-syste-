package javacli.model;



import javacli.cli.TicketSystemCLI;


public class Vendor implements Runnable {
    private final TicketPool pool;
    private final int releaseRate;

    public Vendor(TicketPool pool, int releaseRate) {
        this.pool = pool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        try {
            while (TicketSystemCLI.isRunning) {
                pool.addTicket();
                Thread.sleep(releaseRate); // Add one ticket at the configured rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
