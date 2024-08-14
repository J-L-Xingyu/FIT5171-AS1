// contribute by Huiyi Chen
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightCollection {

    private static ArrayList<Flight> flights = new ArrayList<>();
    private static Map<Integer, Flight> flightByIdMap = new HashMap<>();
    private static Map<String, List<Flight>> flightsByDepartFrom = new HashMap<>();
    private static Map<String, List<Flight>> flightsByDepartTo = new HashMap<>();

    public static ArrayList<Flight> getFlights() {
        return flights;
    }

    public static void print(ArrayList<Flight> flights) {
        for (Flight flight : flights) {
            System.out.println(flight.toString());
        }
    }

    public static void addFlights(ArrayList<Flight> newFlights) {
        for (Flight flight : newFlights) {
            if (!flightByIdMap.containsKey(flight.getFlightID())) {
                flights.add(flight);
                flightByIdMap.put(flight.getFlightID(), flight);
                flightsByDepartFrom.computeIfAbsent(flight.getDepartFrom(), k -> new ArrayList<>()).add(flight);
                flightsByDepartTo.computeIfAbsent(flight.getDepartTo(), k -> new ArrayList<>()).add(flight);
            }
        }
    }

    public static Flight getFlightInfo(int flight_id) {
        // 根据航班ID查找航班
        return flightByIdMap.get(flight_id);
    }

    public static Flight getFlightInfo(String city1, String city2) {
        // 查找从city1到city2的直飞航班
        List<Flight> departFromFlights = flightsByDepartFrom.get(city1);
        if (departFromFlights != null) {
            for (Flight flight : departFromFlights) {
                if (flight.getDepartTo().equals(city2)) {
                    return flight;
                }
            }
        }
        return null;
    }

    public static Flight getFlightInfo(String city) {
        // 查找到达城市为city的航班
        List<Flight> arriveToFlights = flightsByDepartTo.get(city);
        if (arriveToFlights != null && !arriveToFlights.isEmpty()) {
            return arriveToFlights.get(0); // 返回第一个匹配的航班
        }
        return null;
    }

    // 清空所有集合和哈希表
    public static void clearAll() {
        flights.clear();
        flightByIdMap.clear();
        flightsByDepartFrom.clear();
        flightsByDepartTo.clear();
    }

    public static Map<Integer, Flight> getFlightByIdMap() {
        return flightByIdMap;
    }

    public static Map<String, List<Flight>> getFlightsByDepartFrom() {
        return flightsByDepartFrom;
    }

    public static Map<String, List<Flight>> getFlightsByDepartTo() {
        return flightsByDepartTo;
    }
}
