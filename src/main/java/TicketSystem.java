import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;

public class TicketSystem {

    private Passenger passenger = new Passenger();
    private Ticket ticket = new Ticket();
    private Ticket ticket1 = new Ticket();
    private Ticket ticket2 = new Ticket();
    private Flight flight = new Flight();
    private Flight flight1 = new Flight();
    private Flight flight2 = new Flight();
    private Passenger passenger1 = new Passenger();
    Scanner in = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(TicketSystem.class.getName());

    public void chooseTicket(String city1, String city2) throws Exception {
        // 验证出发城市和到达城市的有效性
        if (!isValidCity(city1) || !isValidCity(city2)) {
            throw new IllegalArgumentException("Invalid city name(s). Please check the city names and try again.");
        } else {
            int counter = 1;
            int idFirst = 0;
            int idSecond = 0;

            //search for direct flight from city1 to city2
            Flight flight = FlightCollection.getFlightInfo(city1, city2);
            if (flight != null) {
                int ticket_id = flight.getFlightID();
                if (isTicketBooked(ticket_id)){
                    throw new IllegalArgumentException("This ticket is already booked");
                }
                else
                    System.out.println("There is a ticket");
                    //buyTicket(ticket_id,in);
            } else
            //in case there is no direct ticket from city1 to city2
            {
                //SELECT a flight where depart_to = city2

                Flight depart_to = FlightCollection.getFlightInfo(city2);

                //and search for city with depart_from as connector city

                if (depart_to != null) {
                    String connectCity = depart_to.getDepartFrom();

                    //SELECT * from flight where depart_to = '" + connectCity + "' and depart_from = '" + city1+"'"

                    Flight flightConnectingTwoCities = FlightCollection.getFlightInfo(city1, connectCity);

                    if (flightConnectingTwoCities != null) {
                        System.out.println("There is special way to go there. And it is transfer way, like above. Way №" + counter);
                        idFirst = depart_to.getFlightID();
                        idSecond = flightConnectingTwoCities.getFlightID();
                        counter=2;
                    }

                }

//                counter++;

                if (counter == 1) {
                    System.out.println("There are no possible variants.");
                    return;
                }
                // 验证票是否已预订
                if (isTicketBooked(idFirst) || isTicketBooked(idSecond)) {
                    throw new IllegalArgumentException("One of the tickets is already booked. Please choose another ticket.");
                }//else
                    //buyTicket(idFirst, idSecond); //pass two tickets and buy them

            }
        }
    }
    // 假设这是一个包含所有有效城市名的集合
    private static Set<String> validCities = new HashSet<>();

    static {
        // 初始化有效城市列表，这里添加一些示例城市
        validCities.add("New York");
        validCities.add("Los Angeles");
        validCities.add("Beijing");
        validCities.add("Shanghai");
        validCities.add("Paris");
        validCities.add("London");
        validCities.add("Tokyo");
    }

    // 验证城市名的方法
    private boolean isValidCity(String city) {
        // 检查城市名是否为空
        if (city == null || city.trim().isEmpty()) {
            return false;
        }
        // 检查城市名是否在有效城市列表中
        return validCities.contains(city);
    }

    // 验证票是否已预订的方法
    private boolean isTicketBooked(int ticket_id) {
        Ticket ticket = TicketCollection.getTicketInfo(ticket_id);
        return ticket != null && ticket.ticketStatus();
    }

