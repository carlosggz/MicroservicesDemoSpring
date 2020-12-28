package com.example.moviesapi.controllers;

import an.awesome.pipelinr.Pipeline;
import com.example.moviesapi.application.GetMovieDetailsQuery;
import com.example.moviesapi.application.GetMoviesQuery;
import com.example.moviesapi.config.Constants;
import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MovieDto;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(Constants.MOVIES_BASE_URL)
public class MoviesController {

    private final Pipeline pipeline;

    @GetMapping("")
    public List<MovieDto> getAllMovies() {
        return pipeline.send(new GetMoviesQuery());
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id) {
        val result = pipeline.send(new GetMovieDetailsQuery(id));
        return new ResponseEntity<>(result.isEmpty() ? null : result.get(), result.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
