//contributed by Qianru Zhong

import java.util.ArrayList;

public class TicketCollection {

	private static ArrayList<Ticket> tickets = new ArrayList<>();

	//获得目前的ticket list
	public static ArrayList<Ticket> getTickets() {
		return new ArrayList<>(tickets);
	}

	//添加ticket
	public static void addTicket(Ticket ticket) {
		validateTicket(ticket);
		tickets.add(ticket);
	}

	//添加ticket list
	public static void addTickets(ArrayList<Ticket> tickets_db) {
		for (Ticket ticket : tickets_db) {
			validateTicket(ticket);
		}
		tickets.addAll(tickets_db);
	}

	//展示所有ticket信息
	public static void getAllTickets() {
		//display all available tickets from the Ticket collection
		for (Ticket ticket : tickets) {
			System.out.println(ticket.toString());
		}
	}

	//展示某张ticket信息
	public static Ticket getTicketInfo(int ticket_id) {
		//SELECT a ticket where ticket id = ticket_id
		for (Ticket ticket : tickets) {
			if (ticket.getTicket_id() == ticket_id) {
				return ticket;
			}
		}
		return null;// or throw an exception if preferred

	}

	//验证ticket是否有效
	private static void validateTicket(Ticket ticket) {
		if (ticket == null) {
			throw new IllegalArgumentException("Ticket cannot be null.");
		}

		// Check if the ticket_id already exists in the collection
		for (Ticket existingTicket : tickets) {
			if (existingTicket.getTicket_id() == ticket.getTicket_id()) {
				throw new IllegalArgumentException("Ticket ID " + ticket.getTicket_id() + " already exists.");
			}
		}
	}

	//清除ticket collection
	public static void clearTickets() {
		tickets.clear();
	}


}