    public void buyTicket(int ticket_id, Scanner in) throws Exception
    {
        int flight_id = 0;

        //select ticket where ticket_id="+ticket_id"
        Ticket validTicket = TicketCollection.getTicketInfo(ticket_id);

        if(validTicket == null) //***
        {
            logger.warning("Attempted to purchase a non-existent ticket with ID: " + ticket_id);
            throw new IllegalArgumentException("This ticket does not exist. You should choose another ticket.");
        }
        else{
            flight_id = validTicket.getFlight().getFlightID();
            try
            {
                String firstName = in.nextLine().trim();
                passenger.setFirstName(firstName);

                String secondName = in.nextLine().trim();
                passenger.setSecondName(secondName); //setting passengers info

                String ageInput = in.nextLine().trim();
                Integer age;
                age = Integer.parseInt(ageInput);
                passenger.setAge(age);

                String gender = in.nextLine().trim();
                passenger.setGender(gender);

                String email = in.nextLine().trim();
                passenger.setEmail(email);

                String phoneNumber = in.nextLine().trim();
                passenger.setPhoneNumber(phoneNumber);

                String passportNumber = in.nextLine().trim();
                passenger.setPassport(passportNumber);

                //System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purch = in.nextInt();
                in.nextLine(); // Consume the newline left-over
                if (purch == 0)
                {   System.out.println("Successfully canceled the purchase.");
                    return;
                } else
                {

                    flight = FlightCollection.getFlightInfo(flight_id);
                    Airplane airplane = flight.getAirplane();
                    ticket = TicketCollection.getTicketInfo(ticket_id);
                    ticket.setPassenger(passenger);
                    ticket.setTicketStatus(true);
                    if (ticket.getClassVip() == true)
                    {
                        airplane.setBusinessSitsNumber(airplane.getBusinessSitsNumber() - 1);
                    } else
                    {
                        airplane.setEconomySitsNumber(airplane.getEconomySitsNumber() - 1);
                    }

                }
                System.out.println("Your bill: " + ticket.getPrice() + "\n");
                String cardNumber = in.nextLine().trim();
                passenger.setCardNumber(cardNumber);
                String securityCodeInput = in.nextLine().trim();
                Integer securityCode;
                securityCode = Integer.parseInt(securityCodeInput);
                passenger.setSecurityCode(securityCode);
            } catch (PatternSyntaxException patternException)
            {
                //patternException.printStackTrace();
            }
        }
    }

    public void buyTicket(int ticket_id_first, int ticket_id_second,Scanner in) throws Exception{
        if (this.passenger == null) {
            throw new IllegalStateException("Passenger is null at the start of buyTicket");
        }
        int flight_id_first = 0;
        int flight_id_second = 0;

        Ticket validTicketfirst = TicketCollection.getTicketInfo(ticket_id_first);

        Ticket validTicketSecond = TicketCollection.getTicketInfo(ticket_id_second);//***

        if(validTicketfirst==null || validTicketSecond==null)
        {
            throw new IllegalArgumentException("This ticket does not exist.");
        }

        else
        {
            flight_id_first = validTicketfirst.getFlight().getFlightID();
            flight_id_second = validTicketSecond.getFlight().getFlightID();
            try
            {
                passenger = validTicketfirst.getPassenger(); // 使用第一个票的乘客对象
                //System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purch = in.nextInt();
                in.nextLine(); // Consume the newline left-over
                if (purch == 0)
                {
                    System.out.println("Successfully canceled the purchase.");
                }
                else
                {
//                    //  "select * from flight, airplane where flight_id=" + flight_id_first + " and flight.airplane_id=airplane.airplane_id");
                    flight1 = FlightCollection.getFlightInfo(flight_id_first);
                    Airplane airplane_first = flight1.getAirplane();
                    flight2 = FlightCollection.getFlightInfo(flight_id_second);
                    Airplane airplane_second  = flight2.getAirplane();
                    ticket1 = TicketCollection.getTicketInfo(ticket_id_first);
                    ticket2 = TicketCollection.getTicketInfo(ticket_id_second);
                    //ticket1.setPassenger(passenger);
                    //ticket2.setPassenger(passenger);
                    ticket1.setTicketStatus(true);
                    ticket2.setTicketStatus(true);
                    if (ticket1.getClassVip() == true)
                    {
                        airplane_first.setBusinessSitsNumber(airplane_first.getBusinessSitsNumber() - 1);
                    } else
                    {
                        airplane_first.setEconomySitsNumber(airplane_first.getEconomySitsNumber() - 1);
                    }

                    if (ticket2.getClassVip() == true)
                    {
                        airplane_second.setBusinessSitsNumber(airplane_second.getBusinessSitsNumber() - 1);
                    } else
                    {
                        airplane_second.setEconomySitsNumber(airplane_second.getEconomySitsNumber() - 1);
                    }

                    ticket.setPrice(ticket1.getPrice() + ticket2.getPrice());

                    System.out.println("Your bill: " + ticket.getPrice() + "\n");

                    String cardNumber = in.nextLine().trim();
                    passenger.setCardNumber(cardNumber);

                    Integer securityCode = Integer.parseInt(in.nextLine().trim());
                    passenger.setSecurityCode(securityCode);

                }
            } catch (PatternSyntaxException patternException)
            {
                //patternException.printStackTrace();
            }
        }

    }
}
