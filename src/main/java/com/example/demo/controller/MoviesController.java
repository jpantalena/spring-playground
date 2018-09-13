package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.repository.IMovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final IMovieRepository repository;

    public MoviesController(IMovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Movie> all() {
        Iterable<Movie> movies = repository.findAll();

        movies.forEach(movie -> {

            movie.add(linkTo(MoviesController.class)
                    .slash(movie.getMovieId())
                    .withSelfRel());

            movie.add(linkTo(methodOn(TrailerController.class)
                    .findTrailer(movie.getMovieId())).withRel("trailer"));
        });

        return movies;
    }
}
