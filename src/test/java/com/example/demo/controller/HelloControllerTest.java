package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
@AutoConfigureMockMvc(secure=false)
public class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloWorld_returns200_andCorrectStringResponse() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(content().string("Hello from Spring!"))
                .andExpect(status().isOk());
    }
}