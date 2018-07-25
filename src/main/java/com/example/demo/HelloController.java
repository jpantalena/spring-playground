package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jackpantalena on 7/25/18.
 */
@RestController
public class HelloController {
    @GetMapping("/")
    public String helloWorld() {
        return "Hello from Spring!";
    }
}
