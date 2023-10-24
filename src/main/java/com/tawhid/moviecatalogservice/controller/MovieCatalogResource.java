package com.tawhid.moviecatalogservice.controller;

import com.tawhid.moviecatalogservice.model.CatalogItem;
import com.tawhid.moviecatalogservice.model.Movie;
import com.tawhid.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/{userId}")
    List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("123", 4),
                new Rating("456", 3)
        );
        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        })
        .collect(Collectors.toList());
    }
}
