package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class WordCounter {
    public Map<String, Integer> count(String word) {
        String[] splits = word.split(" ");
        List<String> strings = Arrays.asList(splits);
        return strings.stream().collect(groupingBy(Function.identity(), summingInt(e -> 1)));
    }
}
