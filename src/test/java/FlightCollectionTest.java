import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FlightCollectionTest {

    private Airplane airplane;
    private Flight flight1;
    private Flight flight2;
    private Flight flight3;

    @BeforeEach
    void setUp() {
        airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        flight1 = new Flight(1, "New York", "Los Angeles", "NY123", "Delta", parseTimestamp("01/01/24 10:00:00"), parseTimestamp("01/01/24 12:00:00"), airplane);
        flight2 = new Flight(2, "San Francisco", "New York", "SF123", "United", parseTimestamp("02/01/24 11:00:00"), parseTimestamp("02/01/24 13:00:00"), airplane);
        flight3 = new Flight(3, "Los Angeles", "San Francisco", "LA123", "Southwest", parseTimestamp("03/01/24 09:00:00"), parseTimestamp("03/01/24 11:00:00"), airplane);

        FlightCollection.getFlights().clear(); // Clear the collection before each test
    }

    @Test
    void testAddFlights() {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);

        FlightCollection.addFlights(flights);

        assertEquals(2, FlightCollection.getFlights().size(), "Flights should be added to the collection");
    }

    @Test
    void testValidCityNames() {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(flight1);

        FlightCollection.addFlights(flights);

        assertNotNull(FlightCollection.getFlightInfo("New York", "Los Angeles"), "Valid cities should return a flight");
        assertNull(FlightCollection.getFlightInfo("InvalidCity", "Los Angeles"), "Invalid cities should not return a flight");
    }

    @Test
    void testGetFlightInfoByCity() {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);

        FlightCollection.addFlights(flights);

        assertEquals(flight1, FlightCollection.getFlightInfo("Los Angeles"), "Should return flight arriving at Los Angeles");
        assertNull(FlightCollection.getFlightInfo("InvalidCity"), "Should return null for an invalid city");
    }

    @Test
    void testGetFlightInfoByFlightID() {
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);

        FlightCollection.addFlights(flights);

        assertEquals(flight1, FlightCollection.getFlightInfo(1), "Should return flight with ID 1");
        assertNull(FlightCollection.getFlightInfo(999), "Should return null for a non-existent flight ID");
    }

    private Timestamp parseTimestamp(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            return new Timestamp(dateFormat.parse(dateString).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}
