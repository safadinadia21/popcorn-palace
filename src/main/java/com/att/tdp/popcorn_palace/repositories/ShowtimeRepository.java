package com.att.tdp.popcorn_palace.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.entities.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

        List<Showtime> findByMovie(Movie movie);

        List<Showtime> findByTheaterAndStartTimeBetween(String theater, LocalDateTime startTime,
                        LocalDateTime endTime);

        List<Showtime> findByTheaterAndEndTimeBetween(String theater, LocalDateTime startTime,
                        LocalDateTime endTime);

        List<Showtime> findByTheaterAndStartTimeBeforeAndEndTimeAfter(String theater, LocalDateTime startTime,
                        LocalDateTime endTime);

}
