package com.example.demo.service;

import com.example.demo.config.WordCounterConfigProps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WordCounterServiceTest {

    private WordCounter service;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WordCounterConfigProps props;

    @Before
    public void setUp() throws Exception {
        service = new WordCounter(props);
    }

    @Test
    public void testCount() throws Exception {
        when(props.isCaseSensitive()).thenReturn(false);
        when(props.getWords().getSkip()).thenReturn(asList("me", "thee"));

        Map<String, Integer> actual = service.count("Help Me Test Thee");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("help", 1);
        expected.put("test", 1);

        assertThat(actual, equalTo(expected));
    }
}