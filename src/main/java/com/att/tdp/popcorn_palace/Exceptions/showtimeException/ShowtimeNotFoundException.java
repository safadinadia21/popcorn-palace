package com.att.tdp.popcorn_palace.Exceptions.showtimeException;

public class ShowtimeNotFoundException extends ShowtimeException {
    public ShowtimeNotFoundException(Long id) {
        super("Showtime not found for id:" + id);
    }

}
