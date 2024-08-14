import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PassengerTest {

    // 测试所有字段都设置正确的情况下，Passenger对象能够成功创建
    @Test
    void testPassengerCreationWithAllFields() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertNotNull(passenger);
        assertEquals("John", passenger.getFirstName());
        assertEquals("Doe", passenger.getSecondName());
        assertEquals(30, passenger.getAge());
        assertEquals("Man", passenger.getGender());
        assertEquals("john.doe@example.com", passenger.getEmail());
        assertEquals("0412345678", passenger.getPhoneNumber());
        assertEquals("A12345678", passenger.getPassport());
        assertEquals("1234567890123456", passenger.getCardNumber());
        assertEquals(123, passenger.getSecurityCode());
    }

    // 测试缺少电子邮件字段时，Passenger对象的创建
    @Test
    void testPassengerCreationWithoutEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    null, "0412345678", "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试缺少电话号码字段时，Passenger对象的创建
    @Test
    void testPassengerCreationWithoutPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example.com", null, "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试缺少护照字段时，Passenger对象的创建
    @Test
    void testPassengerCreationWithoutPassport() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example.com", "0412345678", null,
                    "1234567890123456", 123);
        });
    }

    // 测试缺少信用卡号字段时，Passenger对象的创建
    @Test
    void testPassengerCreationWithoutCardNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example.com", "0412345678", "A12345678",
                    null, 123);
        });
    }

    // 测试缺少信用卡安全码字段时，Passenger对象的创建
    @Test
    void testPassengerCreationWithoutSecurityCode() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example.com", "0412345678", "A12345678",
                    "1234567890123456", 0);
        });
    }

    // 测试缺少所有字段时，Passenger对象的创建
    @Test
    void testPassengerCreationWithoutAnyField() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger(null, null, 0, null,
                    null, null, null,
                    null, 0);
        });
    }

    // 测试有效的澳大利亚国内电话号码
    @Test
    void testValidAustralianPhoneNumber() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertEquals("0412345678", passenger.getPhoneNumber());
    }

    // 测试有效的国际格式电话号码
    @Test
    void testValidInternationalPhoneNumber() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "+61 412 345 678", "A12345678",
                "1234567890123456", 123);
        assertEquals("+61 412 345 678", passenger.getPhoneNumber());
    }

    // 测试无效的电话号码（不符合澳大利亚格式）
    @Test
    void testInvalidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example.com", "1234567890", "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试无效的国际格式电话号码
    @Test
    void testInvalidInternationalPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example.com", "+62 412 345 678", "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试有效的电子邮件地址
    @Test
    void testValidEmail() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertEquals("john.doe@example.com", passenger.getEmail());
    }

    // 测试无效的电子邮件地址（缺少@符号）
    @Test
    void testInvalidEmailWithoutAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "johndoe.example.com", "0412345678", "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试无效的电子邮件地址（缺少域名）
    @Test
    void testInvalidEmailWithoutDomain() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@", "0412345678", "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试无效的电子邮件地址（缺少用户名）
    @Test
    void testInvalidEmailWithoutUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "@example.com", "0412345678", "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试无效的电子邮件地址（缺少顶级域名）
    @Test
    void testInvalidEmailWithoutTopLevelDomain() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example", "0412345678", "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试有效的护照号码（不超过9个字符）
    @Test
    void testValidPassport() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertEquals("A12345678", passenger.getPassport());
    }

    // 测试无效的护照号码（超过9个字符）
    @Test
    void testInvalidPassport() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example.com", "0412345678", "A1234567890",
                    "1234567890123456", 123);
        });
    }

    // 测试护照号码（恰好为9个字符）
    @Test
    void testExactlyNineCharacterPassport() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A1234567B",
                "1234567890123456", 123);
        assertEquals("A1234567B", passenger.getPassport());
    }

    // 测试无效的护照号码（空字符串）
    @Test
    void testEmptyPassport() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", 30, "Man",
                    "john.doe@example.com", "0412345678", "",
                    "1234567890123456", 123);
        });
    }

    // 测试设置无效电子邮件
    @Test
    void testSetInvalidEmail() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertThrows(IllegalArgumentException.class, () -> {
            passenger.setEmail("invalid.email");
        });
    }

    // 测试设置无效电话号码
    @Test
    void testSetInvalidPhoneNumber() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertThrows(IllegalArgumentException.class, () -> {
            passenger.setPhoneNumber("invalid_phone");
        });
    }


    // 测试设置无效安全码
    @Test
    void testSetInvalidSecurityCode() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertThrows(IllegalArgumentException.class, () -> {
            passenger.setSecurityCode(-123);
        });
    }

    // 测试设置无效信用卡号
    @Test
    void testSetInvalidCardNumber() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertThrows(IllegalArgumentException.class, () -> {
            passenger.setCardNumber("");
        });
    }

    // 测试设置有效电话号码
    @Test
    void testSetValidPhoneNumber() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        passenger.setPhoneNumber("0412345679");
        assertEquals("0412345679", passenger.getPhoneNumber());
    }

    // 测试设置有效护照号码
    @Test
    void testSetValidPassport() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        passenger.setPassport("B12345678");
        assertEquals("B12345678", passenger.getPassport());
    }

    // 测试设置有效安全码
    @Test
    void testSetValidSecurityCode() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        passenger.setSecurityCode(456);
        assertEquals(456, passenger.getSecurityCode());
    }

    // 测试设置有效信用卡号
    @Test
    void testSetValidCardNumber() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        passenger.setCardNumber("6543210987654321");
        assertEquals("6543210987654321", passenger.getCardNumber());
    }

    // 测试年龄验证
    @Test
    void testSetValidAge() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        passenger.setAge(25);
        assertEquals(25, passenger.getAge());
    }

    // 测试负年龄
    @Test
    void testSetInvalidNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Passenger("John", "Doe", -5, "Man",
                    "john.doe@example.com", "0412345678", "A12345678",
                    "1234567890123456", 123);
        });
    }

    // 测试设置有效的性别
    @Test
    void testSetValidGender() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        passenger.setGender("Woman");
        assertEquals("Woman", passenger.getGender());
    }

    @Test
    public void testSetInvalidGender() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            passenger.setGender("Alien");
        });
        assertEquals("Invalid gender: Alien", exception.getMessage());
    }

    @Test
    void testSetSecurityCodeWithZero() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertThrows(IllegalArgumentException.class, () -> {
            passenger.setSecurityCode(0);
        });
    }

    @Test
    void testSetSecurityCodeWithNegativeValue() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        assertThrows(IllegalArgumentException.class, () -> {
            passenger.setSecurityCode(-1);
        });
    }

    @Test
    void testToStringMethod() {
        Passenger passenger = new Passenger("John", "Doe", 30, "Man",
                "john.doe@example.com", "0412345678", "A12345678",
                "1234567890123456", 123);
        String expectedOutput = "Passenger{ Fullname= John Doe ,email='john.doe@example.com', phoneNumber='0412345678', passport='A12345678'}";
        assertEquals(expectedOutput, passenger.toString());
    }


}
