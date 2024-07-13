//contributed by Qianru Zhong

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TicketCollectionTest {

    private Airplane airplane;
    private Flight flight;
    private Passenger passenger;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        airplane = new Airplane(1, "Boeing 747",
                50, 200, 10);
        flight = new Flight(1, "New York", "Los Angeles", "NY123",
                "Monash", Timestamp.valueOf("2024-07-01 10:00:00"), Timestamp.valueOf("2024-07-01 12:00:00"), airplane);
        passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        ticket = new Ticket(1, 1000, flight, false, passenger);
        TicketCollection.clearTickets(); // 清空静态票据列表
    }

    @Test
    @DisplayName("Test the function of adding and getting a ticket")
    void testAddAndGetATicket() {
        TicketCollection.addTicket(ticket);
        ArrayList<Ticket> tickets = TicketCollection.getTickets();
        assertEquals(1, tickets.size());
        assertTrue(tickets.contains(ticket));
    }

    @Test
    @DisplayName("Test the function of adding and getting tickets")
    void testAddAndGetTickets() {
        Airplane airplane1 = new Airplane(2, "Boeing 767",
                50, 200, 10);
        Flight flight1 = new Flight(2, "Los Angeles", "New York", "LA123",
                "Monash", Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane1);
        Passenger passenger1 = new Passenger("Wendy", "Smith", 24, "Woman",
                "wendy.smith@monash.edu", "0456781234", "A56781234",
                "1234561234567890", 312);
        Ticket ticket1 = new Ticket(2, 2000, flight1, false, passenger1);

        //验证add函数
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket1);
        TicketCollection.addTickets(tickets);

        //验证get函数
        assertEquals(2, tickets.size());
        assertTrue(tickets.contains(ticket1));
        assertTrue(tickets.contains(ticket));
    }

    @Test
    @DisplayName("Test the function of getting all tickets' information")
    void testGetALLTickets() {
        Airplane airplane1 = new Airplane(2, "Boeing 767",
                50, 200, 10);
        Flight flight1 = new Flight(2, "Los Angeles", "New York", "LA123",
                "Monash", Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane1);
        Passenger passenger1 = new Passenger("Wendy", "Smith", 24, "Woman",
                "wendy.smith@monash.edu", "0456781234", "A56781234",
                "1234561234567890", 312);
        Ticket ticket1 = new Ticket(2, 2000, flight1, false, passenger1);

        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket1);
        TicketCollection.addTickets(tickets);

        // Redirect system output to test
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        TicketCollection.getAllTickets();

        String test1 = ticket1.toString();

        // 重置系统输出
        System.setOut(System.out);
        String expectedOutput = ticket.toString() + System.lineSeparator() +
                test1 + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @DisplayName("Test the function of getting a specific ticket's information")
    void testGetTicketsInfo() {
        Airplane airplane1 = new Airplane(2, "Boeing 767",
                50, 200, 10);
        Flight flight1 = new Flight(2, "Los Angeles", "New York", "LA123",
                "Monash", Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane1);
        Passenger passenger1 = new Passenger("Wendy", "Smith", 24, "Woman",
                "wendy.smith@monash.edu", "0456781234", "A56781234",
                "1234561234567890", 312);
        Ticket ticket1 = new Ticket(2, 2000, flight1, false, passenger1);

        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket1);
        TicketCollection.addTickets(tickets);

        // Redirect system output to test
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        TicketCollection.getAllTickets();

        String test1 = TicketCollection.getTicketInfo(2).toString();

        // 重置系统输出
        System.setOut(System.out);
        String expectedOutput = TicketCollection.getTicketInfo(1).toString() + System.lineSeparator() +
                test1 + System.lineSeparator();

        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @DisplayName("Test if a ticket is valid.")
    void testValidateTicket() {
        //验证了Ticket id的唯一性
        TicketCollection.addTicket(ticket);
        Airplane airplane1 = new Airplane(2, "Boeing 767",
                50, 200, 10);
        Flight flight1 = new Flight(2, "Los Angeles", "New York", "LA123",
                "Monash", Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane1);
        Passenger passenger1 = new Passenger("Wendy", "Smith", 24, "Woman",
                "wendy.smith@monash.edu", "0456781234", "A56781234",
                "1234561234567890", 312);
        Ticket ticket1 = new Ticket(1, 2000, flight1, true, passenger1);
        Exception exception0 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(ticket1);
        });
        assertEquals("Ticket ID 1 already exists.", exception0.getMessage());

        //在TicketCollection类中定义了Ticket不能为空，
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(null);
        });
        assertEquals("Ticket cannot be null.", exception.getMessage());

        //在TicketCollectionTest类中验证了Ticket的Ticket id字段的有效性
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(0, 1000, flight, false, passenger));
        });
        assertEquals("Ticket id must be greater than 0.", exception1.getMessage());

        //在TicketCollectionTest类中验证了Ticket的Price字段的有效性
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, -1, flight, false, passenger));
        });
        assertEquals("Price must not be negative.", exception2.getMessage());

        //在TicketCollectionTest类中验证了Flight的有效性
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000, null, false, passenger));
        });
        assertEquals("Flight cannot be null.", exception3.getMessage());

        //在TicketCollectionTest类中验证了Flight的Airplane字段的有效性
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                            Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), null)
                    , false, passenger));
        });
        assertEquals("Airplane cannot be null.", exception5.getMessage());

        //在TicketCollectionTest类中验证了Flight的Flight ID字段的有效性
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000,
                    new Flight(0, "New York", "Los Angeles", "NY123", "Monash",
                            Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger));
        });
        assertEquals("Flight ID must be greater than 0.", exception6.getMessage());

        //在TicketCollectionTest类中验证了Flight的Departure字段的有效性
        Exception departFromException = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000,
                    new Flight(1, "New York", "", "NY123", "Monash",
                            Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger));
        });
        assertEquals("Departure airport cannot be empty.", departFromException.getMessage());

        //在TicketCollectionTest类中验证了Flight的Destination字段的有效性
        Exception departToException = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000,
                    new Flight(1, "", "Los Angeles", "NY123", "Monash",
                            Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger));
        });
        assertEquals("Destination airport cannot be empty.", departToException.getMessage());

        //在TicketCollectionTest类中验证了Flight的code字段的有效性
        Exception exception7 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "", "Monash",
                            Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger));
        });
        assertEquals("Flight code cannot be empty.", exception7.getMessage());

        //在TicketCollectionTest类中验证了Flight的company字段的有效性
        Exception exception8 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "NY123", "",
                            Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger));
        });
        assertEquals("Airline company cannot be empty.", exception8.getMessage());

        //在TicketCollectionTest类中验证了Flight的Departure time字段的有效性
        assertThrows(NullPointerException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                            null, Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger));
        });

        //在TicketCollectionTest类中验证了Flight的arriving time字段的有效性
        assertThrows(NullPointerException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                            Timestamp.valueOf("2024-07-08 10:00:00"), null, airplane)
                    , false, passenger));
        });

        //在TicketCollectionTest类中验证了Ticket的Passenger字段的有效性
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(new Ticket(1, 1000, flight, false, null));
        });
        assertEquals("Passenger cannot be null.", exception4.getMessage());

        //在TicketCollectionTest类中验证了Passenger的first name字段的有效性
        IllegalArgumentException exception9 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John3", "Doe", 30,
                            "Man", "john.doe@example.com", "0456789123",
                            "A12345678", "1234567890123456", 123)));
        });
        assertEquals("Invalid first name: John3", exception9.getMessage());

        //在TicketCollectionTest类中验证了Passenger的second name字段的有效性
        IllegalArgumentException exception10 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John", "Doe2", 30,
                            "Man", "john.doe@example.com", "0456789123",
                            "A12345678", "1234567890123456", 123)));
        });
        assertEquals("Invalid second name: Doe2", exception10.getMessage());

        //在TicketCollectionTest类中验证了Passenger的age字段的有效性
        IllegalArgumentException exception11 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John", "Doe", -1,
                            "Man", "john.doe@example.com", "0456789123",
                            "A12345678", "1234567890123456", 123)));
        });
        assertEquals("Age cannot be negative", exception11.getMessage());

        //在TicketCollectionTest类中验证了Passenger的gender字段的有效性
        IllegalArgumentException exception12 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John", "Doe", 30,
                            "Male", "john.doe@example.com", "0456789123",
                            "A12345678", "1234567890123456", 123)));
        });
        assertEquals("Invalid gender: Male", exception12.getMessage());

        //在TicketCollectionTest类中验证了passenger的email字段的有效性
        IllegalArgumentException exception13 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John", "Doe", 30,
                            "Man", "invalid-email", "0456789123",
                            "A12345678", "1234567890123456", 123)));
        });
        assertEquals("Invalid email format.", exception13.getMessage());

        //在TicketCollectionTest类中验证了passenger的phone number字段的有效性
        IllegalArgumentException exception14 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John", "Doe", 30,
                            "Man", "john.doe@example.com", "invalid-phone-number",
                            "A12345678", "1234567890123456", 123)));
        });
        assertTrue(exception14.getMessage().contains("Invalid phone number format."));

        //在TicketCollectionTest类中验证了passenger的Passport number字段的有效性
        IllegalArgumentException exception15 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John", "Doe", 30,
                            "Man", "john.doe@example.com", "0456789123",
                            "A1234567890", "1234567890123456", 123)));
        });
        assertTrue(exception15.getMessage().contains("Passport number should not be more than 9 characters long."));

        //在TicketCollectionTest类中验证了passenger的card number字段的有效性
        IllegalArgumentException exception16 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John", "Doe", 30,
                            "Man", "john.doe@example.com", "0456789123",
                            "A123456789", "", 123)));
        });
        assertEquals("All fields are required and must be valid.", exception16.getMessage());

        //在TicketCollectionTest类中验证了passenger的security code字段的有效性
        IllegalArgumentException exception17 = assertThrows(IllegalArgumentException.class, () -> {
            TicketCollection.addTicket(
                    new Ticket(1, 1000, flight, false,
                            new Passenger("John", "Doe", 30,
                            "Man", "john.doe@example.com", "0456789123",
                            "A12345678", "1234567890123456", -1)));
        });
        assertEquals("All fields are required and must be valid.", exception17.getMessage());

    }

    @Test
    @DisplayName("Test the function of clearing tickets")
    void testClearTickets() {
        TicketCollection.addTicket(ticket);
        TicketCollection.clearTickets();
        assertEquals(0, TicketCollection.getTickets().size());

        Airplane airplane1 = new Airplane(2, "Boeing 767",
                50, 200, 10);
        Flight flight1 = new Flight(2, "Los Angeles", "New York", "LA123",
                "Monash", Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane1);
        Passenger passenger1 = new Passenger("Wendy", "Smith", 24, "Woman",
                "wendy.smith@monash.edu", "0456781234", "A56781234",
                "1234561234567890", 312);
        Ticket ticket1 = new Ticket(2, 2000, flight1, false, passenger1);

        //验证clear函数
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket1);
        TicketCollection.addTickets(tickets);
        TicketCollection.clearTickets();
        assertEquals(0, TicketCollection.getTickets().size());
    }


}