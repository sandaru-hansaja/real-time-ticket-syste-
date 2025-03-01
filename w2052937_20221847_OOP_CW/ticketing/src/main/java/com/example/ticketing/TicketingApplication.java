package com.example.ticketing;




import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.example.ticketing.model.TicketPool;



@SpringBootApplication
public class TicketingApplication implements CommandLineRunner {
    public TicketingApplication(TicketPool ticketPool) {
    }

    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Ticketing System Started...");
    }
}
