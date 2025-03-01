package com.example.ticketing.model;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;

    public Vendor(TicketPool ticketPool, int releaseRate) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ticketPool.addTicket();
                Thread.sleep(releaseRate); // Add ticket at the release rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
