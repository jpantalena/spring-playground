package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticket {
    private Passenger passenger;
    private int price;


    @JsonGetter("Passenger")
    public Passenger getPassenger() {
        return passenger;
    }

    @JsonGetter("Price")
    public int getPrice() {
        return price;
    }

    @JsonSetter("passenger")
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @JsonSetter("price")
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (price != ticket.price) return false;
        return passenger != null ? passenger.equals(ticket.passenger) : ticket.passenger == null;
    }

    @Override
    public int hashCode() {
        int result = passenger != null ? passenger.hashCode() : 0;
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "passenger=" + passenger +
                ", price=" + price +
                '}';
    }
}
