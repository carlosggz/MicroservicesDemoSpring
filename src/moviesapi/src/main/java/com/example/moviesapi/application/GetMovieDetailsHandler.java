package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MoviesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class GetMovieDetailsHandler implements Command.Handler<GetMovieDetailsQuery, Optional<Movie>> {

    final MoviesRepository repository;

    @Override
    public Optional<Movie> handle(GetMovieDetailsQuery command) {
        return repository.getById(command.id);
    }
}
