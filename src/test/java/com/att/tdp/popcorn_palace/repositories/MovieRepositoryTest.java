package com.att.tdp.popcorn_palace.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.att.tdp.popcorn_palace.entities.Movie;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void MovieRepository_shouldBeAbleToSaveMovie() {
        // Given
        Movie movie = Movie.builder().title("The Matrix").genre("Action").duration(2).rating(3.2).releaseYear(1999)
                .build();

        // When
        Movie savedMovie = movieRepository.save(movie);

        // Then
        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getId()).isGreaterThan(0);
    }

    @Test
    public void MovieRepository_shouldBeAbleToFindMovieById() {
        // Given
        Movie movie = Movie.builder().title("The Matrix").genre("Action").duration(2).rating(3.2).releaseYear(1999)
                .build();
        Movie savedMovie = movieRepository.save(movie);

        // When
        Movie foundMovie = movieRepository.findById(savedMovie.getId()).orElse(null);

        // Then
        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getId()).isEqualTo(savedMovie.getId());
    }

    @Test
    public void MovieRepository_shouldReturnEmptyWhenMovieIdDoesNotExist() {
        // When
        Movie foundMovie = movieRepository.findById(999L).orElse(null);

        // Then
        assertThat(foundMovie).isNull();
    }

    @Test
    public void MovieRepository_shouldBeAbleToDeleteMovie() {
        // Given
        Movie movie = Movie.builder().title("The Matrix").genre("Action").duration(2).rating(3.2).releaseYear(1999)
                .build();
        Movie savedMovie = movieRepository.save(movie);

        // When
        movieRepository.delete(savedMovie);

        // Then
        assertThat(movieRepository.findById(savedMovie.getId())).isEmpty();
    }

    @Test
    public void MovieRepository_shouldBeAbleToFindAllMovies() {
        // Given
        Movie movie1 = Movie.builder().title("The Matrix").genre("Action").duration(2).rating(3.0).releaseYear(1999)
                .build();
        Movie movie2 = Movie.builder().title("The Matrix Reloaded").genre("Action").duration(2).rating(3.0)
                .releaseYear(2003).build();
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        // When
        List<Movie> movies = movieRepository.findAll();

        // Then
        assertThat(movies).isNotNull();
        assertThat(movies.size()).isEqualTo(2);
    }
}
