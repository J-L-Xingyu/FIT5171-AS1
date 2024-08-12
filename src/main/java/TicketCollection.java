import java.util.ArrayList;//contributed by Qianru Zhong

public class TicketCollection {
	private static ArrayList<Ticket> tickets = new ArrayList<>();

	public static ArrayList<Ticket> getTickets() {return new ArrayList<>(tickets);}//获得目前的ticket list

	public static void addTicket(Ticket ticket) {//添加ticket
		if (ticket == null) {throw new IllegalArgumentException("Ticket cannot be null.");}
		for (Ticket existingTicket : tickets) {
			if (existingTicket.getTicket_id() == ticket.getTicket_id()) {
				throw new IllegalArgumentException("Ticket ID " + ticket.getTicket_id() + " already exists.");}
		}
		tickets.add(ticket);
	}

	public static void addTickets(ArrayList<Ticket> tickets_db) {//添加ticket list
		for (Ticket ticket : tickets_db) {
			if (ticket == null) {throw new IllegalArgumentException("Ticket cannot be null.");}
		}
		tickets.addAll(tickets_db);
	}

	public static void getAllTickets() {//展示所有ticket信息
		for (Ticket ticket : tickets) {System.out.println(ticket.toString());}
	}

	public static Ticket getTicketInfo(int ticket_id) {//展示某张ticket信息
		for (Ticket ticket : tickets) {
			if (ticket.getTicket_id() == ticket_id) {return ticket;}
		}
		return null;// or throw an exception if preferred
	}

	public static void clearTickets() {
		tickets.clear();
	}//清除ticket collection
}