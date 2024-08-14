import java.util.logging.Logger;

public class Person {
    private String firstName;  // 名字
    private String secondName;  // 姓氏
    private int age;  // 年龄
    private Gender gender;  // 性别

    // 定义性别枚举，限定性别的取值范围
    public enum Gender {
        WOMAN("Woman"),
        MAN("Man"),
        NON_BINARY("Non-binary | gender diverse"),
        PREFER_NOT_TO_SAY("Prefer not to say"),
        OTHER("Other");

        private final String gender;

        Gender(String gender) {
            this.gender = gender;
        }

        public String getGender() {
            return gender;
        }

        // 从字符串转换为Gender枚举
        public static Gender fromString(String gender) {
            for (Gender g : Gender.values()) {
                if (g.gender.equalsIgnoreCase(gender)) {
                    return g;
                }
            }
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
    }

    // 无参构造方法
    public Person(){}

    // 带参构造方法
    public Person(String firstName, String secondName, int age, String gender) {
        setFirstName(firstName);  // 设置名字并验证
        setSecondName(secondName);  // 设置姓氏并验证
        setAge(age);  // 设置年龄
        setGender(gender);  // 设置性别并验证
    }

    // 获取年龄
    public int getAge() {
        return age;
    }

    // 设置年龄
//    public void setAge(int age) {
//        if (age < 0) {
//            throw new IllegalArgumentException("Age cannot be negative");  // 年龄不能为负数
//        }
//        this.age = age;
//    }
    //If an invalid age is provided, the error is logged before throwing an exception
    public void setAge(int age) {
        if (age < 0) {
           // Logger.getAnonymousLogger("Attempted to set negative age: " + age);
            throw new IllegalArgumentException("Age cannot be negative. Received age: " + age);
        }
        this.age = age;
    }


    // 获取性别
    public String getGender() {
        return gender.getGender();
    }

    // 设置性别并验证
    public void setGender(String gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Invalid gender: null");
        }
        this.gender = Gender.fromString(gender);
    }

    // 获取名字
    public String getFirstName() {
        return firstName;
    }

    // 设置名字并验证
//    public void setFirstName(String firstName) {
//        if (firstName == null || !firstName.matches("^[a-zA-Z]+$")) {
//            throw new IllegalArgumentException("Invalid first name: " + firstName);  // 名字只能包含字母
//        }
//        this.firstName = firstName;
//    }
//providing a more detailed error message when setting the first name.
    public void setFirstName(String firstName) {
        if (firstName == null || !firstName.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Invalid first name: " + firstName + ". First name must only contain letters and cannot be null.");
        }
        this.firstName = firstName;
    }


    // 获取姓氏
    public String getSecondName() {
        return secondName;
    }

    // 设置姓氏并验证
    public void setSecondName(String secondName) {
        if (secondName == null || !secondName.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Invalid second name: " + secondName);  // 姓氏只能包含字母
        }
        this.secondName = secondName;
    }

    // 重写toString方法，返回对象的字符串表示形式
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", gender='" + gender.getGender() + '\'' +
                '}';
    }


}
