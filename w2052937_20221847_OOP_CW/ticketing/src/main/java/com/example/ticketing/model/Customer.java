package com.example.ticketing.model;

public class Customer implements Runnable {
    private final TicketPool ticketPool;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ticketPool.retrieveTicket();
                Thread.sleep(ticketPool.getCustomerRetrievalRate()); // Purchase tickets at the dynamic rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
