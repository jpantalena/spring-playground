package com.example.demo;

import com.example.demo.model.Flight;
import com.example.demo.model.Ticket;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomSerializer extends StdSerializer<Flight> {

    public CustomSerializer() {
        this(null);
    }

    public CustomSerializer(Class<Flight> t) {
        super(t);
    }

    @Override
    public void serialize(
            Flight value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeFieldName("tickets");
        jgen.writeStartArray();
        for (Ticket ticket : value.getTickets()) {
            jgen.writeStartObject();
            jgen.writeObjectField("price", ticket.getPrice());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}

