package com.att.tdp.popcorn_palace.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "showtime")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @NotNull
    private Movie movie;

    @Column(nullable = false)
    @NotBlank
    private String theater;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime startTime;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime endTime;

    @Column(nullable = false)
    @Positive(message = "Price must be greater than 0")
    private double price;

}
