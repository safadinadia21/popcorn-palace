package com.att.tdp.popcorn_palace.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.entities.Showtime;
import com.att.tdp.popcorn_palace.entities.Ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;
    private Movie movie;
    private Showtime showtime;

    @BeforeEach
    public void setUp() {

        movie = movieRepository.save(Movie.builder()
                .title("Inception")
                .genre("Sci-Fi")
                .duration(2)
                .rating(4.3)
                .releaseYear(2010)
                .build());

        // Ensure a Showtime is saved before using it
        showtime = showtimeRepository.save(Showtime.builder()
                .movie(movie)
                .theater("IMAX")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(3))
                .price(15.0)
                .build());
        ;
    }

    @Test
    public void TicketRepository_shouldBeAbleToSaveTicket() {
        // Given

        Ticket ticket = Ticket.builder().showtime(showtime).userId("uuid1").seatNumber("1").build();

        // When
        Ticket savedTicket = ticketRepository.save(ticket);

        // Then
        assertThat(savedTicket).isNotNull();
        assertThat(savedTicket.getBookingId()).isNotNull();
    }

    @Test
    public void TicketRepository_shouldBeAbleToFindTicketById() {
        // Given
        Ticket ticket = Ticket.builder().showtime(showtime).userId("uuid1").seatNumber("1").build();
        Ticket savedTicket = ticketRepository.save(ticket);

        // When
        Ticket foundTicket = ticketRepository.findById(savedTicket.getBookingId()).orElse(null);

        // Then
        assertThat(foundTicket).isNotNull();
        assertThat(foundTicket.getBookingId()).isEqualTo(savedTicket.getBookingId());
    }

    @Test
    public void TicketRepository_shouldBeAbleTofindTicketbyShowtimeAndSeatNumber() {
        // Given
        Ticket ticket = Ticket.builder().showtime(showtime).userId("uuid1").seatNumber("1").build();
        Ticket savedTicket = ticketRepository.save(ticket);

        // When
        Ticket foundTicket = ticketRepository.findByShowtimeAndSeatNumber(showtime,
                "1");

        // Then
        assertThat(foundTicket).isNotNull();
        assertThat(foundTicket.getBookingId()).isEqualTo(savedTicket.getBookingId());
    }

    @Test
    public void TicketRepository_shouldNotBeAbleTofindTicketbyShowtimeAndWrongSeatNumber() {
        // Given
        Ticket ticket = Ticket.builder().showtime(showtime).userId("uuid1").seatNumber("1").build();
        ticketRepository.save(ticket);

        // When
        Ticket foundTicket = ticketRepository.findByShowtimeAndSeatNumber(showtime,
                "2");

        // Then
        assertThat(foundTicket).isNull();

    }

}
