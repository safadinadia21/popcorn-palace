package com.att.tdp.popcorn_palace.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeInUseException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeOverlappingException;
import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.entities.Showtime;
import com.att.tdp.popcorn_palace.repositories.MovieRepository;
import com.att.tdp.popcorn_palace.repositories.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repositories.TicketRepository;
import com.att.tdp.popcorn_palace.requests.ShowtimeRequest;

@Service
public class ShowtimeService {

        @Autowired
        private TicketRepository ticketRepository;
        @Autowired
        private ShowtimeRepository showtimeRepository;
        @Autowired
        private MovieRepository movieRepository;

        public Showtime addShowtime(ShowtimeRequest showtimeRequest) {
                Showtime showtime = validateShowtime(showtimeRequest);
                return showtimeRepository.save(showtime);
        }

        public Showtime updateShowtime(Long id, ShowtimeRequest showtimeRequest) {
                Showtime showtime = showtimeRepository.findById(id)
                                .orElseThrow(() -> new ShowtimeNotFoundException(id));
                Showtime showtimeDetails = validateShowtime(showtimeRequest);

                showtime.setMovie(showtimeDetails.getMovie());
                showtime.setTheater(showtimeDetails.getTheater());
                showtime.setStartTime(showtimeDetails.getStartTime());
                showtime.setEndTime(showtimeDetails.getEndTime());
                showtime.setPrice(showtimeDetails.getPrice());

                return showtimeRepository.save(showtime);
        }

        public void deleteShowtime(Long id) {
                Showtime showtime = showtimeRepository.findById(id)
                                .orElseThrow(() -> new ShowtimeNotFoundException(id));
                if (ticketRepository.findByShowtime(showtime).size() > 0) {
                        throw new ShowtimeInUseException(showtime.getId());
                }
                showtimeRepository.delete(showtime);
        }

        public Showtime getShowtime(Long id) {
                return showtimeRepository.findById(id)
                                .orElseThrow(() -> new ShowtimeNotFoundException(id));
        }

        private Showtime validateShowtime(ShowtimeRequest showtimeRequest) {

                Movie movie = movieRepository.findById(showtimeRequest.getMovieId())
                                .orElseThrow(() -> new MovieNotFoundException("id: " + showtimeRequest.getMovieId()));
                Showtime showtime = Showtime.builder()
                                .movie(movie)
                                .theater(showtimeRequest.getTheater())
                                .startTime(showtimeRequest.getStartTime())
                                .endTime(showtimeRequest.getEndTime())
                                .price(showtimeRequest.getPrice())
                                .build();

                List<Showtime> overLapList1 = showtimeRepository.findByTheaterAndStartTimeBetween(
                                showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
                List<Showtime> overLapList2 = showtimeRepository.findByTheaterAndEndTimeBetween(
                                showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
                List<Showtime> overLapList3 = showtimeRepository.findByTheaterAndStartTimeBeforeAndEndTimeAfter(
                                showtime.getTheater(), showtime.getStartTime(), showtime.getEndTime());
                if (!overLapList1.isEmpty() || !overLapList2.isEmpty() || !overLapList3.isEmpty()) {
                        throw new ShowtimeOverlappingException(
                                        showtime.getTheater());
                }

                return showtime;
        }

}
