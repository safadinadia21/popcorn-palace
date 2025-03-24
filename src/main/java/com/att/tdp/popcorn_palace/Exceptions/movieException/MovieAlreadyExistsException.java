package com.att.tdp.popcorn_palace.Exceptions.movieException;

public class MovieAlreadyExistsException extends MovieException {
    public MovieAlreadyExistsException(String title) {
        super("Movie already exists with title: " + title);
    }
}
