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
        dateFrom = DateUtils.createTimestamp("01/01/23 12:00:00");
        dateTo =  DateUtils.createTimestamp("02/01/23 12:00:00");
    }

    @Test
    void testAllFieldsRequired() {
        assertDoesNotThrow(() -> new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane));

        assertThrows(IllegalArgumentException.class, () -> new Flight(0, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, null, "LAX", "AB123", "Delta", dateFrom, dateTo, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, "NYC", null, "AB123", "Delta", dateFrom, dateTo, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, "NYC", "LAX", null, "Delta", dateFrom, dateTo, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, "NYC", "LAX", "AB123", null, dateFrom, dateTo, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, "NYC", "LAX", "AB123", "Delta", null, dateTo, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, null, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, null));
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

    @Test
    void testFlightIDNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Flight(-1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane));
    }

    @Test
    void testSetDepartTo() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertThrows(IllegalArgumentException.class, () -> flight.setDepartTo(null));
        assertThrows(IllegalArgumentException.class, () -> flight.setDepartTo(""));
        assertDoesNotThrow(() -> flight.setDepartTo("SFO"));
    }

    @Test
    void testSetDepartFrom() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertThrows(IllegalArgumentException.class, () -> flight.setDepartFrom(null));
        assertThrows(IllegalArgumentException.class, () -> flight.setDepartFrom(""));
        assertDoesNotThrow(() -> flight.setDepartFrom("JFK"));
    }

    @Test
    void testSetCode() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertThrows(IllegalArgumentException.class, () -> flight.setCode(null));
        assertThrows(IllegalArgumentException.class, () -> flight.setCode(""));
        assertDoesNotThrow(() -> flight.setCode("XY789"));
    }

    @Test
    void testSetCompany() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertThrows(IllegalArgumentException.class, () -> flight.setCompany(null));
        assertThrows(IllegalArgumentException.class, () -> flight.setCompany(""));
        assertDoesNotThrow(() -> flight.setCompany("American Airlines"));
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

    try {

        return new Timestamp(dateFormat.parse(dateStr).getTime());
    } catch (ParseException e) {
        fail("Timestamp creation failed for date: " + dateStr + "; Error: " + e.getMessage());
        return null; // 这行确实不会执行，因为fail将抛出一个异常
        }
    }

    // Getter method tests

    @Test
    void testGetFlightID() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertEquals(1, flight.getFlightID());
    }

    @Test
    void testGetDepartTo() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertEquals("NYC", flight.getDepartTo());
    }

    @Test
    void testGetDepartFrom() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertEquals("LAX", flight.getDepartFrom());
    }

    @Test
    void testGetCode() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertEquals("AB123", flight.getCode());
    }

    @Test
    void testGetCompany() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertEquals("Delta", flight.getCompany());
    }

    @Test
    void testGetDateFrom() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertEquals(dateFrom, flight.getDateFrom());
    }

    @Test
    void testGetDateTo() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertEquals(dateTo, flight.getDateTo());
    }

    @Test
    void testGetAirplane() {
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertEquals(airplane, flight.getAirplane());
    }
}