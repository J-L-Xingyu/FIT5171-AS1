import java.util.ArrayList;

public class FlightCollection {

	private static ArrayList<Flight> flights = new ArrayList<>();

	public static ArrayList<Flight> getFlights() {
		return flights;
	}

	public static void addFlights(ArrayList<Flight> flights) {
		FlightCollection.flights.addAll(flights);
	}

	public static Flight getFlightInfo(String city1, String city2) {
    for (Flight flight : flights) {
        System.out.println("Checking flight from: " + flight.getDepartFrom() + " to: " + flight.getDepartTo());
        if (flight.getDepartFrom().equals(city1) && flight.getDepartTo().equals(city2)) {
            System.out.println("Match found");
            return flight;
        }
    }
    return null;
}


    public static Flight getFlightInfo(String city) {
    	for (Flight flight : flights) {
    		if (flight.getDepartTo().equals(city)) {
    			return flight;
    		}
    	}
    	return null;
    }

    public static Flight getFlightInfo(int flight_id) {
    	for (Flight flight : flights) {
    		if (flight.getFlightID() == flight_id) {
    			return flight;
    		}
    	}
    	return null;
    }
}
