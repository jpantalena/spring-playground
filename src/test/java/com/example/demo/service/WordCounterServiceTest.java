package com.example.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class WordCounterServiceTest {

    private WordCounter service;

    @Before
    public void setUp() throws Exception {
        service = new WordCounter();
    }

    @Test
    public void testCount() throws Exception {
        Map<String, Integer> actual = service.count("Help Me Test Thee");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("Help", 1);
        expected.put("Me", 1);
        expected.put("Test", 1);
        expected.put("Thee", 1);

        assertThat(actual, equalTo(expected));
    }
}