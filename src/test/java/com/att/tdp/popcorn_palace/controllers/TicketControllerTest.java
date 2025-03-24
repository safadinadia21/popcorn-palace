package com.att.tdp.popcorn_palace.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.att.tdp.popcorn_palace.Exceptions.showtimeException.ShowtimeNotFoundException;
import com.att.tdp.popcorn_palace.Exceptions.ticketException.TicketAlreadyExistsException;
import com.att.tdp.popcorn_palace.entities.Showtime;
import com.att.tdp.popcorn_palace.entities.Ticket;
import com.att.tdp.popcorn_palace.requests.TicketRequest;
import com.att.tdp.popcorn_palace.services.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = TicketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    private TicketRequest ticketRequest;
    private Ticket mockTicket;

    @BeforeEach
    void setup() {
        ticketRequest = TicketRequest.builder()
                .showtimeId(1L)
                .seatNumber("A1")
                .userId("user1")
                .build();

        mockTicket = Ticket.builder()
                .showtime(Showtime.builder().id(1L).build())
                .seatNumber(ticketRequest.getSeatNumber())
                .userId(ticketRequest.getUserId())
                .build();
    }

    @Test
    void TicketController_testAddTicket_Success() throws Exception {
        Mockito.when(ticketService.addTicket(any(TicketRequest.class))).thenReturn(mockTicket);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void TicketController_testAddTicket_showtimeNotFound() throws Exception {
        Mockito.when(ticketService.addTicket(any(TicketRequest.class))).thenThrow(new ShowtimeNotFoundException(1L));

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void TicketController_testAddTicket_ticketAlreadyExists() throws Exception {
        Mockito.when(ticketService.addTicket(any(TicketRequest.class)))
                .thenThrow(new TicketAlreadyExistsException("1L", 1L, "A1"));

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketRequest)))
                .andExpect(status().isConflict());
    }

}
