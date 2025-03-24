package com.att.tdp.popcorn_palace.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.requests.MovieRequest;
import com.att.tdp.popcorn_palace.services.MovieService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody @Valid MovieRequest request) {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .rating(request.getRating())
                .duration(request.getDuration())
                .releaseYear(request.getReleaseYear())
                .build();
        return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.OK);
    }

    @PostMapping("/update/{title}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String title, @Valid @RequestBody MovieRequest request) {
        Movie updatedMovie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .duration(request.getDuration())
                .rating(request.getRating())
                .releaseYear(request.getReleaseYear())
                .build();
        return new ResponseEntity<>(movieService.updateMovie(title, updatedMovie), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{title}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String title) {
        movieService.deleteMovie(title);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getMethodName() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

}
