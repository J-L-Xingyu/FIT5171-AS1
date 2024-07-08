import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    private Set<Flight> flightSet;

    @BeforeEach
    void setUp() {
        flightSet = new HashSet<>();
    }

    @Test
    void testAddFlightWithAllFields() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", dateFrom, dateTo, airplane);

        assertTrue(addFlight(flight), "Flight should be added successfully");
    }

    @Test
    void testAddFlightWithoutAllFields() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight = new Flight(0, "New York", "Los Angeles", "NY123", "Delta", dateFrom, dateTo, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to missing flightID");
    }

    @Test
    void testAddFlightWithoutDepartTo() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight = new Flight(1, null, "Los Angeles", "NY123", "Delta", dateFrom, dateTo, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to missing departTo");
    }

    @Test
    void testAddFlightWithoutDepartFrom() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight = new Flight(1, "New York", null, "NY123", "Delta", dateFrom, dateTo, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to missing departFrom");
    }

    @Test
    void testAddFlightWithoutCode() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight = new Flight(1, "New York", "Los Angeles", null, "Delta", dateFrom, dateTo, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to missing code");
    }

    @Test
    void testAddFlightWithoutCompany() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight = new Flight(1, "New York", "Los Angeles", "NY123", null, dateFrom, dateTo, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to missing company");
    }

    @Test
    void testAddFlightWithoutDateFrom() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", null, dateTo, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to missing dateFrom");
    }

    @Test
    void testAddFlightWithoutDateTo() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");

        Flight flight = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", dateFrom, null, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to missing dateTo");
    }

    @Test
    void testAddFlightWithoutAirplane() {
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", dateFrom, dateTo, null);

        assertFalse(addFlight(flight), "Flight should not be added due to missing airplane");
    }

    @Test
    void testAddFlightWithInvalidDateFormat() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp invalidDateFrom = parseTimestamp("2024-01-01 10:00:00");
        Timestamp invalidDateTo = parseTimestamp("2024-01-01 12:00:00");

        Flight flight = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", invalidDateFrom, invalidDateTo, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to invalid date format");
    }

    @Test
    void testAddFlightWithInvalidTimeFormat() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp invalidDateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp invalidDateTo = parseTimestamp("01/01/24 25:00:00");

        Flight flight = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", invalidDateFrom, invalidDateTo, airplane);

        assertFalse(addFlight(flight), "Flight should not be added due to invalid time format");
    }

    @Test
    void testAddDuplicateFlight() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom = parseTimestamp("01/01/24 10:00:00");
        Timestamp dateTo = parseTimestamp("01/01/24 12:00:00");

        Flight flight1 = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", dateFrom, dateTo, airplane);
        Flight flight2 = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", dateFrom, dateTo, airplane);

        assertTrue(addFlight(flight1), "First flight should be added successfully");
        assertFalse(addFlight(flight2), "Duplicate flight should not be added");
    }

    private boolean addFlight(Flight flight) {
        if (flight.getFlightID() == 0 || flight.getDepartTo() == null || flight.getDepartFrom() == null ||
                flight.getCode() == null || flight.getCompany() == null ||
                flight.getDateFrom() == null || flight.getDateTo() == null ||
                flight.getAirplane() == null) {
            System.out.println("All fields are required.");
            return false;
        }

        if (!isValidDateFormat(flight.getDateFrom()) || !isValidDateFormat(flight.getDateTo())) {
            System.out.println("Date must be in DD/MM/YY format.");
            return false;
        }

        if (!isValidTimeFormat(flight.getDateFrom()) || !isValidTimeFormat(flight.getDateTo())) {
            System.out.println("Time must be in HH:MM:SS format.");
            return false;
        }

        for (Flight f : flightSet) {
            if (f.getFlightID() == flight.getFlightID()) {
                System.out.println("The same flight is already in the system.");
                return false;
            }
        }

        flightSet.add(flight);
        System.out.println("Flight added successfully.");
        return true;
    }

    private boolean isValidDateFormat(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateFormat.format(timestamp));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidTimeFormat(Timestamp timestamp) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        timeFormat.setLenient(false);
        try {
            timeFormat.parse(timeFormat.format(timestamp));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private Timestamp parseTimestamp(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            dateFormat.setLenient(false);
            return new Timestamp(dateFormat.parse(dateString).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}
