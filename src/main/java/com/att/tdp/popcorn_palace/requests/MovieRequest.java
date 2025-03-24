package com.att.tdp.popcorn_palace.requests;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating must not exceed 10")
    private Double rating;

    @NotNull(message = "Release year is required")
    @Min(value = 1900, message = "Release year must be no earlier than 1900")
    private Integer releaseYear;
}