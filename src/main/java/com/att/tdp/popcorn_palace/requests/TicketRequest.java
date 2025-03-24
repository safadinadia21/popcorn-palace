package com.att.tdp.popcorn_palace.requests;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {

    @NotNull(message = "Showtime ID is required")
    private Long showtimeId;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotBlank(message = "User ID is required")
    private String userId;
}