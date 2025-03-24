package com.att.tdp.popcorn_palace.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.entities.Showtime;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ShowtimeRepositoryTest {
        @Autowired
        private ShowtimeRepository showtimeRepository;
        @Autowired
        private MovieRepository movieRepository;
        private Movie movie;

        @BeforeEach
        public void setUp() {

                movie = movieRepository.save(Movie.builder()
                                .title("Inception")
                                .genre("Sci-Fi")
                                .duration(2)
                                .rating(4.2)
                                .releaseYear(2010)
                                .build());
        }

        @Test
        public void ShowtimeRepository_shouldBeAbleToSaveShowtime() {
                // Given
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater("IMAX")
                                .startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(3))
                                .price(15.0)
                                .build();

                // When
                Showtime savedShowtime = showtimeRepository.save(showtime);

                // Then
                assertThat(savedShowtime).isNotNull();
                assertThat(savedShowtime.getId()).isGreaterThan(0);
        }

        @Test
        public void ShowtimeRepository_shouldBeAbleToFindShowtimeById() {
                // Given
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater("IMAX")
                                .startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(3))
                                .price(15.0)
                                .build();
                Showtime savedShowtime = showtimeRepository.save(showtime);

                // When
                Showtime foundShowtime = showtimeRepository.findById(savedShowtime.getId()).orElse(null);

                // Then
                assertThat(foundShowtime).isNotNull();
                assertThat(foundShowtime.getId()).isEqualTo(savedShowtime.getId());
        }

        @Test
        public void ShowtimeRepository_shouldBeAbleToDeleteShowtime() {
                // Given
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater("IMAX")
                                .startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(3))
                                .price(15.0)
                                .build();
                Showtime savedShowtime = showtimeRepository.save(showtime);

                // When
                showtimeRepository.delete(savedShowtime);

                // Then
                assertThat(showtimeRepository.findById(savedShowtime.getId())).isEmpty();
        }

        // add 3 more tests here
        // that you added to the repository

        @Test
        public void ShowtimeRepository_shouldBeAbleToFindShowtimebyTheaterAndStartTimeBetween() {
                // Given
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater("IMAX")
                                .startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(3))
                                .price(15.0)
                                .build();
                showtimeRepository.save(showtime);

                // When
                Showtime foundShowtime = showtimeRepository.findByTheaterAndStartTimeBetween("IMAX",
                                LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1)).get(0);

                // Then
                assertThat(foundShowtime).isNotNull();
                assertThat(foundShowtime.getId()).isEqualTo(showtime.getId());
        }

        @Test
        public void ShowtimeRepository_shouldNotBeAbleToFindShowtimebyTheaterAndStartTimeBetween() {
                // Given
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater("IMAX")
                                .startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(3))
                                .price(15.0)
                                .build();
                showtimeRepository.save(showtime);

                // When
                List<Showtime> foundShowtime = showtimeRepository.findByTheaterAndStartTimeBetween("IMAX",
                                LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(1));

                // Then
                assertThat(foundShowtime).isEmpty();

        }

        @Test
        public void ShowtimeRepository_shouldBeAbleToFindShowtimebyTheaterAndEndTimeBetween() {
                // Given
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater("IMAX")
                                .startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(3))
                                .price(15.0)
                                .build();
                showtimeRepository.save(showtime);

                // When
                Showtime foundShowtime = showtimeRepository.findByTheaterAndEndTimeBetween("IMAX",
                                LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(4)).get(0);

                // Then
                assertThat(foundShowtime).isNotNull();
                assertThat(foundShowtime.getId()).isEqualTo(showtime.getId());
        }

        @Test
        public void ShowtimeRepository_shouldNotBeAbleToFindShowtimebyTheaterAndEndTimeBetween() {
                // Given
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater("IMAX")
                                .startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(3))
                                .price(15.0)
                                .build();
                showtimeRepository.save(showtime);

                // When
                List<Showtime> foundShowtime = showtimeRepository.findByTheaterAndEndTimeBetween("IMAX",
                                LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1));

                // Then
                assertThat(foundShowtime).isEmpty();
        }

        @Test
        public void ShowtimeRepository_shouldBeAbleToFindShowtimebyTheaterAndStartTimeBeforeAndEndTimeAfter() {
                // Given
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater("IMAX")
                                .startTime(LocalDateTime.now())
                                .endTime(LocalDateTime.now().plusHours(3))
                                .price(15.0)
                                .build();
                showtimeRepository.save(showtime);

                // When
                Showtime foundShowtime = showtimeRepository.findByTheaterAndStartTimeBeforeAndEndTimeAfter("IMAX",
                                LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)).get(0);

                // Then
                assertThat(foundShowtime).isNotNull();
                assertThat(foundShowtime.getId()).isEqualTo(showtime.getId());
        }

}
