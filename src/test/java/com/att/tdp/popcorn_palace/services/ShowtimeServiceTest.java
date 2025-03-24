package com.att.tdp.popcorn_palace.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.entities.Showtime;
import com.att.tdp.popcorn_palace.repositories.MovieRepository;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repositories.TicketRepository;
import com.att.tdp.popcorn_palace.requests.ShowtimeRequest;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ShowtimeServiceTest {

        @Mock
        private ShowtimeRepository showtimeRepository;
        @Mock
        private MovieRepository movieRepository;
        @Mock
        private TicketRepository ticketRepository;

        @InjectMocks
        private ShowtimeService showtimeService;

        @Test
        public void ShowtimeService_testAddShowtime() {
                Movie movie = Movie.builder().id(1L).build();
                Showtime returnedShowtime = Showtime.builder().id(1L).movie(movie)
                                .theater("Theater 1").startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(2)).price(10)
                                .build();
                ShowtimeRequest showtime = ShowtimeRequest.builder().movieId(1L).theater("Theater 1")
                                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2)).price(10.0)
                                .build();

                when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

                when(showtimeRepository.save(any(Showtime.class))).thenReturn(returnedShowtime);

                Showtime savedShowtime = showtimeService.addShowtime(showtime);

                assertThat(savedShowtime).isNotNull();
                assertThat(savedShowtime.getTheater()).isEqualTo("Theater 1");
                assertThat(savedShowtime.getPrice()).isEqualTo(10);
        }

        @Test
        public void ShowtimeService_testUpdateShowtime() {
                Movie movie = Movie.builder().id(1L).build();
                // Existing showtime in DB
                Showtime existingShowtime = Showtime.builder().id(1L).movie(movie).theater("Old Theater")
                                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2)).price(10)
                                .build();

                // New data to update
                ShowtimeRequest updatedData = ShowtimeRequest.builder().movieId(movie.getId()).theater("New Theater")
                                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2)).price(10.0)
                                .build();
                // What the repository will return after saving the updated data
                Showtime returnShowtime = Showtime.builder().id(1L).movie(movie).theater("New Theater")
                                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2)).price(10)
                                .build();

                when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
                when(showtimeRepository.findById(1L)).thenReturn(Optional.of(existingShowtime));
                when(showtimeRepository.save(any(Showtime.class))).thenReturn(returnShowtime);

                Showtime updatedShowtime = showtimeService.updateShowtime(1L, updatedData);

                assertThat(updatedShowtime).isNotNull();
                assertThat(updatedShowtime.getTheater()).isEqualTo("New Theater");
        }

        @Test
        public void ShowtimeService_testDeleteShowtime() {
                Movie movie = Movie.builder().id(1L).build();
                // Existing showtime in DB
                Showtime existingShowtime = Showtime.builder().id(1L).movie(movie).theater("Old Theater")
                                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2)).price(10)
                                .build();

                when(showtimeRepository.findById(1L)).thenReturn(Optional.of(existingShowtime));
                when(ticketRepository.findByShowtime(existingShowtime)).thenReturn(List.of());

                showtimeService.deleteShowtime(1L);

        }

        @Test
        public void ShowtimeService_testGetShowtime() {
                Movie movie = Movie.builder().id(1L).build();
                Showtime showtime1 = Showtime.builder().id(1L).movie(movie).theater("Theater 1")
                                .startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2)).price(10)
                                .build();

                when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime1));
                Showtime showtime = showtimeService.getShowtime(1L);
                assertThat(showtime).isNotNull();
        }

}
