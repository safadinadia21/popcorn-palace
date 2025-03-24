package com.att.tdp.popcorn_palace.Exceptions.ticketException;

public class TicketAlreadyExistsException extends TicketException {
    public TicketAlreadyExistsException(String ticketId, Long showId, String seatNumber) {
        super("Ticket already exists with id: " + ticketId + " for show: " + showId + " and seat: " + seatNumber);
    }

}
