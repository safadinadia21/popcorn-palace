package com.att.tdp.popcorn_palace.Exceptions.showtimeException;

public class ShowtimeInUseException extends ShowtimeException {
    public ShowtimeInUseException(Long id) {
        super("Showtime " + id + " is already in use.");
    }

}
