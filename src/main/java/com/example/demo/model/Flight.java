package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Flight {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("Departs")
    private Date departs;

    @JsonProperty("Tickets")
    private List<Ticket> tickets;
}
