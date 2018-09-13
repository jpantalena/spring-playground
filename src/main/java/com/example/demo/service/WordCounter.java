package com.example.demo.service;

import com.example.demo.config.WordCounterConfigProps;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

@Service
public class WordCounter {

    private WordCounterConfigProps props;

    public WordCounter(WordCounterConfigProps props) {
        this.props = props;
    }

    public Map<String, Integer> count(String word) {

        boolean caseSensitive = props.isCaseSensitive();
        List<String> skips = props.getWords().getSkip();

        String[] splits = word.split(" ");
        List<String> words = Arrays.asList(splits);

        if (!caseSensitive) {
            words = words.stream().map(String::toLowerCase).collect(toList());
        }

        if (!skips.isEmpty()) {
            words = words.stream().filter(w -> !skips.contains(w)).collect(toList());
        }

        return words.stream().collect(groupingBy(Function.identity(), summingInt(e -> 1)));
    }
}
