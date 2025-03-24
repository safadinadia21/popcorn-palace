package com.att.tdp.popcorn_palace.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.repositories.MovieRepository;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

        @Mock
        private MovieRepository movieRepository;
        @Mock
        private ShowtimeRepository showtimeRepository;

        @InjectMocks
        private MovieService movieService;

        @Test
        public void MovieService_testAddMovie() {
                Movie returnedMovie = Movie.builder().title("The Matrix").genre("Action").releaseYear(1999)
                                .duration(136)
                                .rating(8.7).build();
                Movie movie = Movie.builder().title("The Matrix").genre("Action").releaseYear(1999).duration(136)
                                .rating(8.7)
                                .build();
                when(movieRepository.save(movie)).thenReturn(returnedMovie);

                Movie savedMovie = movieService.addMovie(movie);

                assertThat(savedMovie).isNotNull();
                assertThat(savedMovie.getTitle()).isEqualTo("The Matrix");
                assertThat(savedMovie.getGenre()).isEqualTo("Action");
                assertThat(savedMovie.getRating()).isEqualTo(8.7);

        }

        @Test
        public void MovieService_testUpdateMovie() {
                // Existing movie in DB
                Movie existingMovie = Movie.builder()
                                .id(1L)
                                .title("The Matrix")
                                .genre("Drama")
                                .releaseYear(2000)
                                .duration(120)
                                .rating(7.0)
                                .build();

                // New data to update
                Movie updatedData = Movie.builder()
                                .title("The Matrix")
                                .genre("Action")
                                .releaseYear(1999)
                                .duration(136)
                                .rating(8.7)
                                .build();

                // What the repository will return after saving
                Movie returnedMovie = Movie.builder()
                                .id(1L)
                                .title("The Matrix")
                                .genre("Action")
                                .releaseYear(1999)
                                .duration(136)
                                .rating(8.7)
                                .build();

                // Mock repository
                when(movieRepository.findByTitle("The Matrix")).thenReturn(existingMovie);
                when(movieRepository.save(any(Movie.class))).thenReturn(returnedMovie);

                // Call service
                Movie updatedMovie = movieService.updateMovie("The Matrix", updatedData);

                // Assert
                assertThat(updatedMovie).isNotNull();
                assertThat(updatedMovie.getTitle()).isEqualTo("The Matrix");
                assertThat(updatedMovie.getGenre()).isEqualTo("Action");
                assertThat(updatedMovie.getRating()).isEqualTo(8.7);
        }

        @Test
        public void MovieService_testDeleteMovie() {
                // Existing movie in DB
                Movie existingMovie = Movie.builder()
                                .id(1L)
                                .title("Old Title")
                                .genre("Drama")
                                .releaseYear(2000)
                                .duration(120)
                                .rating(7.0)
                                .build();

                // Mock repository
                when(showtimeRepository.findByMovie(existingMovie)).thenReturn(List.of());
                when(movieRepository.findByTitle("Old Title")).thenReturn(existingMovie);

                // Call service
                movieService.deleteMovie("Old Title");

                // Assert
                // No exception thrown
        }

        @Test
        public void MovieService_testGetAllMovies_noMovies() {
                // Mock repository
                when(movieRepository.findAll()).thenReturn(null);

                // Call service
                movieService.getAllMovies();

                // Assert
                // No exception thrown
        }

        @Test
        public void MovieService_testGetAllMovies() {
                // Existing movies in DB
                Movie movie1 = Movie.builder()
                                .id(1L)
                                .title("Old Title")
                                .genre("Drama")
                                .releaseYear(2000)
                                .duration(120)
                                .rating(7.0)
                                .build();
                Movie movie2 = Movie.builder()
                                .id(2L)
                                .title("Old Title 2")
                                .genre("Comedy")
                                .releaseYear(2001)
                                .duration(130)
                                .rating(6.5)
                                .build();

                // Mock repository
                when(movieRepository.findAll()).thenReturn(List.of(movie1, movie2));

                // Call service
                List<Movie> movies = movieService.getAllMovies();

                // Assert
                // No exception thrown
                assertThat(movies).isNotNull();
                assertThat(movies.size()).isEqualTo(2);
        }

}
