import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class FlightCollectionTest {
    @BeforeEach
    void setUp() {
        // 初始化测试环境，清空flights集合
        FlightCollection.flights.clear();

        // 假设有几个有效的航班数据
        ArrayList<Flight> newFlights = new ArrayList<>();
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom =  DateUtils.createTimestamp("01/01/23 12:00:00");
        Timestamp dateTo =  DateUtils.createTimestamp("02/01/23 12:00:00");
        Flight flight1 = new Flight(100, "Tokyo", "New York", "AB111", "Delta", dateFrom, dateTo, airplane);
        Flight flight2 = new Flight(200, "Shanghai", "Beijing", "AB222", "Delta", dateFrom, dateTo, airplane);
        Flight flight3 = new Flight(300, "Shanghai", "Tokyo", "AB333", "Delta", dateFrom, dateTo, airplane);
        newFlights.add(flight1);
        newFlights.add(flight2);
        newFlights.add(flight3);
        FlightCollection.addFlights(newFlights);
    }

    @Test
    public void testAddFlights() {
        assertEquals(3, FlightCollection.getFlights().size());
    }

    @Test
    public void testAddFlights_NoDuplicates() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        Timestamp dateFrom =  DateUtils.createTimestamp("01/01/23 12:00:00");
        Timestamp dateTo =  DateUtils.createTimestamp("02/01/23 12:00:00");
        Flight flight1 = new Flight(100, "Tokyo", "New York", "AB111", "Delta", dateFrom, dateTo, airplane);
        Flight flight4 = new Flight(400, "Tokyo", "New York", "AB444", "Delta", dateFrom, dateTo, airplane);

        ArrayList<Flight> duplicateFlights = new ArrayList<>();
        duplicateFlights.add(flight1);
        duplicateFlights.add(flight4);
        assertDoesNotThrow(() ->FlightCollection.addFlights(duplicateFlights)); // 试图添加重复的航班,不会抛出错误，因为不会重复添加相同航班
        assertEquals(4, FlightCollection.getFlights().size()); // 确认没有添加重复的航班，本来有3个航班，添加两个航班，但是有一个是重复的，所以最后只有4个航班
    }


    @Test
    public void testGetFlightInfoByCities() {
        Flight flight1 = FlightCollection.getFlightInfo("Beijing", "Guangzhou");
        Flight flight2 = FlightCollection.getFlightInfo("Beijing", "Shanghai");
        assertEquals(null,flight1);
        assertEquals("Beijing", flight2.getDepartFrom());
        assertEquals("Shanghai", flight2.getDepartTo());
    }

    @Test
    public void testGetFlightInfoByInvalidCities() {
        assertEquals(null,FlightCollection.getFlightInfo("Beijing", "Guangzhou"));
        assertDoesNotThrow(() ->FlightCollection.getFlightInfo("Guangzhou"));
    }

    @Test
    public void testGetFlightInfoByCity() {
        Flight flight1 = FlightCollection.getFlightInfo("Tokyo");
        Flight flight2 = FlightCollection.getFlightInfo("Nanjing");
        Flight flight3 = FlightCollection.getFlightInfo("Shanghai");
        assertEquals(null,flight2);
        assertEquals("Tokyo", flight1.getDepartTo());
        assertEquals("Shanghai", flight3.getDepartTo());
    }

    @Test
    public void testGetFlightInfoById() {
        Flight flight1 = FlightCollection.getFlightInfo(300);
        Flight flight2 = FlightCollection.getFlightInfo(1000);
        assertEquals(null,flight2);
        assertNotNull(flight1);
        assertEquals(300, flight1.getFlightID());
    }
}

