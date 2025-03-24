package com.att.tdp.popcorn_palace.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.att.tdp.popcorn_palace.Exceptions.movieException.MovieNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeInUseException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeOverlappingException;
import com.att.tdp.popcorn_palace.entities.Showtime;
import com.att.tdp.popcorn_palace.requests.ShowtimeRequest;
import com.att.tdp.popcorn_palace.services.ShowtimeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = ShowtimeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ShowtimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShowtimeService showtimeService;

    @Autowired
    private ObjectMapper objectMapper;

    private ShowtimeRequest showtimeRequest;
    private Showtime mockShowtime;

    @BeforeEach
    void setup() {
        showtimeRequest = new ShowtimeRequest();
        showtimeRequest.setMovieId(1L);
        showtimeRequest.setTheater("Theater 1");
        showtimeRequest.setStartTime(LocalDateTime.of(2025, 5, 10, 18, 0));
        showtimeRequest.setEndTime(LocalDateTime.of(2025, 5, 10, 20, 0));
        showtimeRequest.setPrice(15.0);

        mockShowtime = Showtime.builder()
                .id(1L)
                .theater(showtimeRequest.getTheater())
                .startTime(showtimeRequest.getStartTime())
                .endTime(showtimeRequest.getEndTime())
                .price(showtimeRequest.getPrice())
                .build();
    }

    @Test
    void ShowtimeController_testAddShowtime_Success() throws Exception {
        Mockito.when(showtimeService.addShowtime(any(ShowtimeRequest.class))).thenReturn(mockShowtime);

        mockMvc.perform(post("/showtimes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(showtimeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.theater").value(showtimeRequest.getTheater()))
                .andExpect(jsonPath("$.price").value(showtimeRequest.getPrice()));
    }

    @Test
    void ShowtimeController_testUpdateShowtime_Success() throws Exception {
        Mockito.when(showtimeService.updateShowtime(eq(1L), any(ShowtimeRequest.class))).thenReturn(mockShowtime);

        mockMvc.perform(post("/showtimes/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(showtimeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.theater").value(showtimeRequest.getTheater()));
    }

    @Test
    void ShowtimeController_testGetShowtime_Success() throws Exception {
        Mockito.when(showtimeService.getShowtime(1L)).thenReturn(mockShowtime);

        mockMvc.perform(get("/showtimes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.theater").value(mockShowtime.getTheater()));
    }

    @Test
    void ShowtimeController_testDeleteShowtime_Success() throws Exception {
        Mockito.doNothing().when(showtimeService).deleteShowtime(1L);

        mockMvc.perform(delete("/showtimes/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void ShowtimeController_testAddShowtime_MovieNotFound() throws Exception {
        Mockito.when(showtimeService.addShowtime(any(ShowtimeRequest.class)))
                .thenThrow(new MovieNotFoundException("id: 1"));

        mockMvc.perform(post("/showtimes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(showtimeRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void ShowtimeController_testAddShowtime_Overlapping() throws Exception {
        Mockito.when(showtimeService.addShowtime(any(ShowtimeRequest.class)))
                .thenThrow(new ShowtimeOverlappingException("Theater 1"));

        mockMvc.perform(post("/showtimes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(showtimeRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void ShowtimeController_testUpdateShowtime_NotFound() throws Exception {
        Mockito.when(showtimeService.updateShowtime(eq(1L), any(ShowtimeRequest.class)))
                .thenThrow(new ShowtimeNotFoundException(1L));

        mockMvc.perform(post("/showtimes/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(showtimeRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void ShowtimeController_testDeleteShowtime_InUse() throws Exception {
        Mockito.doThrow(new ShowtimeInUseException(1L))
                .when(showtimeService).deleteShowtime(1L);

        mockMvc.perform(delete("/showtimes/delete/1"))
                .andExpect(status().isConflict());
    }

}
