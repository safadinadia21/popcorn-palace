package com.att.tdp.popcorn_palace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.att.tdp.popcorn_palace.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title); // TODO
                                     // -
                                     // Implement
                                     // test
                                     // for
                                     // this
                                     // method
}
