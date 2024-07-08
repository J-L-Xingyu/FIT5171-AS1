//contributed by Qianru Zhong

import java.sql.Timestamp;

public class Ticket
{
    private int ticket_id;
    private int price;
    Flight flight;
    private boolean classVip; //indicates if this is business class ticket or not
    private boolean status; //indicates status of ticket: if it is bought by someone or not
    Passenger passenger;

    public Ticket(int ticket_id,int price, Flight flight, boolean classVip, Passenger passenger)
    {
        setTicket_id(ticket_id);
        setFlight(flight);
        setClassVip(classVip);
        setTicketStatus(false);
        setPassenger(passenger);
        setPrice(price);
    }

    public Ticket() {

    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        if (ticket_id <= 0){
            throw new IllegalArgumentException("Ticket id must be greater than 0.");
        }
        this.ticket_id = ticket_id;
    }

    public int getPrice() { return price; }

    public void setPrice(int price)
    {
        if (price < 0){
            throw new IllegalArgumentException("Price must not be negative.");
        }
        this.price = price;
        applyDiscount(); //changes price of the ticket according to the age category of passenger

        if (status){
            serviceTax(); //changes price by adding service tax to the ticket
        }
    }

    public void applyDiscount()
    {
        if (passenger.getAge() < 15){
            this.price -= (int) (this.price * 0.5); // 应用50%的折扣并向下取整;

        }
        else if(passenger.getAge() >= 60){
            this.price=0; //100% sale for elder people
        }
    }

    public void serviceTax(){
        this.price = (int) Math.ceil(this.price * 1.12);
        // 应用12%的服务税并向上取整
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null.");
        }
        if (flight.getAirplane() == null) {
            throw new IllegalArgumentException("Airplane cannot be null.");
        }
        this.flight = flight;
    }

    public boolean getClassVip() {
        return classVip;
    }

    public void setClassVip(boolean classVip) {
        this.classVip = classVip;
    }

    public boolean ticketStatus()
    {
        return status;
    }

    public void setTicketStatus(boolean newStatus) {
        // 检查状态是否改变，避免不必要的重新计算
        if (this.status != newStatus) {
            this.status = newStatus;
            // 重新应用价格设置以触发税率或折扣逻辑
            setPrice(this.price);
        }
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        if(passenger == null){
            throw new IllegalArgumentException("Passenger cannot be null.");
        }

        validatePassenger(passenger);

        this.passenger = passenger;
    }

    public String toString()
    {
        return"Ticket{" +'\n'+
                "Price=" + getPrice() + "KZT, " + '\n' +
                getFlight() +'\n'+ "Vip status=" + getClassVip() + '\n' +
                getPassenger()+'\n'+ "Ticket was purchased=" + ticketStatus() + "\n}";
    }

    //还需要改进
    public void validatePassenger(Passenger passenger){
        if(passenger.getFirstName() == null){
            throw new IllegalArgumentException("Invalid first name: " + passenger.getFirstName());
        }
        else if(passenger.getSecondName() == null){
            throw new IllegalArgumentException("Invalid second name: " + passenger.getSecondName());
        }
        if(passenger.getAge() <0){
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if(passenger.getGender() == null){
            throw new IllegalArgumentException("Invalid gender: "+ passenger.getGender());
        }
    }
}
