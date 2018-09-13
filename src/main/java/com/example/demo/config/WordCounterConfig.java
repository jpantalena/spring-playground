package com.example.demo.config;

import com.example.demo.service.WordCounter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordCounterConfig {
    @Bean WordCounter wordCounter() {
        return new WordCounter();
    }
}
