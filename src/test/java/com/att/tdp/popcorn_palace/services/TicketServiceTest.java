package com.att.tdp.popcorn_palace.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.ticketException.TicketAlreadyExistsException;
import com.att.tdp.popcorn_palace.entities.Showtime;
import com.att.tdp.popcorn_palace.entities.Ticket;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repositories.TicketRepository;
import com.att.tdp.popcorn_palace.requests.TicketRequest;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private TicketService ticketServiceMock;

    @Test
    public void TicketService_testAddTicket() {
        Showtime showtime = Showtime.builder().id(1L).build();
        TicketRequest ticket = TicketRequest.builder().showtimeId(1L).seatNumber("1").build();
        Ticket returnedTicket = Ticket.builder().bookingId("1L").showtime(showtime).seatNumber("1").build();

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(ticketRepository.findByShowtimeAndSeatNumber(showtime, "1")).thenReturn(null);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(returnedTicket);

        Ticket savedTicket = ticketServiceMock.addTicket(ticket);

        assertThat(savedTicket).isNotNull();
        assertThat(savedTicket.getSeatNumber()).isEqualTo("1");
    }

    @Test
    public void TicketService_testAddTicket_showtimeNotFound() {
        TicketRequest ticket = TicketRequest.builder().showtimeId(1L).seatNumber("1").build();

        when(showtimeRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            ticketServiceMock.addTicket(ticket);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ShowtimeNotFoundException.class);
        }
    }

    @Test
    public void TicketService_testAddTicket_ticketAlreadyExists() {
        Showtime showtime = Showtime.builder().id(1L).build();
        TicketRequest ticket = TicketRequest.builder().showtimeId(1L).seatNumber("1").build();
        Ticket existingTicket = Ticket.builder().bookingId("1L").showtime(showtime).seatNumber("1").build();

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(ticketRepository.findByShowtimeAndSeatNumber(showtime, "1")).thenReturn(existingTicket);

        try {
            ticketServiceMock.addTicket(ticket);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(TicketAlreadyExistsException.class);
        }
    }

}
