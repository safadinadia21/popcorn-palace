package com.att.tdp.popcorn_palace.controllers;

import com.att.tdp.popcorn_palace.Exceptions.movieException.InvalidReleaseYearException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieAlreadyExistsException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieInUseException;
import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieNotFoundException;
import com.att.tdp.popcorn_palace.entities.Movie;
import com.att.tdp.popcorn_palace.requests.MovieRequest;
import com.att.tdp.popcorn_palace.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MovieControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private MovieService movieService;

        @Autowired
        private ObjectMapper objectMapper;

        private MovieRequest movieRequest;
        private Movie movie;

        @BeforeEach
        public void setup() {
                movieRequest = MovieRequest.builder()
                                .title("Inception")
                                .genre("Sci-Fi")
                                .rating(9.0)
                                .duration(148)
                                .releaseYear(2010)
                                .build();

                movie = Movie.builder()
                                .title("Inception")
                                .genre("Sci-Fi")
                                .rating(9.0)
                                .duration(148)
                                .releaseYear(2010)
                                .build();
        }

        @Test
        public void MovieController_AddMovie_ReturnsMovie() throws Exception {
                when(movieService.addMovie(any(Movie.class))).thenReturn(movie);

                ResultActions response = mockMvc.perform(post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movieRequest)));

                response.andExpect(status().isOk())
                                .andExpect(jsonPath("$.title", CoreMatchers.is(movie.getTitle())))
                                .andExpect(jsonPath("$.genre", CoreMatchers.is(movie.getGenre())))
                                .andExpect(jsonPath("$.rating", CoreMatchers.is(movie.getRating())))
                                .andExpect(jsonPath("$.duration", CoreMatchers.is(movie.getDuration())))
                                .andExpect(jsonPath("$.releaseYear", CoreMatchers.is(movie.getReleaseYear())));
        }

        @Test
        public void MovieController_AddMovie_ThrowsMovieAlreadyExistsException() throws Exception {
                doThrow(new MovieAlreadyExistsException("Inception")).when(movieService).addMovie(any(Movie.class));

                ResultActions response = mockMvc.perform(post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movieRequest)));

                response.andExpect(status().isConflict());
        }

        @Test
        public void MovieController_AddMovie_ThrowsInvalidReleaseYearException() throws Exception {
                movieRequest.setReleaseYear(2100);
                doThrow(new InvalidReleaseYearException(2100)).when(movieService).addMovie(any(Movie.class));

                ResultActions response = mockMvc.perform(post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movieRequest)));

                response.andExpect(status().isBadRequest());
        }

        @Test
        public void MovieController_UpdateMovie_ReturnsUpdatedMovie() throws Exception {
                String originalTitle = "Inception";
                when(movieService.updateMovie(eq(originalTitle), any(Movie.class))).thenReturn(movie);

                ResultActions response = mockMvc.perform(post("/movies/update/{title}", originalTitle)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movieRequest)));

                response.andExpect(status().isOk())
                                .andExpect(jsonPath("$.title", CoreMatchers.is(movie.getTitle())))
                                .andExpect(jsonPath("$.genre", CoreMatchers.is(movie.getGenre())));
        }

        @Test
        public void MovieController_UpdateMovie_ThrowsMovieNotFoundException() throws Exception {
                doThrow(new MovieNotFoundException("Inception"))
                                .when(movieService).updateMovie(anyString(), any(Movie.class));

                ResultActions response = mockMvc.perform(post("/movies/update/{title}", "Inception")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movieRequest)));

                response.andExpect(status().isNotFound());
        }

        @Test
        public void MovieController_DeleteMovie_ReturnsOk() throws Exception {
                doNothing().when(movieService).deleteMovie("Inception");

                ResultActions response = mockMvc.perform(delete("/movies/delete/{title}", "Inception"));

                response.andExpect(status().isOk());
        }

        @Test
        public void MovieController_DeleteMovie_ThrowsMovieNotFoundException() throws Exception {
                doThrow(new MovieNotFoundException("Inception"))
                                .when(movieService).deleteMovie("Inception");

                ResultActions response = mockMvc.perform(delete("/movies/delete/{title}", "Inception"));

                response.andExpect(status().isNotFound());
        }

        @Test
        public void MovieController_DeleteMovie_ThrowsMovieInUseException() throws Exception {
                doThrow(new MovieInUseException("Inception"))
                                .when(movieService).deleteMovie("Inception");

                ResultActions response = mockMvc.perform(delete("/movies/delete/{title}", "Inception"));

                response.andExpect(status().isConflict());
        }

        @Test
        public void MovieController_GetAllMovies_ReturnsList() throws Exception {
                List<Movie> movieList = Arrays.asList(movie);
                when(movieService.getAllMovies()).thenReturn(movieList);

                ResultActions response = mockMvc.perform(get("/movies/all")
                                .contentType(MediaType.APPLICATION_JSON));

                response.andExpect(status().isOk())
                                .andExpect(jsonPath("$.size()", CoreMatchers.is(movieList.size())))
                                .andExpect(jsonPath("$[0].title", CoreMatchers.is(movie.getTitle())));
        }

}
