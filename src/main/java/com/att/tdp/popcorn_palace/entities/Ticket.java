package com.att.tdp.popcorn_palace.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor // 👈 REQUIRED by JPA/Hibernate
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @NotBlank
    @Column(nullable = false)
    private String seatNumber;

    @NotBlank
    @Column(nullable = false)
    private String userId;

}
