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
    void setUp() {//在每次测试之前创建
        airplane = new Airplane(1, "Boeing 747",
                50, 200, 10);
        flight = new Flight(1, "New York", "Los Angeles", "NY123",
                "Monash", Timestamp.valueOf("2024-07-01 10:00:00"), Timestamp.valueOf("2024-07-01 12:00:00"), airplane);
        passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
    }

    @Test
    @DisplayName("Test the function of setting and getting a ticket id.")
    void testSetAndGetTicketID() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        ticket.setTicket_id(2);
        assertEquals(2, ticket.getTicket_id());
    }

    @Test
    @DisplayName("Test if a ticket id is valid.")
    void TicketIDBVT() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        //正常边界测试
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

        //最坏情况的健壮性测试
        Ticket ticket1 = new Ticket(2, 1000, flight, false, passenger);
        ticket1.setTicket_id(Integer.MAX_VALUE);
        ticket1.setPrice(0);
        assertEquals(Integer.MAX_VALUE, ticket1.getTicket_id());
        assertEquals(0, ticket1.getPrice());

        //特殊值
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setTicket_id(Integer.MIN_VALUE);
        });
        assertEquals("Ticket id must be greater than 0.", exception3.getMessage());
    }

    @Test
    @DisplayName("Test the function of setting and getting a business class ticket.")
    void testSetAndGetClassVip() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        assertFalse(ticket.getClassVip());
        ticket.setClassVip(true);
        assertTrue(ticket.getClassVip());
    }

    @Test
    @DisplayName("Test the function of setting and getting a ticket status.")
    void testSetAndGetTicketStatus() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        ticket.setTicketStatus(true);
        assertTrue(ticket.ticketStatus());
        ticket.setTicketStatus(false);
        assertFalse(ticket.ticketStatus());
    }

    @Test
    @DisplayName("Test the function of setting and getting a flight.")
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
        // 在Ticket类的setFlight()中定义了flight不能为空
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setFlight(null);
        });
        assertEquals("Flight cannot be null.", exception.getMessage());

        //验证flight中的airplane不能为空
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), null)
                    , false, passenger);
        });
        assertEquals("Airplane cannot be null.", exception1.getMessage());

        //在TicketTest类中验证了flight id的有效性
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000,
                    new Flight(0, "New York", "Los Angeles", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger);
        });
        assertEquals("Flight ID must be greater than 0.", exception2.getMessage());

        //在TicketTest类中验证了Departure的有效性
        Exception departFromException = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000,
                    new Flight(1, "New York", "", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger);
        });
        assertEquals("Departure airport cannot be empty.", departFromException.getMessage());

        //在TicketTest类中验证了Destination的有效性
        Exception departToException = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000,
                    new Flight(1, "", "Los Angeles", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger);
        });
        assertEquals("Destination airport cannot be empty.", departToException.getMessage());

        //在TicketTest类中验证了code的有效性
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger);
        });
        assertEquals("Flight code cannot be empty.", exception3.getMessage());

        //在TicketTest类中验证了company的有效性
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "NY123", "",
                    Timestamp.valueOf("2024-07-08 10:00:00"), Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger);
        });
        assertEquals("Airline company cannot be empty.", exception4.getMessage());

        //在TicketTest类中验证了Departure time的有效性
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                    null, Timestamp.valueOf("2024-07-08 12:00:00"), airplane)
                    , false, passenger);
        });

        //在TicketTest类中验证了arriving time的有效性
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000,
                    new Flight(1, "New York", "Los Angeles", "NY123", "Monash",
                    Timestamp.valueOf("2024-07-08 10:00:00"), null, airplane)
                    , false, passenger);
        });
    }

    @Test
    @DisplayName("Test the function of setting and getting a passenger.")
    void testSetAndGetPassenger() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        Passenger passenger1 = new Passenger("Wendy", "Smith", 24, "Woman",
                "wendy.smith@monash.edu", "0416782345", "A56781234",
                "1234561234567890", 312);
        ticket.setPassenger(passenger1);
        assertEquals(passenger1, ticket.getPassenger());
    }

    @Test
    @DisplayName("Test if a passenger is valid.")
    void testValidPassenger() {
        //在Ticket类的setPassenger()中定义了passenger不能为空
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticket.setPassenger(null);
        });
        assertEquals("Passenger cannot be null.", exception.getMessage());

        //验证了passenger的first name的有效性
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John3", "Doe", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", 123));
        });
        assertEquals("Invalid first name: John3", exception1.getMessage());

        //验证了passenger的second name的有效性
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John", "Doe2", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", 123));
        });
        assertEquals("Invalid second name: Doe2", exception2.getMessage());

        //验证了passenger的Age的有效性
        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John", "Doe", -1,
                    "Man", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", 123));
        });
        assertEquals("Age cannot be negative", exception3.getMessage());

        //验证了passenger的gender的有效性
        IllegalArgumentException exception4 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John", "Doe", 30,
                    "Male", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", 123));
        });
        assertEquals("Invalid gender: Male", exception4.getMessage());

        //在TicketTest类中验证了passenger的email的有效性
        IllegalArgumentException exception5 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John", "Doe", 30,
                    "Man", "invalid-email", "0456789123",
                    "A12345678", "1234567890123456", 123));
        });
        assertEquals("Invalid email format.", exception5.getMessage());

        //在TicketTest类中验证了passenger的phone number的有效性
        IllegalArgumentException exception6 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John", "Doe", 30,
                    "Man", "john.doe@example.com", "invalid-phone-number",
                    "A12345678", "1234567890123456", 123));
        });
        assertTrue(exception6.getMessage().contains("Invalid phone number format."));

        //在TicketTest类中验证了passenger的Passport的有效性
        IllegalArgumentException exception7 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John", "Doe", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A1234567890", "1234567890123456", 123));
        });
        assertTrue(exception7.getMessage().contains("Passport number should not be more than 9 characters long."));

        //在TicketTest类中验证了passenger的card number的有效性
        IllegalArgumentException exception8 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John", "Doe", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A123456789", "", 123));
        });
        assertEquals("All fields are required and must be valid.", exception8.getMessage());

        //在TicketTest类中验证了passenger的security code的有效性
        IllegalArgumentException exception9 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(1, 1000, flight, false,
                    new Passenger("John", "Doe", 30,
                    "Man", "john.doe@example.com", "0456789123",
                    "A12345678", "1234567890123456", -1));
        });
        assertEquals("All fields are required and must be valid.", exception9.getMessage());
    }

    @Test
    @DisplayName("Test the function of setting and getting a ticket price.")
    void testSetAndGetPrice() {
        Ticket ticket = new Ticket(1, 1000, flight, false, passenger);
        assertEquals(1000, ticket.getPrice());
        ticket.setPrice(1500);  // 然后设置价格，以确保状态改变能够触发税率应用
        assertEquals(1500, ticket.getPrice());
    }

    @ParameterizedTest(name = "{index}: status = {0}, basePrice = {1}, expectedPrice = {2}")
    @CsvSource({
            "false, 1000, 1000",   // Ticket not sold, price should not include tax.
            "true, 2000, 2240",   // Ticket sold, price should include tax.
            "false, 2000, 2000"

    })
    @DisplayName("Ensure ticket status correctly affects the price")
    void testTicketStatusAffectsPrice(boolean status, int basePrice, int expectedPrice) {
        Ticket ticket = new Ticket(1, basePrice, flight, false, passenger);
        ticket.setTicketStatus(status); // 先设置状态
        ticket.setPrice(basePrice);  // 然后设置价格，以确保状态改变能够触发税率应用
        assertEquals(expectedPrice, ticket.getPrice());
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

        //正常边界测试
        ticket.setPrice(0);
        assertEquals(0, ticket.getPrice(), "Price is zero.");

        //最坏情况
        Ticket ticket1 = new Ticket(2, 1000, flight, false, passenger);
        ticket1.setPrice(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, ticket1.getPrice());

        //最坏情况的健壮性测试
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Ticket(0, Integer.MAX_VALUE, flight, false, passenger);
        });
        assertEquals("Ticket id must be greater than 0.", exception1.getMessage());

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

    //assignment 2 increase the following test case 1
    @Test
    @DisplayName("Test the toString method of Ticket class.")
    void testToString() {
        Ticket ticket = new Ticket(1, 1000, flight, true, passenger);
        String expectedString = "Ticket{\n" +
                "Price=1000KZT, \n" +
                flight.toString() + "\n" +
                "Vip status=true\n" +
                passenger.toString() + "\n" +
                "Ticket was purchased=false\n}";
        assertEquals(expectedString, ticket.toString(), "Ticket information should be displayed correctly");
    }

    //assignment 2 increase the following test case 2
    @Test
    @DisplayName("Test ticket status change triggers price recalculation.")
    void testTicketStatusChangeTriggersPriceRecalculation() {
        Ticket ticket = new Ticket(1, 1000, flight, true, passenger);
        passenger.setAge(30); // No discount
        ticket.setPrice(1000); // Initial price

        ticket.setTicketStatus(true);
        assertEquals(1120, ticket.getPrice(), "Price should be 1120 after applying 12% service tax when status is true");

        ticket.setTicketStatus(false);
        ticket.setPrice(1000);
        assertEquals(1000, ticket.getPrice(), "Price should revert to original 1000 when status is false");
    }

}