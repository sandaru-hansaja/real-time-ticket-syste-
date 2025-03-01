package com.example.ticketing.controller;

import org.springframework.web.bind.annotation.*;



import com.example.ticketing.model.Customer;
import com.example.ticketing.model.TicketPool;
import com.example.ticketing.model.Vendor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {
    private final TicketPool ticketPool;
    private List<Thread> vendorThreads = new ArrayList<>();
    private List<Thread> customerThreads = new ArrayList<>();

    public VendorController(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @PostMapping("/set-config")
    public String setConfiguration(@RequestParam int maxCapacity,
                                   @RequestParam int totalTicketCapacity,
                                   @RequestParam int customerRetrievalRate,
                                   @RequestParam int ticketReleaseRate,
                                   @RequestParam int numVendors,
                                   @RequestParam int numCustomers) {
        ticketPool.setMaxCapacity(maxCapacity);
        ticketPool.setTotalTicketCapacity(totalTicketCapacity);
        ticketPool.setCustomerRetrievalRate(customerRetrievalRate);

        // Stop existing threads before restarting
        stopSystem();

        // Create threads for vendors
        vendorThreads.clear();  // Clear existing vendor threads
        for (int i = 0; i < numVendors; i++) {
            Thread vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate));
            vendorThreads.add(vendorThread);
        }

        // Create threads for customers
        customerThreads.clear();  // Clear existing customer threads
        for (int i = 0; i < numCustomers; i++) {
            Thread customerThread = new Thread(new Customer(ticketPool));
            customerThreads.add(customerThread);
        }

        return "Configuration updated and system reset with " + numVendors + " vendors and " + numCustomers + " customers.";
    }

    @PostMapping("/start")
    public String startSystem() {
        if (vendorThreads.isEmpty() || customerThreads.isEmpty()) {
            return "System configuration is not set. Please configure the system first.";
        }

        // Start all vendor threads if they are not already running
        for (Thread vendorThread : vendorThreads) {
            if (!vendorThread.isAlive()) {
                vendorThread.start();
            }
        }

        // Start all customer threads if they are not already running
        for (Thread customerThread : customerThreads) {
            if (!customerThread.isAlive()) {
                customerThread.start();
            }
        }

        return "System started with " + vendorThreads.size() + " vendors and " + customerThreads.size() + " customers.";
    }

    @PostMapping("/stop")
    public String stopSystem() {
        // Interrupt all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }

        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        return "System stopped.";
    }

    @PostMapping("/reset")
    public String resetSystem() {
        stopSystem();

        // Reset all ticket-related values to zero
        ticketPool.setMaxCapacity(0);
        ticketPool.setTotalTicketCapacity(0);
        ticketPool.setCustomerRetrievalRate(0);
        ticketPool.resetTicketPool();

        return "System reset and configurations cleared.";
    }

    // New method to get the status of the ticket pool
    @GetMapping("/status")
    public String getStatus() {
        return "Current tickets: " + ticketPool.getCurrentTickets() + ", Total tickets added: " + ticketPool.getTotalAddedTickets();
    }
}
