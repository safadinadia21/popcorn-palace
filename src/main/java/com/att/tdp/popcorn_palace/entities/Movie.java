package com.att.tdp.popcorn_palace.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// import jakarta.validation.constraints.Max;
// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor // 👈 REQUIRED by JPA/Hibernate
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank(message = "Title is mandatory")
    @Column(nullable = false)
    private String title;

    // @NotBlank(message = "Genre is mandatory")
    @Column(nullable = false)
    private String genre;

    // @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(nullable = false)
    private Integer duration;

    // @Min(value = 0, message = "Rating must be at least 0")
    // @Max(value = 10, message = "Rating must be at most 10")
    @Column(nullable = false)
    private Double rating;

    // @Min(value = 1900, message = "Release year must be at least 1900")
    @Column(nullable = false)
    private Integer releaseYear;

}
