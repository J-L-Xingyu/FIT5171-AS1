//contributed by Qianru Zhong

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    private Airplane airplane;
    private Flight flight;
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        airplane = new Airplane(1, "Boeing 747",
                50, 200, 10);
        flight = new Flight(1, "New York", "Los Angeles", "NY123",
                "Monash", Timestamp.valueOf("2024-07-01 10:00:00"), Timestamp.valueOf("2024-07-01 12:00:00"), airplane);
        passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
    }

    @Test
    @DisplayName("Test the function of set and get a ticket id.")
    void testSetAndGetTicketID() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        ticket.setTicket_id(2);
        assertEquals(2, ticket.getTicket_id());
    }

    @Test
    @DisplayName("Test if a ticket id is valid.")
    void TicketIDBVT() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        //Normal boundary value analysis
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setTicket_id(0);
        });
        assertEquals("Ticket id must be greater than 0.", exception1.getMessage());

        //健壮性测试
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setTicket_id(-1);
        });
        assertEquals("Ticket id must be greater than 0.", exception2.getMessage());

        //最坏情况
        ticket.setTicket_id(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, ticket.getTicket_id());

        //特殊值
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setTicket_id(Integer.MIN_VALUE);
        });
        assertEquals("Ticket id must be greater than 0.", exception3.getMessage());
    }

    @Test
    @DisplayName("Test the function of set and get a business class ticket.")
    void testSetAndGetClassVip() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        assertFalse(ticket.getClassVip());
        ticket.setClassVip(true);
        assertTrue(ticket.getClassVip());
    }

    @Test
    @DisplayName("Test the function of set and get a ticket status.")
    void testSetAndGetTicketStatus() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        ticket.setTicketStatus(true);
        assertTrue(ticket.ticketStatus());
        ticket.setTicketStatus(false);
        assertFalse(ticket.ticketStatus());
    }

    @Test
    @DisplayName("Test the function of set and get a flight.")
    void testSetAndGetFlight() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        Flight flight1 = new Flight(2, "Los Angeles","New York",  "LA123",
                "Delta", Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane);
        ticket.setFlight(flight1);
        assertEquals(flight1, ticket.getFlight());
    }

    @Test
    @DisplayName("Test if a flight is valid.")
    void testValidFlight() {
        // 在Ticket类的setFlight()中定义了flight不能为空，以及flight中的airplane不能为空
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setFlight(null);
        });
        assertEquals("Flight cannot be null.", exception.getMessage());

        Flight flight1 = new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), null);
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight1, false, passenger);
        });
        assertEquals("Airplane cannot be null.", exception1.getMessage());

        //在TicketTest类中验证了flight其他字段的有效性
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(0, "New York", "Los Angeles", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane);
        });
        assertEquals("Flight ID must be greater than 0.", exception2.getMessage());

        Exception departFromException = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(1, "New York", "", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane);
        });
        assertEquals("Departure airport cannot be empty.", departFromException.getMessage());

        Exception departToException = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(1, "", "Los Angeles", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane);
        });
        assertEquals("Destination airport cannot be empty.", departToException.getMessage());

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(1, "New York", "Los Angeles", "", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane);
        });
        assertEquals("Flight code cannot be empty.", exception3.getMessage());

        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(1, "New York", "Los Angeles", "NY123", "",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane);
        });
        assertEquals("Airline company cannot be empty.", exception4.getMessage());

        assertThrows(NullPointerException.class, () -> {
            new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                    null, Timestamp.valueOf("2024-07-08 12:00:00"), airplane);
        });

        assertThrows(NullPointerException.class, () -> {
            new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), null, airplane);
        });
    }

    @Test
    @DisplayName("Test the function of set and get a passenger.")
    void testSetAndGetPassenger() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        Passenger passenger1 = new Passenger("Wendy", "Smith", 24, "Woman",
                "wendy.smith@monash.edu", "0416782345", "A56781234",
                "1234561234567890", 312);
        ticket.setPassenger(passenger1);
        assertEquals(passenger1, ticket.getPassenger());
    }

    //初稿
    @Test
    @DisplayName("Test if a passenger is valid.")
    void testValidPassenger() {
        //在Ticket类的setPassenger()中定义了passenger不能为空，验证了passenger前四个字段的有效性
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setPassenger(null);
        });
        assertEquals("Passenger cannot be null.", exception.getMessage());

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John3", "Doe", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", 123);
        });
        assertEquals("Invalid first name: John3", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe2", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", 123);
        });
        assertEquals("Invalid second name: Doe2", exception2.getMessage());

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", -1,
                    "Man", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", 123);
        });
        assertEquals("Age cannot be negative", exception3.getMessage());

        IllegalArgumentException exception4 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30,
                    "Male", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", 123);
        });
        assertEquals("Invalid gender: Male", exception4.getMessage());

        //在TicketTest类中验证了passenger其他字段的有效性
        IllegalArgumentException exception5 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30,
                    "Man", "invalid-email", "0456789123",
                    "A12345678", "1234567890123456", 123);
        });
        assertEquals("Invalid email format.", exception5.getMessage());

        IllegalArgumentException exception6 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30,
                    "Man", "john.doe@example.com", "invalid-phone-number",
                    "A12345678", "1234567890123456", 123);
        });
        assertTrue(exception6.getMessage().contains("Invalid phone number format."));

        IllegalArgumentException exception7 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A1234567890", "1234567890123456", 123);
        });
        assertTrue(exception7.getMessage().contains("Passport number should not be more than 9 characters long."));


        IllegalArgumentException exception8 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A123456789", "", 123);
        });
        assertEquals("All fields are required and must be valid.", exception8.getMessage());

        IllegalArgumentException exception9 = assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", -1);
        });
        assertEquals("All fields are required and must be valid.", exception9.getMessage());
    }

    @ParameterizedTest(name = "{index}: status = {0}, basePrice = {1}, expectedPrice = {2}")
    @CsvSource({
            "false, 1000, 1000",   // Ticket not sold, price should not include tax.
            "true, 2000, 2240",   // Ticket sold, price should include tax.

    })
    @DisplayName("Test the function of set and get a ticket price.")
    void testSetAndGetPrice(boolean status, int basePrice, int expectedPrice) {
        Ticket ticket = new Ticket(1, basePrice, flight, false, passenger);
        ticket.setTicketStatus(status); // 先设置状态
        ticket.setPrice(basePrice);  // 然后设置价格，以确保状态改变能够触发税率应用
        assertEquals(expectedPrice, ticket.getPrice());
    }

    @Test
    @DisplayName("Ensure ticket status correctly affects the price")
    void testTicketStatusAffectsPrice() {
        Ticket ticket = new Ticket(1, 2000, flight, false, passenger);
        ticket.setTicketStatus(false);
        ticket.setPrice(2000);  // Setting initial price without tax.
        assertEquals(2000, ticket.getPrice(), "Price should be set without tax when not sold.");
        ticket.setTicketStatus(true);
        assertEquals(2000 * 1.12, ticket.getPrice(), "Service tax should be applied when ticket is sold.");
    }

    @Test
    @DisplayName("Test if a ticket price is valid.")
    void PriceBVT() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        //健壮性测试
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setPrice(-1);
        });
        assertEquals("Price must not be negative.", exception.getMessage());

        //Normal boundary value analysis
        ticket.setPrice(0);
        assertEquals(0, ticket.getPrice(), "Price is zero.");

        //最坏情况
        ticket.setPrice(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, ticket.getPrice());

        //特殊值
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setPrice(Integer.MIN_VALUE);
        });
        assertEquals("Price must not be negative.", exception3.getMessage());
    }

    @ParameterizedTest(name = "{index}: age = {0}, basePrice = {1}, expectedPrice = {2}")
    @CsvSource({
            "14, 1000, 560", // 14岁应用50%折扣和12%税
            "15, 2000, 2240", // 15岁无折扣，有12%税
            "30, 2000, 2240", // 30岁无折扣，有12%税
            "60, 3000, 0",   // 60岁免费
            "61, 1000, 0",  // 61岁免费
            "59, 2000, 2240"  // 59岁无折扣，有税
    })
    @DisplayName("Test discounts based on the age category of the passenger.")
    void testDiscountPrice(int age, int basePrice, int expectedPrice) {
        Ticket ticket = new Ticket(1, basePrice, flight, false, passenger);
        passenger.setAge(age);
        ticket.setTicketStatus(true);
        ticket.setPrice(basePrice);
        assertEquals(expectedPrice, ticket.getPrice());
    }

    @ParameterizedTest(name = "{index}: age = {0}, basePrice = {1}, expectedPrice = {2}")
    @CsvSource({
            "14, 10, 6",
            "13, 8, 5",
            "30, 5, 6",
            "59, 7, 8"
    })
    @DisplayName("Test if the final price is integer.")//向上取整
    void testFinalPrice(int age, int basePrice, int expectedPrice) {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        passenger.setAge(age);
        ticket.setTicketStatus(true);
        ticket.setPrice(basePrice);
        assertEquals(expectedPrice, ticket.getPrice());
    }
}