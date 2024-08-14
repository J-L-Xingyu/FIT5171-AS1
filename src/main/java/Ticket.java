public class Ticket //contributed by Qianru Zhong
{
    private int ticket_id;
    private int price;
    Flight flight;
    private boolean classVip; //indicates if this is business class ticket or not
    private boolean status; //indicates status of ticket: if it is bought by someone or not
    Passenger passenger;

    public Ticket(int ticket_id,int price, Flight flight, boolean classVip, Passenger passenger) { //构造函数
        setTicket_id(ticket_id);
        setFlight(flight);
        setClassVip(classVip);
        this.status=false;// changes in Assignment2
        setPassenger(passenger);
        setPrice(price);
    }

    public Ticket(){}

    public int getTicket_id() {
        return ticket_id;
    }//返回Ticket_id

    public void setTicket_id(int ticket_id) {//设置Ticket_id
        if (ticket_id <= 0) {throw new IllegalArgumentException("Ticket id must be greater than 0.");}
        this.ticket_id = ticket_id;
    }

    public int getPrice() { return price; }//返回价格

    public void setPrice(int price) {//设置价格
        if (price < 0){throw new IllegalArgumentException("Price must not be negative.");}
        this.price = price;
        //applyDiscount(); //changes price of the ticket according to the age category of passenger
        if (passenger != null) {
            applyDiscount(); // 确保 passenger 已设置
        }
        if (status) {
            serviceTax(); // 仅在 status 为 true 时应用税率
        }
        //if (status){serviceTax();} //changes price by adding service tax to the ticket
    }

    public void applyDiscount(){//增加的折扣函数
        if (passenger.getAge() < 15){this.price -= (int) (this.price * 0.5);} // 应用50%的折扣并向下取整;
        else if(passenger.getAge() >= 60){this.price=0;} //100% sale for elder people
    }

    public void serviceTax(){this.price = (int) Math.ceil(this.price * 1.12);}//应用12%的服务税并向上取整

    public Flight getFlight() {return flight;}//返回flight

    public void setFlight(Flight flight) {//设置flight
        if (flight == null) {throw new IllegalArgumentException("Flight cannot be null.");}
        if (flight.getAirplane() == null) {throw new IllegalArgumentException("Airplane cannot be null.");}
        this.flight = flight;
    }

    public boolean getClassVip() {return classVip;}//返回座位舱

    public void setClassVip(boolean classVip) {this.classVip = classVip;} //设置座位舱

    public boolean ticketStatus() {return status;}//返回订票状态

    public void setTicketStatus(boolean newStatus) {//设置订票状态
        if (this.status != newStatus) {// 检查状态是否改变，避免不必要的重新计算
            this.status = newStatus;
            setPrice(this.price); // 重新应用价格设置以触发税率或折扣逻辑
        }
    }

    public Passenger getPassenger() {return passenger;}//返回passenger

    public void setPassenger(Passenger passenger) {//设置passenger
        if(passenger == null){throw new IllegalArgumentException("Passenger cannot be null.");}// changes in Assignment2
        if(passenger.getFirstName() == null){throw new IllegalArgumentException("Invalid first name: " + passenger.getFirstName());}// changes in Assignment2
        if(passenger.getSecondName() == null){throw new IllegalArgumentException("Invalid second name: " + passenger.getSecondName());}// changes in Assignment2
        if(passenger.getGender() == null){throw new IllegalArgumentException("Invalid gender: "+ passenger.getGender());}
        this.passenger = passenger;
    }

    public String toString(){return"Ticket{" +'\n'+ "Price=" + getPrice() + "KZT, " + '\n' + getFlight() +'\n'+ "Vip status=" + getClassVip() + '\n' + getPassenger()+'\n'+ "Ticket was purchased=" + ticketStatus() + "\n}";}//展示ticket信息
}
