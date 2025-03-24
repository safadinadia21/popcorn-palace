package com.att.tdp.popcorn_palace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.popcorn_palace.entities.Showtime;

import com.att.tdp.popcorn_palace.requests.ShowtimeRequest;

import com.att.tdp.popcorn_palace.services.ShowtimeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;

    @PostMapping
    public ResponseEntity<Showtime> addShowtime(@Valid @RequestBody ShowtimeRequest request) {

        return new ResponseEntity<>(showtimeService.addShowtime(request), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long id, @Valid @RequestBody ShowtimeRequest request) {

        return new ResponseEntity<>(showtimeService.updateShowtime(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtime(@PathVariable Long id) {
        return new ResponseEntity<>(showtimeService.getShowtime(id), HttpStatus.OK);
    }

}
