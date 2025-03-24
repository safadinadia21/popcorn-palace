package com.att.tdp.popcorn_palace.Exceptions.showtimeException;

public class ShowtimeOverlappingException extends ShowtimeException {
    public ShowtimeOverlappingException(String theater) {
        super("There is an overlapping showtime in the same theater:" + theater);
    }

}
