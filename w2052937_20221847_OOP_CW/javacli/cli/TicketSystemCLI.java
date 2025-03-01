package javacli.cli;

import javacli.config.SystemConfiguration;
import javacli.model.Customer;
import javacli.model.TicketPool;
import javacli.model.Vendor;

import java.util.Scanner;

public class TicketSystemCLI {
    public static volatile boolean isRunning = false; // Control flag for threads
    private static TicketPool ticketPool;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SystemConfiguration config = new SystemConfiguration();

        System.out.println("Welcome to the Ticket Management System!");

        // Configuration Setup
        config.configureSystem();

        ticketPool = new TicketPool(config.getTotalTicketCapacity(), config.getMaxTicketCapacity());

        System.out.println("Use 'start' to begin or 'exit' to terminate.");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "start":
                    if (!isRunning) {
                        isRunning = true;
                        System.out.println("System started. Total tickets: " + ticketPool.getTotalTickets());
                        startSystem(config.getTicketReleaseRate(), config.getCustomerRetrievalRate());
                    } else {
                        System.out.println("System is already running.");
                    }
                    break;

                case "exit":
                    stopSystem();
                    System.out.println("Exiting application. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid command. Use 'start' or 'exit'.");
            }
        }
    }

    private static void startSystem(int ticketReleaseRate, int customerRetrievalRate) {
        Thread vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate));
        Thread customerThread = new Thread(new Customer(ticketPool, customerRetrievalRate));
        Thread vendorThread1 = new Thread(new Vendor(ticketPool, ticketReleaseRate));
        Thread customerThread1 = new Thread(new Customer(ticketPool, customerRetrievalRate));
        Thread vendorThread2 = new Thread(new Vendor(ticketPool, ticketReleaseRate));
        Thread customerThread2 = new Thread(new Customer(ticketPool, customerRetrievalRate));

        vendorThread.start();
        customerThread.start();
        vendorThread1.start();
        customerThread1.start();
        vendorThread2.start();
        customerThread2.start();

        System.out.println("System started. Real-time monitoring is active.");
    }

    private static void stopSystem() {
        isRunning = false;
        System.out.println("System stopped. Real-time monitoring is inactive.");
    }
}
