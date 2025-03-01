package com.example.ticketing.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class TicketPool {
    private int currentTickets;
    private int totalTicketCapacity;
    private int maxCapacity;
    private int totalAddedTickets;

    private final AtomicInteger customerRetrievalRate = new AtomicInteger(3000); // Default rate: 3 seconds
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    // Constructor with default values
    public TicketPool() {
        this.maxCapacity = 0; // Default max capacity
        this.totalTicketCapacity = 0; // Default total capacity
        this.currentTickets = 0;
        this.totalAddedTickets = 0;
    }

    // Setter methods for vendor configuration
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setTotalTicketCapacity(int totalTicketCapacity) {
        this.totalTicketCapacity = totalTicketCapacity;
        this.currentTickets = totalTicketCapacity; // Initialize current tickets
        this.totalAddedTickets = totalTicketCapacity; // Initialize total tickets added
        System.out.println("System initialized with total tickets: " + totalTicketCapacity);
    }

    public void setCustomerRetrievalRate(int rate) {
        customerRetrievalRate.set(rate);
    }

    // Method to add a ticket (vendor)
    public void addTicket() throws InterruptedException {
        lock.lock();
        try {
            // Wait if the pool is full or the total ticket capacity is reached
            while (currentTickets >= maxCapacity || totalAddedTickets >= maxCapacity    ) {
                if (totalAddedTickets >= maxCapacity) {
                    System.out.println("Vendor reached max ticket limit (" + maxCapacity + ").");
                    return;
                }
                System.out.println("Pool is full. Waiting to add tickets...");
                notFull.await(); // Wait until there's space for more tickets
            }

            // Add a ticket
            currentTickets++;
            totalAddedTickets++;
            System.out.println("Vendor added a ticket. Current tickets: " + currentTickets +
                    ", Total tickets added: " + totalAddedTickets);
            notEmpty.signalAll(); // Notify customers that they can buy a ticket
        } finally {
            lock.unlock();
        }
    }

    // Method to retrieve a ticket (customer)
    public void retrieveTicket() throws InterruptedException {
        lock.lock();
        try {
            // Wait if no tickets are available
            while (currentTickets <= 0) {
                System.out.println("No tickets available. Waiting...");
                notEmpty.await(); // Wait until a ticket is available
            }

            // Retrieve a ticket
            currentTickets--;
            System.out.println("Customer purchased a ticket. Remaining tickets: " + currentTickets);
            notFull.signalAll(); // Notify vendor to add more tickets if needed
        } finally {
            lock.unlock();
        }
    }

    // Getter methods for current ticket status
    public int getCurrentTickets() {
        return currentTickets;
    }

    public int getTotalAddedTickets() {
        return totalAddedTickets;
    }

    // Getter method for customer retrieval rate
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate.get();
    }

    // Method to reset the ticket pool
    public void resetTicketPool() {
        lock.lock();
        try {
            this.currentTickets = totalTicketCapacity; // Reset available tickets to initial capacity
            this.totalAddedTickets = totalTicketCapacity; // Reset total added tickets to initial capacity
            System.out.println("Ticket pool has been reset to initial total tickets: " + totalTicketCapacity);
        } finally {
            lock.unlock();
        }
    }
}
