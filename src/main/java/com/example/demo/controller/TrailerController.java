package com.example.demo.controller;

import com.example.demo.model.Trailer;
import com.example.demo.repository.ITrailerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trailers")
public class TrailerController {
    private final ITrailerRepository trailerRepository;

    public TrailerController(ITrailerRepository trailerRepository) {
        this.trailerRepository = trailerRepository;
    }

    @GetMapping("/{id}")
    public Trailer findTrailer(@PathVariable Long id) {
        return this.trailerRepository.findById(id).orElse(null);
    }
}
