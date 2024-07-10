import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class DateUtils {

    public static Timestamp createTimestamp(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        try {
            return new Timestamp(dateFormat.parse(dateStr).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("Timestamp creation failed for date: " + dateStr + "; Error: " + e.getMessage());
        }
    }
}


