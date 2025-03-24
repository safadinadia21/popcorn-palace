package com.att.tdp.popcorn_palace.Exceptions.movieException;

import java.time.Year;

public class InvalidReleaseYearException extends MovieException {
    public InvalidReleaseYearException(int year) {
        super("Invalid release year: " + year + ". Must be before our current year : " + Year.now().getValue());
    }

}
