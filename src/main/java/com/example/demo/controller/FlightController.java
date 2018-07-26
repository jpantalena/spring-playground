package com.example.demo.controller;

import com.example.demo.model.Flight;
import com.example.demo.model.Passenger;
import com.example.demo.model.Ticket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@RestController
@RequestMapping("/flights")
public class FlightController {
    @GetMapping("/flight")
    public Flight flight() {
        return Flight.builder()
                .departs(new Date())
                .tickets(singletonList(
                        Ticket.builder()
                                .passenger(Passenger.builder()
                                        .firstName("Some name")
                                        .lastName("Some other name")
                                        .build())
                                .price(200)
                                .build()))
                .build();
    }


    @GetMapping()
    public List<Flight> flights() {
        return asList(
                Flight.builder()
                        .departs(new Date())
                        .tickets(singletonList(Ticket.builder()
                                .passenger(Passenger.builder()
                                        .firstName("Some name")
                                        .lastName("Some other name")
                                        .build())
                                .price(200)
                                .build()))
                        .build(),
                Flight.builder()
                        .departs(new Date())
                        .tickets(singletonList(Ticket.builder()
                                .passenger(Passenger.builder()
                                        .firstName("Some other name")
                                        .build())
                                .price(400)
                                .build()))
                        .build()
        );
    }
}
