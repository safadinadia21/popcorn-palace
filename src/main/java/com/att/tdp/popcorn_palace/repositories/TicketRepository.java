package com.att.tdp.popcorn_palace.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.att.tdp.popcorn_palace.entities.Showtime;
import com.att.tdp.popcorn_palace.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    Ticket findByShowtimeAndSeatNumber(Showtime showtime, String seatNumber);

    List<Ticket> findByShowtime(Showtime showtime);

}
