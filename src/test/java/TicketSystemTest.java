import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

class TicketSystemTest {

    private TicketSystem ticketSystem;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        ticketSystem = new TicketSystem();
        // 初始化测试环境，清空flights集合
        FlightCollection.flights.clear();
        TicketCollection.clearTickets();
        // 假设有几个有效的航班数据
        ArrayList<Flight> newFlights = new ArrayList<>();
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom =  DateUtils.createTimestamp("01/01/23 12:00:00");
        Timestamp dateTo =  DateUtils.createTimestamp("02/01/23 12:00:00");
        Flight flight1 = new Flight(1, "Tokyo", "New York", "AB111", "Delta", dateFrom, dateTo, airplane);
        Flight flight2 = new Flight(2, "Beijing", "New York", "AB222", "Delta", dateFrom, dateTo, airplane);
        Flight flight3 = new Flight(3, "Shanghai", "Tokyo", "AB333", "Delta", dateFrom, dateTo, airplane);
        newFlights.add(flight1);
        newFlights.add(flight2);
        newFlights.add(flight3);
        FlightCollection.addFlights(newFlights);
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        Ticket ticket1 = new Ticket(1, 1000, flight1, false, passenger);
        TicketCollection.addTicket(ticket1);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreSystemInputOutput() {
        System.setIn(System.in); // 恢复System.in到原始输入流
        System.setOut(System.out); // 恢复System.out到原始输出流
        outContent.reset(); // 重置输出流
    }

    @Test
    @DisplayName("Test choosing a ticket with at least one invalid city")
    void testChooseTicketWithInvalidCity() throws Exception {
        assertThrows(IllegalArgumentException.class, () ->ticketSystem.chooseTicket("InvalidCity", "Los Angeles"));
        assertThrows(IllegalArgumentException.class, () ->ticketSystem.chooseTicket("", "Los Angeles"));
        assertThrows(IllegalArgumentException.class, () ->ticketSystem.chooseTicket("l", "Los"));
    }

    @Test
    @DisplayName("Test choosing an already booked ticket")
    void testChooseAlreadyBookedTicket() throws Exception {
        // 假设票已被预订
        TicketCollection.getTicketInfo(1).setTicketStatus(true);
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThrows(IllegalArgumentException.class, () ->ticketSystem.chooseTicket("New York", "Tokyo"));
//        ticketSystem.chooseTicket("New York", "Tokyo");
//        assertTrue(outContent.toString().contains("This ticket is already booked"));
    }

    @Test
    @DisplayName("Test invalid ticket")
    void testInvalidID() throws Exception {
        Scanner in = new Scanner(System.in);
        assertThrows(IllegalArgumentException.class, () -> ticketSystem.buyTicket(99,in));
    }

    @Test
    @DisplayName("Test purchasing a ticket with a layover when no direct flights are available")
    void testPurchaseTicketWithLayover() throws Exception {
        // 捕获输出以验证预期的购买成功消息
        outContent.reset();
        ticketSystem.chooseTicket("New York", "Shanghai");
        String expectedOutput = "There is special way to go there. And it is transfer way";
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    @DisplayName("Test purchasing a ticket when no direct or layover flights are available")
    void testPurchaseTicketWithoutDirectOrLayover() throws Exception {
        // 捕获输出以验证预期的错误消息
        outContent.reset();
        ticketSystem.chooseTicket("New York", "Los Angeles");
        String expectedOutput = "There are no possible variants";
        assertTrue(outContent.toString().contains(expectedOutput));
    }


    @Test
    @DisplayName("Test invalid passenger information")
    void testInvalidPassengerInformation(){
        // 模拟无效的用户输入（包括空值和无效格式）
        String input = "\n\n\n\n\n\n\n\n0\n\n\n"; // 注意这里的0表示不购买
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        // 期望代码在处理无效输入时抛出IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> ticketSystem.buyTicket(1,in));
    }

    @Test
    @DisplayName("Test valid passenger information")
    void testValidPassengerInformation() throws Exception {
        String input = "John\nDoe\n-2\nMan\njohn.doe@example.com\n0412345678\nA12345678\n1\n1234567890123456\n123\n"; // 输入有效信息
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        Exception exception = assertThrows(IllegalArgumentException.class,() -> ticketSystem.buyTicket(1,in));
        assertEquals("Age cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Test user can cancel the process when buying ticket")
    void testCancelThePurchaseWhenBuyingTicket() throws Exception {
        // 模拟有效的用户输入
        String input = "John\nDoe\n30\nMan\njohn.doe@example.com\n0412345678\nA12345678\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Scanner in = new Scanner(System.in);
        ticketSystem.buyTicket(1,in);
        assertTrue(outContent.toString().contains("Successfully canceled the purchase."));
    }


    @Test
    @DisplayName("Test display correct value when buying ticket")
    void testDisplayCorrectValueWhenBuyingTicket() throws Exception {
        // 模拟有效的用户输入
        String input = "John\nDoe\n30\nMan\njohn.doe@example.com\n0412345678\nA12345678\n1\n1234567890123456\n123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Scanner in = new Scanner(System.in);
        ticketSystem.buyTicket(1,in);
        // 验证账单金额是否正确显示
        assertTrue(outContent.toString().contains("Your bill: 1120\n"));
    }


}
