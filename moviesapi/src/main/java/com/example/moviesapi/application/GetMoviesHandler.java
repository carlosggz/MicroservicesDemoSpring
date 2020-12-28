package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.domain.MoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class GetMoviesHandler implements Command.Handler<GetMoviesQuery, List<MovieDto>> {

    MoviesRepository repository;

    @Override
    public List<MovieDto> handle(GetMoviesQuery command) {
        return repository.getAll();
    }
}
