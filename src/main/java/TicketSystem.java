import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

public class TicketSystem {

    private Passenger passenger = new Passenger();
    private Ticket ticket = new Ticket();
    private Flight flight = new Flight();
    Scanner in = new Scanner(System.in);


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
//                flight.getFlightID();
//                System.out.println("\nEnter ID of Flight you want to choose:");
//                int ticket_id = in.nextInt();
//                int ticket_id = flight.getFlightID();
                int ticket_id = 1;
                System.out.println(ticket_id);
                if (isTicketBooked(ticket_id)){
                    System.out.println("This ticket is already booked");
                    throw new IllegalArgumentException("This ticket is already booked");
                }
                else
                    buyTicket(ticket_id,in);
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
                        counter++;
                    }

                }

//                counter++;

                if (counter == 1) {
                    System.out.println("There are no possible variants.");
                    return;
                }
                // 验证票是否已预订
                if (isTicketBooked(idFirst) || isTicketBooked(idSecond)) {
                    System.out.println("One of the tickets is already booked. Please choose another ticket.");
                    throw new IllegalArgumentException("One of the tickets is already booked. Please choose another ticket.");
                }else
                    buyTicket(idFirst, idSecond); //pass two tickets and buy them

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

    public void showTicket()
    {
        try
        {
            System.out.println("You have bought a ticket for flight " + ticket.flight.getDepartFrom() + " - " + ticket.flight.getDepartTo() + "\n\nDetails:");
            System.out.println(this.ticket.toString());
        }
        catch (NullPointerException e)
        {
            return;
        }
    }

    public void buyTicket(int ticket_id, Scanner in) throws Exception
    //method for buying one ticket with direct flight
    {
        int flight_id = 0;

        //select ticket where ticket_id="+ticket_id"
        Ticket validTicket = TicketCollection.getTicketInfo(ticket_id);
//        System.out.println(validTicket);

        //if there is a valid ticket id was input then we buy it, otherwise show message
        if(validTicket == null) //***
        {
            System.out.println("This ticket does not exist.");
            throw new IllegalArgumentException("This ticket does not exist.");
        }
        else{
            //select flight_id from ticket where ticket_id=" + ticket_id

            flight_id = validTicket.getFlight().getFlightID();
//            System.out.println(flight_id);

            try
            {
                System.out.println("Enter your First Name: ");
                String firstName = in.nextLine().trim();
                passenger.setFirstName(firstName);


                System.out.println("Enter your Second name:");
                String secondName = in.nextLine().trim();
                passenger.setSecondName(secondName); //setting passengers info

                System.out.println("Enter your age:");
                String ageInput = in.nextLine().trim();
                Integer age;
                age = Integer.parseInt(ageInput);
//                Integer age = Integer.valueOf(in.nextLine().trim());
//                in.nextLine();
                passenger.setAge(age);

                System.out.println("Enter your gender: ");
                String gender = in.nextLine().trim();
                passenger.setGender(gender);

                System.out.println("Enter your e-mail address");
                String email = in.nextLine().trim();
                passenger.setEmail(email);

                System.out.println("Enter your phone number (+7):");
                String phoneNumber = in.nextLine().trim();
                passenger.setPhoneNumber(phoneNumber);

                System.out.println("Enter your passport number:");
                String passportNumber = in.nextLine().trim();
                passenger.setPassport(passportNumber);

                System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purch = in.nextInt();
                in.nextLine(); // Consume the newline left-over
                if (purch == 0)
                {   System.out.println("Successfully canceled the purchase.");
                    return;
                } else
                {

                    flight = FlightCollection.getFlightInfo(flight_id);
                    System.out.println(flight);

//                    int airplane_id = flight.getAirplane().getAirplaneID();
                    Airplane airplane = flight.getAirplane();
//                    System.out.println(airplane);

//                    Airplane airplane = Airplane.getAirPlaneInfo(airplane_id);

                    ticket = TicketCollection.getTicketInfo(ticket_id);

                    ticket.setPassenger(passenger);
                    ticket.setTicket_id(ticket_id);
                    ticket.setFlight(flight);
                    ticket.setPrice(ticket.getPrice());
                    ticket.setClassVip(ticket.getClassVip());
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

//                System.out.println("Enter your card number:");
                String cardNumber = in.nextLine().trim();
                passenger.setCardNumber(cardNumber);

//                System.out.println("Enter your security code:");
                String securityCodeInput = in.nextLine().trim();
                Integer securityCode;
                securityCode = Integer.parseInt(securityCodeInput);
                passenger.setSecurityCode(securityCode);


            } catch (PatternSyntaxException patternException)
            {
                patternException.printStackTrace();
            }
        }
    }

    @SuppressWarnings("null")
    public void buyTicket(int ticket_id_first, int ticket_id_second) throws Exception{
        //method for buying two tickets with transfer flight
        int flight_id_first = 0;

        int flight_id_second = 0;


        System.out.println(ticket_id_first + " " + ticket_id_second);

        Ticket validTicketfirst = TicketCollection.getTicketInfo(ticket_id_first);

        Ticket validTicketSecond = TicketCollection.getTicketInfo(ticket_id_second);//***


        System.out.println("Processing...");

        //if there is a valid ticket id was input then we buy it, otherwise show message

        if(validTicketfirst==null || validTicketSecond==null)
        {
            System.out.println("This ticket does not exist.");
            return;
        }

        else
        {
            flight_id_first = validTicketfirst.getFlight().getFlightID();

            flight_id_second = validTicketfirst.getFlight().getFlightID();


            try
            {
                System.out.println("Enter your First Name: ");
                String firstName = "";
                passenger.setFirstName(firstName);


                System.out.println("Enter your Second name:");
                String secondName = "";
                passenger.setSecondName(secondName); //setting passengers info

                System.out.println("Enter your age:");
                Integer age = 0;
                in.nextLine();
                passenger.setAge(age);

                System.out.println("Enter your gender: ");
                String gender = "";
                passenger.setGender(gender);

                System.out.println("Enter your e-mail address");
                String email = "";
                passenger.setEmail(email);

                System.out.println("Enter your phone number (+7):");
                String phoneNumber = "";
                passenger.setPhoneNumber(phoneNumber);

                System.out.println("Enter your passport number:");
                String passportNumber = "";
                passenger.setPassport(passportNumber);

                System.out.println("Do you want to purchase?\n 1-YES 0-NO");
                int purch = in.nextInt();
                in.nextLine(); // Consume the newline left-over
                if (purch == 0)
                {
                    return;
                }
                else
                {

                    //  "select * from flight, airplane where flight_id=" + flight_id_first + " and flight.airplane_id=airplane.airplane_id");
                    Flight flight_first = FlightCollection.getFlightInfo(flight_id_first);

                    int airplane_id_first = flight_first.getAirplane().getAirplaneID();

                    Airplane airplane_first = Airplane.getAirPlaneInfo(airplane_id_first);

                    Flight flight_second = FlightCollection.getFlightInfo(flight_id_second);

                    int airplane_id_second = flight_second.getAirplane().getAirplaneID();

                    Airplane airpairplane_second  = Airplane.getAirPlaneInfo(airplane_id_second);

                    Ticket ticket_first = TicketCollection.getTicketInfo(ticket_id_first);

                    Ticket ticket_second = TicketCollection.getTicketInfo(ticket_id_second);

                    ticket_first.setPassenger(passenger);
                    ticket_first.setTicket_id(ticket_id_first);
                    ticket_first.setFlight(flight_first);
                    ticket_first.setPrice(ticket_first.getPrice());
                    ticket_first.setClassVip(ticket_first.getClassVip());
                    ticket_first.setTicketStatus(true);

                    if (ticket_first.getClassVip() == true)
                    {
                        airplane_first.setBusinessSitsNumber(airplane_first.getBusinessSitsNumber() - 1);
                    } else
                    {
                        airplane_first.setEconomySitsNumber(airplane_first.getEconomySitsNumber() - 1);
                    }

                    System.out.println("--*-*-");

                    ticket_second.setPassenger(passenger);
                    ticket_second.setTicket_id(ticket_id_second);
                    ticket_second.setFlight(flight_first);
                    ticket_second.setPrice(ticket_second.getPrice());
                    ticket_second.setClassVip(ticket_second.getClassVip());
                    ticket_second.setTicketStatus(true);
                    if (ticket_second.getClassVip() == true)
                    {
                        airpairplane_second.setBusinessSitsNumber(airpairplane_second.getBusinessSitsNumber() - 1);
                    } else
                    {
                        airpairplane_second.setEconomySitsNumber(airpairplane_second.getEconomySitsNumber() - 1);
                    }

                    System.out.println("--*-*-");

                    ticket.setPrice(ticket_first.getPrice() + ticket_second.getPrice());

                    System.out.println("Your bill: " + ticket.getPrice() + "\n");

                    System.out.println("Enter your card number:");

                    String cardNumber = "";
                    passenger.setCardNumber(cardNumber);

                    System.out.println("Enter your security code:");
                    Integer securityCode = 0;
                    passenger.setSecurityCode(securityCode);

                    showTicket();

                }
            } catch (PatternSyntaxException patternException)
            {
                patternException.printStackTrace();
            }
        }

    }
}
