package javacli.config;

import java.util.Scanner;

public class SystemConfiguration {
    private int maxTicketCapacity;
    private int totalTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public void configureSystem() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please configure the system:");

        maxTicketCapacity = getValidInput(scanner, 10, 4000, 0, "Enter max ticket capacity (10 to 4000): ");
        totalTicketCapacity = getValidInput(scanner, 10, maxTicketCapacity, 0, 
            "Enter total tickets capacity (must not exceed max capacity): ");
        ticketReleaseRate = getValidInput(scanner, 1, 100, 0, 
            "Enter ticket release rate (1 to 100 seconds between additions): ") * 1000; // Convert to milliseconds
        customerRetrievalRate = getValidInput(scanner, 1, 100, 0, 
            "Enter customer retrieval rate (1 to 100 seconds between purchases): ") * 1000; // Convert to milliseconds

        System.out.println("Configuration completed.");
    }

    private int getValidInput(Scanner scanner, int min, int max, int comparisonValue, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (value >= min && value <= max && (comparisonValue == 0 || value <= comparisonValue)) {
                    return value;
                }
            } else {
                scanner.next(); // Clear invalid input
            }
            System.out.println("Invalid input. Enter a value between " + min + " and " + max + 
                (comparisonValue > 0 ? ", and not exceeding " + comparisonValue : "") + ".");
        }
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTotalTicketCapacity() {
        return totalTicketCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
}
