package com.example.demo.controller;

import com.example.demo.service.WordCounter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WordCounterController.class)
@AutoConfigureMockMvc(secure=false)
public class WordCounterControllerWebMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    WordCounter wordCounter;

    @Test
    public void testController() throws Exception {
        Map<String, Integer> result = new HashMap<>();
        result.put("test", 1);
        result.put("me", 1);

        when(wordCounter.count("test me")).thenReturn(result);

        MockHttpServletRequestBuilder request = post("/words/count")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("test me");

        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.test", is(1)))
                .andExpect(jsonPath("$.me", is(1)));
    }
}