package com.att.tdp.popcorn_palace.Exceptions.movieException;

public class MovieNotFoundException extends MovieException {
    public MovieNotFoundException(String title) {
        super("Movie not found for " + title);

    }

}
