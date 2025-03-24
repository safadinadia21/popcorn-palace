package com.att.tdp.popcorn_palace.services;

import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.Exceptions.movieException.InvalidReleaseYearException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieAlreadyExistsException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieInUseException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieNotFoundException;
import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.repositories.MovieRepository;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;

    public Movie addMovie(Movie movie) {
        Movie existingMovie = movieRepository.findByTitle(movie.getTitle());
        if (existingMovie != null) {
            throw new MovieAlreadyExistsException(movie.getTitle());

        }
        validateMovieData(movie);
        return movieRepository.save(movie);
    }

    public Movie updateMovie(String title, Movie movieDetails) {
        Movie movie = movieRepository.findByTitle(title);
        if (movie == null) {
            throw new MovieNotFoundException(title);
        }
        validateMovieData(movieDetails);
        movie.setTitle(movieDetails.getTitle());
        movie.setGenre(movieDetails.getGenre());
        movie.setDuration(movieDetails.getDuration());
        movie.setRating(movieDetails.getRating());
        movie.setReleaseYear(movieDetails.getReleaseYear());

        return movieRepository.save(movie);
    }

    public void deleteMovie(String title) {
        Movie movie = movieRepository.findByTitle(title);
        if (movie == null) {
            throw new MovieNotFoundException(title);
        }
        if (showtimeRepository.findByMovie(movie).size() > 0) {
            throw new MovieInUseException(title);
        }
        movieRepository.delete(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    private void validateMovieData(Movie movie) {
        if (movie.getReleaseYear() > Year.now().getValue()) {
            throw new InvalidReleaseYearException(movie.getReleaseYear());
        }
    }

}
