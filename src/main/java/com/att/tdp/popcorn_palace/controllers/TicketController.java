package com.att.tdp.popcorn_palace.controllers;

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
    public ResponseEntity<String> bookTicket(@RequestBody @Valid TicketRequest request) {
        return new ResponseEntity<>("bookingId : " + ticketService.addTicket(request).getBookingId(), HttpStatus.OK);
    }

}
