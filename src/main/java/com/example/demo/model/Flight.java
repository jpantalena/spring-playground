package com.example.demo.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flight {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date departs;

    private List<Ticket> tickets;

    public Date getDeparts() {
        return departs;
    }

    @JsonGetter("Tickets")
    public List<Ticket> getTickets() {
        return tickets;
    }

    @JsonSetter("departs")
    public void setDeparts(Date departs) {
        this.departs = departs;
    }

    @JsonSetter("tickets")
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (departs != null ? !departs.equals(flight.departs) : flight.departs != null) return false;
        return tickets != null ? tickets.equals(flight.tickets) : flight.tickets == null;
    }

    @Override
    public int hashCode() {
        int result = departs != null ? departs.hashCode() : 0;
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "departs=" + departs +
                ", tickets=" + tickets +
                '}';
    }
}
