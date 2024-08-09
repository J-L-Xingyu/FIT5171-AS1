import java.util.ArrayList;
import java.util.HashSet;

public class FlightCollection {
	
	public static ArrayList<Flight> flights= new ArrayList<>();

	public static ArrayList<Flight> getFlights() {
		return flights;
	}

    public static void print(ArrayList<Flight> flights){
        for (Flight flight : flights) {
            System.out.println(flight.toString());
        }
    }

	public static void addFlights(ArrayList<Flight> flights) {
    HashSet<Integer> set = new HashSet<>();  // 使用Integer类型来存储已存在的flightID
    for (Flight flight : FlightCollection.flights) {
        set.add(flight.getFlightID());  // 将已存在的flightID添加到HashSet中
    }

    for (Flight newFlight : flights) {
        if (!set.contains(newFlight.getFlightID())) {  // 通过flightID检查是否已经存在相同的航班
            FlightCollection.flights.add(newFlight);  // 如果不存在，添加到FlightCollection中
            set.add(newFlight.getFlightID());  // 同时添加到HashSet中，保持同步
        } // {
            //System.out.println("Flight_id = "+newFlight.getFlightID() + " flight  Already existed!");  // 输出已存在航班信息
        //}
    }
}

	
	public static Flight getFlightInfo(String city1, String city2) {
    	//display the flights where there is a direct flight from city 1 to city2
		// 查找从city1到city2的直飞航班
        for (Flight flight : flights) {
            if (flight.getDepartFrom().equals(city1) && flight.getDepartTo().equals(city2)) {
				//System.out.println("Get the flight info: "+ flight.toString());
                return flight;
            }
        }
        //System.out.println("can not find direct flight from "+ city1+ " to "+ city2+ " flight");
        return null;
    }
    
    public static Flight getFlightInfo(String city) {
    	//SELECT a flight where depart_to = city
		// 查找到达城市为city的航班
        for (Flight flight : flights) {
            if (flight.getDepartTo().equals(city)) {
               // System.out.println("Get the flight info: " + flight.toString());
                return flight;
            }
        }
        //System.out.println("can not find depart_to = "+city + " flight");
        return null;

    }
    public static Flight getFlightInfo(int flight_id) {
    	//SELECT a flight with a particular flight id
		// 根据航班ID查找航班
        for (Flight flight : flights) {
            if (flight.getFlightID() == flight_id) {
                return flight;
            }
        }
        //System.out.println("can not find flight_id = "+ flight_id + " flight");
        return null;
    }

}
