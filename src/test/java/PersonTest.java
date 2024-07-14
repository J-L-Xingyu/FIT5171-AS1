import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testValidPersonCreation() {
        Person person = new Person("John", "Doe", 30, "Man");
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getSecondName());
        assertEquals(30, person.getAge());
        assertEquals("Man", person.getGender());
    }

    @Test
    public void testInvalidGender1() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("Jane", "Doe", 25, "Al");
        });
        assertEquals("Invalid gender: Al", exception.getMessage());
    }

    @Test
    public void testInvalidGender2() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("Jane", "Doe", 25, "0");
        });
        assertEquals("Invalid gender: 0", exception.getMessage());
    }

    @Test
    public void testNullGender() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("John", "Doe", 30, null);
        });
        assertEquals("Invalid gender: null", exception.getMessage());
    }

    @Test
    public void testInvalidFirstName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("1John", "Doe", 30, "Man");
        });
        assertEquals("Invalid first name: 1John", exception.getMessage());
    }

    @Test
    public void testInvalidSecondName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("John", "#Doe", 30, "Man");
        });
        assertEquals("Invalid second name: #Doe", exception.getMessage());
    }

    @Test
    public void testNegativeAge() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("John", "Doe", -1, "Man");
        });
        assertEquals("Age cannot be negative", exception.getMessage());
    }

    @Test
    public void testExtremeAge() {
        Person person = new Person("John", "Doe", 120, "Man");
        assertEquals(120, person.getAge());
    }

    @Test
    public void testSetAge() {
        Person person = new Person("John", "Doe", 30, "Man");
        person.setAge(25);
        assertEquals(25, person.getAge());
    }

    @Test
    public void testSetGender() {
        Person person = new Person("John", "Doe", 30, "Man");
        person.setGender("Woman");
        assertEquals("Woman", person.getGender());
    }

    @Test
    public void testSetFirstName() {
        Person person = new Person("John", "Doe", 30, "Man");
        person.setFirstName("Alice");
        assertEquals("Alice", person.getFirstName());
    }

    @Test
    public void testSetSecondName() {
        Person person = new Person("John", "Doe", 30, "Man");
        person.setSecondName("Smith");
        assertEquals("Smith", person.getSecondName());
    }

    @Test
    public void testNullFirstName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(null, "Doe", 30, "Man");
        });
        assertEquals("Invalid first name: null", exception.getMessage());
    }

    @Test
    public void testNullSecondName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("John", null, 30, "Man");
        });
        assertEquals("Invalid second name: null", exception.getMessage());
    }

    @Test
    public void testValidGenders() {
        Person person = new Person("John", "Doe", 30, "Man");
        person.setGender("Woman");
        assertEquals("Woman", person.getGender());
        person.setGender("Non-binary | gender diverse");
        assertEquals("Non-binary | gender diverse", person.getGender());
        person.setGender("Prefer not to say");
        assertEquals("Prefer not to say", person.getGender());
        person.setGender("Other");
        assertEquals("Other", person.getGender());
    }

    @Test
    public void testSettingSameValue() {
        Person person = new Person("John", "Doe", 30, "Man");
        person.setFirstName("John");
        assertEquals("John", person.getFirstName());
        person.setSecondName("Doe");
        assertEquals("Doe", person.getSecondName());
        person.setGender("Man");
        assertEquals("Man", person.getGender());
        person.setAge(30);
        assertEquals(30, person.getAge());
    }

}
