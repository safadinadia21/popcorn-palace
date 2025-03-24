package com.att.tdp.popcorn_palace.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.att.tdp.popcorn_palace.Exceptions.movieException.InvalidReleaseYearException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieAlreadyExistsException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieInUseException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeInUseException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeOverlappingException;
import com.att.tdp.popcorn_palace.Exceptions.ticketException.TicketAlreadyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Loop over field errors and build a nice message
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        // Return 400 Bad Request with error details
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Movie Exceptions
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<String> handleMovieNotFound(MovieNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<String> handleMovieAlreadyExists(MovieAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidReleaseYearException.class)
    public ResponseEntity<String> handleInvalidReleaseYear(InvalidReleaseYearException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieInUseException.class)
    public ResponseEntity<String> handleMovieInUse(MovieInUseException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Showtime Exceptions
    @ExceptionHandler(ShowtimeNotFoundException.class)
    public ResponseEntity<String> handleShowtimeNotFound(ShowtimeNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShowtimeOverlappingException.class)
    public ResponseEntity<String> handleShowtimeOverlapping(ShowtimeOverlappingException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShowtimeInUseException.class)
    public ResponseEntity<String> handleShowtimeInUse(ShowtimeInUseException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Ticket Exceptions
    @ExceptionHandler(TicketAlreadyExistsException.class)
    public ResponseEntity<String> handleTicketAlreadyExists(TicketAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
