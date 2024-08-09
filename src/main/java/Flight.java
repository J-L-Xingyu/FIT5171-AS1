import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Flight {
    private int flightID;
    private String departTo;
    private String departFrom;
    private String code;
    private String company;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private Airplane airplane;

    public Flight() {
    }

    public Flight(int flightID, String departTo, String departFrom, String code, String company, Timestamp dateFrom, Timestamp dateTo, Airplane airplane) {
        setFlightID(flightID);
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

    public void setFlightID(int flightID) {
        if (flightID <= 0) {
            throw new IllegalArgumentException("Flight ID must be greater than 0.");
        }
        this.flightID = flightID;
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

    public Airplane getAirplane() {
        if (airplane == null) {
            throw new IllegalArgumentException("Invalid Airplane.");
        }
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        if (airplane == null) {
            throw new IllegalArgumentException("Airplane cannot be null.");
        }
        this.airplane = airplane;
    }

    private void validateDatestamp(Timestamp timestamp, String fieldName) {
        if (timestamp == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateTimeFormat.setLenient(false);
        try {
            String formattedDate = dateTimeFormat.format(timestamp);
            dateTimeFormat.parse(formattedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(fieldName + " is not a valid date/time.");
        }
    }
}
