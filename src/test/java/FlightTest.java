import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class FlightTest {
    private Airplane airplane;
    private Timestamp dateFrom;
    private Timestamp dateTo;

    @BeforeEach
    void setUp() {
        airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        dateFrom = createTimestamp("01/01/23 12:00:00");
        dateTo = createTimestamp("02/01/23 12:00:00");
        Date date = new Date();
        System.out.println("-1: " + date.getTime());
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println("0: " + timestamp);
    }

    @Test
    void testAllFieldsRequired() {
        assertDoesNotThrow(() -> new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane));

        assertThrows(IllegalArgumentException.class, () -> new Flight(1, null, "LAX", "AB123", "Delta", dateFrom, dateTo, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, "NYC", null, "AB123", "Delta", dateFrom, dateTo, airplane));
    }

    @Test
    void testValidDateAndTimeFormat() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertDoesNotThrow(() -> flight.setDateFrom(dateFrom));
        assertDoesNotThrow(() -> flight.setDateTo(dateTo));
    }

    @Test
    void testDateFromWithIncorrectFormat() {
        Timestamp incorrectDateFrom = createIncorrectTimestamp("31-12-23 23:59:59"); // Correct the date format here
        Flight flight = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", incorrectDateFrom, incorrectDateFrom, airplane);
        assertDoesNotThrow(() -> flight.setDateFrom(incorrectDateFrom));
    }

    private Timestamp createTimestamp(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        try {
            return new Timestamp(dateFormat.parse(dateStr).getTime());
        } catch (ParseException e) {
            fail("Timestamp creation failed for date: " + dateStr + "; Error: " + e.getMessage());
            return null;
        }
    }

    private Timestamp createIncorrectTimestamp(String dateStr) {
        System.out.println("1: " + dateStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        System.out.println("2: " + dateFormat);
    try {
        System.out.println("3: " + dateFormat.parse(dateStr).getTime());
        System.out.println("4: " + new Timestamp(dateFormat.parse(dateStr).getTime()));
        return new Timestamp(dateFormat.parse(dateStr).getTime());
    } catch (ParseException e) {
        fail("Timestamp creation failed for date: " + dateStr + "; Error: " + e.getMessage());
        return null; // 这行确实不会执行，因为fail将抛出一个异常
    }
}
}
