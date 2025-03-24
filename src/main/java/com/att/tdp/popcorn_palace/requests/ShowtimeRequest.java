package com.att.tdp.popcorn_palace.requests;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeRequest {

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    @NotBlank(message = "Theater is required")
    private String theater;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @Positive(message = "Price must be greater than 0")
    private Double price;
}
