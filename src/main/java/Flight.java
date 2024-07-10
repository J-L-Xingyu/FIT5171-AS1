import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;


public class Flight {
    private int flightID;
    private String departTo;
    private String departFrom;
    private String code;
    private String company;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    Airplane airplane;

    public Flight(){}

    public Flight(int flight_id, String departTo, String departFrom, String code, String company, Timestamp dateFrom,Timestamp dateTo,Airplane airplane)
    {
            setFlightID(flight_id);
            setDepartTo(departTo);
            setDepartFrom(departFrom);
            setCode(code);
            setCompany(company);
            setDateFrom(dateFrom);
            setDateTo(dateTo);
            setAirplane(airplane);
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightid) {
        if (flightid <= 0) {
            throw new IllegalArgumentException("Flight ID must be greater than 0.");
        }
        this.flightID = flightid;
    }

    public String getDepartTo() {
        return departTo;
    }

    public void setDepartTo(String departTo) {
        if (departTo == null || departTo.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination airport cannot be empty.");
        }
        this.departTo = departTo;
    }

    public String getDepartFrom() {
        return departFrom;
    }

    public void setDepartFrom(String departFrom) {
        if (departFrom == null || departFrom.trim().isEmpty()) {
            throw new IllegalArgumentException("Departure airport cannot be empty.");
        }
        this.departFrom = departFrom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight code cannot be empty.");
        }
        this.code = code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        if (company == null || company.trim().isEmpty()) {
            throw new IllegalArgumentException("Airline company cannot be empty.");
        }
        this.company = company;
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        validateDatestamp(dateFrom, "Date from");
        this.dateFrom = dateFrom;
    }

    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        validateDatestamp(dateTo, "Date to");
        this.dateTo = dateTo;
    }

    private void validateDatestamp(Timestamp timestamp, String fieldName) {
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    dateTimeFormat.setLenient(false);
    try {
        // 直接格式化 Timestamp 对象
        String formattedDate = dateTimeFormat.format(timestamp);
        // 尝试解析格式化后的字符串，确认其符合预期的日期时间格式
        dateTimeFormat.parse(formattedDate);
    } catch (ParseException e) {
        throw new IllegalArgumentException(fieldName + " must be in the correct format (DD/MM/YY HH:MM:SS).");
    }
}

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public String toString()
    {
            return "Flight{" + airplane.toString() +
                    ", date to=" +  getDateTo() + ", " + '\'' +
                    ", date from='" + getDateFrom() + '\'' +
                    ", depart from='" + getDepartFrom() + '\'' +
                    ", depart to='" + getDepartTo() + '\'' +
                    ", code=" + getCode() + '\'' +
                    ", company=" + getCompany() + '\'' +
                    ", code=" + getCode() + '\'' +
                    '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightID == flight.flightID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightID);
    }

}
