Ticket Management System CLI
==========================
The Ticket Management System CLI is a command-line application for managing a ticketing system. It allows users to configure ticket settings, manage ticket releases, and simulate customer retrieval in a multi-threaded environment.


Features
--------
Configure System Settings: Specify total ticket capacity, max ticket capacity, ticket release rate, and customer retrieval rate.

Multi-threaded Operation: Simulate ticket addition and retrieval using vendor and customer threads.

Real-time Monitoring: Monitor system behavior while running.

User-friendly Commands: Start and stop the system with simple commands.


Setup Instructions
-----------------
Prerequisites

Java Development Kit (JDK): Ensure you have Java JDK (version 8 or higher) installed.

IDE/Text Editor: Use an IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code for easier execution.

Command-Line Interface (CLI): Ensure you can run Java applications via the terminal or command prompt.


Steps
-----
1.Clone this repository:
git clone <repository-url>
cd <repository-folder>

2.Navigate to the javacli directory.

3.Compile the project:
javac -d out -sourcepath src src/athi/javacli/cli/TicketSystemCLI.java

4.Run the application:
java -cp out athi.javacli.cli.TicketSystemCLI


Usage Guidelines
-----------------
Starting the Application
---------------------------
Run the program using the setup instructions above.

Follow the on-screen prompts to configure the system:

Total Tickets Capacity: Enter a value between 10 and 4000.
Max Tickets Capacity: Enter a value between 10 and 3000. This cannot exceed the total tickets capacity.
Ticket Release Rate: Enter a value between 1 and 100 (in seconds).
Customer Retrieval Rate: Enter a value between 1 and 100 (in seconds).
Once configured, use the following commands to interact with the system:

start: Start the system with the configured settings.
exit: Stop the system and exit the application.



Example Interaction


Welcome to the Ticket Management System!
Please configure the system:
Enter total tickets capacity (10 to 4000): 3000
Enter max ticket capacity (10 to 3000): 2500
Enter ticket release rate in seconds (1 to 100): 5
Enter customer retrieval rate in seconds (1 to 100): 10
Configuration completed. Use 'start' to begin or 'exit' to terminate.

> start
System started. Real-time monitoring is active.

> exit
Exiting application. Goodbye!



Troubleshooting
-----------------
Invalid Input: Enter values within the allowed ranges.
Java Issues: Ensure Java is installed and added to the PATH.
Application Errors: Recompile the code if needed using the javac command.

Enjoy using the Ticket Management System CLI! 🎟️

