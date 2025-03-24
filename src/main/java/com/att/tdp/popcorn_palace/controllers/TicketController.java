package com.att.tdp.popcorn_palace.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.popcorn_palace.requests.TicketRequest;
import com.att.tdp.popcorn_palace.services.TicketService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/bookings")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> bookTicket(@RequestBody @Valid TicketRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("bookingId", ticketService.addTicket(request).getBookingId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
