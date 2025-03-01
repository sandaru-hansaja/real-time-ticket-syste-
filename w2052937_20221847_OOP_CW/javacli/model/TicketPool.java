package javacli.model;

import javacli.cli.TicketSystemCLI;

public class TicketPool {
    private int currentTickets; // Tickets available for customers to buy
    private final int totalTicketCapacity; // Total tickets available as per user input
    private final int maxCapacity; // Max capacity limit for the pool
    private int totalAddedTickets; // Tracks how many tickets the vendor has added

    public TicketPool(int totalTicketCapacity, int maxCapacity) {
        this.currentTickets = totalTicketCapacity; // Start with the total tickets set by the user
        this.totalTicketCapacity = totalTicketCapacity;
        this.maxCapacity = maxCapacity;
        this.totalAddedTickets = totalTicketCapacity; // Initialize the added tickets to total capacity
    }

    public synchronized void addTicket() throws InterruptedException {
        if (totalAddedTickets < totalTicketCapacity) {
            // Add tickets up to totalTicketCapacity
            totalAddedTickets++;
            currentTickets++;
            System.out.println("Vendor added 1 ticket. Total tickets added: " + totalAddedTickets);
            if (totalAddedTickets == totalTicketCapacity) {
                System.out.println("Reached total ticket capacity (" + totalTicketCapacity + "). Transitioning to max capacity.");
            }
        } else if (totalAddedTickets < maxCapacity) {
            // Add tickets up to max capacity
            totalAddedTickets++;
            currentTickets++;
            System.out.println("Vendor added 1 ticket. Total tickets: " + totalAddedTickets);
        }

        if (totalAddedTickets == maxCapacity) {
            System.out.println("Max capacity reached (" + maxCapacity + "). Waiting for all tickets to be purchased...");
        }

        notifyAll(); // Notify consumers
    }

    public synchronized void retrieveTicket() throws InterruptedException {
        while (currentTickets <= 0 && TicketSystemCLI.isRunning) {
            wait(); // Wait if no tickets are available
        }
        if (!TicketSystemCLI.isRunning) return; // Exit if the system is stopped
        currentTickets--;
        System.out.println("Customer purchased 1 ticket. Available tickets: " + currentTickets);

        // Stop the system if max capacity has been reached and all tickets have been purchased
        if (totalAddedTickets == maxCapacity && currentTickets == 0) {
            System.out.println("All tickets have been purchased. Stopping the system...");
            TicketSystemCLI.isRunning = false; // Stop the system
        }
        notifyAll(); // Notify producers
    }

    public int getTotalTickets() {
        return totalTicketCapacity;
    }

    public int getCurrentTickets() {
        return currentTickets;
    }
}
