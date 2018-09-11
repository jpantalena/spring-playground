package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.model.MovieResponse;
import com.example.demo.model.OmdbMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class MovieService {

    private RestTemplate restTemplate;

    @Autowired
    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Movie> getMovies(String search) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://www.omdbapi.com")
                .queryParam("s", search)
                .queryParam("apikey", "37bd901e")
                .build()
                .toUri();

        ResponseEntity<MovieResponse> movieResponse = restTemplate
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<MovieResponse>() {
                });

        List<OmdbMovie> omdbMovies = movieResponse.getBody().getSearch();
        return omdbMovies.stream()
                .map(omdbMovie -> new Movie(omdbMovie.getTitle(), omdbMovie.getImdbID(), omdbMovie.getPoster(), Integer.valueOf(omdbMovie.getYear())))
                .collect(toList());
    }
}
