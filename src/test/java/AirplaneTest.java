import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AirplaneTest {

    @Test
    void testAirplaneCreation() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        assertThrows(IllegalArgumentException.class, () -> new Airplane(-1, "Boeing 747", 50, 200, 10));
        assertThrows(IllegalArgumentException.class, () -> new Airplane(0, "Boeing 747", 50, 200, 10)); // 新增测试用例
        assertEquals(1, airplane.getAirplaneID(), "Airplane ID test failed");
        assertEquals("Boeing 747", airplane.getAirplaneModel(), "Airplane Model test failed");
        assertEquals(50, airplane.getBusinessSitsNumber(), "Business Seats Number test failed");
        assertEquals(200, airplane.getEconomySitsNumber(), "Economy Seats Number test failed");
        assertEquals(10, airplane.getCrewSitsNumber(), "Crew Seats Number test failed");

    }
    @Test
void testValidBusinessSitsNumber() {
    Airplane airplane = new Airplane(1, "Boeing 747", 1, 200, 10);
    airplane.setBusinessSitsNumber(300);
    assertEquals(300, airplane.getBusinessSitsNumber(), "Valid business seats number test failed");
}

@Test
void testValidEconomySitsNumber() {
    Airplane airplane = new Airplane(1, "Boeing 747", 50, 1, 10);
    airplane.setEconomySitsNumber(300);
    assertEquals(300, airplane.getEconomySitsNumber(), "Valid economy seats number test failed");
}

@Test
void testValidCrewSitsNumber() {
    Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 1);
    airplane.setCrewSitsNumber(300);
    assertEquals(300, airplane.getCrewSitsNumber(), "Valid crew seats number test failed");
}


    @Test
    void testInvalidBusinessSitsNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 350, 200, 10);
        }, "Business Seats Number range test failed - should throw exception");
    }

    @Test
    void testInvalidEconomySitsNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 50, 350, 10);
        }, "Economy Seats Number range test failed - should throw exception");
    }

    @Test
    void testInvalidCrewSitsNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 50, 200, 350);
        }, "Crew Seats Number range test failed - should throw exception");
    }

    @Test
    void testSetBusinessSitsNumber() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            airplane.setBusinessSitsNumber(350);
        }, "Set Business Seats Number range test failed - should throw exception");
    }

    @Test
    void testSetEconomySitsNumber() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            airplane.setEconomySitsNumber(350);
        }, "Set Economy Seats Number range test failed - should throw exception");
    }

    @Test
    void testSetCrewSitsNumber() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            airplane.setCrewSitsNumber(350);
        }, "Set Crew Seats Number range test failed - should throw exception");
    }

    @Test
    void testToString() {
        Airplane airplane = new Airplane(1, "Boeing 747", 50, 200, 10);
        String expected = "Airplane{model=Boeing 747', business sits=50', economy sits=200', crew sits=10'}";
        assertEquals(expected, airplane.toString(), "toString method test failed");
    }

}
