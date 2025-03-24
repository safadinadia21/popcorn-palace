package com.att.tdp.popcorn_palace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.ticketException.TicketAlreadyExistsException;
import com.att.tdp.popcorn_palace.entities.Showtime;
import com.att.tdp.popcorn_palace.entities.Ticket;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repositories.TicketRepository;
import com.att.tdp.popcorn_palace.requests.TicketRequest;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;

    public Ticket addTicket(TicketRequest ticketRequest) {
        Showtime showtime = showtimeRepository.findById(ticketRequest.getShowtimeId())
                .orElseThrow(() -> new ShowtimeNotFoundException(ticketRequest.getShowtimeId()));
        Ticket ticket = Ticket.builder()
                .showtime(showtime)
                .seatNumber(ticketRequest.getSeatNumber())
                .userId(ticketRequest.getUserId())
                .build();

        Ticket existingTicket = ticketRepository.findByShowtimeAndSeatNumber(ticket.getShowtime(),
                ticket.getSeatNumber());

        if (existingTicket != null) {
            throw new TicketAlreadyExistsException(existingTicket.getBookingId(), ticket.getShowtime().getId(),
                    ticket.getSeatNumber());
        }

        return ticketRepository.save(ticket);
    }

}
