package com.att.tdp.popcorn_palace.Exceptions.movieException;

public class MovieInUseException extends MovieException {
    public MovieInUseException(String title) {
        super("Movie is in use: " + title);
    }

}
