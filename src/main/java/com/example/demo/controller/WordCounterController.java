package com.example.demo.controller;

import com.example.demo.service.WordCounter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WordCounterController {
    private WordCounter wordCounter;

    public WordCounterController(WordCounter wordCounter) {
        this.wordCounter = wordCounter;
    }

    @PostMapping("/words/count")
    public Map<String, Integer> postWord(@RequestBody String word) {
        return wordCounter.count(word);
    }
}
