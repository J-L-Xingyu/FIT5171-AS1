import java.util.ArrayList;

public class TicketCollection {

	private static ArrayList<Ticket> tickets = new ArrayList<>();

	public static ArrayList<Ticket> getTickets() {
		return new ArrayList<>(tickets);
	}

	public static void addTicket(Ticket ticket) {
		validateTicket(ticket);
		tickets.add(ticket);
	}

	public static void addTickets(ArrayList<Ticket> tickets_db) {
		for (Ticket ticket : tickets_db) {
			validateTicket(ticket);
		}
		tickets.addAll(tickets_db);
	}

	public static void getAllTickets() {
		//display all available tickets from the Ticket collection
		for (Ticket ticket : tickets) {
			System.out.println(ticket.toString());
		}
	}
	public static Ticket getTicketInfo(int ticket_id) {
		//SELECT a ticket where ticket id = ticket_id
		for (Ticket ticket : tickets) {
			if (ticket.getTicket_id() == ticket_id) {
				return ticket;
			}
		}
		return null;// or throw an exception if preferred

	}

	private static void validateTicket(Ticket ticket) {
		if (ticket == null) {
			throw new IllegalArgumentException("Ticket cannot be null.");
		}

		// Add more validation logic if necessary
	}

	public static void clearTickets() {
		tickets.clear();
	}


}