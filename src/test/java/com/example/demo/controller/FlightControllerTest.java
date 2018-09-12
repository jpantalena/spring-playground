package com.example.demo.controller;

import com.example.demo.CustomSerializer;
import com.example.demo.model.Flight;
import com.example.demo.model.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FlightController.class)
@AutoConfigureMockMvc(secure=false)
public class FlightControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void flight_returns200AndCorrectJSON() throws Exception {
        this.mockMvc.perform(get("/flights/flight")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Tickets[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$.Tickets[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$.Tickets[0].Price", is(200)));

    }

    @Test
    public void flights_returns200AndCorrectJSON() throws Exception {
        this.mockMvc.perform(get("/flights")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].Tickets[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$[0].Tickets[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$[0].Tickets[0].Price", is(200)))

                .andExpect(jsonPath("$[1].Tickets[0].Passenger.FirstName", is("Some other name")))
                .andExpect(jsonPath("$[1].Tickets[0].Price", is(400)));
    }

    @Test
    public void ticketTotal_returns200AndCorrectPriceSum_StringLiteral() throws Exception {
        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"tickets\": [{\"passenger\":null,\"price\": 200}]}");

        this.mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(200)));
    }

    @Test
    public void ticketTotal_returns200AndCorrectPriceSum_Jackson() throws Exception {
        List<Ticket> tickets = asList(
                Ticket.builder().price(100).build(),
                Ticket.builder().price(300).build(),
                Ticket.builder().price(100).build()
        );

        Flight requestBody = Flight.builder().tickets(tickets).build();

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Flight.class, new CustomSerializer());
        objectMapper.registerModule(module);

        String jsonRequestString = objectMapper.writeValueAsString(requestBody);

        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestString);

        this.mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(500)));
    }

    @Test
    public void ticketTotal_returns200AndCorrectPriceSum_Fixture() throws Exception {
        String jsonRequestString = getJSON("/data.json");

        MockHttpServletRequestBuilder request = post("/flights/tickets/total")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestString);

        this.mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(350)));
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}