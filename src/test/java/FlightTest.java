import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;


public class FlightTest {
    private Airplane airplane;
    private Timestamp dateFrom;
    private Timestamp dateTo;

    @BeforeEach
    void setUp() {
        airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        dateFrom = parseTimestamp("01/01/24 10:00:00");
        dateTo = parseTimestamp("01/01/24 12:00:00");
    }

    @Test
    void testAllFieldsRequired() {
        // 确认所有参数均提供时不抛出异常
        assertDoesNotThrow(() -> new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane));

        // 测试每个参数为空时是否抛出异常
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, null, "LAX", "AB123", "Delta", dateFrom, dateTo, airplane));
        assertThrows(IllegalArgumentException.class, () -> new Flight(1, "NYC", null, "AB123", "Delta", dateFrom, dateTo, airplane));
        // 继续为其他字段添加类似的测试
    }

    @Test
    void testValidDateAndTimeFormat() {
        // 使用@BeforeEach中设置的dateFrom和dateTo进行测试
        Flight flight = new Flight(1, "NYC", "LAX", "AB123", "Delta", dateFrom, dateTo, airplane);
        assertDoesNotThrow(() -> flight.setDateFrom(dateFrom));
        assertDoesNotThrow(() -> flight.setDateTo(dateTo));
    }

    private Timestamp parseTimestamp(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateFormat.setLenient(false); // 设置为非宽松模式，确保日期必须符合指定的格式
        try {
            Date parsedDate = dateFormat.parse(dateString);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date format is invalid: " + dateString);
        }
    }

}
