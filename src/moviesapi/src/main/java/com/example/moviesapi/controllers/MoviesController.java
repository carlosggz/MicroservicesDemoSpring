package com.example.moviesapi.controllers;

import an.awesome.pipelinr.Pipeline;
import com.example.moviesapi.application.GetMovieDetailsQuery;
import com.example.moviesapi.application.GetMoviesInListQuery;
import com.example.moviesapi.application.GetMoviesQuery;
import com.example.moviesapi.application.LikeCommand;
import com.example.moviesapi.config.Constants;
import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MovieDto;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
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

    @PostMapping("{id}")
    public ResponseEntity<Boolean> like(@PathVariable String id) {
        val result = pipeline.send(new LikeCommand(id));
        return new ResponseEntity<>(result, result ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/search")
    public List<MovieDto> like(@RequestBody List<String> ids) { return pipeline.send(new GetMoviesInListQuery(ids)); }
}
