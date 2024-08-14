import java.util.logging.Logger;
public class Passenger extends Person {
    private static final Logger logger = Logger.getLogger(Passenger.class.getName());
    private String email;
    private String phoneNumber;
    private String cardNumber;
    private int securityCode;
    private String passport;

    public Passenger() {}

    private boolean isValidPhoneNumber(String phoneNumber) {
        // 正则表达式验证电话号码是否符合澳大利亚的格式
        return phoneNumber.matches("^0[45]\\d{8}$") || phoneNumber.matches("^\\+61 [45]\\d{2} \\d{3} \\d{3}$");
    }

    private boolean isValidEmail(String email) {
        // 正则表达式验证电子邮件是否符合有效的模式
        return email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    private boolean isValidPassport(String passport) {
        return passport != null && passport.length() <= 9;
    }

    private void validateFields(String email, String phoneNumber, String passport, String cardNumber, int securityCode) {
        if (email == null || email.isEmpty() ||
                phoneNumber == null || phoneNumber.isEmpty() ||
                passport == null || passport.isEmpty() ||
                cardNumber == null || cardNumber.isEmpty() ||
                securityCode <= 0) {
            throw new IllegalArgumentException("All fields are required and must be valid.");
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (!isValidPassport(passport)) {
            throw new IllegalArgumentException("Passport number should not be more than 9 characters long.");
        }
    }

    public Passenger(String firstName, String secondName, int age, String gender,
                     String email, String phoneNumber, String passport,
                     String cardNumber, int securityCode) {
        super(firstName, secondName, age, gender);
        validateFields(email, phoneNumber, passport, cardNumber, securityCode);
        this.securityCode = securityCode;
        this.cardNumber = cardNumber;
        this.passport = passport;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

//    public void setEmail(String email) {
//        if (!isValidEmail(email)) {
//            throw new IllegalArgumentException("Invalid email format.");
//        }
//        this.email = email;
//    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            logger.severe("Invalid email format: " + email);  // 记录无效输入
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        if (!isValidPassport(passport)) {
            throw new IllegalArgumentException("Passport number should not be more than 9 characters long.");
        }
        this.passport = passport;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        if (securityCode <= 0) {
            throw new IllegalArgumentException("Security code must be positive.");
        }
        this.securityCode = securityCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty.");
        }
        this.cardNumber = cardNumber;
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public String getSecondName() {
        return super.getSecondName();
    }

    @Override
    public void setSecondName(String secondName) {
        super.setSecondName(secondName);
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    @Override
    public void setGender(String gender) {
        super.setGender(gender);
    }

    @Override
    public String toString() {
        return "Passenger{" + " Fullname= " + super.getFirstName() + " " + super.getSecondName() +
                " ,email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}
